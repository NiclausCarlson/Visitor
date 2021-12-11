import Tokens.Tokenizer;
import Visitors.CalcVisitor;
import Visitors.ParserVisitor;
import Visitors.PrintVisitor;

public class Main {
    public static void main(String[] args) {
        String a = "2+2*2";
        Tokenizer t = new Tokenizer();
        ParserVisitor visitor = new ParserVisitor();
        var l = t.tokenize(a);
        PrintVisitor printVisitor = new PrintVisitor();
        printVisitor.visit(l);
        System.out.println();
        var ll = visitor.visit(l);
        printVisitor.visit(ll);
        System.out.println();
        CalcVisitor calcVisitor = new CalcVisitor();
        System.out.println(calcVisitor.visit(ll));
    }
}
