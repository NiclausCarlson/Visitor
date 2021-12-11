package Visitors;

import Tokens.Tokenizer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CalcVisitorTest {
    private Tokenizer tokenizer;
    private ParserVisitor parserVisitor;
    private CalcVisitor calcVisitor;

    @Before
    public void init() {
        this.tokenizer = new Tokenizer();
        this.parserVisitor = new ParserVisitor();
        this.calcVisitor = new CalcVisitor();
    }

    public int calculate(String input) {
        var tokens = tokenizer.tokenize(input);
        var parsed = parserVisitor.visit(tokens);
        return calcVisitor.visit(parsed);
    }

    @Test
    public void simpleExpressions() throws Exception {
        Assert.assertEquals(1, calculate("1"));
        Assert.assertEquals(2, calculate("  2"));
        Assert.assertEquals(4, calculate("1+1+1+1"));
        Assert.assertEquals(4, calculate("1+ 1 + 1+             1"));
        Assert.assertEquals(8, calculate(" 2*2*2"));
        Assert.assertEquals(6, calculate("   2 +2* 2 "));
        Assert.assertEquals(35, calculate("      5+5              * 5+ 5"));
    }

    @Test
    public void bracketTest() throws Exception {
        Assert.assertEquals(1, calculate("( 1)"));
        Assert.assertEquals(2, calculate(" ( 1+ 1)"));
        Assert.assertEquals(3, calculate("1+( 1+1)"));
        Assert.assertEquals(3, calculate("( 1+1 ) +1"));
        Assert.assertEquals(8, calculate("( 2+     2)* 2 "));
        Assert.assertEquals(219, calculate(" (3+ 4)*(5+(6+ 7*(1+2)))+(( 11 + 12 - 34)+(2+ 2* 2))"));
    }
}