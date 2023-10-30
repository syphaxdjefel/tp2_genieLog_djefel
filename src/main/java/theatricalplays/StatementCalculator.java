package theatricalplays;

import java.util.Map;

/**
 * Classe StatementCalculator est responsable de la gestion des calculs
 * associés à un relevé, y compris la détermination du montant total et des crédits de volume.
 */
public class StatementCalculator {

    // Montant total pour le relevé.
    public double totalAmount;

    // Crédits totaux gagnés pour le relevé.
    public int volumeCredits;

    /**
     * Constructeur pour initialiser un nouvel objet StatementCalculator.
     */
    public StatementCalculator() {
        this.totalAmount = 0;
        this.volumeCredits = 0;
    }

    /**
     * Calcule le montant total et les crédits de volume pour une facture donnée et une liste de pièces.
     *
     * @param invoice La facture pour laquelle le calcul est effectué.
     * @param plays   La map des pièces associées à la facture.
     */
    public void calculate(Invoice invoice, Map<String, Play> plays) {
        for (Performance perf : invoice.performances) {
            Play play = plays.get(perf.playID);
            this.totalAmount += calculateAmountForPerformance(play, perf);
            this.volumeCredits += calculateVolumeCreditsForPerformance(play, perf);
        }

        // Vérifie si une réduction de fidélité est applicable et l'applique si c'est le cas.
        Customer customer = invoice.customer;
        if (customer.canRedeemLoyaltyDiscount()) {
            this.totalAmount -= 15; 
            customer.redeemLoyaltyDiscount(); 
        }
    }

    /**
     * Calcule le montant pour une performance donnée.
     *
     * @param play La pièce associée à la performance.
     * @param perf La performance pour laquelle le calcul est effectué.
     * @return Le montant calculé pour la performance.
     */
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

    /**
     * Calcule les crédits de volume pour une performance donnée.
     *
     * @param play La pièce associée à la performance.
     * @param perf La performance pour laquelle le calcul est effectué.
     * @return Les crédits de volume calculés pour la performance.
     */
    public int calculateVolumeCreditsForPerformance(Play play, Performance perf) {
        int volumeCredits = Math.max(perf.audience - 30, 0);
        if (PlayType.COMEDY.equals(play.type)) volumeCredits += Math.floor(perf.audience / 5);
        return volumeCredits;
    }
}
