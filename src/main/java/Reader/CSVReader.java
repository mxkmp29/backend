package Reader;

import models.Data;
import models.Point;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {

    private String csvFile;
    private static String cvsSplitBy = ";";
    private static String[] cityNames;

    public CSVReader() {
    }

    public CSVReader(String csvFile) {
        this.csvFile = csvFile;
    }

    public void readCsv() {
        String line = "";
        BufferedReader br = null;
        int idx = 0;
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] entries = line.split(cvsSplitBy);
                if (idx == 0) {
                    cityNames = entries;
                } else {
                    String name = entries[0];
                    Point p = new Point(name);
                    for (int i = 1; i < entries.length; i++) {
                        String destinationName = cityNames[i];
                        int distance = Integer.parseInt(entries[i]);
                        p.getCostsList().put(destinationName, distance);
                    }
                    Data.cities.add(p);
                }
                idx++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
