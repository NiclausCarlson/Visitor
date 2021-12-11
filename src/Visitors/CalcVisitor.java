package Visitors;

import Tokens.Token;

import java.util.List;
import java.util.Stack;

public class CalcVisitor implements TokenVisitor {
    Stack<Integer> stack;

    public CalcVisitor() {
        this.stack = new Stack<>();
    }

    @Override
    public void visit(Token.Number number) {
        stack.push(number.get_value());
    }

    @Override
    public void visit(Token.Operation op) {
        if (stack.size() < 2) {
            throw new RuntimeException("Invalid token");
        }
        var second_number = stack.pop();
        var first_number = stack.pop();
        switch (op) {
            case Token.Plus p -> stack.push(first_number + second_number);
            case Token.Minus p -> stack.push(first_number - second_number);
            case Token.Asterisk p -> stack.push(first_number * second_number);
            case Token.Divide p -> stack.push(first_number / second_number);
            default -> throw new IllegalStateException("Unexpected value: " + op);
        }
    }

    @Override
    public void visit(Token.Bracket br) {

    }

    @Override
    public void visit(Token.Eof eof) {
    }

    public int visit(List<Token> tokens) {
        stack.clear();
        for (var t : tokens) {
            t.accept(this);
        }
        return stack.peek();
    }
}
