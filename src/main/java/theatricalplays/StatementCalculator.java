package theatricalplays;

import java.util.Map;

public class StatementCalculator {

    public double totalAmount;
    public int volumeCredits;

    public StatementCalculator() {
        this.totalAmount = 0;
        this.volumeCredits = 0;
    }

    public void calculate(Invoice invoice, Map<String, Play> plays) {
        for (Performance perf : invoice.performances) {
            Play play = plays.get(perf.playID);
            this.totalAmount += calculateAmountForPerformance(play, perf);
            this.volumeCredits += calculateVolumeCreditsForPerformance(play, perf);
        }

        // Check for loyalty discount
        Customer customer = invoice.customer;
        if (customer.canRedeemLoyaltyDiscount()) {
            this.totalAmount -= 15; 
            customer.redeemLoyaltyDiscount(); 
        }
    }

    public double calculateAmountForPerformance(Play play, Performance perf) {
        double thisAmount = 0;

        switch (play.type) {
            case TRAGEDY:
                thisAmount = 400.00;
                if (perf.audience > 30) {
                    thisAmount += 10.00 * (perf.audience - 30);
                }
                break;
            case COMEDY:
                thisAmount = 300.00;
                if (perf.audience > 20) {
                    thisAmount += 100.00 + 5.00 * (perf.audience - 20);
                }
                thisAmount += 3.00 * perf.audience;
                break;
        }
        return thisAmount;
    }

    public int calculateVolumeCreditsForPerformance(Play play, Performance perf) {
        int volumeCredits = Math.max(perf.audience - 30, 0);
        if (PlayType.COMEDY.equals(play.type)) volumeCredits += Math.floor(perf.audience / 5);
        return volumeCredits;
    }
}
