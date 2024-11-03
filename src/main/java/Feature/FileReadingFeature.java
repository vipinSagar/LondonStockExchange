package Feature;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FileReadingFeature {
    List<String[]> ListOfRecord;

    public FileReadingFeature( List<String[]> ListOfRecord)
    {
        this.ListOfRecord =ListOfRecord;
    }

    /**
     * @implSpec  1st Function this will return consecutive data points based on input
     * @param NoOfConsecutiveData in integer
     * @return List Of Data based on NoOfConsecutiveData
     * @since 3 Nov 2024
     *
     */
    public List<String[]> ReadConsecutiveData(int NoOfConsecutiveData)
    {
        List<String[]> records =ListOfRecord;
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
}
