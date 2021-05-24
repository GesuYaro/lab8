package collectionmanager;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Класс, умеющий преобразовывать коллекцию в CSV формат
 */
public class CollectionToCSV {

    /**
     * Преобразовывает коллекцию в строку в CSV формате
     * @param arrayList Коллекция элементов, реализующих интерфейс CSVConvertible
     * @return Строка в CSV формате
     */
    public static String toCSV(ArrayList<? extends CSVConvertible> arrayList) {
        String CSVString = "";
        for (Iterator<? extends CSVConvertible> it = arrayList.iterator(); it.hasNext(); ) {
            CSVString += it.next().toCSV() + "\n";
        }
        return CSVString;
    }
}
