import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MathEvaluatorTest {

    private MathEvaluator mathEvaluator;

    @BeforeEach
    public void setUp() {
        mathEvaluator = new MathEvaluator();
    }

    private double evaluateExpression(String expression, Map<String, Double> variables) {
        ExpressionBuilder expressionBuilder = new ExpressionBuilder(expression);
        for (String variable : variables.keySet()) {
            expressionBuilder.variable(variable);
        }
        Expression exp = expressionBuilder.build();

        for (Map.Entry<String, Double> variable : variables.entrySet()) {
            exp.setVariable(variable.getKey(), variable.getValue());
        }

        return exp.evaluate();
    }

    @Test
    public void testSimpleAddition() {
        Map<String, Double> variables = new HashMap<>();
        variables.put("x", 5.0);
        variables.put("y", 10.0);
        double result = evaluateExpression("x + y", variables);
        assertEquals(15.0, result);
    }

    @Test
    public void testWithMultiplication() {
        Map<String, Double> variables = new HashMap<>();
        variables.put("x", 5.0);
        variables.put("y", 10.0);
        double result = evaluateExpression("x * y + 5", variables);
        assertEquals(55.0, result);
    }

    @Test
    public void testWithDivision() {
        Map<String, Double> variables = new HashMap<>();
        variables.put("x", 10.0);
        variables.put("y", 2.0);
        double result = evaluateExpression("x / y", variables);
        assertEquals(5.0, result);
    }

    @Test
    public void testWithMultipleVariables() {
        Map<String, Double> variables = new HashMap<>();
        variables.put("a", 3.0);
        variables.put("b", 4.0);
        variables.put("c", 5.0);
        double result = evaluateExpression("a + b * c", variables);
        assertEquals(23.0, result);
    }




}
