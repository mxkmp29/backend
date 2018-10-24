import Reader.CSVReader;

public class Main {

    public static void main(String[] args){
        CSVReader reader = new CSVReader("./Examples/Test_01.csv");
        reader.readCsv();
    }
}
