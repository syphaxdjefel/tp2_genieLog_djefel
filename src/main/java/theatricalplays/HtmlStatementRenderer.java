package theatricalplays;

import java.text.NumberFormat;
import java.util.Map;
import java.util.Locale;

/**
 * Classe HtmlStatementRenderer est responsable de la génération
 * d'un relevé au format HTML à partir d'une facture et d'un ensemble de pièces.
 */
public class HtmlStatementRenderer implements StatementRenderer {
    
    @Override
    public String render(Invoice invoice, Map<String, Play> plays) {
        StatementCalculator calculator = new StatementCalculator();
        calculator.calculate(invoice, plays);

        // Formatteur pour afficher les montants dans le format monétaire approprié.
        NumberFormat frmt = NumberFormat.getCurrencyInstance(Locale.US);
        StringBuilder result = new StringBuilder();

        // Récupère les détails du client depuis la facture.
        Customer customer = invoice.customer;

        // Commence la construction du document HTML.
        result.append("<html><head><title>Statement</title><style>\n" +
                "table, th, td {\n" +
                "border: 1px solid black;\n" +
                "}\n" +
                "th, td {\n" +
                "padding: 5px;\n" +
                "}\n" +
                "</style>\n" + "</head><body> \n");

        // Ajoute le nom du client et son ID.
        result.append(String.format("<h1>Statement for %s</h1>", customer.getName()));
        result.append(String.format("<p>Customer ID: %s</p>", customer.getClientNumber())); // Affiche l'ID du client.

        // Affiche les points de fidélité du client.
        result.append(String.format("<p>Remaining loyalty points: %s</p>", customer.getLoyaltyPoints()));

        // Débute la table pour lister les performances.
        result.append("<table><thead><tr><th>Play</th><th>Seats</th><th>Cost</th></tr></thead><tbody>");

        // Parcourt et affiche chaque performance dans la table.
        for (Performance perf : invoice.performances) {
            Play play = plays.get(perf.playID);
            result.append(String.format("<tr><td>%s</td><td>%s</td><td>%s</td></tr>",
                        play.name,
                        perf.audience,
                        frmt.format(calculator.calculateAmountForPerformance(play, perf))
            ));
        }

        // Termine la table.
        result.append("</tbody></table>");

        // Affiche le montant total dû et les crédits gagnés.
        result.append(String.format("<p>Amount owed is %s</p>", frmt.format(calculator.totalAmount)));
        result.append(String.format("<p>You earned %s credits</p>", calculator.volumeCredits));

        // Termine le document HTML.
        result.append("</body></html>");

        return result.toString();
    }
}
