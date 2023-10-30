package theatricalplays;

import java.text.NumberFormat;
import java.util.Map;
import java.util.Locale;

public class HtmlStatementRenderer implements StatementRenderer {
    @Override
    public String render(Invoice invoice, Map<String, Play> plays) {
        StatementCalculator calculator = new StatementCalculator();
        calculator.calculate(invoice, plays);

        NumberFormat frmt = NumberFormat.getCurrencyInstance(Locale.US);
        StringBuilder result = new StringBuilder();
                
        
        // Extract customer details from invoice
        Customer customer = invoice.customer;

        result.append("<html><head><title>Statement</title><style>\n"+ 
                "table, th, td {\n" +
                "border: 1px solid black;\n" +
                "}\n" +
                "th, td {\n" +
                "padding: 5px;\n" +
                "}\n" +
                "</style>\n" + "</head><body> \n");  
            
        result.append(String.format("<h1>Statement for %s</h1>", customer.getName()));
        result.append(String.format("<p>Customer ID: %s</p>", customer.getClientNumber())); // Display customer ID

        result.append(String.format("<p>Remaining loyalty points: %s</p>", customer.getLoyaltyPoints())); // Display loyalty points

        result.append("<table><thead><tr><th>Play</th><th>Seats</th><th>Cost</th></tr></thead><tbody>");
        
        for (Performance perf : invoice.performances) {
            Play play = plays.get(perf.playID);
            result.append(String.format("<tr><td>%s</td><td>%s</td><td>%s</td></tr>", 
                        play.name, 
                        perf.audience, 
                        frmt.format(calculator.calculateAmountForPerformance(play, perf))
            ));
        }
        
        result.append("</tbody></table>");
        result.append(String.format("<p>Amount owed is %s</p>", frmt.format(calculator.totalAmount)));
        result.append(String.format("<p>You earned %s credits</p>", calculator.volumeCredits));
    
        result.append("</body></html>");
        
        return result.toString();
    }
}
