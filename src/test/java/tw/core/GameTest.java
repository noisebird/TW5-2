package tw.core;

import org.junit.Before;
import org.junit.Test;
import tw.core.exception.OutOfRangeAnswerException;
import tw.core.generator.AnswerGenerator;
import tw.core.model.GuessResult;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static tw.core.GameStatus.CONTINUE;
import static tw.core.GameStatus.FAIL;
import static tw.core.GameStatus.SUCCESS;

/**
 * 在GameTest文件中完成Game中对应的单元测试
 */


public class GameTest {
    private Game game;

    @Before
    public void setUp() throws OutOfRangeAnswerException {
        Answer actualAnswer = Answer.createAnswer("1 2 3 4");
        AnswerGenerator answerGenerator = mock(AnswerGenerator.class);
        when(answerGenerator.generate()).thenReturn(actualAnswer);
        game = new Game(answerGenerator);
    }

    @Test
    public void should_guess_number_then_return_the_value() throws Exception {
        Answer inputAnswer = Answer.createAnswer("4 5 6 7");
        GuessResult guessResult = game.guess(inputAnswer);
        assertEquals("0A1B", guessResult.getResult());
        assertEquals(inputAnswer.toString(), guessResult.getInputAnswer().toString());

    }

    @Test
    public void should_guess_number_then_show_the_history() throws Exception {
        Answer inputAnswer = Answer.createAnswer("4 5 6 7");
        game.guess(inputAnswer);
        Answer inputAnswer1 = Answer.createAnswer("4 5 6 7");
        game.guess(inputAnswer1);
        List<GuessResult> list = game.guessHistory();
        assertThat(list.size() == 2).isTrue();
        assertEquals("0A1B", list.get(0).getResult());
        assertEquals(inputAnswer.toString(), list.get(0).getInputAnswer().toString());
        assertEquals("0A1B", list.get(1).getResult());
        assertEquals(inputAnswer.toString(), list.get(1).getInputAnswer().toString());
    }

    @Test
    public void should_checkStatus_CONTINUE_then_return_result() throws Exception {
        Answer inputAnswer = Answer.createAnswer("4 5 6 7");
        for (int i = 0; i < 3; i++) {
            game.guess(inputAnswer);
        }
        assertTrue(game.checkCoutinue());
    }

    @Test
    public void should_checkStatus_SUCCESS_then_return_result() throws Exception {
        Answer inputAnswer = Answer.createAnswer("4 5 6 7");
        game.guess(inputAnswer);
        Answer inputAnswer1 = Answer.createAnswer("4 5 6 7");
        game.guess(inputAnswer1);
        Answer inputAnswer2 = Answer.createAnswer("1 2 3 4");
        game.guess(inputAnswer2);
        assertFalse(game.checkCoutinue());
    }

    @Test
    public void should_checkStatus_FALSE_then_return_result() throws Exception {
        Answer inputAnswer = Answer.createAnswer("4 5 6 7");
        for (int i = 0; i < 7; i++) {
            game.guess(inputAnswer);
        }
        assertFalse(game.checkCoutinue());
    }

    @Test
    public void should_check_status_according_to_condition_then_return_SUCCESS() throws Exception {
        Answer inputAnswer = Answer.createAnswer("4 5 6 7");
        game.guess(inputAnswer);
        Answer inputAnswer1 = Answer.createAnswer("4 5 6 7");
        game.guess(inputAnswer1);
        Answer inputAnswer2 = Answer.createAnswer("1 2 3 4");
        game.guess(inputAnswer2);
        assertEquals(game.checkStatus(), SUCCESS);

    }

    @Test
    public void should_check_status_according_to_condition_then_return_FAIL() throws Exception {
        Answer inputAnswer = Answer.createAnswer("4 5 6 7");
        for (int i = 0; i < 7; i++) {
            game.guess(inputAnswer);
        }
        assertEquals(game.checkStatus(), FAIL);
    }

    @Test
    public void should_check_status_according_to_condition_then_return_CONTINUE() throws Exception {
        Answer inputAnswer = Answer.createAnswer("4 5 6 7");
        for (int i = 0; i < 3; i++) {
            game.guess(inputAnswer);
        }
        assertEquals(game.checkStatus(), CONTINUE);
    }

}
