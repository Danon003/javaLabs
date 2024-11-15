import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void testAdd() {
        List<Integer> list = new ArrayList<>();
        int valueToAdd = 42;

        list.add(valueToAdd);

        assertEquals(1, list.size(), "List size should be 1 after adding one element");
        assertEquals(valueToAdd, list.get(0), "The added value should be at index 0");
    }

    @Test
    void testGet() {
        List<Integer> list = new ArrayList<>();
        int valueToAdd = 42;
        list.add(valueToAdd);
        int retrievedValue = list.get(0);

        assertEquals(valueToAdd, retrievedValue, "Retrieved value should match the added value");

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(1); // Индекс 1 не существует
        });
    }

    @Test
    void testRemove() {
        List<Integer> list = new ArrayList<>();
        int valueToAdd = 42;
        list.add(valueToAdd);

        boolean removed = list.remove((Integer) valueToAdd);
        assertTrue(removed, "Remove should return true when the element is successfully removed");
        assertEquals(0, list.size(), "List size should be 0 after removing the element");

        removed = list.remove((Integer) valueToAdd);
        assertFalse(removed, "Remove should return false when the element does not exist in the list");
    }

    @Test
    void testPerformance() {
        int iterations = 1500;

        List<Integer> arrayList = new ArrayList<>();
        int timeAddArrayList = Main.testAdd(arrayList, iterations);
        int timeGetArrayList = Main.testGet(arrayList, iterations);
        int timeRemoveArrayList = Main.testRemove(arrayList, iterations);

        System.out.printf("ArrayList - Add: %d ns, Get: %d ns, Remove: %d ns%n", timeAddArrayList, timeGetArrayList, timeRemoveArrayList);

        List<Integer> linkedList = new LinkedList<>();
        int timeAddLinkedList = Main.testAdd(linkedList, iterations);
        int timeGetLinkedList = Main.testGet(linkedList, iterations);
        int timeRemoveLinkedList = Main.testRemove(linkedList, iterations);

        System.out.printf("LinkedList - Add: %d ns, Get: %d ns, Remove: %d ns%n", timeAddLinkedList, timeGetLinkedList, timeRemoveLinkedList);
    }
}
