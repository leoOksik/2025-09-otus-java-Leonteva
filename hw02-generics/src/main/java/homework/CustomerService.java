package homework;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

@SuppressWarnings({"java:S1186", "java:S1135", "java:S1172"}) // при выполнении ДЗ эту аннотацию надо удалить
public class CustomerService {

    private final TreeMap<Customer, String> map = new TreeMap<>(Comparator.comparing(Customer::getScores));

    // todo: 3. надо реализовать методы этого класса
    // важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    public Map.Entry<Customer, String> getSmallest() {
        final Map.Entry<Customer, String> mapEntrySmallest = map.firstEntry();
        return mapEntrySmallest == null ? null
            : Map.entry(copyOfCustomer(mapEntrySmallest), mapEntrySmallest.getValue());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        final Map.Entry<Customer, String> mapEntryNext = map.higherEntry(customer);
        return mapEntryNext == null ? null : Map.entry(copyOfCustomer(mapEntryNext), mapEntryNext.getValue());
    }

    public void add(Customer customer, String data) {
        map.put(customer, data);
    }

    private Customer copyOfCustomer(Map.Entry<Customer, String> mapEntry) {
        return new Customer(mapEntry.getKey().getId(), mapEntry.getKey().getName(), mapEntry.getKey().getScores());
    }
}
