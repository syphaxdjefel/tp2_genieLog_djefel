package theatricalplays;
import java.util.Map;
public class Statement {
    private Invoice invoice;
    private Map<String, Play> plays;
    private StatementCalculator calculator;

    public Statement(Invoice invoice, Map<String, Play> plays) {
        this.invoice = invoice;
        this.plays = plays;
        this.calculator = new StatementCalculator();
        this.calculator.calculate(invoice, plays);
    }

    public String renderAsText() {
        return new TextStatementRenderer().render(invoice, plays);
    }

    public String renderAsHtml() {
        return new HtmlStatementRenderer().render(invoice, plays);
    }
}
