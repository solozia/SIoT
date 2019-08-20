package ip;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class IPvoSelection {

	// private static final String CVOpath = "./datasetJPDC/listOfCVO2.csv";
	private static final String CVOpath = "./datasetJPDC/test/CVOtest.csv";
	private static final String VOpath = "./datasetJPDC/test/VO10000.csv";
	static String retreivedUserID = null;
	static String retreivedVOid = null;
	static String retreivedPOid = null;
	static String retreivedPriceScore = null;
	static String retreivedDurationScore = null;
	static String retreivedReputationScore = null;
	static String retreivedSuccessrateScore = null;
	static String retreivedAvailabilityScore = null;

//	HashMap<Integer, voList> voListt = new HashMap<>();
	
	// List<voList> VOlist =new ArrayList <>();

	public static void voSelection(int numberOfVO, double capValUpperBound, HashMap<String, Integer> userPref) {

		Map<Integer, Capability> caps = generator(numberOfVO, capValUpperBound);
		System.out.println("caps>>>>>>>>>>>>>>>>:  " + caps);
		HashMap<Integer, Capability> scoredVOprice = calculationNeg(caps, "price", userPref);
		HashMap<Integer, Capability> scoredVOreputation = calculationPos(caps, "reputation", userPref);
		HashMap<Integer, Capability> scoredVOexperience = calculationPos(caps, "experience", userPref);
		HashMap<Integer, Capability> scoredVOsuccessrate = calculationPos(caps, "successRate", userPref);
		HashMap<Integer, Capability> scoredVOavailability = calculationPos(caps, "availability", userPref);

		HashMap<Integer, Double> SummedVO = new HashMap<Integer, Double>();

		Set<Integer> keySet = scoredVOavailability.keySet();
		for (Integer key : keySet) {
			Capability cP = scoredVOprice.get(key);
			Capability cR = scoredVOreputation.get(key);
			Capability cE = scoredVOexperience.get(key);
			Capability cS = scoredVOsuccessrate.get(key);
			Capability cA = scoredVOavailability.get(key);

			double sum = cP.getCapValue() + cR.getCapValue() + cE.getCapValue() + cS.getCapValue() + cA.getCapValue();
			SummedVO.put(key, sum);
			// System.out.println("\n ppppppppppppppppppppppppppppppp
			// qqqqqqqqqqqqqqqqqqqqqqqqqq---"+key+"
			// "+"------"+sum+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		}

		int selectedVOkey = 0;
		double selectedVOval = (Collections.max(SummedVO.values()));
		// System.out.println(selectedVOval+"outooooooooooooooooooooo");
		for (Entry<Integer, Double> entry : SummedVO.entrySet()) {
			if (entry.getValue() == selectedVOval) {
				selectedVOkey = entry.getKey();
				// System.out.println(selectedVOkey+"From Inside the
				// looop===================");
			}
		}
		System.out.println("Selected VO=========" + selectedVOkey + "Value:" + selectedVOval);
	}

	public static int maxIndex(Map<Integer, Capability> caps, String capName) {
		int maxIndex = -1, minIndex = -1;
		Capability maxCap = new Capability(capName, -1);
		Capability minCap = new Capability(capName, 101);
		for (Entry<Integer, Capability> entry : caps.entrySet()) {
			if (entry.getValue().getCapValue() > maxCap.getCapValue()
					&& entry.getValue().getCapName().equals(capName)) {
				maxIndex = entry.getKey();
				maxCap = entry.getValue();
			}
			if (entry.getValue().getCapValue() < minCap.getCapValue()
					&& entry.getValue().getCapName().equals(capName)) {
				minIndex = entry.getKey();
				minCap = entry.getValue();
			}
		}
		return maxIndex;
	}

	public static int minIndex(Map<Integer, Capability> caps, String capName) {
		int maxIndex = -1, minIndex = -1;
		Capability maxCap = new Capability(capName, -1);
		Capability minCap = new Capability(capName, 101);
		for (Entry<Integer, Capability> entry : caps.entrySet()) {

			if (entry.getValue().getCapValue() < minCap.getCapValue()
					&& entry.getValue().getCapName().equals(capName)) {
				minIndex = entry.getKey();
				minCap = entry.getValue();
			}
		}
		return minIndex;
	}

	public static HashMap<Integer, Capability> calculationPos(Map<Integer, Capability> caps, String capName,
			HashMap<String, Integer> userPref) {
		HashMap<Integer, Capability> scoredVO = new HashMap<Integer, Capability>();
		double objectScorePos = 0.0;
		double w = 0.0;
		double maxValue = caps.get(maxIndex(caps, capName)).getCapValue();
		double minValue = caps.get(minIndex(caps, capName)).getCapValue();
		for (Entry<Integer, Capability> entry : caps.entrySet()) {
			if (maxValue - minValue != 0) {
				objectScorePos = (entry.getValue().capValue - minValue) / (maxValue - minValue);

				w = objectScorePos * (userPref.get(capName)); // multiplied by
																// userPref
				// System.out.println("\n Vij>>>>>>>>>>>>>>>>>>>>>>:
				// "+Vij+"wwwwww="+w+"\n ---userprefe: "+userPref.get("price"));

				Capability score = new Capability(capName, w);
				score.setCapName(capName);
				score.setCapValue(w);
				scoredVO.put(entry.getKey(), score);
			} else {
				objectScorePos = 1;
				w = objectScorePos * (userPref.get("price"));
				Capability score = new Capability(capName, w);
				score.setCapName(capName);
				score.setCapValue(w);
				// score.setVOid(voID);
				scoredVO.put(entry.getKey(), score);
			}
		}
		return scoredVO;
	}

	public static HashMap<Integer, Capability> calculationNeg(Map<Integer, Capability> caps, String capName,
			HashMap<String, Integer> userPref) {
		HashMap<Integer, Capability> scoredVO = new HashMap<Integer, Capability>();
		double objectScoreNeg = 0.0;
		double w = 0.0;
		double maxValue = caps.get(maxIndex(caps, capName)).getCapValue();
		double minValue = caps.get(minIndex(caps, capName)).getCapValue();
		for (Entry<Integer, Capability> entry : caps.entrySet()) {
			if (maxValue - minValue != 0) {
				objectScoreNeg = (maxValue - entry.getValue().getCapValue()) / (maxValue - minValue);

				w = objectScoreNeg * (userPref.get(capName)); // multiplied by
																// userPref
																// (weight)

				Capability score = new Capability(capName, w);
				score.setCapName(capName);
				score.setCapValue(w);
				scoredVO.put(entry.getKey(), score);
			} else {
				objectScoreNeg = 1;
				// System.out.println("\n VO score: "+Vij+" "+w);
				w = objectScoreNeg * (userPref.get("price"));
				Capability score = new Capability(capName, w);
				score.setCapName(capName);
				score.setCapValue(w);
				scoredVO.put(entry.getKey(), score);
			}
		}
		return scoredVO;
	}

	public static int randomVal(int upperbound) {
		Random randomGenerator = new Random();
		int randomVal = randomGenerator.nextInt(upperbound);

		return randomVal;
	}

	public static Map<Integer, Capability> generator(int numberOfVO, double upperBound) {
		Map<Integer, Capability> caps = new HashMap<Integer, Capability>();

		final String[] CapNames = { "price", "reputation", "availability", "successRate", "experience" };
		int Flag;
		Random randomGenerator = new Random();
		int flag = 0;
		for (int i = 1; i <= numberOfVO; i++) {
			int randomVal = randomGenerator.nextInt(100);
			double voCapVal = randomVal;
			if (flag < 5) {
				Flag = flag;

				Capability cap = new Capability(CapNames[flag], voCapVal);

				caps.put(i, cap);
				flag++;
			} else {
				flag = 0;
			}
		}

		// for(Entry entry : caps.entrySet()){
		// System.out.println("----------"+entry.toString());
		// }
		return caps;
	}

	// public static int lastCVOid() throws IOException{
	// FileInputStream in = new
	// FileInputStream("./datasetJPDC/listOfCVOtest.csv");
	// BufferedReader br = new BufferedReader(new InputStreamReader(in));
	//
	// String strLine = null, tmp;
	//
	// while ((tmp = br.readLine()) != null)
	// {
	// strLine = tmp;
	// }
	//
	// String lastLine = strLine;
	// System.out.println(lastLine);
	// System.out.println("----"+lastLine);
	// System.out.println(lastLine.substring(lastLine.indexOf("_")+1,
	// lastLine.indexOf(",")-1));
	// String CVO_lastIDstr=lastLine.substring(lastLine.indexOf("_")+1,
	// lastLine.indexOf(",")-1);
	// int CVO_lastIDint=Integer.valueOf(CVO_lastIDstr);
	// try {
	// in.close();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return CVO_lastIDint ;
	//
	//
	// }
	// public static void createCVO(int noOfCVO, int numberofVOrequired) throws
	// IOException {
	//// private final String STRING_ARRAY_SAMPLE =
	// "./datasetJPDC/listOfVO.csv";
	// int numberOfcvo = noOfCVO;
	// int numberofVOreq=numberofVOrequired;
	//// String csv = "data.csv";
	// for (int i = 1; i <= numberOfcvo; i++){
	//
	// for (int j = 1; j <= numberofVOreq; j++){
	// CSVWriter writer = new CSVWriter(new FileWriter(CVOpath, true));
	// int newCvoID=lastCVOid()+i;
	// String cvoID="cvoID_"+newCvoID;
	// String voID="voID_"+j;
	// String memberVO="memberVO_"+j;
	// String rec=cvoID+","+voID+","+memberVO;
	// System.out.println("-------------"+rec);
	// String [] record = rec.split(",");
	//
	// writer.writeNext(record);
	//// System.out.println("Record: "+record);
	// writer.close();
	//
	// }
	// }
	//
	// }
	//
	/*
	 * public static void createCVO(int numberOfCVO, int numberofVOrequired)
	 * throws IOException{ // private final String STRING_ARRAY_SAMPLE =
	 * "./datasetJPDC/listOfVO.csv"; int numberOfcvo = numberOfCVO; int
	 * numberofVOreq=numberofVOrequired; for (int i = 1; i <= numberOfcvo; i++){
	 * 
	 * 
	 * try ( Writer writer = Files.newBufferedWriter(Paths.get(CVOpath));
	 * 
	 * CSVWriter csvWriter = new CSVWriter(writer, CSVWriter.DEFAULT_SEPARATOR,
	 * CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER,
	 * CSVWriter.DEFAULT_LINE_END); ) { String[] headerRecord = {"CVOid",
	 * "VOid", "VOnumber"}; csvWriter.writeNext(headerRecord);
	 * 
	 * for (int j = 1; j <=numberofVOreq ; j++){ int randPrice =randomVal(4)+1;
	 * int randDuration =randomVal(4)+1; int randReputation =randomVal(4)+1; int
	 * randSuccessRate =randomVal(4)+1; int randAvailability =randomVal(4)+1;
	 * csvWriter.writeNext(new String[]{"cvoID"+j, "voID"+j, "VOnumber"+j}); } }
	 * } }
	 * 
	 */
	// end of createVO mehod
	//////////////////////////////////////////////////////////////
	public static void createCVO(int noOfCVO, int numberofVOrequired) throws IOException {
		// private final String STRING_ARRAY_SAMPLE =
		// "./datasetJPDC/listOfVO.csv";
		int numberOfcvo = noOfCVO;
		int numberofVOreq = numberofVOrequired;
		// String csv = "data.csv";
		CSVWriter writer = new CSVWriter(new FileWriter(CVOpath, true));

		for (int i = 1; i <= numberOfcvo; i++) {
			for (int j = 1; j <= numberofVOreq; j++) {
				int newCvoID = lastCVOid() + i;
				String cvoID = "cvoID_" + newCvoID;

				String voID = "voID_" + retrieveVO(10000);
				String memberVO = "memberVO_" + j;
				String rec = cvoID + "," + voID + "," + memberVO;
				System.out.println("-------------" + rec);
				String[] record = rec.split(",");
				writer.writeNext(record);
				// System.out.println("Record: "+record);

			}
		}
		writer.close();
	}

	public static int retrieveVO(int totalNumberofVO) throws IOException {

		// int selectedVOid = randomVal(9998) + 2;
		for (int i = 0; i <= totalNumberofVO; i++) {
			System.out.println(i);
			readLine(VOpath, i);

		}

		return 0;

	}

	public static void readLine(String filePath, int line_No) throws IOException {
		{
			// csv file containing data
			String strFile = filePath;
			CSVReader reader = new CSVReader(new FileReader(strFile));
			String[] nextLine;
			int lineNumber = -1;
			while ((nextLine = reader.readNext()) != null) {
				lineNumber++;

				if (lineNumber == line_No) {
					System.out.println("Got your line: " + lineNumber);
					// String strVO=nextLine[0] + nextLine[1] + nextLine[2] +
					// nextLine[3] + nextLine[4] + nextLine[5]
					// + nextLine[6] + nextLine[7];

					// System.out.println(strVO);

					retreivedUserID = nextLine[0].replace("UserID_", "");
					retreivedVOid = nextLine[1].replace("voID_", "");
					retreivedPOid = nextLine[2].replace("poID_", "");
					retreivedPriceScore = nextLine[3].replace("P_", "");
					retreivedDurationScore = nextLine[4].replace("D_", "");
					retreivedReputationScore = nextLine[5].replace("R_", "");
					retreivedSuccessrateScore = nextLine[6].replace("S_", "");
					retreivedAvailabilityScore = nextLine[7].replace("A_", "");
					// System.out.println(s+"==============");
					System.out.println("\n retreivedUserID: " + retreivedUserID + "\n retreivedVOid: " + retreivedVOid
							+ "\n retreivedPOid: " + retreivedPOid + "\n retreivedPriceScore : " + retreivedPriceScore
							+ "\n retreivedDurationScore: " + retreivedDurationScore + "\n retreivedReputationScore: "
							+ retreivedReputationScore + "\n retreivedSuccessrateScore: " + retreivedSuccessrateScore
							+ "\n retreivedAvailabilityScore: " + retreivedAvailabilityScore);
					// System.out.println(nextLine[0] + nextLine[1] +
					// nextLine[2] + nextLine[3] + nextLine[4] + nextLine[5]
					// + nextLine[6] + nextLine[7]);

					// HashMap <Integer, voList> voList=new HashMap <Integer,
					// voList>();
					// voList.
					
					HashMap<Integer, voList> vo = new HashMap<>();
				vo.
				
				}
				// System.out.println("Line # " + lineNumber);

				// nextLine[] is an array of values from the line

			}
		}
	}

	public static int lastCVOid() throws IOException {
		FileInputStream in = new FileInputStream(CVOpath);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		String strLine = null, tmp;

		while ((tmp = br.readLine()) != null) {
			strLine = tmp;
		}

		String lastLine = strLine;
		System.out.println(lastLine);
		System.out.println("----" + lastLine);
		System.out.println(lastLine.substring(lastLine.indexOf("_") + 1, lastLine.indexOf(",") - 1));
		String CVO_lastIDstr = lastLine.substring(lastLine.indexOf("_") + 1, lastLine.indexOf(",") - 1);
		int CVO_lastIDint = Integer.valueOf(CVO_lastIDstr);
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return CVO_lastIDint;

	}

	//////////////////////////////////////////////////////////////

	public static void main(String args[]) throws IOException {

		// createCVO(1, 3);
		retrieveVO(5);

		HashMap<String, Integer> userPref = new HashMap<String, Integer>();

		userPref.put("price", randomVal(5));
		userPref.put("reputation", randomVal(5));
		userPref.put("availability", randomVal(5));
		userPref.put("successRate", randomVal(5));
		userPref.put("experience", randomVal(5));

		//
		/// *
		// HashMap<String, Integer> userPrefNeg = new HashMap<String,
		// Integer>();
		//
		// userPrefNeg.put("price", randomVal(5));
		//// userPrefNeg.put("reputation", randomVal(5));
		//// userPrefNeg.put("availability", randomVal(5));
		//// userPrefNeg.put("successRate", randomVal(5));
		// userPrefNeg.put("experience", randomVal(5));
		// */
		//// System.out.println("FROM main ------------"+userPrefPos);
		// long startTime = System.nanoTime();
		//// calculationPos(caps, capName)
		// long startTime2 = System.currentTimeMillis();
		// double prevUsedMemory =
		// (double)((double)(Runtime.getRuntime().totalMemory()/1024)/1024)-
		// ((double)((double)(Runtime.getRuntime().freeMemory()/1024)/1024));
		//// voSelection(Integer.valueOf(args[0]), 100.0, userPref);
		// voSelection(5, 100.0, userPref);
		// double afterusedMemory
		// =((double)((double)(Runtime.getRuntime().totalMemory()/1024)/1024)-
		// ((double)((double)(Runtime.getRuntime().freeMemory()/1024)/1024)));
		// double memoryUsed= afterusedMemory - prevUsedMemory;
		//// System.err.println("\n Memory used :"+usedMemory);
		//// voSelection(1000, 100.0, userPrefPos);
		// Long endTime = System.nanoTime();
		// long endTime2 = System.currentTimeMillis();
		// long duration2=endTime2-startTime2;
		//// long endTime = System.nanoTime();
		// long duration = ((endTime - startTime));
		//
		//
		// String.format("%02d min, %02d sec",
		// TimeUnit.MILLISECONDS.toMinutes(duration),
		// TimeUnit.MILLISECONDS.toSeconds(duration) -
		// TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))
		// );
		//
		// System.out.println("Nanoseconds: Starting time: "+startTime+" End
		// Time: "+endTime+" Duration:
		// "+TimeUnit.NANOSECONDS.toSeconds(duration) +
		// "sec"+"----------"+(duration));
		// System.out.println("Memmory in MB: " +memoryUsed);
		//
		// System.out.println("Duration in MS: "+duration2);
	}

}
