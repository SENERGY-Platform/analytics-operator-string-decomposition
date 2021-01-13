import org.infai.ses.senergy.exceptions.NoValueException;
import org.infai.ses.senergy.operators.BaseOperator;
import org.infai.ses.senergy.operators.Config;
import org.infai.ses.senergy.operators.Message;
import org.infai.ses.senergy.utils.ConfigProvider;

import java.util.regex.Pattern;

public class StringDecomposer extends BaseOperator {

    Pattern pattern;
    String outputName;

    public StringDecomposer() {
        Config config = ConfigProvider.getConfig();
        String patternString = config.getConfigValue("pattern", "\\s");
        pattern = Pattern.compile(patternString);

        outputName = config.getConfigValue("outputName", "value");
    }

    @Override
    public void run(Message message) {
        String composed;
        Double composedDouble = null;
        try {
            composed = message.getInput("composed").getString();
            if (composed.length() == 0) {
                composedDouble = message.getInput("composed").getValue();
            }
        } catch (NullPointerException | NoValueException n) {
            System.err.println("Empty message value. Message ignored");
            return;
        }

        if (composedDouble != null) {
            composed = composedDouble.toString();
        }
        String[] split = pattern.split(composed);

        try {
            double val = Double.parseDouble(split[0]);
            message.output(outputName, val);
        } catch (Exception e) {
            if (composed.equals(""))
                System.err.println("Got empty message string");
            else
                System.err.println("Could not parse: " + composed);
            e.printStackTrace();
        }
    }

    @Override
    public Message configMessage(Message message) {
        message.addInput("composed");
        return message;
    }
}
