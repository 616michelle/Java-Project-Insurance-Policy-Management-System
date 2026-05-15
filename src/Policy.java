
public abstract class Policy {
	
	//Attributes = type of data the classes store
	private String policyId;
	private String providerName;
	private double coverageAmount; //can be decimal numbers
	
	// What the values look like by default to us
	public Policy() {
		this.policyId = "PL-XXXX";
		this.providerName = "Unkown";
		this.coverageAmount = 0.0;
	}
	
	public Policy(String policyId, String providerName, double coverageAmount) {
		this.policyId = policyId;
		this.providerName = providerName;
		this.coverageAmount = coverageAmount;
	}
	
	//getters - how other classes can read the attribute values
	public String getPolicyId() { return policyId; }
	public String getproviderName() { return providerName; }
	public double getCoverageAmount() { return coverageAmount; }
	
	// setters - can't be left blank by customer 
	public void setPolicyId(String policyId) {
		assert policyId != null : "Please enter your Policy ID.";
		this.policyId = policyId;
	}
	
	public void setCoverageAmount(double coverageAmount) {
		assert coverageAmount > 0 : "Coverage amount must be positive";
		this.coverageAmount = coverageAmount;
	}
	
	public abstract double monthlyPremium(); //no need to set default value as each policy is diff
	
	@Override
	public String toString() {
		return String.format("PolicyId: %s | Provider: %s | Coverage: £%.2f | Monthly Premium: £%.2f", //%.2f rounds to 2 decimal places
				policyId, providerName, coverageAmount, monthlyPremium());  // this like the final summary line, writing the values
	}
	
}
