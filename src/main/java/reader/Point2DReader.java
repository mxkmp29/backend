package reader;

import models.Data;
import models.Point2D;
import models.PointFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Point2DReader implements CSVReaderInterface {


    private String csvFile;
    private static String cvsSplitBy = ";";
    private static String[] cityNames;
    private PointFile<Point2D> pointFile = new PointFile<>();

    public Point2DReader() {
    }

    public Point2DReader(String csvFile) {
        Data.cities2d = new ArrayList<Point2D>();
        this.csvFile = csvFile;
    }

    //TODO: Errormsg
    @Override
    public void readCsv() throws IOException {
        String line = "";
        BufferedReader br = null;
        int idx = 0;
        br = new BufferedReader(new FileReader(csvFile));
        while ((line = br.readLine()) != null) {
            // use comma as separator
            String[] entries = line.split(cvsSplitBy);
            if (idx == 0) { //description
                File file = new File(csvFile);
                this.pointFile.setName(file.getName());
                this.pointFile.setDescription(entries[1]);
                this.pointFile.setPath(file.getPath());
                idx++;
                continue;
            }

            Point2D p = new Point2D(entries[0], Float.parseFloat(entries[1]), Float.parseFloat(entries[2]));
            this.pointFile.getPoint().add(p);
            Data.cities2d.add(p);
            idx++;
        }
        if (br != null) {
            br.close();
        }
    }

    public PointFile<Point2D> getPointFile() {
        return pointFile;
    }
}
