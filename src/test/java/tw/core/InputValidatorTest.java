package tw.core;

import org.junit.Before;
import org.junit.Test;
import tw.validator.InputValidator;

import static org.junit.Assert.*;

/**
 * 在InputValidatorTest文件中完成InputValidator中对应的单元测试
 */
public class InputValidatorTest {
    private InputValidator inputValidator;
    @Before
    public void setUp() {
        inputValidator=new InputValidator();
    }
    @Test
    public void should_validateSingleGigit_method_then_numList_size_is_less_than_4() throws Exception {
        assertFalse(inputValidator.validate("1 2 3"));
    }

    @Test
    public void should_validateDigitsCount_method_then_numList_size_is_4() throws Exception {
        assertTrue(inputValidator.validate("1 2 3 4"));
    }

    @Test
    public void should_validateDigitsCount_method_after_filter_repeat_num_then_numList_size_is_less_than_4() throws Exception {
        assertFalse(inputValidator.validate("1 2 3 3"));
    }
    @Test
    public void should_validateDigitsCount_method_after_filter_outOfRange_num_then_numList_size_is_less_than_4() throws Exception {
        assertFalse(inputValidator.validate("1 2 3 12"));
    }
    @Test
    public void should_validateDigitsCount_method_after_filter_num_then_numList_size_is_4() throws Exception {
        assertFalse(inputValidator.validate("1 2 3 4 4 12"));
    }
}
