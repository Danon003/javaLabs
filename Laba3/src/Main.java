import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Main {

    public static void main(String[] args) {
        int iterations = 1500; // количество итераций для тестирования
        String filePath = "C:\\IntelliJ\\Laba3\\src\\text"; // путь к файлу с результатами

        // Отрисовка таблицы заголовков
        System.out.printf("%-15s %-10s %-10s %-10s%n", "Type of List", "Add (ns)", "Get (ns)", "Remove (ns)");
        System.out.println("---------------------------------------------------------");

        // Тестирование различных типов списков
        testList(new ArrayList<>(), "ArrayList", iterations);
        testList(new LinkedList<>(), "LinkedList", iterations);

        // Организация чтения из файла
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
            StringBuilder text = new StringBuilder();
            String line;
            // Чтение построчно
            while ((line = reader.readLine()) != null)
                text.append(line).append(System.lineSeparator());
            System.out.println(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Тестирует производительность методов.
     *
     * @param list список для тестирования
     * @param type тип списка ("ArrayList" или "LinkedList")
     * @param iterations количество итераций для тестирования
     */
    public static void testList(List<Integer> list, String type, int iterations) {
        int timeAdd = testAdd(list, iterations);
        int timeGet = testGet(list, iterations);
        int timeRemove = testRemove(list, iterations);
        System.out.printf("%-15s %-10d %-10d %-10d%n", type, timeAdd, timeGet, timeRemove);
    }

    /**
     * Измеряет время добавления элементов в список.
     *
     * @param list список, в который добавляются элементы
     * @param iterations количество итераций для тестирования
     * @return время в наносекундах, затраченное на добавление элементов
     */
    public static int testAdd(List<Integer> list, int iterations) {
        long startTime, endTime;
        startTime = System.nanoTime();
        for (int i = 0; i < iterations; i++)
            list.add(i);
        endTime = System.nanoTime();
        return (int)(endTime - startTime);
    }

    /**
     * Измеряет время получения элементов из списка.
     *
     * @param list список, из которого получаются элементы
     * @param iterations количество итераций для тестирования
     * @return время в наносекундах, затраченное на получение элементов
     */
    public static int testGet(List<Integer> list, int iterations) {
        long startTime, endTime;
        startTime = System.nanoTime();
        for (int i = 0; i < iterations; i++)
            list.get(i);
        endTime = System.nanoTime();
        return (int)(endTime - startTime);
    }

    /**
     * Измеряет время удаления элементов из списка.
     *
     * @param list список, из которого удаляются элементы
     * @param iterations количество итераций для тестирования
     * @return время в наносекундах, затраченное на удаление элементов
     */
    public static int testRemove(List<Integer> list, int iterations) {
        long startTime, endTime;

        startTime = System.nanoTime();
        try {
            for (int i = 0; i < iterations; i++)
                if (list.size() > 10)
                    list.remove(10);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds while trying to remove element.");
        }
        endTime = System.nanoTime();

        return (int)(endTime - startTime);
    }
}