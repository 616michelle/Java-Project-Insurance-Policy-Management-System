
public class MotorPolicy extends Policy {

    // Additional attributes
    private double vehicleValue;  //value of the car
    private int yearsNoClaims; // 

    // Default constructor
    public MotorPolicy() {
        super();
        this.vehicleValue = 0.0;
        this.yearsNoClaims = 0;
    }

    // Parameterised constructor
    public MotorPolicy(String policyId, String providerName, double coverageAmount, double vehicleValue, int yearsNoClaims) {
        super(policyId, providerName, coverageAmount);
        this.vehicleValue = vehicleValue;
        this.yearsNoClaims = yearsNoClaims;
    }

    // Getters
    public double getVehicleValue() { return vehicleValue; }
    public int getYearsNoClaims() { return yearsNoClaims; }

    // Setters
    public void setVehicleValue(double vehicleValue) {
        assert vehicleValue > 0 : "Vehicle value must be positive";
        this.vehicleValue = vehicleValue;
    }

    public void setYearsNoClaims(int yearsNoClaims) {
        assert yearsNoClaims >= 0 : "Years no claims cannot be negative";
        this.yearsNoClaims = yearsNoClaims;
    }

    // Premium calculation
    @Override
    public double monthlyPremium() {
        double basePremium = (getCoverageAmount() * 0.004) + (vehicleValue * 0.002);

        // 10% discount per year, but can't be more than 50%
        double discountRate = yearsNoClaims * 0.10;
        if (discountRate > 0.50) {
            discountRate = 0.50;
        }

        double premium = basePremium * (1 - discountRate);
        return premium;
    }

    // toString
    @Override
    public String toString() {
        return super.toString() + String.format(" | Type: Motor | Vehicle Value: £%.2f | Years No Claims: %d", vehicleValue, yearsNoClaims);
    }
}