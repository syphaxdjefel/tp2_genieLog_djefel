package theatricalplays;

import java.text.NumberFormat;
import java.util.Map;
import java.util.Locale;

public class TextStatementRenderer implements StatementRenderer {
    @Override
    public String render(Invoice invoice, Map<String, Play> plays) {
        StatementCalculator calculator = new StatementCalculator();
        calculator.calculate(invoice, plays);
        
        NumberFormat frmt = NumberFormat.getCurrencyInstance(Locale.US);
        StringBuilder result = new StringBuilder();
        result.append(String.format("Statement for %s (Customer ID: %s)\n", invoice.customer.getName(), invoice.customer.getClientNumber()));
        
        for (Performance perf : invoice.performances) {
            Play play = plays.get(perf.playID);
            result.append(String.format("  %s: %s (%s seats)\n", play.name, frmt.format(calculator.calculateAmountForPerformance(play, perf)), perf.audience));
        }

        result.append(String.format("Amount owed is %s\n", frmt.format(calculator.totalAmount)));
        result.append(String.format("You earned %s credits\n", calculator.volumeCredits));
        result.append(String.format("Remaining loyalty points: %s\n", invoice.customer.getLoyaltyPoints())); 

        return result.toString();
    }
    
}