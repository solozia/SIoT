package SIoTtest;

public class evaluationMetricsList {
	
	double memory;
	double latency;
	String filePath;
	
	
	



	public evaluationMetricsList(double memory, double latency, String filePath) {
		super();
		this.memory = memory;
		this.latency = latency;
	}



	public double getMemory() {
		return memory;
	}

	public void setMemory(double memory) {
		this.memory = memory;
	}

	public double getLatency() {
		return latency;
	}

	public void setLatency(double latency) {
		this.latency = latency;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	@Override
	public String toString() {
		return "Evaluation Metrics [Memory=" + memory + ", Latency=" + latency + "]";
	}
	//

}
