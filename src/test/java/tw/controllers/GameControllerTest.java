package tw.controllers;

import com.google.inject.Injector;
import org.junit.Before;
import org.junit.Test;
import tw.GuessNumberModule;
import tw.commands.GuessInputCommand;
import tw.commands.InputCommand;
import tw.core.Answer;
import tw.core.Game;
import tw.core.exception.OutOfRangeAnswerException;
import tw.core.generator.AnswerGenerator;
import tw.core.generator.RandomIntGenerator;
import tw.core.model.GuessResult;
import tw.views.GameView;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

import static com.google.inject.Guice.createInjector;

/**
 * 在GameControllerTest文件中完成GameController中对应的单元测试
 */
public class GameControllerTest {
    private GameController gameController;
    private AnswerGenerator answerGenerator;
    private InputCommand inputGuess;
    private Answer answer;
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp() throws OutOfRangeAnswerException {
        Answer actualAnswer=Answer.createAnswer("1 2 3 4");
        answerGenerator = mock(AnswerGenerator.class);
        inputGuess = mock(InputCommand.class);
        when(answerGenerator.generate()).thenReturn(actualAnswer);
        gameController = new GameController(new Game(answerGenerator),new GameView());
        System.setOut(new PrintStream(outContent));
        answer = new Answer();
    }

    @Test
    public void should_game_begin_then_retrun_the_same_output() throws Exception {
        gameController.beginGame();
        assertThat(outContent.toString()).startsWith("------Guess Number Game, You have 6 chances to guess!  ------");
    }

    @Test
    public void should_game_continue_then_return_the_status() throws Exception {
        when(inputGuess.input()).thenReturn(Answer.createAnswer("2 3 7 5"))
                .thenReturn(Answer.createAnswer("2 5 4 6"))
                .thenReturn(Answer.createAnswer("2 7 4 6"))
                .thenReturn(Answer.createAnswer("2 8 3 6"))
                .thenReturn(Answer.createAnswer("2 5 7 6"))
                .thenReturn(Answer.createAnswer("2 1 9 6"));
        gameController.play(inputGuess);
        assertThat(outContent.toString()).contains("Guess Result: 0A2B\n"+
                "Guess History:\n" +
                "[Guess Numbers: 2 3 7 5, Guess Result: 0A2B]\n" +
                "[Guess Numbers: 2 5 4 6, Guess Result: 0A2B]\n" +
                "[Guess Numbers: 2 7 4 6, Guess Result: 0A2B]\n" +
                "[Guess Numbers: 2 8 3 6, Guess Result: 1A1B]\n" +
                "[Guess Numbers: 2 5 7 6, Guess Result: 0A1B]\n" +
                "[Guess Numbers: 2 1 9 6, Guess Result: 0A2B]\n"+
                "Game Status: fail");
        verify(inputGuess,times(6)).input();
    }
    @Test
    public void should_game_continue_to_3_then_success() throws Exception {
        when(inputGuess.input()).thenReturn(Answer.createAnswer("2 3 7 5"))
                .thenReturn(Answer.createAnswer("2 5 4 6"))
                .thenReturn(Answer.createAnswer("1 2 3 4"));
        gameController.play(inputGuess);
        assertThat(outContent.toString()).contains("Guess Result: 4A0B\n"+
                "Guess History:\n" +
                "[Guess Numbers: 2 3 7 5, Guess Result: 0A2B]\n" +
                "[Guess Numbers: 2 5 4 6, Guess Result: 0A2B]\n" +
                "[Guess Numbers: 1 2 3 4, Guess Result: 4A0B]\n"+
                "Game Status: success");
///使用verify时必须要是mockito的对象
        verify(inputGuess,times(3)).input();
    }

    @Test
    public void should_game_continue_to_6_then_success() throws Exception {
        when(inputGuess.input()).thenReturn(Answer.createAnswer("2 3 7 5"))
                .thenReturn(Answer.createAnswer("2 5 4 6"))
                .thenReturn(Answer.createAnswer("1 2 3 5"))
                .thenReturn(Answer.createAnswer("1 2 3 8"))
                .thenReturn(Answer.createAnswer("1 5 2 4"))
                .thenReturn(Answer.createAnswer("1 2 3 4"));
        gameController.play(inputGuess);
        assertThat(outContent.toString()).contains("Guess Result: 4A0B\n"+
                "Guess History:\n" +
                "[Guess Numbers: 2 3 7 5, Guess Result: 0A2B]\n" +
                "[Guess Numbers: 2 5 4 6, Guess Result: 0A2B]\n" +
                "[Guess Numbers: 1 2 3 5, Guess Result: 3A0B]\n" +
                "[Guess Numbers: 1 2 3 8, Guess Result: 3A0B]\n" +
                "[Guess Numbers: 1 5 2 4, Guess Result: 2A1B]\n" +
                "[Guess Numbers: 1 2 3 4, Guess Result: 4A0B]\n"+
                "Game Status: success");
        verify(inputGuess,times(6)).input();
    }
    @Test
    public void should_game_success_then_return_the_status() throws Exception {
        Game game = mock(Game.class);
        when(game.checkCoutinue()).thenReturn(false);
        when(game.checkStatus()).thenReturn("success");
        assertEquals("success", game.checkStatus());
    }
    @Test
    public void should_game_end_then_return_the_status() throws Exception {
        Game game = mock(Game.class);
        when(game.checkCoutinue()).thenReturn(false);
        when(game.checkStatus()).thenReturn("fail");
        assertEquals("fail", game.checkStatus());
    }
}