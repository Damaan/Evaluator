import java.util.LinkedList;
public class Evaluator {

    public static int calculate(String expr) {
        // Convertim l'string d'entrada en una llista de tokens
        Token[] tokens = Token.getTokens(expr);

        //La list result enmmagatzemara els tokens ja ordenats.
        //La list stack servira com a pila per als operadors.
        LinkedList<Token> result = new LinkedList<>();
        LinkedList<Token> stack = new LinkedList<>();

        for (int i = 0; i < tokens.length; i++) {
            //Aquest for s'executa per a cada token a l'array "tokens".

            if (tokens[i].getTtype() == Token.Toktype.NUMBER){
                //Si el token en iteracio es un numero l'afegeix al resultat final.
                result.add(tokens[i]);

            } else if (tokens[i].getTtype() == Token.Toktype.OP){

                int x = stack.size();
                //En cas de que el token sigui un operador,
                //primer mirar els numeros a dalt de tot de la pila,
                //si trobam un amb major o igual prioritat, el treim i afegim al resultat,
                //si no es dona aquesta condició simplement afegim el token al resultat.
                    for (int j = 0; j < x; j++) {
                        if (priority(tokens[i].getTk()) <= priority(stack.peek().getTk())) {
                            result.add(stack.pop());
                        }
                    }
                    stack.push(tokens[i]);

            } else if (tokens[i].getTtype() == Token.Toktype.PAREN){
                //Si es un parentesis, en cas que sigui obert l'afegim al stack.
                char c = tokens[i].getTk();
                if ( c == '(') {
                    stack.push(tokens[i]);
                }
                //Si es tancat, buidam l'stack d'operadors fins al parentesis obert
                //i els afegim al resultat, els parentesis son eliminats
                else {
                    while (stack.peek().getTk() != '(') {
                        result.add(stack.pop());
                    }
                    stack.pop();
                }
            }
        }
        //Si acaba el for, mentres stack no sigui buid, afegeix els operadors al resultat.
        while (!stack.isEmpty()){
                result.add(stack.pop());

        }
        //Convertim la llista a array de Tokens.
        tokens = new Token[result.size()];
        result.toArray(tokens);
//         Finalment, crida a calcRPN amb la nova llista de tokens i torna el resultat
        return calcRPN(tokens);
    }



    // Calcula el valor resultant d'avaluar la llista de tokens
    public static int calcRPN(Token[] list) {

        //Aquesta list s'encarregara de emmagatzemar els numeros i resultats.
        LinkedList<Integer> nums = new LinkedList<>();
        for (int i = 0; i < list.length; i++) {
            //Aquest for s'executara per a cada Token dins l'array d' entrada

            if (list[i].getTtype() == Token.Toktype.OP){
                //Si el token en iteració es un operador,extraiem dos numeros de la llista
                //, els operam com sigui necesari i afegim a la llista el resultat.
                int b = nums.remove();
                int a = nums.remove();
                char cal = list[i].getTk();
                nums.push(calcul(b,a,cal));
            }else{
                //En cas de que no sigui un operador simplement l'afegim al resultat.
                nums.push(list[i].getValue());
            }
        }
        int res = nums.getFirst();
        return res;
    }

    static int priority(char c){
        //Switch emprat per a indicar la prioritat del operador.
        switch (c){
            case '+' : case '-' : return 1;
            case '*' : case '/' : return 2;
            default: return 0;
        }
    }

    static int calcul(int b, int a, char c){
        switch (c){
            case '+' : return a + b;
            case '-' : return a - b;
            case '*' : return a * b;
            case '/' : return a / b;
            default:   return 0;
        }
    }
}
