package tw.core;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import tw.core.exception.OutOfRangeAnswerException;
import tw.core.model.Record;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * 在AnswerTest文件中完成Answer中对应的单元测试
 */
public class AnswerTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    private Answer answer;
    @Before
    public final void before() {
        answer = new Answer();
    }

    @Test
    public void should_createAnswer_method_will_return_answer_object() {
        answer = new Answer();
        answer.setNumList(Arrays.asList("1", "2", "3", "4"));
        assertEquals(answer.toString(), answer.createAnswer("1 2 3 4").toString());
    }

    @Test
    public void should_validate_method_throw_OutOfRangeAnswerException_when_input_has_repeat_number() throws OutOfRangeAnswerException {
        exception.expect(OutOfRangeAnswerException.class);
        exception.expectMessage("Answer format is incorrect");
        answer = Answer.createAnswer("1 2 3 3");
        answer.validate();
    }

    @Test
    public void should_validate_throw_OutOfRangeAnswerException_when_answer_contains_number_greater_than_9() throws OutOfRangeAnswerException {
        exception.expect(OutOfRangeAnswerException.class);
        exception.expectMessage("Answer format is incorrect");
        answer=Answer.createAnswer("10 11 2 4");
        answer.validate();
    }

    @Test
    public void should_validate_throw_OutOfRangeAnswerException_when_input_is_normal() throws OutOfRangeAnswerException {
        answer=Answer.createAnswer("1 2 3 4");
        answer.validate();
    }

    @Test
    public void should_check_method_return_the_correct_record_has_three_right_but_position_different() throws Exception {
        Answer actualAnswer=Answer.createAnswer("1 2 3 4");
        answer=Answer.createAnswer("2 3 4 5");
        Record record=actualAnswer.check(answer);
        assertEquals(record.getValue()[0],0);
        assertEquals(record.getValue()[1],3);
    }
    @Test
    public void should_check_method_return_the_correct_record_is_all_right() throws Exception {
        Answer actualAnswer=Answer.createAnswer("1 2 3 4");
        answer=Answer.createAnswer("1 2 3 4");
        Record record=actualAnswer.check(answer);
        assertEquals(record.getValue()[0],4);
        assertEquals(record.getValue()[1],0);
    }
    @Test
    public void should_check_method_return_the_correct_record_is_all_wrong() throws Exception {
        Answer actualAnswer=Answer.createAnswer("1 2 3 4");
        answer=Answer.createAnswer("5 6 7 8");
        Record record=actualAnswer.check(answer);
        assertEquals(record.getValue()[0],0);
        assertEquals(record.getValue()[1],0);
    }

    @Test
    public void should_toString_method_will_return_the_same_string() throws Exception {
        answer=Answer.createAnswer("1 2 3 4");
        assertEquals("1 2 3 4",answer.toString());
    }
}