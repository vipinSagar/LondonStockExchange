package Feature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OutliersHandlingFeature {

    public OutliersHandlingFeature()
    {

    }

    /***
     * 2nd API/function that gets the output from 1st one as a feed and returns the list of outliers.
     * @param  inputData
     * @return List<String[]>
     * @since 03 Nov 22024
     * @author vipin sagar
     *
     */
    public List<String[]> GetListOfOutlier(List<String[]> inputData)
    {
        List<String[]> dataInSortedFormat=  this.SortingOfList(inputData);
        // Calculate Q1, Q3, and IQR
        double q1 = getQuartile(dataInSortedFormat, 0.25);
        double q3 = getQuartile(dataInSortedFormat, 0.75);
        double iqr = q3 - q1;



        // find the outlier boundaries ( formula based on maths)
        double lowerBound = q1 - 1.5 * iqr;
        double upperBound = q3 + 1.5 * iqr;



        // Find outliers
        List<String[]> outliers = new ArrayList<>();
        for (String[] record : dataInSortedFormat) {
            Double price = Double.parseDouble(record[2]);
            if (price < lowerBound || price > upperBound) {
                outliers.add(record);
            }
        }
        return outliers;
    }

    /***
     * Method to format the input List into sorting order based on Price
     * @param inputData
     * @return
     */
    public List<String[]>  SortingOfList(List<String[]> inputData)
    {
        Collections.sort(inputData, new Comparator<String[]>() {

            public int compare(String[] record1, String[] record2) {
                // Convert the String price to Double for comparison
                Double price1 = Double.parseDouble(record1[2]);
                Double price2 = Double.parseDouble(record2[2]);
                return price1.compareTo(price2);
            }
        });

        return inputData;
    }

    /***
     * this Method will return  quartile value based on input sortedList and quartile value
     * @param sortedInputData
     * @param quartile
     * @return
     */
    public static double getQuartile(List<String[]> sortedInputData, double quartile) {
        double index = (double) (quartile * (sortedInputData.size() - 1));
        if(index%2==0){
            int intValue = (int) index;
            Double price = Double.parseDouble(sortedInputData.get(intValue)[2]);
            return price;
        }
        else
        {
            int lowerIndexValue = (int) Math.floor(index);
            int upperIndexValue = (int) Math.ceil(index);
            Double price = (Double.parseDouble(sortedInputData.get(lowerIndexValue)[2]) +
                    Double.parseDouble(sortedInputData.get(upperIndexValue)[2])) / 2;
            return price;
        }

    }

}
