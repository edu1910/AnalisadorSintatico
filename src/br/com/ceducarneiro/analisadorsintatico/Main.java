package br.com.ceducarneiro.analisadorsintatico;

import java.io.*;

public class Main {

    private static Analisador lex;
    private static BufferedWriter out;
    private static String comando = "";
    private static int bloco = 0;

    public static void main(String[] args) throws LexException, SinException {
        if (args.length == 2) {
            lex = new Analisador(args[0]);

            try {
                FileWriter writer = null;
                writer = new FileWriter(new File(args[1]));
                out = new BufferedWriter(writer);
                S(lex.nextToken());
                out.close();
                System.out.println("Compilado!");
            } catch (IOException e) {
                System.out.println("Não foi possível gerar o arquivo de saída.");
            }
        }
    }

    public static void S(Token token) throws LexException, SinException {
        MAIN(token);
        BLOCO(lex.nextToken());
    }

    public static void MAIN(Token token) throws LexException, SinException {
        if (token != null && token.tipo == TipoToken.MAIN) {
            token = lex.nextToken();
            if (token != null && token.tipo == TipoToken.ABRE_PAR) {
                token = lex.nextToken();
                if (token != null && token.tipo == TipoToken.FECHA_PAR) {
                    /* All right! */
                } else {
                    throw new SinException(token, TipoToken.FECHA_PAR);
                }
            } else {
                throw new SinException(token, TipoToken.ABRE_PAR);
            }
        } else {
            throw new SinException(token, TipoToken.MAIN);
        }
    }

    public static void BLOCO(Token token) throws LexException, SinException {
        if (token != null && token.tipo == TipoToken.ABRE_BLOCO) {
            LISTA_DECLARACOES(lex.nextToken());
            LISTA_COMANDOS(lex.nextToken());
            token = lex.nextToken();
            if (token != null && token.tipo == TipoToken.FECHA_BLOCO) {
                /* All right! */
            } else {
                throw new SinException(token, TipoToken.FECHA_BLOCO);
            }
        } else {
            throw new SinException(token, TipoToken.ABRE_BLOCO);
        }
    }

    public static void LISTA_DECLARACOES(Token token) throws LexException, SinException {
        if (token != null && token.tipo == TipoToken.TIPO_INT) {
            token = lex.nextToken();
            if (token != null && token.tipo == TipoToken.ID) {
                RESTO_LISTA_IDS(lex.nextToken());
                RESTO_LISTA_DECLARACOES(lex.nextToken());
            }
        } else {
            lex.revertToken(token);
        }
    }

    public static void RESTO_LISTA_IDS(Token token) throws LexException, SinException {
        if (token != null && token.tipo == TipoToken.VIRGULA) {
            LISTA_IDS(lex.nextToken());
        } else {
            lex.revertToken(token);
        }
    }

    public static void RESTO_LISTA_DECLARACOES(Token token) throws LexException, SinException {
        if (token != null && token.tipo == TipoToken.FIM_COMANDO) {
            LISTA_DECLARACOES(lex.nextToken());
        } else {
            throw new SinException(token, TipoToken.FIM_COMANDO);
        }
    }

    public static void LISTA_IDS(Token token) throws LexException, SinException {
        if (token != null && token.tipo == TipoToken.ID) {
            RESTO_LISTA_IDS(lex.nextToken());
        } else {
            throw new SinException(token, TipoToken.ID);
        }
    }

    public static void LISTA_COMANDOS(Token token) throws LexException, SinException {
        if (token != null) {
            if (token.tipo == TipoToken.ENTRADA || token.tipo == TipoToken.SAIDA
                    || token.tipo == TipoToken.ID || token.tipo == TipoToken.IF) {
                COMANDO(token);
                LISTA_COMANDOS(lex.nextToken());
            } else {
                lex.revertToken(token);
            }
        }
    }

    public static void COMANDO(Token token) throws LexException, SinException {
        if (token != null && token.tipo == TipoToken.ENTRADA) {
            ENTRADA(token);
        } else if (token != null && token.tipo == TipoToken.SAIDA) {
            SAIDA(token);
        } else if (token != null && token.tipo == TipoToken.IF) {
            IF(token);
        } else if (token != null && token.tipo == TipoToken.ID) {
            ATRIBUICAO(token);
        } else {
            throw new SinException(token, TipoToken.ENTRADA);
        }
    }

    public static void IF(Token token) throws LexException, SinException {
        if (token != null && token.tipo == TipoToken.IF) {
            comando = "if ";
            token = lex.nextToken();
            if (token != null && token.tipo == TipoToken.ABRE_PAR) {
                EXP_LOGICA(lex.nextToken());
                comando += ":";
                escreverComando(comando);
                token = lex.nextToken();
                if (token != null && token.tipo == TipoToken.FECHA_PAR) {
                    bloco++;
                    BLOCO(lex.nextToken());
                    bloco--;
                } else {
                    throw new SinException(token, TipoToken.FECHA_PAR);
                }
            } else {
                throw new SinException(token, TipoToken.ABRE_PAR);
            }
        } else {
            throw new SinException(token, TipoToken.IF);
        }
    }

    public static void EXP_LOGICA(Token token) throws LexException, SinException {
        if (token != null && token.tipo == TipoToken.ID) {
            comando += token.lexema;
            RESTO_EXP_LOGICA(lex.nextToken());
        } else {
            throw new SinException(token, TipoToken.ID);
        }
    }

    public static void RESTO_EXP_LOGICA(Token token) throws LexException, SinException {
        if (token != null && token.tipo == TipoToken.OP_MAIOR_QUE) {
            comando += " > ";
            token = lex.nextToken();
            if (token != null && token.tipo == TipoToken.ID) {
                /* All right! */
                comando += token.lexema;
            } else {
                throw new SinException(token, TipoToken.ID);
            }
        } else if (token != null && token.tipo == TipoToken.OP_MAIOR_IGUAL_QUE) {
            comando += " >= ";
            token = lex.nextToken();
            if (token != null && token.tipo == TipoToken.ID) {
                /* All right! */
                comando += token.lexema;
            } else {
                throw new SinException(token, TipoToken.ID);
            }
        } else if (token != null && token.tipo == TipoToken.OP_MENOR_QUE) {
            comando += " < ";
            token = lex.nextToken();
            if (token != null && token.tipo == TipoToken.ID) {
                /* All right! */
                comando += token.lexema;
            } else {
                throw new SinException(token, TipoToken.ID);
            }
        } else if (token != null && token.tipo == TipoToken.OP_MENOR_IGUAL_QUE) {
            comando += " <= ";
            token = lex.nextToken();
            if (token != null && token.tipo == TipoToken.ID) {
                /* All right! */
                comando += token.lexema;
            } else {
                throw new SinException(token, TipoToken.ID);
            }
        } else if (token != null && token.tipo == TipoToken.OP_IGUALDADE) {
            comando += " == ";
            token = lex.nextToken();
            if (token != null && token.tipo == TipoToken.ID) {
                /* All right! */
                comando += token.lexema;
            } else {
                throw new SinException(token, TipoToken.ID);
            }
        } else if (token != null && token.tipo == TipoToken.OP_DIFERENCA) {
            comando += " != ";
            token = lex.nextToken();
            if (token != null && token.tipo == TipoToken.ID) {
                /* All right! */
                comando += token.lexema;
            } else {
                throw new SinException(token, TipoToken.ID);
            }
        } else {
            throw new SinException(token, TipoToken.OP_MAIOR_QUE);
        }
    }

    public static void escreverComando(String comando) {
        try {
            for (int idx = 0; idx < bloco; idx++) {
                out.write("  ");
            }

            out.write(comando);
            out.newLine();
        } catch (IOException e) {
            System.out.println("Erro na escrita do arquivo de saída.");
        }
    }

    public static void ATRIBUICAO(Token token) throws LexException, SinException {
        if (token != null && token.tipo == TipoToken.ID) {
            comando = token.lexema;
            token = lex.nextToken();
            if (token.tipo == TipoToken.OP_ATRIBUICAO) {
                comando += " = ";
                EXP(lex.nextToken());
                token = lex.nextToken();
                if (token.tipo == TipoToken.FIM_COMANDO) {
                    /* All right! */
                    escreverComando(comando);
                } else {
                    throw new SinException(token, TipoToken.FIM_COMANDO);
                }
            } else {
                throw new SinException(token, TipoToken.OP_ATRIBUICAO);
            }
        } else {
            throw new SinException(token, TipoToken.ID);
        }
    }

    public static void EXP(Token token) throws LexException, SinException {
        SOMA(token);
    }

    public static void SOMA(Token token) throws LexException, SinException {
        if (token != null && token.tipo == TipoToken.ID) {
            comando += token.lexema;
            RESTO_SOMA(lex.nextToken());
        } else {
            throw new SinException(token, TipoToken.ID);
        }
    }

    public static void RESTO_SOMA(Token token) throws LexException, SinException {
        if (token != null && token.tipo == TipoToken.OP_SOMA) {
            token = lex.nextToken();
            comando += " + ";
            if (token != null && token.tipo == TipoToken.ID) {
                comando += token.lexema;
                RESTO_SOMA(lex.nextToken());
            } else {
                throw new SinException(token, TipoToken.ID);
            }
        } else {
            lex.revertToken(token);
        }
    }

    public static void ENTRADA(Token token) throws LexException, SinException {
        if (token != null && token.tipo == TipoToken.ENTRADA) {
            comando = "";
            token = lex.nextToken();
            if (token.tipo == TipoToken.ABRE_PAR) {
                PARAMS_ENTRADA(lex.nextToken());
                token = lex.nextToken();
                if (token.tipo == TipoToken.FECHA_PAR) {
                    token = lex.nextToken();
                    if (token.tipo == TipoToken.FIM_COMANDO) {
                        /* All right! */
                        comando += " = int(input())";
                        escreverComando(comando);
                    } else {
                        throw new SinException(token, TipoToken.FIM_COMANDO);
                    }
                } else {
                    throw new SinException(token, TipoToken.FECHA_PAR);
                }
            } else {
                throw new SinException(token, TipoToken.ABRE_PAR);
            }
        } else {
            throw new SinException(token, TipoToken.SAIDA);
        }
    }

    public static void PARAMS_ENTRADA(Token token) throws LexException, SinException {
        if (token != null && token.tipo == TipoToken.FMT_NUM) {
            token = lex.nextToken();
            if (token != null && token.tipo == TipoToken.VIRGULA) {
                token = lex.nextToken();
                if (token != null && token.tipo == TipoToken.ENDERECO) {
                    token = lex.nextToken();
                    if (token != null && token.tipo == TipoToken.ID) {
                        /* All right! */
                        comando = token.lexema;
                    } else {
                        throw new SinException(token, TipoToken.ID);
                    }
                } else {
                    throw new SinException(token, TipoToken.ENDERECO);
                }
            } else {
                throw new SinException(token, TipoToken.VIRGULA);
            }
        } else {
            throw new SinException(token, TipoToken.FMT_NUM);
        }
    }

    public static void SAIDA(Token token) throws LexException, SinException {
        if (token != null && token.tipo == TipoToken.SAIDA) {
            comando = "print(";

            token = lex.nextToken();
            if (token.tipo == TipoToken.ABRE_PAR) {
                PARAMS_SAIDA(lex.nextToken());
                token = lex.nextToken();
                if (token.tipo == TipoToken.FECHA_PAR) {
                    token = lex.nextToken();
                    if (token.tipo == TipoToken.FIM_COMANDO) {
                        /* All right! */
                        comando += ")";
                        escreverComando(comando);
                    } else {
                        throw new SinException(token, TipoToken.FIM_COMANDO);
                    }
                } else {
                    throw new SinException(token, TipoToken.FECHA_PAR);
                }
            } else {
                throw new SinException(token, TipoToken.ABRE_PAR);
            }
        } else {
            throw new SinException(token, TipoToken.SAIDA);
        }
    }

    public static void PARAMS_SAIDA(Token token) throws LexException, SinException {
        if (token != null && token.tipo == TipoToken.FMT_NUM) {
            token = lex.nextToken();
            if (token != null && token.tipo == TipoToken.VIRGULA) {
                token = lex.nextToken();
                if (token != null && token.tipo == TipoToken.ID) {
                    /* All right! */
                    comando += token.lexema;
                } else {
                    throw new SinException(token, TipoToken.ID);
                }
            } else {
                throw new SinException(token, TipoToken.VIRGULA);
            }
        } else if (token != null && token.tipo == TipoToken.FMT_STRING) {
            token = lex.nextToken();
            if (token != null && token.tipo == TipoToken.VIRGULA) {
                token = lex.nextToken();
                if (token != null && token.tipo == TipoToken.STRING) {
                    /* All right! */
                    comando += token.lexema;
                } else {
                    throw new SinException(token, TipoToken.STRING);
                }
            } else {
                throw new SinException(token, TipoToken.VIRGULA);
            }
        } else {
            throw new SinException(token, TipoToken.FMT_NUM);
        }
    }

}
