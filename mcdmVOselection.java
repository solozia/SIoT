package mcdm;

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
import java.util.Random;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class mcdmVOselection {
	// private static final String voCreationPath = "";
	// private static final String CVOpath = "./datasetJPDC/listOfCVO2.csv";
	private static String CVOpath = "./datasetJPDCzia/CVOmcdm.csv";
	private static String VOpath = "";
	// private static String=c"./datasetJPDC/test/results";
	// private static final String CVOpath = "./datasetJPDC/test/CVOtest.csv";
	// private static final String VOpath = "./datasetJPDC/test/VO1000d.csv";
	static int retreivedUserID_int;
	static int retreivedVOid_int;
	static int retreivedPOid_int;
	static double retreivedPriceScoreD;
	static double retreivedDurationScoreD;
	static double retreivedReputationScoreD;
	static double retreivedSuccessrateScoreD;

	static double retreivedAvailabilityScoreD;

	static String retreivedUserID;
	static String retreivedVOid;
	static String retreivedPOid;
	static String retreivedPriceScore;
	static String retreivedDurationScore;
	static String retreivedReputationScore;
	static String retreivedSuccessrateScore;
	static String retreivedAvailabilityScore;

	// static HashMap<String, Integer> userPreference = new HashMap<String,
	// Integer>();

	public static int voSelection(String voPath, HashMap<String, Integer> userPref) throws IOException {
		List<voList> voTable = retrieveVO(voPath);
		HashMap<String, Double> scoredVOlst = new HashMap<String, Double>();
		// System.out.println("before for loop>>>>>>>>>>>>>>>>>");
		for (int i = 0; i <= voTable.size() - 1; i++) {
			double scoredVOprice = calculationNeg(voTable.get(i), voTable.get(i).getPriceRevScore(), "price", userPref);
			double scoredVOduration = calculationNeg(voTable.get(i), voTable.get(i).getDurationRevScore(), "duration",
					userPref);
			double scoredVOreputation = calculationPos(voTable.get(i), voTable.get(i).getReputationRevScore(),
					"reputation", userPref);
			double scoredVOsuccessRate = calculationPos(voTable.get(i), voTable.get(i).getSuccessRateRevScore(),
					"successRate", userPref);
			double scoredVOavailability = calculationPos(voTable.get(i), voTable.get(i).getAvailableRevScore(),
					"availability", userPref);
			double sumScore = scoredVOprice + scoredVOduration + scoredVOreputation + scoredVOsuccessRate
					+ scoredVOavailability;

			// System.out.println("sumScore: " + sumScore);
			scoredVOlst.put(String.valueOf(voTable.get(i).getVoID()), sumScore);
		}

		int selectedVOid = 0;
		double selectedVOval = (Collections.max(scoredVOlst.values()));
		for (Entry<String, Double> entry : scoredVOlst.entrySet()) {
			if (entry.getValue() == selectedVOval) {
				selectedVOid = Integer.parseInt(entry.getKey());
			}
		}
		// System.out.println("scoredVOlst" + scoredVOlst + "\n
		// selectedVOid@@@@@@@@@@@: " + selectedVOid);
		// System.out.println("selectedVOid: "+selectedVOid);
		return selectedVOid;
	}

	public static int randomVal(int upperbound) {
		Random randomGenerator = new Random();
		int randomVal = randomGenerator.nextInt(upperbound);

		return randomVal;
	}

	public static double calculationPos(voList vo, double revScore, String qcName, HashMap<String, Integer> userPref) {
		// HashMap<Integer, voList> scoredQC = new HashMap<Integer, voList>();
		double Vij = 0.0;
		double calculatedQC = 0.0;
		// System.out.println(vo);
		List<Double> QClist = new ArrayList<Double>();
		QClist.add(vo.getPriceRevScore());
		QClist.add(vo.getDurationRevScore());
		QClist.add(vo.getReputationRevScore());
		QClist.add(vo.getSuccessRateRevScore());
		QClist.add(vo.getAvailableRevScore());
		// System.out.println(QClist);
		int Flag = 2;
		if (qcName == "reputation") {
			Flag = 2;
		} else if (qcName == "successRate") {
			Flag = 3;
		} else if (qcName == "availability") {
			Flag = 4;
		} else {
			System.out.println("QC name is out of scope");
		}
		double maxVal = Collections.max(QClist);
		double minVal = Collections.min(QClist);
		// System.out.println(maxVal + "----" + minVal);

		if (maxVal - minVal != 0) {
			Vij = (QClist.get(Flag) - minVal) / (maxVal - minVal);
			// System.out.println("Vij before mutliplied by w: " + Vij);
			calculatedQC = Vij * (userPref.get(qcName)); // multiplied by
															// userPref
			// (weight)
			// System.out.println("\n Vij>>>>>>>>>>>>>>>>>>>>>>:" + Vij +
			// "wwwwww=" + calculatedQC + "\n ---userprefe: "
			// + qcName + userPref.get(qcName));

		} else {
			Vij = 1;
			// System.out.println("\n VO score: "+Vij+" "+w);
			calculatedQC = Vij * (userPref.get(qcName));
			// System.out.println("W>>>>>>>" + calculatedQC);

		}
		// } //end for
		return calculatedQC;
	}

	public static double calculationNeg(voList vo, double revScore, String qcName, HashMap<String, Integer> userPref) {
		double Vij = 0.0;
		double calculatedQC = 0.0;
		// System.out.println(vo);
		List<Double> QClist = new ArrayList<Double>();
		QClist.add(vo.getPriceRevScore());
		QClist.add(vo.getDurationRevScore());
		QClist.add(vo.getReputationRevScore());
		QClist.add(vo.getSuccessRateRevScore());
		QClist.add(vo.getAvailableRevScore());
		// System.out.println(QClist);
		int Flag;
		if (qcName == "duration") {
			Flag = 1;
		} else {
			Flag = 0;
		}
		double maxVal = Collections.max(QClist);
		double minVal = Collections.min(QClist);
		// System.out.println(maxVal + "----" + minVal);
		if (maxVal - minVal != 0) {
			Vij = (maxVal - QClist.get(Flag)) / (maxVal - minVal);
			// System.out.println("Vij before mutliplied by w: " + Vij);
			calculatedQC = Vij * (userPref.get(qcName)); // multiplied by
															// userPref
			// (weight)
			// System.out.println("\n Vij>>>>>>>>>>>>>>>>>>>>>>:" + Vij +
			// "wwwwww=" + calculatedQC + "\n ---userprefe: "
			// + qcName + userPref.get(qcName));

		} else {
			Vij = 1;
			// System.out.println("\n VO score: "+Vij+" "+w);
			calculatedQC = Vij * (userPref.get(qcName));
			// System.out.println("W>>>>>>>" + calculatedQC);

		}
		// } //end for
		return calculatedQC;
	}

	public static void createCVO(String voPath, int noOfCVO, int numberofVOrequired, int numberOfusers, int candidateVO)// ,
			// HashMap<String,
			// Integer>
			// userPref)
			throws IOException {
		int numberOfcvo = noOfCVO;
		int numberofVOreq = numberofVOrequired;
		// String csv = "data.csv";
		CSVWriter writer = new CSVWriter(new FileWriter(CVOpath, true));

		for (int i = 1; i <= numberOfcvo; i++) {
			for (int j = 1; j <= numberofVOreq; j++) {
				HashMap<String, Integer> userPref = new HashMap<String, Integer>();

				userPref.put("price", randomVal(3) + randomVal(2));
				userPref.put("duration", randomVal(3) + randomVal(2));
				userPref.put("reputation", randomVal(4) + randomVal(2));
				userPref.put("successRate", randomVal(4) + randomVal(2));
				userPref.put("availability", randomVal(4) + randomVal(2));

				int newCvoID = lastCVOid() + i;
				String cvoID = "cvoID_" + newCvoID;
				String voID = null;

				// voselection start
				double prevMemoryVOselection = (double) ((double) (Runtime.getRuntime().totalMemory() / 1024) / 1024)
						- ((double) ((double) (Runtime.getRuntime().freeMemory() / 1024) / 1024));
				long startTimeVOselection = System.currentTimeMillis(); // reading
																		// time

				String selectedVOid = String.valueOf(voSelection(voPath, userPref));
				// String checkedVO= checkVOid(CVOpath, selectedVOid);
				// while(checkedVO.contains("1")){
				// for(char checkedVO: "1".toCharArray()){
				// userPref.put("price", randomVal(3) + 1);
				// userPref.put("duration", randomVal(3) + 1);
				// userPref.put("reputation", randomVal(4) + 1);
				// userPref.put("successRate", randomVal(4) + 1);
				// userPref.put("availability", randomVal(4) + 1);
				// selectedVOid=String.valueOf(voSelection(voPath, userPref));
				// checkedVO= checkVOid(CVOpath, selectedVOid);
				// }
				voID = "voID_" + selectedVOid;// retrieveVO(VOpath);
				long endTimeVOselection = System.currentTimeMillis();
				double afterMemoryVOselection = (double) ((double) (Runtime.getRuntime().totalMemory() / 1024) / 1024)
						- ((double) ((double) (Runtime.getRuntime().freeMemory() / 1024) / 1024));
//				System.out.println("Vo Selection Memory used:" + (afterMemoryVOselection - prevMemoryVOselection));
//				System.out.println("Vo Duration: " + (endTimeVOselection - startTimeVOselection));
				double VOselectionMemoryUsed = afterMemoryVOselection - prevMemoryVOselection;
				long VOselectionDuration = endTimeVOselection - startTimeVOselection;
				writeCSV("./datasetJPDCzia/results/voselection_", numberOfusers, numberofVOreq, candidateVO,
						String.valueOf(VOselectionMemoryUsed), String.valueOf(VOselectionDuration), "-", "-");
				// vo Selection end

				// int finalCVOid=checkVOid(CVOpath, newCvoID)+i;
				String memberVO = "memberVO_" + j;
				String rec = cvoID + "," + voID + "," + memberVO;
				String[] record = rec.split(",");
				writer.writeNext(record);

			}
		}
		writer.close();
	}

	public static List<voList> retrieveVO(String voPath) throws IOException {
		List<voList> voTable = new ArrayList<voList>();
		String strFile = voPath;
		CSVReader reader = new CSVReader(new FileReader(strFile));
		String[] nextLine;
		int lineNumber = 0;
		while ((nextLine = reader.readNext()) != null) {
			lineNumber++;
			if (lineNumber > 1) {
				retreivedUserID = nextLine[0].replace("UserID_", "");
				retreivedVOid = nextLine[1].replace("voID_", "");
				retreivedPOid = nextLine[2].replace("poID_", "");
				retreivedPriceScore = nextLine[3].replace("P_", "");
				retreivedDurationScore = nextLine[4].replace("D_", "");
				retreivedReputationScore = nextLine[5].replace("R_", "");
				retreivedSuccessrateScore = nextLine[6].replace("S_", "");
				retreivedAvailabilityScore = nextLine[7].replace("A_", "");
				retreivedUserID_int = Integer.valueOf(retreivedUserID);/////////
				retreivedVOid_int = Integer.parseInt(retreivedVOid);
				retreivedPOid_int = Integer.parseInt(retreivedPOid);
				retreivedPriceScoreD = Double.parseDouble(retreivedPriceScore);
				retreivedDurationScoreD = Double.parseDouble(retreivedDurationScore);
				retreivedReputationScoreD = Double.parseDouble(retreivedReputationScore);
				retreivedSuccessrateScoreD = Double.parseDouble(retreivedSuccessrateScore);
				retreivedAvailabilityScoreD = Double.parseDouble(retreivedAvailabilityScore);
				voList vo = new voList(retreivedVOid_int, retreivedPriceScoreD, retreivedDurationScoreD,
						retreivedReputationScoreD, retreivedSuccessrateScoreD, retreivedAvailabilityScoreD);
				// System.out.println("VO----" + vo);
				voTable.add(vo);

			} // end of line if
				// System.out.println("voTable: <<<<<<<<<<<<<" + voTable);
		} // end of while
		return voTable;
	}

	public static int countVO() throws IOException {
		FileInputStream in = new FileInputStream(VOpath);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String tmp;
		// String strLine = null, tmp;
		int numberOfVO = 0;
		while ((tmp = br.readLine()) != null) {
			numberOfVO++;
		}

		return numberOfVO - 1;

	}

	public static int lastCVOid() throws IOException {
		FileInputStream in = new FileInputStream(CVOpath);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		String strLine = null, tmp;

		while ((tmp = br.readLine()) != null) {
			strLine = tmp;
			// System.out.println(strLine);
		}

		String lastLine = strLine;

		String CVO_lastIDstr = lastLine.substring(lastLine.indexOf("_") + 1, lastLine.indexOf(",") - 1);
		int CVO_lastIDint = Integer.parseInt(CVO_lastIDstr);
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return CVO_lastIDint;

	}

	public static String trigger() {
		return "break;";

	}

	// public static String checkVOid(String path, String voID) throws
	// IOException{
	//// voID="811";
	//// System.out.println("received voID in check"+voID);
	// path=CVOpath;
	//// voID = "0";
	// String returnVar = "0";
	//// String trigger;
	// CSVReader reader = new CSVReader(new FileReader(path));
	// String[] nextLine;
	// int lineNumber = 0;
	// search: {
	// while ((nextLine = reader.readNext()) != null) {
	// lineNumber++;
	// if (lineNumber > 1) {
	//
	// String voIDc = nextLine[1].replace("voID_", "");
	//// System.out.println(voIDc);
	// if(voIDc.contains(voID)){
	//
	// returnVar="1";
	// break search;
	//
	// }
	//
	// // System.out.println("VO----" + vo);
	//
	// } // end of line if
	//
	//// trigger="break;";
	// // System.out.println("voTable: <<<<<<<<<<<<<" + voTable);
	// } // end of while
	// }
	//// System.out.println("voID>>>>>>>ReturnVar: "+returnVar);
	// return returnVar;
	// }
	// CSVWriter writer = new CSVWriter(new FileWriter(CVOpath, true));

	public static void writeCSV(String csvPath, int NumberOfusers, int numberOFvo, int candidateVO,
			String memoryUsedEntire, String durationEntireMS, String memoryUsedSingleCVO, String singleCVOduration)
			throws IOException {
		CSVWriter writer = new CSVWriter(new FileWriter(
				csvPath + "MCDM" + "_U" + NumberOfusers + "_VO" + numberOFvo + "_Cand" + candidateVO, true));
		String rec = memoryUsedEntire + "," + durationEntireMS + "," + memoryUsedSingleCVO + "," + singleCVOduration;

		String[] record = rec.split(",");
		writer.writeNext(record);

		// try (Writer writer = Files.newBufferedWriter(Paths.get(csvPath +
		// "results"+NumberOfusers+"_"+numberOFvo)); // +
		// // System.currentTimeMillis()));
		//
		// CSVWriter csvWriter = new CSVWriter(writer,
		// CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER,
		// CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);) {
		// String[] headerRecord = { "a", "b", "c", "d" };
		// csvWriter.writeNext(headerRecord);
		// csvWriter.writeNext(new String[] { a, b, c, d });
		// // }
		// }
		writer.close();
	}

	public static void createVO(int numberOfVO) {

		try (Writer writer = Files.newBufferedWriter(Paths.get(VOpath));

				CSVWriter csvWriter = new CSVWriter(writer, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER,
						CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);) {
			String[] headerRecord = { "UserID", "VOid", "POid", "Price", "Duration", "Reputation", "SuccessRate",
					"Availability" };
			csvWriter.writeNext(headerRecord);

			for (int i = 1; i <= numberOfVO; i++) {
				double randPrice = randomVal(99) + 1;
				double randDuration = randomVal(99) + 1;
				double randReputation = randomVal(99) + 1;
				double randSuccessRate = randomVal(99) + 1;
				double randAvailability = randomVal(99) + 1;
				csvWriter.writeNext(new String[] { "UserID_" + i, "voID_" + i, "poID_" + i, "P_" + randPrice,
						"D_" + randDuration, "R_" + randReputation, "S_" + randSuccessRate, "A_" + randAvailability, });
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String args[]) throws IOException {
		 int candidateVO=Integer.parseInt(args[0]);
		 int numberofVOreq=Integer.parseInt(args[1]);
		 int NumberOfusers=Integer.parseInt(args[2]);

//		int candidateVO = 100;
//		int numberofVOreq = 10;
//		int NumberOfusers = 10;

		VOpath = "./datasetJPDCzia/VO" + candidateVO + ".csv";
		// System.out.println("Printed"+VOpath);
		createVO(candidateVO);
		// VOcreation.createVO(100);
		// retrieveVO(VOpath);
		// countVO();
		// retrieveVO(VOpath);
		// HashMap<String, Integer> userPreference = new HashMap<String,
		// Integer>();
		//
		// userPreference.put("price", randomVal(3) + 1);
		// userPreference.put("duration", randomVal(3) + 1);
		// userPreference.put("reputation", randomVal(4) + 1);
		// userPreference.put("successRate", randomVal(4) + 1);
		// userPreference.put("availability", randomVal(4) + 1);
		// System.out.println("-------------------"+userPref);
		// int numberofVOreq=3;

		long startTime = System.nanoTime();
		// calculationPos(caps, capName)
		long startTime2 = System.currentTimeMillis();
		long startTimeSingleCVO = 0;
		long endTimeSingleCVO = 0;
		double prevUsedMemorySingleCVO = 0;
		double afterusedMemorySingleCVO = 0;
		double prevUsedMemory = (double) ((double) (Runtime.getRuntime().totalMemory() / 1024) / 1024)
				- ((double) ((double) (Runtime.getRuntime().freeMemory() / 1024) / 1024));

		// voSelection(Integer.valueOf(args[0]), 100.0, userPref);
		for (int i = 1; i <= 1; i++) {
			startTimeSingleCVO = System.currentTimeMillis();
			prevUsedMemorySingleCVO = (double) ((double) (Runtime.getRuntime().totalMemory() / 1024) / 1024)
					- ((double) ((double) (Runtime.getRuntime().freeMemory() / 1024) / 1024));

			createCVO(VOpath, 1, numberofVOreq, NumberOfusers, candidateVO);
			afterusedMemorySingleCVO = ((double) ((double) (Runtime.getRuntime().totalMemory() / 1024) / 1024)
					- ((double) ((double) (Runtime.getRuntime().freeMemory() / 1024) / 1024)));
			endTimeSingleCVO = System.currentTimeMillis();

		} // end of for
		double afterusedMemory = ((double) ((double) (Runtime.getRuntime().totalMemory() / 1024) / 1024)
				- ((double) ((double) (Runtime.getRuntime().freeMemory() / 1024) / 1024)));
		long endTime = System.nanoTime();
		long endTime2 = System.currentTimeMillis();

		double memoryUsedEntire = afterusedMemory - prevUsedMemory;
		double memoryUsedSingleCVO = afterusedMemorySingleCVO - prevUsedMemorySingleCVO;
		long durationEntireMS = endTime2 - startTime2;
		long singleCVOduration = endTimeSingleCVO - startTimeSingleCVO;
		// long endTime = System.nanoTime();
		long duration = ((endTime - startTime));

		// String.format("%02d min, %02d sec",
		// TimeUnit.MILLISECONDS.toMinutes(duration),
		// TimeUnit.MILLISECONDS.toSeconds(duration)
		// -
		// TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
		//
		// System.out.println("Nanoseconds: Starting time: " + startTime + " End
		// Time: " + endTime + " Duration: "
		// + TimeUnit.NANOSECONDS.toSeconds(duration) + "sec" + "----------" +
		// (duration));
//		System.out.println("Memmory in MB entire: " + memoryUsedEntire);
//
		System.out.println("Duration in MS entire: " + durationEntireMS);
//		System.out.println("Memmory in MB single CVO: " + memoryUsedSingleCVO);
//
//		System.out.println("Duration in MS single CVO: " + singleCVOduration);
		writeCSV("./datasetJPDCzia/results/entire_", NumberOfusers, numberofVOreq, candidateVO,
				String.valueOf(memoryUsedEntire), String.valueOf(durationEntireMS), String.valueOf(memoryUsedSingleCVO),
				String.valueOf(singleCVOduration));

		// voSelection(VOpath, userPref);
	}

}
