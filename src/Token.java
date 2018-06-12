import java.util.ArrayList;

public class Token {
    enum Toktype {
        NUMBER, OP, PAREN
    }

    private Toktype ttype;
    private int value;
    private char tk;

    Toktype getTtype() { return ttype; }
    int getValue() { return value; }
    char getTk() { return tk; }

    // Constructor privat. Evita que es puguin construir objectes Token externament
    private Token() {
    }

    // Torna un token de tipus "NUMBER"
    static Token tokNumber(int value) {
        Token ae = new Token();
        ae.value = value; ae.ttype = Toktype.NUMBER;
        return ae;
    }

    // Torna un token de tipus "OP"
    static Token tokOp(char c) {
        Token ae = new Token();
        ae.tk = c; ae.ttype = Toktype.OP;
        return ae;
    }

    // Torna un token de tipus "PAREN"
    static Token tokParen(char c) {
        Token ae = new Token();
        ae.tk = c; ae.ttype = Toktype.PAREN;
        return ae;
    }

    // Mostra un token (conversió a String)
    public String toString() { return "" + this.ttype + this.value + this.tk;}

    // Mètode equals. Comprova si dos objectes Token són iguals
    public boolean equals(Object o) {
        Token x = (Token) o;
        return (this.tk == x.tk && this.value == x.value && this.ttype == x.ttype);
    }

    // A partir d'un String, torna una llista de tokens
    public static Token[] getTokens(String expr) {

        //Cream una ArrayList y un StringBuilder, la list per a enmmagatzemar el resultat final
        // i el StringBuilder per a acumular numeros.
        ArrayList<Token> ar = new ArrayList<>();
        StringBuilder acumulat = new StringBuilder();

        for (int i = 0; i < expr.length(); i++) {
            //Aquest bucle es repeteix per a cada caracter del String de entrada.
            char c = expr.charAt(i);
            //Guargam el caracter.
            if (c == ' '){
                ar = transform(ar,acumulat);acumulat = new StringBuilder();
                // En cas de que sigui un espai, cridam a la funcio que transforma
                // l'acumulat a integer i buidam l'acumulat.
            } else if (c == '(' || c == ')') {
                //Si el caracter es un parentesis cridam a transform
                // i afegim al resultat un token paren.
                ar = transform(ar,acumulat);acumulat = new StringBuilder();
                ar.add(tokParen(c));
            } else if (c == '+' || c == '-' || c == '*' || c == '/' ) {
                //Si el caracter es un operador cridam a transform
                // i afegim al resultat un token operador.
                ar = transform(ar,acumulat);acumulat = new StringBuilder();
                ar.add(tokOp(c));
            } else {
                //A aquesta opció nomes arribarem si no es ni parentesis ni operador
                // , per lo tant com es un numero simplement l'afegim al acumulat.
                acumulat.append(c);
            }
            if (i == expr.length() -1 && acumulat.length() > 0){
                //Si acabam el string d'entrada i l'acumulat te qualque contingut
                // , cridam a transform
                ar = transform(ar,acumulat);acumulat = new StringBuilder();
            }
        }
        return ar.toArray(new Token[ar.size()]);
    }

    static ArrayList<Token> transform(ArrayList<Token> ar, StringBuilder x) {
        //Aquesta funció,si el string que reb no es buid,
        // el transforma a integer i l'afegeix a la llista "ar".
        if ( x.length() > 0 ){
            int z = Integer.parseInt(x.toString());
            ar.add(tokNumber(z));
            return ar;
        }
        return ar;
    }
}
