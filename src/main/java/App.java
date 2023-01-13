import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Person generate, sort, write into file as an object, read
 */
public class App {

    public static final String[] FIRST_NAME = {"Ivan", "John", "Vitali", "Gena", "Kolya", "Alina", "Tanya", "Sonya"};
    public static final String[] SECOND_NAME = {"Ivanov(a)", "Petrov(a)", "Sidorov(a)", "Popov(a)", "Kovalyov(a)"};
    public static final int AGE_BOUND = 16;
    public static final int AGE_BOUND_INCREMENT = 15;
    public static final int PERSON_QUANTITY = 100;
    public static final Random RANDOM = new Random();
    public static final String FILE_PATH = "src/main/java/person_list.txt";
    public static final String UP_TO_21 = "AGE UP TO 21 : ";
    public static final String SORTED_BY_SURNAME_THEN_NAME = "SORTED BY SURNAME THEN NAME : ";
    public static final String REMOVE_DUPLICATES = "REMOVE DUPLICATES : ";

    public static void main(String[] args) {

        List<Person> personList = createPersonList();

        sortPersonList(personList);

        File file = createFile();
        writePersonsAsObjectsIntoFile(personList, file);

        List<Person> personListFromFile = new ArrayList<>();
        readPersonsAsObjectsFromFile(file, personListFromFile);

        createPersonListFromNameAndSurname(personListFromFile);
    }

    private static void createPersonListFromNameAndSurname(List<Person> personListFromFile) {
        List<String> resultingList = personListFromFile.stream()
                .map(p -> p.getName() + " " + p.getSurname())
                .collect(Collectors.toList());
        for (String s : resultingList) {
            System.out.println(s);
        }
    }

    private static void readPersonsAsObjectsFromFile(File file, List<Person> personListFromFile) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            while (file.canRead()) {
                personListFromFile.add((Person) ois.readObject());
                System.out.println();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.getStackTrace();
        }
    }

    private static void writePersonsAsObjectsIntoFile(List<Person> personList, File file) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            for (Person p : personList
            ) {
                oos.writeObject(p);
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    private static File createFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.getStackTrace();
            }
        }
        return file;
    }

    private static void sortPersonList(List<Person> personList) {
        personList.stream()
                .filter(p -> p.getAge() < 21)
                .peek(p -> System.out.println(UP_TO_21 + p))
                .sorted(Comparator.comparing(Person::getSurname).thenComparing(Person::getName))
                .peek(p -> System.out.println(SORTED_BY_SURNAME_THEN_NAME + p))
                .distinct()
                .collect(Collectors.toList())
                .forEach(p -> System.out.println(REMOVE_DUPLICATES + p));
    }

    static List<Person> createPersonList() {
        List<Person> personList = Stream
                .generate(() -> new Person(FIRST_NAME[RANDOM.nextInt(FIRST_NAME.length)],
                        SECOND_NAME[RANDOM.nextInt(SECOND_NAME.length)], RANDOM.nextInt(AGE_BOUND) + AGE_BOUND_INCREMENT))
                .limit(PERSON_QUANTITY)
                .collect(Collectors.toList());
        return personList;
    }
}