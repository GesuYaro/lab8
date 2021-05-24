package collectionmanager;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

/**
 * Класс, осуществляющий парсинг CSV
 */
public class Parser {

    /**
     * @param reader Входной поток, содержащий CSV
     * @return Коллекцию коллекций с текстовым значением полей
     * @throws IOException
     */
    public ArrayList<ArrayList<String>> parseCSV(Reader reader) throws IOException, IllegalStateException {
            ArrayList<ArrayList<String>> list = new ArrayList<>();
            CSVParser parser = CSVParser.parse(reader, CSVFormat.RFC4180);
            for (CSVRecord csvRecord : parser) {
                ArrayList<String> insideList = new ArrayList<>();
                for (String record : csvRecord) {
                    insideList.add(record);
                }
                list.add(insideList);
            }
            return list;
    }

    public ArrayList<ArrayList<String>> parseCSV(String string) throws IOException {
        ArrayList<ArrayList<String>> list = new ArrayList<>();
        CSVParser parser = CSVParser.parse(string,CSVFormat.RFC4180);
        for (CSVRecord csvRecord : parser) {
            ArrayList<String> insideList = new ArrayList<>();
            for (String record : csvRecord) {
                insideList.add(record);
            }
            list.add(insideList);
        }
        return list;
    }
}
