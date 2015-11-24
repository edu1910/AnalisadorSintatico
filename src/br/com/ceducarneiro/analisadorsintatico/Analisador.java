package br.com.ceducarneiro.analisadorlexico;

import java.io.*;

public class Analisador {

    private BufferedReader in;
    private int line;
    private Token nextToken = null;

    private String code = null;

    private int idx = 0;
    private char ch;

    public void readCh() {
        if ((code != null) && (code.length() > idx)) {
            ch = code.charAt(idx++);
        } else {
            code = null;
            try {
                code = in.readLine();
            } catch (IOException e) { /* Empty */ }

            if (code != null) {
                idx = 0;
                line++;
                ch = '\n';
            } else {
                ch = 0;
            }
        }
    }

    public Analisador(String filePath) {
        try {
            FileReader reader = new FileReader(new File(filePath));
            in = new BufferedReader(reader);

            code = in.readLine();
            line = 1;
        } catch (Exception e) {

        }
    }

    public boolean readCh(char ch) {
        readCh();
        return (this.ch == ch);
    }

    public Token nextToken() throws LexException {
        Token token = this.nextToken;

        if (token == null) {
            int estado = 0;
            String lexema = "";
            ch = ' ';

            while (token == null && ch != 0) {
                switch (estado) {
                    case 0:
                        readCh();

                        if (ch == '(')
                            estado = 1;
                        else if (ch == ')')
                            estado = 2;
                        else if (ch == '+')
                            estado = 3;
                        else if (ch == '*')
                            estado = 4;
                        else if (ch == '-')
                            estado = 5;
                        else if (ch == '/')
                            estado = 6;
                        else if (Character.isLetter(ch) || ch == '_')
                            estado = 7;
                        else if (Character.isDigit(ch))
                            estado = 8;
                        else if (!Character.isWhitespace(ch) && ch != 0)
                            throw new LexException(line, idx, ch);
                        break;

                    case 1:
                        token = new Token(TipoToken.PAR_AB);
                        break;

                    case 2:
                        token = new Token(TipoToken.PAR_FE);
                        break;

                    case 3:
                        token = new Token(TipoToken.SOMA);
                        break;

                    case 4:
                        token = new Token(TipoToken.MULT);
                        break;

                    case 5:
                        token = new Token(TipoToken.SUB);
                        break;

                    case 6:
                        token = new Token(TipoToken.DIV);
                        break;

                    case 7:
                        lexema += ch;
                        readCh();

                        if (!Character.isLetterOrDigit(ch) && ch != '_') {
                            token = new Token(TipoToken.ID, lexema);

                            if (ch != 0) {
                                idx--;
                            }
                        }
                        break;

                    case 8:
                        lexema += ch;
                        readCh();

                        if (!Character.isDigit(ch)) {
                            token = new Token(TipoToken.NUM, lexema);

                            if (ch != 0) {
                                idx--;
                            }
                        }
                        break;
                }
            }

            if (token == null)
                System.out.println("EOF");
            else
                System.out.println(token.tipo + " " + token.lexema);
        }

        nextToken = null;

        return token;
    }

    public void revertToken(Token token) {
        nextToken = token;
    }
}
