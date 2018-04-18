package tw.core.generator;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import tw.core.Answer;
import tw.core.exception.OutOfRangeAnswerException;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * 在AnswerGeneratorTest文件中完成AnswerGenerator中对应的单元测试
 */
public class AnswerGeneratorTest {
    private RandomIntGenerator randomIntGenerator;
    private AnswerGenerator answerGenerator;
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        randomIntGenerator = mock(RandomIntGenerator.class);
        answerGenerator = new AnswerGenerator(randomIntGenerator);
    }

    @Test
    public void should_generate_the_object_of_answer() throws Exception {
        when(randomIntGenerator.generateNums(9, 4)).thenReturn("1 2 3 4");
        AnswerGenerator answerGenerator = new AnswerGenerator(randomIntGenerator);
        Answer answer = answerGenerator.generate();
        assertTrue(answer.toString().equals("1 2 3 4"));
    }

    @Test
    public void should_generate_method_happen_error_when_input_size_is_repeat() throws OutOfRangeAnswerException {
        when(randomIntGenerator.generateNums(9, 4)).thenReturn("1 2 3 3");
        exception.expect(OutOfRangeAnswerException.class);
        exception.expectMessage("Answer format is incorrect");
        answerGenerator.generate();
    }
    @Test
    public void should_generate_method_happen_error_when_input_is_more_than_10() throws OutOfRangeAnswerException {
        when(randomIntGenerator.generateNums(9, 4)).thenReturn("1 2 3 13");
        exception.expect(OutOfRangeAnswerException.class);
        exception.expectMessage("Answer format is incorrect");
        answerGenerator.generate();
    }
}

