
public class HomePolicy extends Policy {

    // Additional attributes
    private double propertySqm;
    private boolean floodRisk;

    // Default constructor
    public HomePolicy() {
        super();
        this.propertySqm = 0.0;
        this.floodRisk = false;
    }

    // Parameterised constructor
    public HomePolicy(String policyId, String providerName, double coverageAmount, double propertySqm, boolean floodRisk) {
        super(policyId, providerName, coverageAmount);
        this.propertySqm = propertySqm;
        this.floodRisk = floodRisk;
    }

    // Getters
    public double getPropertySqm() { return propertySqm; }
    public boolean isFloodRisk() { return floodRisk; }

    // Setters
    public void setPropertySqm(double propertySqm) {
        assert propertySqm > 0 : "Property size must be positive";
        this.propertySqm = propertySqm;
    }

    public void setFloodRisk(boolean floodRisk) {
        this.floodRisk = floodRisk;
    }

    // Premium calculation
    @Override
    public double monthlyPremium() {
        double premium = getCoverageAmount() * 0.003 * (propertySqm / 50);

        if (floodRisk) {
            premium = premium * 1.3; // if the property has flood concerns, add a 30% extra
        }

        return premium;
    }

    // toString
    @Override
    public String toString() {
        return super.toString() + String.format(" | Type: Home | Property Sqm: %.2f | Flood Risk: %b", propertySqm, floodRisk);
    }
}