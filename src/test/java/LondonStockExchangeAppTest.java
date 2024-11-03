import Feature.FileReadingFeature;
import Feature.OutliersHandlingFeature;
import Helper.CSVFileHandler;

import java.io.File;
import java.util.List;

public class LondonStockExchangeAppTest {

    public static void main(String[] args)
    {
        generateTheOutliersDetailsOutPutFile("src/main/resources/NYSE","src/main/java/OutPut",30);
    }

    /***
     * Method to generate the output file based on source location and place the output in destination location
     * @param SourceLocation
     * @param DestinationLocation
     * @param NoOfConsecutiveData
     */
    public static void generateTheOutliersDetailsOutPutFile(String SourceLocation,String DestinationLocation,int NoOfConsecutiveData)
    {
        File folder = new File(SourceLocation);
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                // Print file details
                if(file.getName().contains(".csv")) {
                    ProcessTheFileToFindOutliers(file.getPath(), DestinationLocation+file.getParentFile().getName(), file.getName(), NoOfConsecutiveData);
                }
            }
            System.out.println("List of outliers generated in output folder");
        } else {
            System.out.println("The specified folder is empty or could not be read.");
        }
    }

    /***
     * Method to Process the Outliers
     * @param sourceFilePath
     * @param destinationFilePath
     * @param FileName
     * @param NoOfConsecutiveData
     */
    public static void ProcessTheFileToFindOutliers(String sourceFilePath,String destinationFilePath,String FileName,int NoOfConsecutiveData)
    {

        // CSVFileHandler  class is part of helper class which help to read the csv file based on filepath
        CSVFileHandler csvFile = new CSVFileHandler(sourceFilePath);
        List<String[]> ReadFileData = csvFile.ReadCSVData();


        //FileReadingFeature is a class containing function which return consecutive data based on given input
        FileReadingFeature fileReadingFeature = new FileReadingFeature(ReadFileData);
        List<String[]> filteredRecord=fileReadingFeature.ReadConsecutiveData(NoOfConsecutiveData);
        // get the List of outlier
        OutliersHandlingFeature outliersHandlingFeature = new OutliersHandlingFeature();
        List<String[]> sorted= outliersHandlingFeature.GetListOfOutlier(filteredRecord);
        // generate output file
        csvFile.GenerateOutPutFile(destinationFilePath,FileName,sorted);
    }
}
