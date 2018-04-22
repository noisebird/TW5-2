package tw.commands;

import tw.core.Answer;
import tw.validator.InputValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by jxzhong on 2017/5/19.
 */
public class GuessInputCommand implements InputCommand {

    public static final String INPUT_HINT = "------Please input your answer as x x x x , x <10 ------";
    private BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public Answer input() throws IOException {
        System.out.println(INPUT_HINT);
        String input = bufferedReader.readLine();
        return new InputValidator().validate(input) ? Answer.createAnswer(input) : input();
    }
}
