//
//
//package ip;
//
//public class voList {
//	int voID;
//	String qcName;
//	double qcValue;
//	
//	public voList(String qcName, double qcValue){
//		super();
//		this.qcName = qcName;
//		this.qcValue = qcValue;
////		System.out.println("QC2called>>>>>>22222222222222>>>>>>>>>>");
//
//	}
////	public voList(int voID, String qcName, double qcValue) {
////		super();
////		this.voID=voID;
////		this.qcName = qcName;
////		this.qcValue = qcValue;
////		
//////		System.out.println("QC3called>>>>>>>3333333333>>>>>>>>>");
////	}
//
//	public int getVOid() {
//		return voID;
//	}
//	public String getQCName() {
//		return qcName;
//	}
//	public double getQCValue() {
//		return qcValue;
//	}
//	
//	public void setVOid(int voID) {
//		this.voID = voID;
//	}
//	public void setQCName(String qcName) {
//		this.qcName = qcName;
//	}
//	public void setQCValue(double qcValue) {
//		this.qcValue = qcValue;
//	}
//
//
//	@Override
//	public String toString() {
//		return "voList [qcName=" + qcName + ", qcValue=" + qcValue + "]";
//	} 
//	
//
//}


//
package ip;

public class voList {
	int voID;
	double priceRevScore;
	double durationRevScore;
	double reputationRevScore;
	double successRateRevScore;
	double availableRevScore;
	double sumScore;
	
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
	public double getSumscore() {
		return sumScore;
	}
	public void setSumscore(double sumScore) {
		this.sumScore = sumScore;
	}

	@Override
	public String toString() {
		return "voList [voID=" + voID + ", priceRevScore=" + priceRevScore + ", durationRevScore=" + durationRevScore
				+ ", reputationRevScore=" + reputationRevScore + ", successRateRevScore=" + successRateRevScore
				+ ", availableRevScore=" + availableRevScore + "]";
	}

}
