package br.com.ceducarneiro.analisadorlexico;

public class Token {

    public TipoToken tipo;
    public String lexema;

    public Token(TipoToken tipo) {
        this.tipo = tipo;
    }

    public Token(TipoToken tipo, String lexema) {
        this.tipo = tipo;
        this.lexema = lexema;
    }

    @Override
    public String toString() {
        return lexema != null ? String.format("<%s,\"%s\">", tipo, lexema) : String.format("<%s>", tipo);
    }
}
