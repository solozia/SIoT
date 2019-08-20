package ip;

public class voList {
	int voID;
	double priceRevScore;
	double durationRevScore;
	double reputationRevScore;
	double successRateRevScore;
	double availableRevScore;
	
	
	public voList(int voID, double priceRevScore, double durationRevScore, double reputationRevScore, double successRateRevScore, double availableRevScore){
		super();
		this.voID=voID;
		this.priceRevScore=priceRevScore;
		this.durationRevScore=durationRevScore;
		this.reputationRevScore=reputationRevScore;
		this.successRateRevScore=successRateRevScore;
		this.availableRevScore=availableRevScore;
	}
	
	
	public int getVoID() {
		return voID;
	}
	public void setVoID(int voID) {
		this.voID = voID;
	}
	public double getPriceRevScore() {
		return priceRevScore;
	}
	public void setPriceRevScore(double priceRevScore) {
		this.priceRevScore = priceRevScore;
	}
	public double getDurationRevScore() {
		return durationRevScore;
	}
	public void setDurationRevScore(double durationRevScore) {
		this.durationRevScore = durationRevScore;
	}
	public double getReputationRevScore() {
		return reputationRevScore;
	}
	public void setReputationRevScore(double reputationRevScore) {
		this.reputationRevScore = reputationRevScore;
	}
	public double getSuccessRateRevScore() {
		return successRateRevScore;
	}
	public void setSuccessRateRevScore(double successRateRevScore) {
		this.successRateRevScore = successRateRevScore;
	}
	public double getAvailableRevScore() {
		return availableRevScore;
	}
	public void setAvailableRevScore(double availableRevScore) {
		this.availableRevScore = availableRevScore;
	}
	

	@Override
	public String toString() {
		return "voList [voID=" + voID + ", priceRevScore=" + priceRevScore + ", durationRevScore=" + durationRevScore
				+ ", reputationRevScore=" + reputationRevScore + ", successRateRevScore=" + successRateRevScore
				+ ", availableRevScore=" + availableRevScore + "]";
	}

}
