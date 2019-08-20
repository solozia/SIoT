package SIoTtest;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class average {
	 static String test="/GoogleDrive/Dev/workspace/myphdworkspace/SIoT/datasetJPDCzia/results/test/";
//static String path=test;
	 static String path=test;//"/GoogleDrive/Dev/workspace/myphdworkspace/SIoT/datasetJPDCzia/results/MCDM_Var-VO/voselection/";


	public static void parseForCsvFiles(String parentDirectory) throws NumberFormatException, IOException {
		File[] filesInDirectory = new File(parentDirectory).listFiles();
		int Flag=0;
		for (File f : filesInDirectory) {
			List<evaluationMetricsList> metricsTable = new ArrayList<evaluationMetricsList>();

			
//			if (f.isDirectory()) {
//				
//				parseForCsvFiles(f.getAbsolutePath());
//			
//				// System.out.println("CSV file found -> " +
//				// f.getAbsolutePath());
//			}
			String filePath = f.getAbsolutePath();
			String fileExtenstion = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
			if ("csv".equals(fileExtenstion)) {
				int LineNo = 0;
            	String[] nextLine;
				CSVReader reader = new CSVReader(new FileReader(filePath));
				while ((nextLine = reader.readNext()) != null) {
					LineNo++;
//					System.out.println("CSV file found -> " + filePath);
													
					double memory = Double.parseDouble( nextLine[0]);
					double latency = Double.parseDouble( nextLine[1]);
					System.out.println(memory+" : "+latency+":"+filePath);
					evaluationMetricsList m = new evaluationMetricsList(memory, latency, "optional");
					metricsTable.add(m);
				}
//				System.out.println("metricsTable: "+metricsTable);
				double sumMem=0.0;
				double sumLat=0.0;
				for(int i=0; i<=metricsTable.size()-1; i++){
//					System.out.println(metricsTable.size()+": "+metricsTable.get(i));
					sumMem+=metricsTable.get(i).getMemory();
					sumLat+=metricsTable.get(i).getLatency();
//					System.out.println(metricsTable.get(i).getMemory()+": metricsTable.get(i).getMemory()");
				}
				double avgMem=(sumMem/metricsTable.size()); 
				double	avgLat=(sumLat/metricsTable.size());
				System.out.println("Average Memory: ----"+avgMem+" Average Latency: "+avgLat);
				
				CSVWriter writer = new CSVWriter(new FileWriter(path+"Avg.csv", true));
				String rec = avgMem+","+avgLat+","+filePath.replace("/GoogleDrive/Dev/workspace/myphdworkspace/SIoT/datasetJPDCzia/results/", "");
				String[] record = rec.split(",");
				writer.writeNext(record);
				writer.close();
				System.out.println(record+"--------------------------------");
				Flag++;
//				System.out.println("reading file"+Flag);
			}
			
			
		}
	}
	public static void main(String[] args) throws IOException {

		 parseForCsvFiles(path);
	}
}
