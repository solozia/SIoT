package SIoTtest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;



public class removeLine
{


    public static void main(String[] args) throws IOException
    {
        File inputFile = new File("./datasetJPDCzia/VO10000.csv");
        File tempFile = new File("./datasetJPDCzia/VO10000tmp.csv");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            if(null!=currentLine && !currentLine.equalsIgnoreCase("10001")){
                writer.write(currentLine + System.getProperty("line.separator"));
            }
        }
        writer.close(); 
        reader.close(); 
        boolean successful = tempFile.renameTo(inputFile);
        System.out.println(successful);
    }

}