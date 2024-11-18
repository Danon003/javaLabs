import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    private static final String TEST_CSV_FILE = "test_humans.csv";

    @BeforeEach
    public void setUp() throws IOException {
        // Создание тестового CSV-файла перед каждым тестом
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_CSV_FILE))) {
            writer.write("ID;Name;Sex;BirthDate;Department;Salary\n");
            writer.write("1;John Doe;Male;15.05.1990;HR;50000\n");
            writer.write("2;Jane Smith;Female;20.10.1985;IT;60000\n");
        }
    }

    @Test
    public void testReadHumansFromCSV() throws IOException, ParseException {
        ArrayList<Main.Human> humans = Main.readHumansFromCSV(TEST_CSV_FILE);

        assertEquals(2, humans.size());

        Main.Human firstHuman = humans.get(0);
        assertEquals(1, firstHuman.ID);
        assertEquals("John Doe", firstHuman.getName());
        assertEquals(Main.Sex.Male, firstHuman.getSex());
        assertEquals(50000, firstHuman.salary);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        assertEquals(dateFormat.parse("15.05.1990"), firstHuman.birthDate);

        Main.Human secondHuman = humans.get(1);
        assertEquals(2, secondHuman.ID);
        assertEquals("Jane Smith", secondHuman.getName());
        assertEquals(Main.Sex.Female, secondHuman.getSex());
        assertEquals(60000, secondHuman.salary);
        assertEquals(dateFormat.parse("20.10.1985"), secondHuman.birthDate);
    }

    @Test
    public void testDepartmentIDCalculation() {
        Main.Department departmentA = new Main.Department("A");
        assertEquals(1, departmentA.getID());

        Main.Department departmentB = new Main.Department("B");
        assertEquals(2, departmentB.getID());

        Main.Department departmentZ = new Main.Department("Z");
        assertEquals(26, departmentZ.getID());
    }

    @Test
    public void testToString() throws ParseException {
        Main.Department department = new Main.Department("HR");
        Date birthDate = new SimpleDateFormat("dd.MM.yyyy").parse("15.05.1990");
        Main.Human human = new Main.Human(1, "John Doe", Main.Sex.Male, department, 50000, birthDate);

        String expectedString = "ID: 1, Name: John Doe, Sex: Male, Birth Date: 15.05.1990, Department: HR, ID Department 8, Salary: 50000";

        assertEquals(expectedString, human.toString());
    }
}