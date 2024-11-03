package Helper;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CSVFileHandler {
    String filePath;
    public CSVFileHandler(String filePath)
    {
        this.filePath=filePath;
    }

    /***
     * Method Help to read the CSV file based on given filePath
     * @return List <String[]>
     */
    public List<String[]> ReadCSVData()
    {
        List<String[]> records =null;
        try (com.opencsv.CSVReader csvReader = new com.opencsv.CSVReader(new FileReader(filePath))) {
             records = csvReader.readAll();

        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            e.printStackTrace();
        }
        return records;

    }

    /**
     * @implSpec  1st Function this will return 30 consecutive data points
     * @param NoOfConsecutiveData in integer
     * @return List Of Data based on NoOfConsecutiveData
     * @since 3 Nov 2024
     *
     */
    public List<String[]> ReadConsecutiveData(int NoOfConsecutiveData)
    {
        List<String[]> records =ReadCSVData();
        List<String[]> filteredRecord = new ArrayList<>();
        
        if (records.size() <= NoOfConsecutiveData) {
            System.out.println("Not enough records in the file.");
            return null;
        }
        // maxvalue  = this will the maximum row value from where we can start the record from zero
        int Maxvalue = records.size() - NoOfConsecutiveData;

        // generating random number in the to cover range NoOfConsecutiveData
        int startingPointOfTheRow = new Random().nextInt(Maxvalue);
        //endRowNo from the start based on
        int endPointOfTheRow = startingPointOfTheRow + NoOfConsecutiveData;

        for (int i = startingPointOfTheRow; i < endPointOfTheRow; i++) {
            String[] record = records.get(i);
            filteredRecord.add(record);

        }
        return filteredRecord;
    }

    public void GenerateOutPutFile(String FolderName,String FileName,List<String[]> output)
    {

        String csvFile = FolderName; // CSV file path
        String fileName =FileName;
        // Create folder if it doesn't exist
        File folder = new File(csvFile);
        if (!folder.exists()) {
            folder.mkdir(); // Creates the folder
        }

        try (FileWriter writer = new FileWriter(csvFile+"/"+fileName)) {
            // Write header
            for (String[] row : output) {
                writer.append(String.join(",", row));
                writer.append("\n"); // New line after each row
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }



}
