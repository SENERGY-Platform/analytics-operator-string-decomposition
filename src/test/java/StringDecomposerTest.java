import org.infai.seits.sepl.operators.Message;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class StringDecomposerTest {

    @Test
    public void run() throws Exception {
        StringDecomposer decomposer = new StringDecomposer();
        List<Message> messages = TestMessageProvider.getTestMesssagesSet();
        for (int i = 0; i < messages.size(); i++) {
            Message m = messages.get(i);
            decomposer.config(m);
            decomposer.run(m);

            try {
                m.addInput("decomposed");
                double expected = m.getInput("decomposed").getValue();
                double actual = Double.parseDouble(m.getMessageString().split("value\":")[1].split("}")[0]);
                Assert.assertEquals(expected, actual, 0.001);
                System.out.println("Successfully decomposed.");
            } catch (NullPointerException | IndexOutOfBoundsException e) {
                System.out.println("Skipped test for day because no expected values were provided.");
            } catch (NumberFormatException e) {
                Assert.fail("Failed test: Could not read value");
            }
        }
    }
}
