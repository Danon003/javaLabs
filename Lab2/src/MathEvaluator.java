import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MathEvaluator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите выражение для вычисления: ");
        String expressionString = scanner.nextLine();

        try {
            // Ищем переменные в выражении
            Map<String, Double> variables = new HashMap<>();
            StringBuilder variableStringBuilder = new StringBuilder();

            // Находим и собираем переменные
            for (String token : expressionString.split("[+\\-*/()]")) {
                token = token.trim();
                if (token.matches("[a-zA-Z]+") && !variables.containsKey(token)) {
                    variableStringBuilder.append(token).append(", ");
                    System.out.print("Введите значение для переменной '" + token + "': ");
                    double value = scanner.nextDouble();
                    scanner.nextLine();
                    variables.put(token, value);
                }
            }


            if (variableStringBuilder.length() > 0) {
                variableStringBuilder.setLength(variableStringBuilder.length() - 2);
            }

            // Строим и вычисляем выражение
            ExpressionBuilder expressionBuilder = new ExpressionBuilder(expressionString);
            for (String variable : variables.keySet()) {
                expressionBuilder.variable(variable);
            }

            Expression expression = expressionBuilder.build();

            for (Map.Entry<String, Double> variable : variables.entrySet()) {
                expression.setVariable(variable.getKey(), variable.getValue());
            }

            double result = expression.evaluate();
            System.out.println("Результат: " + result);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
