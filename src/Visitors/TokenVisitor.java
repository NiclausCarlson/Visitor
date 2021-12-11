package Visitors;

import Tokens.Token;

import java.util.List;

public interface TokenVisitor {
    void visit(Token.Number number);

    void visit(Token.Operation op);

    void visit(Token.Bracket br);

    void visit(Token.Eof eof);
}
