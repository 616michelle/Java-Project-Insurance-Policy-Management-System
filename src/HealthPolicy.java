
public class HealthPolicy extends Policy { //inherets everything from policy
	private int age;
	private boolean preExisting;
	
	public HealthPolicy() {
		super();    
		this.age =0;  //extra attributes
		this.preExisting = false;
	}
	
	public HealthPolicy(String policyId, String providerName, double coverageAmount, int age, boolean preExisting) {
		super(policyId, providerName, coverageAmount);
		this.age = age;
		this.preExisting = preExisting;
	}
	
	public int getAge() { return age; }
	public boolean isPreExisting() { return preExisting; } 
	
	public void setAge(int age) {
		assert age >= 0 && age <= 120 : "Age must be between 0 and 120"; // this is a setter, && means both need to be true
		this.age = age;
	}
	
	public void setPreExisting(boolean preExisting) {
		this.preExisting = preExisting;
	}
	
	@Override
    public double monthlyPremium() {
        double ageFactor;
        if (age < 40) {
            ageFactor = 1.0;
        } else if (age <= 59) {
            ageFactor = 1.5;
        } else {
            ageFactor = 2.0;
        }
	
        double premium = getCoverageAmount() * 0.005 * ageFactor; // insurance increases with age

        if (preExisting) {
            premium = premium * 1.2; // if there's a condition, extra charge insurance
        }

        return premium;
	}
	
	@Override
    public String toString() {
        return super.toString() + String.format(" | Type: Health | Age: %d | Pre-Existing: %b", age, preExisting); //concluding sentence
    }
	
	
}