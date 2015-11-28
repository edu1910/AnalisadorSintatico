package br.com.ceducarneiro.analisadorsintatico;

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
                            token = new Token(TipoToken.ABRE_PAR);
                        else if (ch == ')')
                            token = new Token(TipoToken.FECHA_PAR);
                        else if (ch == '&')
                            token = new Token(TipoToken.ENDERECO);
                        else if (ch == ',')
                            token = new Token(TipoToken.VIRGULA);
                        else if (ch == '{')
                            token = new Token(TipoToken.ABRE_BLOCO);
                        else if (ch == '}')
                            token = new Token(TipoToken.FECHA_BLOCO);
                        else if (ch == ';')
                            token = new Token(TipoToken.FIM_COMANDO);
                        else if (ch == '+')
                            token = new Token(TipoToken.OP_SOMA);
                        else if (ch == '=')
                            estado = 25;
                        else if (ch == 'm')
                            estado = 1;
                        else if (ch == 'i')
                            estado = 5;
                        else if (ch == 's')
                            estado = 8;
                        else if (ch == 'p')
                            estado = 13;
                        else if (Character.isLetter(ch) || ch == '_')
                            estado = 19;
                        else if (ch == '"')
                            estado = 20;
                        else if (ch == '!')
                            estado = 27;
                        else if (ch == '<')
                            estado = 28;
                        else if (ch == '>')
                            estado = 29;
                        else if (!Character.isWhitespace(ch) && ch != 0)
                            throw new LexException(line, idx, ch);
                        break;

                    case 1:
                        lexema += ch;
                        readCh();

                        if (ch == 'a')
                            estado = 2;
                        else if (!Character.isLetterOrDigit(ch) && ch != '_') {
                            token = new Token(TipoToken.ID, lexema);

                            if (ch != 0 && idx > 0) {
                                idx--;
                            }
                        } else {
                            estado = 19;
                        }
                        break;
                    case 2:
                        lexema += ch;
                        readCh();

                        if (ch == 'i')
                            estado = 3;
                        else if (!Character.isLetterOrDigit(ch) && ch != '_') {
                            token = new Token(TipoToken.ID, lexema);

                            if (ch != 0 && idx > 0) {
                                idx--;
                            }
                        } else {
                            estado = 19;
                        }
                        break;
                    case 3:
                        lexema += ch;
                        readCh();

                        if (ch == 'n')
                            estado = 4;
                        else if (!Character.isLetterOrDigit(ch) && ch != '_') {
                            token = new Token(TipoToken.ID, lexema);

                            if (ch != 0 && idx > 0) {
                                idx--;
                            }
                        } else {
                            estado = 19;
                        }
                        break;
                    case 4:
                        lexema += ch;
                        readCh();

                        if (!Character.isLetterOrDigit(ch) && ch != '_') {
                            token = new Token(TipoToken.MAIN);

                            if (ch != 0 && idx > 0) {
                                idx--;
                            }
                        } else {
                            estado = 19;
                        }
                        break;
                    case 5:
                        lexema += ch;
                        readCh();

                        if (ch == 'n')
                            estado = 6;
                        else if (ch == 'f')
                            estado = 26;
                        else if (!Character.isLetterOrDigit(ch) && ch != '_') {
                            token = new Token(TipoToken.ID, lexema);

                            if (ch != 0 && idx > 0) {
                                idx--;
                            }
                        } else {
                            estado = 19;
                        }
                        break;
                    case 6:
                        lexema += ch;
                        readCh();

                        if (ch == 't')
                            estado = 7;
                        else if (!Character.isLetterOrDigit(ch) && ch != '_') {
                            token = new Token(TipoToken.ID, lexema);

                            if (ch != 0 && idx > 0) {
                                idx--;
                            }
                        } else {
                            estado = 19;
                        }
                        break;
                    case 7:
                        lexema += ch;
                        readCh();

                        if (!Character.isLetterOrDigit(ch) && ch != '_') {
                            token = new Token(TipoToken.TIPO_INT);

                            if (ch != 0 && idx > 0) {
                                idx--;
                            }
                        } else {
                            estado = 19;
                        }
                        break;
                    case 8:
                        lexema += ch;
                        readCh();

                        if (ch == 'c')
                            estado = 9;
                        else if (!Character.isLetterOrDigit(ch) && ch != '_') {
                            token = new Token(TipoToken.ID, lexema);

                            if (ch != 0 && idx > 0) {
                                idx--;
                            }
                        } else {
                            estado = 19;
                        }
                        break;
                    case 9:
                        lexema += ch;
                        readCh();

                        if (ch == 'a')
                            estado = 10;
                        else if (!Character.isLetterOrDigit(ch) && ch != '_') {
                            token = new Token(TipoToken.ID, lexema);

                            if (ch != 0 && idx > 0) {
                                idx--;
                            }
                        } else {
                            estado = 19;
                        }
                        break;
                    case 10:
                        lexema += ch;
                        readCh();

                        if (ch == 'n')
                            estado = 11;
                        else if (!Character.isLetterOrDigit(ch) && ch != '_') {
                            token = new Token(TipoToken.ID, lexema);

                            if (ch != 0 && idx > 0) {
                                idx--;
                            }
                        } else {
                            estado = 19;
                        }
                        break;
                    case 11:
                        lexema += ch;
                        readCh();

                        if (ch == 'f')
                            estado = 12;
                        else if (!Character.isLetterOrDigit(ch) && ch != '_') {
                            token = new Token(TipoToken.ID, lexema);

                            if (ch != 0 && idx > 0) {
                                idx--;
                            }
                        } else {
                            estado = 19;
                        }
                        break;
                    case 12:
                        lexema += ch;
                        readCh();

                        if (!Character.isLetterOrDigit(ch) && ch != '_') {
                            token = new Token(TipoToken.ENTRADA);

                            if (ch != 0 && idx > 0) {
                                idx--;
                            }
                        } else {
                            estado = 19;
                        }
                        break;
                    case 13:
                        lexema += ch;
                        readCh();

                        if (ch == 'r')
                            estado = 14;
                        else if (!Character.isLetterOrDigit(ch) && ch != '_') {
                            token = new Token(TipoToken.ID, lexema);

                            if (ch != 0 && idx > 0) {
                                idx--;
                            }
                        } else {
                            estado = 19;
                        }
                        break;
                    case 14:
                        lexema += ch;
                        readCh();

                        if (ch == 'i')
                            estado = 15;
                        else if (!Character.isLetterOrDigit(ch) && ch != '_') {
                            token = new Token(TipoToken.ID, lexema);

                            if (ch != 0 && idx > 0) {
                                idx--;
                            }
                        } else {
                            estado = 19;
                        }
                        break;
                    case 15:
                        lexema += ch;
                        readCh();

                        if (ch == 'n')
                            estado = 16;
                        else if (!Character.isLetterOrDigit(ch) && ch != '_') {
                            token = new Token(TipoToken.ID, lexema);

                            if (ch != 0 && idx > 0) {
                                idx--;
                            }
                        } else {
                            estado = 19;
                        }
                        break;
                    case 16:
                        lexema += ch;
                        readCh();

                        if (ch == 't')
                            estado = 17;
                        else if (!Character.isLetterOrDigit(ch) && ch != '_') {
                            token = new Token(TipoToken.ID, lexema);

                            if (ch != 0 && idx > 0) {
                                idx--;
                            }
                        } else {
                            estado = 19;
                        }
                        break;
                    case 17:
                        lexema += ch;
                        readCh();

                        if (ch == 'f')
                            estado = 18;
                        else if (!Character.isLetterOrDigit(ch) && ch != '_') {
                            token = new Token(TipoToken.ID, lexema);

                            if (ch != 0 && idx > 0) {
                                idx--;
                            }
                        } else {
                            estado = 19;
                        }
                        break;
                    case 18:
                        lexema += ch;
                        readCh();

                        if (!Character.isLetterOrDigit(ch) && ch != '_') {
                            token = new Token(TipoToken.SAIDA);

                            if (ch != 0 && idx > 0) {
                                idx--;
                            }
                        } else {
                            estado = 19;
                        }
                        break;
                    case 19:
                        lexema += ch;
                        readCh();

                        if (!Character.isLetterOrDigit(ch) && ch != '_') {
                            token = new Token(TipoToken.ID, lexema);

                            if (ch != 0 && idx > 0) {
                                idx--;
                            }
                        }
                        break;
                    case 20:
                        lexema += ch;
                        readCh();

                        if (ch == '%')
                            estado = 21;
                        else if (ch == '"') {
                            lexema += ch;
                            token = new Token(TipoToken.STRING, lexema);
                        } else if (ch != 0)
                            estado = 22;
                        else
                            throw new LexException(line, idx, ch);
                        break;
                    case 21:
                        lexema += ch;
                        readCh();

                        if (ch == 'd')
                            estado = 23;
                        else if (ch == 's')
                            estado = 24;
                        else if (ch != 0)
                            estado = 22;
                        else
                            throw new LexException(line, idx, ch);
                        break;
                    case 22:
                        lexema += ch;
                        readCh();

                        if (ch == '"') {
                            lexema += ch;
                            token = new Token(TipoToken.STRING, lexema);
                        } else if (ch == 0)
                            throw new LexException(line, idx, ch);
                        break;
                    case 23:
                        lexema += ch;
                        readCh();

                        if (ch == '"')
                            token = new Token(TipoToken.FMT_NUM);
                        else if (ch != 0)
                            estado = 22;
                        else
                            throw new LexException(line, idx, ch);
                        break;
                    case 24:
                        lexema += ch;
                        readCh();

                        if (ch == '"')
                            token = new Token(TipoToken.FMT_STRING);
                        else if (ch != 0)
                            estado = 22;
                        else
                            throw new LexException(line, idx, ch);
                        break;
                    case 25:
                        lexema += ch;
                        readCh();

                        if (ch == '=')
                            token = new Token(TipoToken.OP_IGUALDADE);
                        else {
                            token = new Token(TipoToken.OP_ATRIBUICAO);

                            if (ch != 0 && idx > 0) {
                                idx--;
                            }
                        }
                        break;
                    case 26:
                        lexema += ch;
                        readCh();

                        if (!Character.isLetterOrDigit(ch) && ch != '_') {
                            token = new Token(TipoToken.IF);

                            if (ch != 0 && idx > 0) {
                                idx--;
                            }
                        } else {
                            estado = 19;
                        }
                        break;
                    case 27:
                        lexema += ch;
                        readCh();

                        if (ch == '=')
                            token = new Token(TipoToken.OP_DIFERENCA);
                        else
                            throw new LexException(line, idx, ch);
                        break;
                    case 28:
                        lexema += ch;
                        readCh();

                        if (ch == '=')
                            token = new Token(TipoToken.OP_MENOR_IGUAL_QUE);
                        else {
                            token = new Token(TipoToken.OP_MENOR_QUE);

                            if (ch != 0 && idx > 0) {
                                idx--;
                            }
                        }
                        break;
                    case 29:
                        lexema += ch;
                        readCh();

                        if (ch == '=')
                            token = new Token(TipoToken.OP_MAIOR_IGUAL_QUE);
                        else {
                            token = new Token(TipoToken.OP_MAIOR_QUE);

                            if (ch != 0 && idx > 0) {
                                idx--;
                            }
                        }
                }
            }

            /*if (token == null)
                System.out.println("EOF");
            else
                System.out.println(token.tipo + " " + token.lexema);*/
        }

        nextToken = null;

        return token;
    }

    public void revertToken(Token token) {
        nextToken = token;
    }
}
