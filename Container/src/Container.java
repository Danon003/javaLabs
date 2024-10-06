
public class Container {
    private Object[] items;
    private int size;
    private static final int INITIAL_CAPACITY = 4;

    public Container() {
        items = new Object[INITIAL_CAPACITY];
        size = 0;
    }

    // Метод для добавления элемента
    public void add(Object item) {
        if (size == items.length) {
            resize();
        }
        items[size++] = item;
    }

    // Метод для получения элемента по индексу
    public Object get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Индекс вне диапазона: " + index);
        }
        return items[index];
    }

    // Метод для получения количества элементов
    public int size() {
        return size;
    }

    // Метод для увеличения размера массива
    private void resize() {
        int newCapacity = items.length * 2; // Увеличиваем в два раза
        Object[] newItems = new Object[newCapacity];
        System.arraycopy(items, 0, newItems, 0, items.length);
        items = newItems;
    }

    // Метод для удаления элемента по индексу
    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Индекс вне диапазона: " + index);
        }
        // Сдвигаем элементы влево
        for (int i = index; i < size - 1; i++) {
            items[i] = items[i + 1];
        }
        items[--size] = null; // Уменьшаем размер и обнуляем последний элемент
    }

    public static void main(String[] args) {
        Container container = new Container();

        container.add("Hello");
        container.add(123);
        container.add(45.67);
        container.add(12);
        container.add(4.7);
        container.add(0);
        container.add(457);

        System.out.println("Размер контейнера: " + container.size()); // 7
        System.out.println("Элемент по индексу 0: " + container.get(0)); // Hello
        System.out.println("Элемент по индексу 1: " + container.get(1)); // 123
        System.out.println("Элемент по индексу 2: " + container.get(2)); // 45.67

        container.remove(1); // Удаляем элемент с индексом 1
        System.out.println("Размер контейнера после удаления: " + container.size()); // 2
        System.out.println("Элемент по индексу 1 (после удаления): " + container.get(1)); // 45.67
    }
}
