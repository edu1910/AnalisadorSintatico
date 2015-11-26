package br.com.ceducarneiro.analisadorsintatico;

public class LexException extends Exception {

    private char badCh;
    private int line, column;

    public LexException(int line, int column, char ch) {
        badCh = ch;
        this.line = line;
        this.column = column;
    }

    @Override
    public String toString() {

        return String.format("Caractere inesperado: \"%s\" na linha %d coluna %d", badCh != 0 ? badCh : "EOF", line, column);
    }

}
