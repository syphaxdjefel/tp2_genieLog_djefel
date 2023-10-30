package theatricalplays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;

import static org.approvaltests.Approvals.verify ;
import static org.approvaltests.Approvals.verifyHtml;


public class StatementPrinterTests {

   @Test
    void exampleStatement() {
        Map<String, Play> plays = Map.of(
                "hamlet",  new Play("Hamlet", PlayType.TRAGEDY),
                "as-like", new Play("As You Like It", PlayType.COMEDY),
                "othello", new Play("Othello", PlayType.TRAGEDY));

               
    Customer customer = new Customer("BigCo", "12345", 160);


     Invoice invoice = new Invoice(customer, List.of(
                new Performance("hamlet", 55),
                new Performance("as-like", 35),
                new Performance("othello", 40)));
        Statement statement = new Statement(invoice, plays);

    // Obtenir la représentation en texte
    String result = statement.renderAsHtml();

        verifyHtml(result);
    }

    
}
