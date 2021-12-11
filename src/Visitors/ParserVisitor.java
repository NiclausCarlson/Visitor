package Visitors;

import Tokens.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ParserVisitor implements TokenVisitor {
    List<Token> ans;
    Stack<Token.TokenInterface> stack;

    public ParserVisitor() {
        this.ans = new ArrayList<>();
        this.stack = new Stack<>();
    }

    @Override
    public void visit(Token.Number number) {
        ans.add(new Token(number));
    }

    @Override
    public void visit(Token.Operation op) {
        while (true) {
            if (stack.isEmpty() ||
                    !(stack.peek() instanceof Token.Operation)) {
                break;
            }
            if (((Token.Operation) stack.peek()).getPriority() <= op.getPriority()) {
                break;
            }
            ans.add(new Token(stack.pop()));
        }
        stack.push(op);
    }

    @Override
    public void visit(Token.Bracket br) {
        if (br.isOpen()) {
            stack.push(br);
        } else {
            while (true) {
                if (stack.isEmpty()) {
                    throw new RuntimeException("Invalid expression");
                }
                var top = stack.pop();
                if (top instanceof Token.Bracket) {
                    var bracket = (Token.Bracket) (top);
                    if (!bracket.isOpen()) {
                        throw new RuntimeException("Invalid brackets order");
                    }
                    break;
                } else {
                    ans.add(new Token(top));
                }
            }
        }
    }

    @Override
    public void visit(Token.Eof eof) {
        while (!stack.isEmpty()) {
            ans.add(new Token(stack.pop()));
        }
    }

    public List<Token> visit(List<Token> tokens) {
        stack.clear();
        for (var item : tokens) {
            item.accept(this);
        }
        return ans;
    }
}
