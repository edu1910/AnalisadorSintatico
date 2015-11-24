package br.com.ceducarneiro.analisadorlexico;

public enum TipoToken {

    PAR_AB("PAR_AB"),
    PAR_FE("PAR_FE"),
    SOMA("SOMA"),
    SUB("SUB"),
    MULT("MULT"),
    DIV("DIV"),
    ID("ID"),
    NUM("NUM");

    String tipoStr;

    TipoToken(String tipoStr) {
        this.tipoStr = tipoStr;
    }

    @Override
    public String toString() {
        return tipoStr;
    }
}
