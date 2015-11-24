package br.com.ceducarneiro.analisadorlexico;

public class Main {

    private static Analisador lex;
    private static int waitingToken = 0;


    public static void main(String[] args) throws LexException, SinException {
        if (args.length == 1) {
            lex = new Analisador(args[0]);

            S(lex.nextToken(), false);
            System.out.println("Parece que talvez dependendo de como foi pode ser que suportamente em uma hipótese qualquer deu certo.");
        }
    }

    public static void S(Token token, boolean ignoreExp) throws LexException, SinException {
        if (token != null) {
            try {
                S_LINHA(token);
            } catch (SinException ex) {
                if (!ignoreExp) {
                    throw ex;
                } else {
                    lex.revertToken(ex.getBadToken());
                }
            }
        }
    }

    public static void S_LINHA(Token token) throws SinException, LexException {
            if (token != null && token.tipo == TipoToken.PAR_AB) {
            waitingToken++;
            EXP(lex.nextToken());
            waitingToken--;

            token = lex.nextToken();

            if (token != null && token.tipo == TipoToken.PAR_FE) {
                S(lex.nextToken(), waitingToken > 0);
            } else {
                throw new SinException(token, TipoToken.PAR_FE);
            }
        } else {
            EXP(token);
        }
    }

    public static void EXP(Token token) throws SinException, LexException {
        if (token != null) {
            if (token.tipo == TipoToken.NUM
                    || token.tipo == TipoToken.ID) {
                S(lex.nextToken(), waitingToken > 0);
            } else if (token.tipo == TipoToken.SOMA
                    || token.tipo == TipoToken.SUB
                    || token.tipo == TipoToken.MULT
                    || token.tipo == TipoToken.DIV) {
                S_LINHA(lex.nextToken());
            } else if (token.tipo == TipoToken.PAR_AB) {
                waitingToken++;
                EXP(lex.nextToken());
                waitingToken--;

                token = lex.nextToken();

                if (token != null && token.tipo == TipoToken.PAR_FE) {
                    S(lex.nextToken(), waitingToken > 0);
                } else {
                    throw new SinException(token, TipoToken.PAR_FE);
                }
            } else {
                throw new SinException(token, TipoToken.ID);
            }
        } else {
            throw new SinException(token, TipoToken.ID);
        }
    }

}
