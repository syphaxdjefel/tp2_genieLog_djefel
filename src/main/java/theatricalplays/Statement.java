package theatricalplays;

import java.util.Map;

/**
 * Classe Statement représente un relevé de pièces de théâtre.
 * Elle fournit des méthodes pour obtenir une représentation du relevé sous différents formats.
 */
public class Statement {

    // La facture associée à ce relevé.
    private Invoice invoice;

    // Map contenant les pièces de théâtre.
    private Map<String, Play> plays;

    // Calculateur pour déterminer les montants et les crédits.
    private StatementCalculator calculator;

    /**
     * Constructeur pour créer un nouvel objet Statement.
     *
     * @param invoice La facture à utiliser pour ce relevé.
     * @param plays   La map contenant les pièces de théâtre.
     */
    public Statement(Invoice invoice, Map<String, Play> plays) {
        this.invoice = invoice;
        this.plays = plays;
        this.calculator = new StatementCalculator();
        this.calculator.calculate(invoice, plays);
    }

    /**
     * Renvoie une représentation textuelle du relevé.
     *
     * @return Le relevé au format texte.
     */

    public String renderAsText() {
        return new TextStatementRenderer().render(invoice, plays);
    }

    /**
     * Renvoie une représentation HTML du relevé.
     *
     * @return Le relevé au format HTML.
     */
    
    public String renderAsHtml() {
        return new HtmlStatementRenderer().render(invoice, plays);
    }
}
