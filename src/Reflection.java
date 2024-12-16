import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * Класс, реализующий инъекцию зависимостей с использованием рефлексии.
 */
public class Reflection {

    /**
     * Класс для инъекции зависимостей.
     */
    public static class Injector {
        private Properties properties;

        public Injector() {
            loadProperties();
        }

        /**
         * Загружает свойства из файла конфигурации.
         */
        private void loadProperties() {
            try (InputStream inStream = getClass().getResourceAsStream("config.properties")) {
                properties = new Properties();
                properties.load(inStream);
            } catch (IOException e) {
                throw new RuntimeException("Ошибка загрузки файла", e);
            }
        }

        /**
         * Инъектирует зависимости в переданный объект.
         *
         * @param object объект, в который будут инъектированы зависимости
         * @param <T> тип объекта
         * @return объект с инъектированными зависимостями
         */

        public <T> T inject(T object) {
            Field[] fields = object.getClass().getDeclaredFields();

            for (Field field : fields) {
                if (field.isAnnotationPresent(AutoInjectable.class)) {
                    String interfaceName = field.getType().getName();
                    String implClassName = properties.getProperty(interfaceName);

                    if (implClassName == null || implClassName.trim().isEmpty()) {
                        throw new RuntimeException("Реализация " + interfaceName + " не найдена");
                    }

                    try {
                        Class<?> implClass = Class.forName(implClassName);
                        Constructor<?> constructor = implClass.getConstructor();
                        Object instance = constructor.newInstance();

                        field.setAccessible(true);
                        field.set(object, instance);
                    } catch (ReflectiveOperationException | SecurityException e) {
                        throw new RuntimeException("Ошибка при создании экземпляра класса", e);
                    }
                }
            }
            return object;
        }
    }

    interface SomeInterface {
        void doSomething();
    }

    interface SomeOtherInterface {
        void doSomeOther();
    }

    static class SomeImpl implements SomeInterface {
        public SomeImpl() {}

        public void doSomething() {
            System.out.println("A");
        }
    }

    static class OtherImpl implements SomeInterface {
        public OtherImpl() {}

        public void doSomething() {
            System.out.println("B");
        }
    }

    static class SODoer implements SomeOtherInterface {
        public SODoer() {}

        public void doSomeOther() {
            System.out.println("C");
        }
    }

    static class SomeBean {
        @AutoInjectable
        private SomeInterface field1;
        @AutoInjectable
        private SomeOtherInterface field2;

        public void foo() {
            field1.doSomething();
            field2.doSomeOther();
        }
        public SomeInterface getField1(){
            return field1;
        }
        public SomeOtherInterface getField2(){
            return field2;
        }
    }

    public static void main(String[] args) {
        SomeBean someBean = (new Injector()).inject(new SomeBean());
        someBean.foo();
    }
}