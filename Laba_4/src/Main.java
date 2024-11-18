import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Main {
    public enum Sex{
        Male,
        Female
    }

    public static class Department{
        int ID;
        String title;


        Department(){
            ID = 0;
            title = " ";
        }

        /**
         * Конструктор для создания нового объекта Department.
         *
         * @param title Название отдела, которое содержит ровно одну заглавную букву латинского алфавита.
         * ID Идентификатор генерируется путем преобразования первой буквы названия в её ASCII-код и вычитания 64.
         * Например, для названия "A" идентификатор будет равен 1, для "B" - 2, и так далее.
         */
        Department(String title){
            ID = title.charAt(0) - 64;
            this.title = title;
        }
        public String getName(){
            return title;
        }
        public int getID(){
            return ID;
        }
    }

    public static class Human{
        int ID;
        String name;
        Sex sex;
        Department department;
        int salary;
        Date birthDate;

        Human(){
            ID = 0;
            name = "";
        }

        Human(int ID, String name, Sex sex, Department department, int salary, Date birthDate){
            this.ID = ID;
            this.name = name;
            this.sex = sex;
            this.department = department;
            this.salary = salary;
            this.birthDate = birthDate;
        }

        /**

         * Формат даты рождения: "дд.Мм.Гггг".
         *
         * @return строковое представление объекта {@code Human} в формате:
         *         "ID: {ID}, Name: {name}, Sex: {sex}, Birth Date: {birthDate},
         *         Department: {departmentName}, ID Department: {departmentID}, Salary: {salary}".
         */
        @Override
        public String toString() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            return "ID: " + ID +
                    ", Name: " + name +
                    ", Sex: " + sex +
                    ", Birth Date: " + dateFormat.format(birthDate)+
                    ", Department: " + department.getName() +
                    ", ID Department " + department.getID() +
                    ", Salary: " + salary;
        }

        public String getName() {
            return name;
        }

        public Sex getSex() {
            return sex;
        }
    }

    /**
     * Читает данные о людях из CSV-файла и возвращает список объектов {@code Human}.
          * Данные в файле должны быть разделены точкой с запятой и содержать следующие поля:
     * ID (целое число), Имя (строка), Пол (строка: "Male" или "Female"),
     * Дата рождения (строка в формате "дд.Мм.Гггг"), Название отдела (строка),
     * Зарплата (целое число)
     *
     * @param filePath путь к CSV-файлу, из которого будут считываться данные.
     * @return список объектов {@code Human}, созданных на основе данных из файла.
     * @throws IOException если происходит ошибка ввода-вывода при чтении файла.
     * @throws ParseException если не удается разобрать дату рождения в заданном формате.
     */


    public static ArrayList<Human> readHumansFromCSV(String filePath) throws IOException, ParseException{

        ArrayList<Human> humans = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;
            boolean firstLine = true;

            while((line = br.readLine()) != null){
                /*
                 Флаг firstLine: Я добавил булеву переменную,
                 которая будет использоваться для отслеживания того, обрабатывается ли первая
                 строку файла. Если это так, то просто пропускаем ее.
                 */

                if(firstLine) {
                    firstLine = false;
                    continue;
                }

                /*
                Парсим строку, используя разделитель.
                И закидываем все наши переменные в конструктор Human.
                 */
                String[] values = line.split(";");
                int id = Integer.parseInt(values[0]);
                String name = values[1];
                Sex sex = values[2].equals("Male")?Sex.Male : Sex.Female;
                Date birthDate = dateFormat.parse(values[3]);
                Department department = new Department(values[4]);
                int salary = Integer.parseInt(values[5]);

                Human human = new Human(id, name, sex, department, salary, birthDate);
                humans.add(human);
            }
        }
        return humans;
    }

    /**
     * Главный метод программы считывает данные о людях из CSV-файла и выводит
     * информацию о первых пяти на экран. Вывод был сделан для проверки работоспособности кода.
     *
     * @throws IOException если происходит ошибка ввода-вывода при чтении файла.
     * @throws ParseException если не удается разобрать дату рождения в заданном формате.
     */

    public static void main(String[] args) throws IOException, ParseException {
       String filePath = "C:\\IntelliJ\\Laba_4\\foreign_names.csv";
       ArrayList<Human> humans =  readHumansFromCSV(filePath);

        for (int i = 0; i < Math.min(5, humans.size()); i++)
            System.out.println(humans.get(i));



    }
}