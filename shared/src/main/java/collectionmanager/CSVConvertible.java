package collectionmanager;

/**
 * Интерфейс, который должен реализовывать объект, лежащий в коллекции, которую необходимо представить в CSV формате
 */
public interface CSVConvertible {
    String toCSV();
}
