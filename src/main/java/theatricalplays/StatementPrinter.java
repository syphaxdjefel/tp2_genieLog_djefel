package theatricalplays;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

public class StatementPrinter {

    public String print(Invoice invoice, Map<String, Play> plays) {
        double totalAmount = 0;  
        int volumeCredits = 0;  
        StringBuilder result = new StringBuilder();
        result.append(String.format("Statement for %s\n", invoice.customer));

        NumberFormat frmt = NumberFormat.getCurrencyInstance(Locale.US);

        for (Performance perf : invoice.performances) {
            Play play = plays.get(perf.playID);
            double thisAmount = calculateAmountForPerformance(play, perf);  

            volumeCredits += calculateVolumeCreditsForPerformance(play, perf);

            appendPerformanceDetails(result, play, thisAmount, perf, frmt);
            totalAmount += thisAmount;
        }

        appendFooterDetails(result, totalAmount, volumeCredits, frmt);
        return result.toString();
    }
    private double calculateAmountForPerformance(Play play, Performance perf) {  
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

    private int calculateVolumeCreditsForPerformance(Play play, Performance perf) {  
        int volumeCredits = Math.max(perf.audience - 30, 0);  
        if (play.type.equals(PlayType.COMEDY)) volumeCredits += Math.floor(perf.audience / 5);
        return volumeCredits;
    }

    private void appendPerformanceDetails(StringBuilder result, Play play, double thisAmount, Performance perf, NumberFormat frmt) {
        result.append(String.format("  %s: %s (%s seats)\n", play.name, frmt.format(thisAmount), perf.audience));
    }

    private void appendFooterDetails(StringBuilder result, double totalAmount, int volumeCredits, NumberFormat frmt) {
        result.append(String.format("Amount owed is %s\n", frmt.format(totalAmount)));
        result.append(String.format("You earned %s credits\n", volumeCredits));
    }



} 