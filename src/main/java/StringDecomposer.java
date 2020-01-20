import org.infai.seits.sepl.operators.Config;
import org.infai.seits.sepl.operators.Message;
import org.infai.seits.sepl.operators.OperatorInterface;

import java.util.regex.Pattern;

public class StringDecomposer implements OperatorInterface {

    Pattern pattern;
    String outputName;
    Boolean convertToDouble;

    public StringDecomposer() {
        Config config = new Config();
        String patternString = config.getConfigValue("pattern", "\\s");
        pattern = Pattern.compile(patternString);

        outputName = config.getConfigValue("outputName", "value");
        convertToDouble = Boolean.parseBoolean(config.getConfigValue("convertToDouble", "True"));
    }

    @Override
    public void run(Message message) {
        String composed = message.getInput("composed").getString();
        String[] split = pattern.split(composed);

        if (convertToDouble) {
            double val = Double.parseDouble(split[0]);
            message.output(outputName, val);
        } else {
            message.output(outputName, split[0]);
        }

    }

    @Override
    public void config(Message message) {
        message.addInput("composed");
    }
}
