package reader;

import models.Data;
import models.Point2D;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Point2DReader implements CSVReaderInterface {


    private String csvFile;
    private static String cvsSplitBy = ";";
    private static String[] cityNames;

    public Point2DReader() {
    }

    public Point2DReader(String csvFile) {
        Data.cities2d = new ArrayList<Point2D>();
        this.csvFile = csvFile;
    }

    @Override
    public void readCsv() {
        String line = "";
        BufferedReader br = null;
        int idx = 0;
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                // use comma as separator

                String[] entries = line.split(cvsSplitBy);
                Point2D point = new Point2D(entries[0], Float.parseFloat(entries[1]), Float.parseFloat(entries[2]));
                Data.cities2d.add(point);
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
