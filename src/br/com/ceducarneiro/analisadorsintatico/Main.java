package br.com.ceducarneiro.analisadorsintatico;

public class Main {

    private static Analisador lex;
    private static int waitingToken = 0;


    public static void main(String[] args) throws LexException, SinException {
        if (args.length == 1) {
            lex = new Analisador(args[0]);

            S(lex.nextToken());
            System.out.println("Parece que talvez dependendo de como foi pode ser que suportamente em uma hipótese qualquer deu certo.");
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
            if (token.tipo == TipoToken.ENTRADA || token.tipo == TipoToken.SAIDA) {
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
        } else {
            throw new SinException(token, TipoToken.ENTRADA);
        }
    }

    public static void ENTRADA(Token token) throws LexException, SinException {
        if (token != null && token.tipo == TipoToken.ENTRADA) {
            token = lex.nextToken();
            if (token.tipo == TipoToken.ABRE_PAR) {
                PARAMS_ENTRADA(lex.nextToken());
                token = lex.nextToken();
                if (token.tipo == TipoToken.FECHA_PAR) {
                    token = lex.nextToken();
                    if (token.tipo == TipoToken.FIM_COMANDO) {
                        /* All right! */
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
            token = lex.nextToken();
            if (token.tipo == TipoToken.ABRE_PAR) {
                PARAMS_SAIDA(lex.nextToken());
                token = lex.nextToken();
                if (token.tipo == TipoToken.FECHA_PAR) {
                    token = lex.nextToken();
                    if (token.tipo == TipoToken.FIM_COMANDO) {
                        /* All right! */
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
