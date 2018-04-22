package tw.core;

import com.google.inject.Inject;
import tw.core.exception.OutOfRangeAnswerException;
import tw.core.generator.AnswerGenerator;
import tw.core.model.GuessResult;

import java.util.ArrayList;
import java.util.List;

import static tw.core.GameStatus.CONTINUE;
import static tw.core.GameStatus.FAIL;
import static tw.core.GameStatus.SUCCESS;

/**
 * Created by jxzhong on 2017/5/16.
 */
public class Game {

    private static final int MAX_TIMES = 6;
    private final Answer actualAnswer;
    private final List<GuessResult> guessResults;
    private final String CORRECT_RESULT_STANDAR = "4A0B";

    @Inject
    public Game(AnswerGenerator answerGenerator) throws OutOfRangeAnswerException {
        this.actualAnswer = answerGenerator.generate();
        this.guessResults = new ArrayList();
    }

    //猜结果
    public GuessResult guess(Answer inputAnswer) {
        final int[] existRecord = actualAnswer.check(inputAnswer).getValue();
        String result = String.format("%1$sA%2$sB", existRecord[0], existRecord[1]);
        GuessResult guessResult = new GuessResult(result, inputAnswer);
        guessResults.add(guessResult);
        return guessResult;
    }

    public List<GuessResult> guessHistory() {
        return guessResults;
    }

    //判断是否要继续
    public boolean checkCoutinue() {
        return this.checkStatus().equals(CONTINUE);
    }

    //检查状态
    public String checkStatus() {
//       这里有问题，需要把条件的顺序换一下
        if (checkCorrectGuessResult())
            return SUCCESS;
        if (guessResults.size() >= MAX_TIMES)
            return FAIL;
         return CONTINUE;

    }

    private boolean checkCorrectGuessResult() {
        return guessResults.stream().anyMatch(result -> result.getResult().contentEquals(CORRECT_RESULT_STANDAR));
    }
}
