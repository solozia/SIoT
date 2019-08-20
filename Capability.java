
public class Capability {

	String capName;
	double capValue;
	
	
	public Capability(String capName, double capValue) {
		super();
		this.capName = capName;
		this.capValue = capValue;
	}


	public String getCapName() {
		return capName;
	}


	public void setCapName(String capName) {
		this.capName = capName;
	}


	public double getCapValue() {
		return capValue;
	}


	public void setCapValue(double capValue) {
		this.capValue = capValue;
	}


	@Override
	public String toString() {
		return "Capability [capName=" + capName + ", capValue=" + capValue + "]";
	} 
//	
	
}
