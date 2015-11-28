package br.com.ceducarneiro.analisadorsintatico;

public enum TipoToken {
    MAIN("MAIN"),
    ABRE_BLOCO("ABRE_BLOCO"),
    FECHA_BLOCO("FECHA_BLOCO"),
    ENTRADA("ENTRADA"),
    SAIDA("SAIDA"),
    IF("IF"),
    FMT_NUM("FMT_NUM"),
    FMT_STRING("FMT_STRING"),
    TIPO_INT("TIPO_INT"),
    ENDERECO("ENDERECO"),
    FIM_COMANDO("FIM_COMANDO"),
    VIRGULA("VIRGULA"),
    ABRE_PAR("ABRE_PAR"),
    FECHA_PAR("FECHA_PAR"),
    ID("ID"),
    OP_ATRIBUICAO("OP_ATRIBUICAO"),
    OP_SOMA("OP_SOMA"),
    OP_IGUALDADE("OP_IGUALDADE"),
    OP_DIFERENCA("OP_DIFERENCA"),
    OP_MAIOR_QUE("OP_MAIOR_QUE"),
    OP_MENOR_QUE("OP_MENOR_QUE"),
    OP_MAIOR_IGUAL_QUE("OP_MAIOR_IGUAL_QUE"),
    OP_MENOR_IGUAL_QUE("OP_MENOR_IGUAL_QUE"),
    STRING("STRING");

    String tipoStr;

    TipoToken(String tipoStr) {
        this.tipoStr = tipoStr;
    }

    @Override
    public String toString() {
        return tipoStr;
    }
}

