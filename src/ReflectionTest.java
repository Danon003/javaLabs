import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// ... (ваш исходный код здесь) ...

import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.util.Properties;

class ReflectionTest {

    @Test
    void testSuccessfulInjection() {
        Properties props = new Properties();
        props.setProperty(Reflection.SomeInterface.class.getName(), Reflection.SomeImpl.class.getName());
        props.setProperty(Reflection.SomeOtherInterface.class.getName(), Reflection.SODoer.class.getName());

        Reflection.Injector injector = new Reflection.Injector();
        // переопределяем свойства для теста
        Field field = null;
        try {
            field = Reflection.Injector.class.getDeclaredField("properties");
            field.setAccessible(true);
            field.set(injector,props);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }


        Reflection.SomeBean bean = injector.inject(new Reflection.SomeBean());
        bean.foo(); // Выведет "A" и "C"
        assertEquals(Reflection.SomeImpl.class, bean.getField1().getClass());
        assertEquals(Reflection.SODoer.class, bean.getField2().getClass());
    }

    @Test
    void testMissingImplementation() {
        Properties props = new Properties();
        props.setProperty(Reflection.SomeOtherInterface.class.getName(), Reflection.SODoer.class.getName());

        Reflection.Injector injector = new Reflection.Injector();
        Field field = null;
        try {
            field = Reflection.Injector.class.getDeclaredField("properties");
            field.setAccessible(true);
            field.set(injector,props);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }


        assertThrows(RuntimeException.class, () -> injector.inject(new Reflection.SomeBean()));
    }

    @Test
    void testInvalidImplementationClassName() {
        Properties props = new Properties();
        props.setProperty(Reflection.SomeInterface.class.getName(), "NonExistentClass");
        props.setProperty(Reflection.SomeOtherInterface.class.getName(), Reflection.SODoer.class.getName());

        Reflection.Injector injector = new Reflection.Injector();
        Field field = null;
        try {
            field = Reflection.Injector.class.getDeclaredField("properties");
            field.setAccessible(true);
            field.set(injector,props);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }


        assertThrows(RuntimeException.class, () -> injector.inject(new Reflection.SomeBean()));
    }

    @Test
    void testNoDefaultConstructor() {
        Properties props = new Properties();
        props.setProperty(Reflection.SomeInterface.class.getName(), Reflection.SomeImpl.class.getName());
        props.setProperty(Reflection.SomeOtherInterface.class.getName(), NoDefaultConstructor.class.getName());

        Reflection.Injector injector = new Reflection.Injector();
        Field field = null;
        try {
            field = Reflection.Injector.class.getDeclaredField("properties");
            field.setAccessible(true);
            field.set(injector,props);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        assertThrows(RuntimeException.class, () -> injector.inject(new Reflection.SomeBean()));
    }


    @Test
    void testInjectionIntoFieldWithoutAnnotation() {
        Properties props = new Properties();
        props.setProperty(Reflection.SomeInterface.class.getName(), Reflection.SomeImpl.class.getName());
        props.setProperty(Reflection.SomeOtherInterface.class.getName(), Reflection.SODoer.class.getName());

        Reflection.Injector injector = new Reflection.Injector();
        Field field = null;
        try {
            field = Reflection.Injector.class.getDeclaredField("properties");
            field.setAccessible(true);
            field.set(injector,props);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        SomeBeanWithNonAnnotatedField bean = injector.inject(new SomeBeanWithNonAnnotatedField());
        assertNull(bean.getNonAnnotatedField()); // поле без аннотации не должно быть инъектировано
    }



    static class NoDefaultConstructor implements Reflection.SomeOtherInterface {
        public NoDefaultConstructor(String arg) {}
        @Override
        public void doSomeOther() {}
    }

    static class SomeBeanWithNonAnnotatedField {
        @AutoInjectable
        private Reflection.SomeInterface field1;
        private Reflection.SomeOtherInterface nonAnnotatedField;

        public Reflection.SomeInterface getField1(){
            return field1;
        }
        public Reflection.SomeOtherInterface getNonAnnotatedField(){
            return nonAnnotatedField;
        }
    }
}