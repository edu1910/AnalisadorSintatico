package br.com.ceducarneiro.analisadorlexico;

public class SinException extends Exception {

    private Token badToken;
    private TipoToken tipoEsperado;

    public SinException(Token token, TipoToken tipoEsperado) {
        badToken = token;
        this.tipoEsperado = tipoEsperado;
    }

    public Token getBadToken() {
        return badToken;
    }

    @Override
    public String toString() {
        return String.format("Token inesperado: \"%s\". Esperado: \"%s\"",
                badToken != null ? badToken.tipo : "EOF",
                tipoEsperado != null ? tipoEsperado.toString(): "EOF");
    }

}
