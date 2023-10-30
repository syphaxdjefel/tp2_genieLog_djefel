package theatricalplays;


public class Customer {
    private String name;
    private String clientNumber;
    private int loyaltyPoints;

    public Customer(String name, String clientNumber, int initialLoyaltyPoints) {
        this.name = name;
        this.clientNumber = clientNumber;
        this.loyaltyPoints = initialLoyaltyPoints;
    }

    public String getName() {
        return name;
    }

    public String getClientNumber() {
        return clientNumber;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void addLoyaltyPoints(int points) {
        this.loyaltyPoints += points;
    }

    public boolean canRedeemLoyaltyDiscount() {
        return this.loyaltyPoints >= 150;
    }

    public void redeemLoyaltyDiscount() {
        if (canRedeemLoyaltyDiscount()) {
            this.loyaltyPoints -= 150;
        }
    }
}
