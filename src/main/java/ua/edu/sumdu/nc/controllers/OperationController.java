package ua.edu.sumdu.nc.controllers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.edu.sumdu.nc.beans.Operation;
import ua.edu.sumdu.nc.dao.DAO;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

@Controller
@Getter
@Setter
public class OperationController {
    @Resource(name = "dao")
    private DAO dao;
    private static Map<String, Character> operationChars = new HashMap<>();
    private static Map<Character, BiFunction<Double, Double, Double>> calculations = new HashMap<>();

    static {
        operationChars.put("add", '+');
        operationChars.put("subtract", '-');
        operationChars.put("multiply", '*');
        operationChars.put("divide", '/');

        calculations.put('+', Double::sum);
        calculations.put('-', (one, two) -> one - two);
        calculations.put('*', (one, two) -> one * two);
        calculations.put('/', (one, two) -> one / two);
    }

    @RequestMapping(value = "/calculate", method = RequestMethod.POST)
    public ModelAndView add(@RequestBody(required = true) Operation operation, @RequestHeader(value = "User-ID", required = true) int userId) {
        ModelAndView modelAndView = new ModelAndView();
        double one = operation.getOne();
        double two = operation.getTwo();
        char op = operationChars.get(operation.getOperation());
        modelAndView.getModelMap().addAttribute("one", one);
        modelAndView.getModelMap().addAttribute("two", two);
        try {
            modelAndView.getModelMap().addAttribute("result", calculations.get(op).apply(one, two));
            modelAndView.getModelMap().addAttribute("operationChar", op);
            modelAndView.setViewName("result");
            recordUserOperation(modelAndView, userId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return modelAndView;
    }

    private void recordUserOperation(ModelAndView modelAndView, int userId) {
        try (Connection connection = dao.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO OPERATIONS (USERID, ONE, TWO, OPERATION, RESULT) VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setDouble(1, userId);
            preparedStatement.setDouble(2, (double) modelAndView.getModelMap().get("one"));
            preparedStatement.setDouble(3, (double) modelAndView.getModelMap().get("two"));
            preparedStatement.setString(4, String.valueOf(modelAndView.getModelMap().get("operationChar")));
            preparedStatement.setDouble(5, (double) modelAndView.getModelMap().get("result"));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
