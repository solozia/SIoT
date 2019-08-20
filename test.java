package SIoTtest;

import java.io.FileReader;
import java.io.IOException;

import com.opencsv.CSVReader;

public class test {
	private static final String CVOpath = "./datasetJPDC/test/CVOtest.csv";
	private static final String VOpath = "./datasetJPDC/test/VO1000d.csv";

	
	public static String checkVOid(String path, String voID) throws IOException{
		System.out.println("recevied voID in check: "+voID);
		path=CVOpath;
//		voID = "0";
		String returnVar = "0";
		String trigger;
		CSVReader reader = new CSVReader(new FileReader(path));
		String[] nextLine;
		int lineNumber = 0;
		search: {
			while ((nextLine = reader.readNext()) != null) {
			lineNumber++;
			if (lineNumber > 1) {
				
				String voIDc = nextLine[1].replace("voID_", "");
				System.out.println(voIDc);
				if(voIDc.contains(voID)){
					returnVar="1";
					break search;
					
				}
				
				// System.out.println("VO----" + vo);

			} // end of line if
			
//			trigger="break;";
				// System.out.println("voTable: <<<<<<<<<<<<<" + voTable);
		} // end of while
		}
		System.out.println("voID>>>>>>>: "+returnVar);
		return returnVar;
	}
	
	
	
//
	public static void main(String[] args) throws IOException{
		String voID="811";
//		System.out.println("ababb");
		System.out.println(checkVOid(CVOpath, voID));
		
	

	}

}
