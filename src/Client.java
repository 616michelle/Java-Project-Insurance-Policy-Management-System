
public class Client implements Assessable {

    // Attributes
    private String clientId;
    private String clientName;
    private double monthlyIncome;
    private Policy[] policies; //all policies gonna be stored in an array
    private int policyCount;

    // Default values
    public Client() {
        this.clientId = "CL-0000";
        this.clientName = "Unknown";
        this.monthlyIncome = 0.0;
        this.policies = new Policy[5];
        this.policyCount = 0; 
    }

    // Parameterised constructor
    public Client(String clientId, String clientName, double monthlyIncome) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.monthlyIncome = monthlyIncome;
        this.policies = new Policy[5]; //policies get added separately
        this.policyCount = 0;
    }

    // Getters
    public String getClientId() { return clientId; }
    public String getClientName() { return clientName; }
    public double getMonthlyIncome() { return monthlyIncome; }
    public Policy[] getPolicies() { return policies; }
    public int getPolicyCount() { return policyCount; }

    // Setters
    public void setClientId(String clientId) {
        assert clientId != null : "Please enter Client ID"; //you must enter client id
        this.clientId = clientId;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setMonthlyIncome(double monthlyIncome) { //conditions to proceed, you must be earning
        if (monthlyIncome < 0) {
            throw new IllegalArgumentException("You are in debt. No insurance for you :/ ");
        } else if (monthlyIncome == 0) {
            throw new IllegalArgumentException("Your income is zero. No insurance for you");
        }
        this.monthlyIncome = monthlyIncome;
    }

    // Validate ID format (must be CL-XXXX where X is a digit)
    private boolean isValidClientId(String id) {
        return id.matches("CL-\\d{4}"); //make sures the id is 4 digits
    }

    // Validate policy ID format (must be PL-XXXX where X is a digit)
    private boolean isValidPolicyId(String id) {
        return id.matches("PL-\\d{4}");
    }

    // Add a policy to the client
    public void addPolicy(Policy policy) throws Exception {
        // Check policy ID format
        if (!isValidPolicyId(policy.getPolicyId())) {
            throw new Exception("Invalid policy ID format: " + policy.getPolicyId() + ". Must be PL-XXXX.");
        }

        // Check for duplicate policy ID
        for (int i = 0; i < policyCount; i++) {
            if (policies[i].getPolicyId().equals(policy.getPolicyId())) { //all IDs have to be unique
                throw new Exception("Duplicate policy ID: " + policy.getPolicyId());
            }
        }

        // Check coverage rule - max 5 policies
        if (policyCount >= 5) {
            throw new Exception("Coverage rule limit reached, client already has 5 policies.");
        }

        // Check coverage rule - total premium must not exceed 40% of income
        double totalPremium = getTotalPremium() + policy.monthlyPremium();
        if (totalPremium > monthlyIncome * 0.40) {
            throw new Exception("Coverage rule violated, total premiuim would exceed 40% of monthly income ");
        }

        // After all conditions checked, new policy is stored in the next slot of the array
        policies[policyCount] = policy;
        policyCount++;
    }

    // Remove a policy by index
    public void removePolicy(int index) throws Exception {
        if (index < 0 || index >= policyCount) {
            throw new ArrayIndexOutOfBoundsException("Invalid policy index: " + index);
        }

        // Shift policies left to fill the gap
        for (int i = index; i < policyCount - 1; i++) {
            policies[i] = policies[i + 1];
        }
        policies[policyCount - 1] = null;
        policyCount--;
    }

    // Calculate total monthly premium across all policies
    public double getTotalPremium() {
        double total = 0;
        for (int i = 0; i < policyCount; i++) {
            total += policies[i].monthlyPremium();
        }
        return total;
    }

    // From Assessable interface - checks if client is within coverage limit
    @Override
    public boolean isWithinCoverageLimit() {
        return getTotalPremium() <= monthlyIncome * 0.40;
    }

    // From Assessable interface - prints a summary of the client
    @Override
    public void printSummary() {
        System.out.println("Client: " + clientId + " " + clientName + //layout of printed line
                " Income: £" + String.format("%.2f", monthlyIncome) + "/month" +
                " Policies: " + policyCount + "/5");

        String withinLimit = isWithinCoverageLimit() ? "YES" : "NO";
        System.out.println("Within coverage limit - " + withinLimit +
                " (Total premium: £" + String.format("%.2f", getTotalPremium()) +
                " / Limit: £" + String.format("%.2f", monthlyIncome * 0.40) + ")");

        // Print table header
        System.out.printf("%-10s %-10s %-15s %-15s %-15s%n",
                "PolicyID", "Type", "Provider", "Coverage", "MonthlyPremium"); //keeps the columns properly

        // Print each policy
        for (int i = 0; i < policyCount; i++) {
            Policy p = policies[i];
            String type = "";
            if (p instanceof HealthPolicy) type = "Health";
            else if (p instanceof MotorPolicy) type = "Motor";
            else if (p instanceof HomePolicy) type = "Home";

            System.out.printf("%-10s %-10s %-15s %-15s %-15s%n",
                    p.getPolicyId(),
                    type,
                    p.getproviderName(),
                    "£" + String.format("%.2f", p.getCoverageAmount()),
                    "£" + String.format("%.2f", p.monthlyPremium()));
        }
    }

    // toString
    @Override
    public String toString() {
        return String.format("Client: %s | Name: %s | Income: £%.2f/month | Policies: %d/5",
                clientId, clientName, monthlyIncome, policyCount);
    }
}