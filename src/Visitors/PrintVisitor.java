package Visitors;

import Tokens.Token;

import java.util.List;

public class PrintVisitor implements TokenVisitor {
    @Override
    public void visit(Token.Number number) {
        System.out.print(number.toString() + " ");
    }

    @Override
    public void visit(Token.Operation op) {
        System.out.print(op.toString() + " ");
    }

    @Override
    public void visit(Token.Bracket br) {
        System.out.print(br.toString() + " ");
    }

    @Override
    public void visit(Token.Eof eof) {
        System.out.print(eof.toString() + " ");
    }

    public void visit(List<Token> tokens) {
        for (var item : tokens) {
            item.accept(this);
        }
    }
}
