package Tokens;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Tokenizer {
    private final Set<Character> operationSymbols = Set.of('+', '-', '*', '/', '(', ')');

    private abstract static class State {
        public abstract void step(char ch);

        public Token flush() {
            return new Token();
        }
    }

    private class OperationState extends State {
        @Override
        public void step(char ch) {
            if (operationSymbols.contains(ch)) {
                tokens.add(new Token(ch));
            } else if (Character.isDigit(ch)) {
                curState = new NumberState();
                curState.step(ch);
            } else if (!Character.isWhitespace(ch)) {
                throw new IllegalArgumentException("Invalid symbol");
            }
        }
    }

    private class NumberState extends State {
        StringBuilder number = new StringBuilder();

        @Override
        public void step(char ch) throws NumberFormatException {
            if (Character.isDigit(ch)) {
                number.append(ch);
            } else {
                tokens.add(new Token(Integer.parseInt(number.toString())));
                curState = new OperationState();
                curState.step(ch);
            }
        }

        @Override
        public Token flush() {
            tokens.add(new Token(Integer.parseInt(number.toString())));
            return new Token();
        }
    }

    private final List<Token> tokens;
    private State curState;

    public Tokenizer() {
        this.tokens = new ArrayList<>();
        this.curState = new OperationState();
    }

    public List<Token> tokenize(String str) {
        tokens.clear();
        curState = new OperationState();
        for (char c : str.toCharArray()) {
            curState.step(c);
        }
        tokens.add(curState.flush());
        return tokens;
    }
}
