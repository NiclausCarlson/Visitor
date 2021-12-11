package Tokens;

import Visitors.TokenVisitor;

public class Token {
    public interface TokenInterface {
        void accept(TokenVisitor visitor);
    }

    public static abstract class Operation implements TokenInterface {
        public abstract int getPriority();

        @Override
        public void accept(TokenVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static abstract class Number implements TokenInterface {
        @Override
        public void accept(TokenVisitor visitor) {
            visitor.visit(this);
        }
        public abstract int get_value();
    }

    public static abstract class Bracket implements TokenInterface {
        @Override
        public void accept(TokenVisitor visitor) {
            visitor.visit(this);
        }

        public boolean isOpen() {
            return this instanceof Open;
        }
    }

    public static class Plus extends Operation {
        public String toString() {
            return "PLUS";
        }

        @Override
        public int getPriority() {
            return 0;
        }
    }

    public static class Minus extends Operation {
        public String toString() {
            return "MINUS";
        }

        @Override
        public int getPriority() {
            return 0;
        }
    }

    public static class Asterisk extends Operation {
        public String toString() {
            return "ASTERISK";
        }

        @Override
        public int getPriority() {
            return 1;
        }
    }

    public static class Divide extends Operation {
        public String toString() {
            return "DIVIDE";
        }

        @Override
        public int getPriority() {
            return 1;
        }
    }

    public static class Open extends Bracket {
        public String toString() {
            return "OPEN";
        }
    }

    public static class Closed extends Bracket {
        public String toString() {
            return "CLOSED";
        }
    }

    private static class Int extends Number {
        private final int value;

        public Int(int value) {
            this.value = value;
        }

        public String toString() {
            return "NUMBER(" + value + ")";
        }

        @Override
        public int get_value() {
            return value;
        }
    }

    public static class Eof implements TokenInterface {
        @Override
        public void accept(TokenVisitor visitor) {
            visitor.visit(this);
        }

        public String toString() {
            return "";
        }
    }

    private TokenInterface value;

    public Token() {
        this.value = new Eof();
    }

    public Token(char ch) {
        switch (ch) {
            case '+' -> value = new Plus();
            case '-' -> value = new Minus();
            case '*' -> value = new Asterisk();
            case '/' -> value = new Divide();
            case '(' -> value = new Open();
            case ')' -> value = new Closed();
        }
    }

    public Token(int value) {
        this.value = new Int(value);
    }

    public Token(TokenInterface otherValue) {
        this.value = otherValue;
    }

    public String toString() {
        return value.toString();
    }

    public void accept(TokenVisitor visitor) {
        value.accept(visitor);
    }
}

