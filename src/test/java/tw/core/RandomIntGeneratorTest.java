
package tw.core;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import tw.core.exception.OutOfRangeAnswerException;
import tw.core.generator.RandomIntGenerator;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * 在RandomIntGeneratorTest文件中完成RandomIntGenerator中对应的单元测试
 */
public class RandomIntGeneratorTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();
    private RandomIntGenerator randomIntGenerator;

    @Before
    public final void before() {
        randomIntGenerator = new RandomIntGenerator();
    }

    @Test
    public void should_generate_then_correct_number_happen_error() throws IllegalArgumentException {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Can't ask for more numbers than are available");
        randomIntGenerator.generateNums(2, 4);
    }

    @Test
    public void should_generate_method_will_produce_the_number_of_request() throws Exception {
        String randNum=randomIntGenerator.generateNums(9, 5);
        assertEquals(5,randNum.split(" ").length);
    }

    @Test
    public void should_generat_method_produce_the_number_is_less_than_10() throws Exception {
        String randNum=randomIntGenerator.generateNums(9, 4);
        assertTrue(Arrays.stream(randNum.split(" ")).allMatch(item->Integer.parseInt(item)< 10));

    }

    @Test
    public void should_generate_mehod_will_produce_no_repeat_number() throws Exception {
        String randNum=randomIntGenerator.generateNums(9, 4);
        assertTrue(4==Arrays.stream(randNum.split(" ")).distinct().count());
    }


}