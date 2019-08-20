//source: https://www.callicoder.com/java-read-write-csv-file-opencsv/

package ip;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
public class VOcreation {
	
//	private static final String STRING_ARRAY_SAMPLE = "./datasetJPDC/test/";
	private static final String STRING_ARRAY_SAMPLE = "./datasetJPDCzia";

	
	public static int randomVal(int upperbound){
		Random randomGenerator = new Random();
		int randomVal = randomGenerator.nextInt(upperbound);
		
		return randomVal;	
	}

	public static void createVO(int numberOfVO) throws IOException{
		
	      try (
	              Writer writer = Files.newBufferedWriter(Paths.get(STRING_ARRAY_SAMPLE+"VO"+numberOfVO));

	              CSVWriter csvWriter = new CSVWriter(writer,
	                      CSVWriter.DEFAULT_SEPARATOR,
	                      CSVWriter.NO_QUOTE_CHARACTER,
	                      CSVWriter.DEFAULT_ESCAPE_CHARACTER,
	                      CSVWriter.DEFAULT_LINE_END);
	          ) {
	              String[] headerRecord = {"UserID", "VOid", "POid", "Price", "Duration", "Reputation", "SuccessRate", "Availability"};
	              csvWriter.writeNext(headerRecord);
	        
	              for (int i = 1; i <= numberOfVO; i++){
	              	 double randPrice =randomVal(99)+1;
	              	 double randDuration =randomVal(99)+1;
	              	 double randReputation =randomVal(99)+1;
	              	 double randSuccessRate =randomVal(99)+1;
	              	 double randAvailability =randomVal(99)+1;
	              csvWriter.writeNext(new String[]{"UserID_"+i, "voID_"+i, "poID_"+i, "P_"+randPrice, "D_"+randDuration,"R_"+randReputation,"S_"+randSuccessRate,"A_"+randAvailability,});
	              }
	          }
	}
	
    public static void main(String[] args) throws IOException {
    	createVO(500);
//        try (
//            Writer writer = Files.newBufferedWriter(Paths.get(STRING_ARRAY_SAMPLE));
//
//            CSVWriter csvWriter = new CSVWriter(writer,
//                    CSVWriter.DEFAULT_SEPARATOR,
//                    CSVWriter.NO_QUOTE_CHARACTER,
//                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
//                    CSVWriter.DEFAULT_LINE_END);
//        ) {
//            String[] headerRecord = {"UserID", "VOid", "POid", "Price", "Duration", "Reputation", "SuccessRate", "Availability"};
//            csvWriter.writeNext(headerRecord);
//      
//            for (int i = 1; i <= 1000; i++){
//            	 double randPrice =randomVal(99)+1;
//            	 double randDuration =randomVal(99)+1;
//            	 double randReputation =randomVal(99)+1;
//            	 double randSuccessRate =randomVal(99)+1;
//            	 double randAvailability =randomVal(99)+1;
//            csvWriter.writeNext(new String[]{"UserID_"+i, "voID_"+i, "poID_"+i, "P_"+randPrice, "D_"+randDuration,"R_"+randReputation,"S_"+randSuccessRate,"A_"+randAvailability,});
//            }
//        }
    }
	
	
}
	
	//userPref.put("price", randomVal(5));
	//userPref.put("reputation", randomVal(5));
	//userPref.put("availability", randomVal(5));
	//userPref.put("successRate", randomVal(5));
	//userPref.put("experience", randomVal(5));
	
	
	
/*	
	
	public static void main(String[] args) throws IOException {
        CSVWriter csvWriter = new CSVWriter(new FileWriter("ex1.csv"));
 
        List<String[]> rows = new LinkedList<String[]>();
        for (int i = 1; i <= 15; i++){
        	
        
        rows.add(new String[]{"id-"+i, "jan", "Male", "20"});
  //      rows.add(new String[]{"2", "con", "Male", "24"});
   //     rows.add(new String[]{"3", "jane", "Female", "18"});
  //      rows.add(new String[]{"4", "ryo", "Male", "28"});
        csvWriter.writeAll(rows);
        }
        csvWriter.close();
    }*/
