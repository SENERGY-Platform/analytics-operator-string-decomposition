import org.infai.seits.sepl.operators.Config;
import org.infai.seits.sepl.operators.Message;
import org.infai.seits.sepl.operators.OperatorInterface;

import java.util.regex.Pattern;

public class StringDecomposer implements OperatorInterface {

    Pattern pattern;
    String outputName;

    public StringDecomposer() {
        Config config = new Config();
        String patternString = config.getConfigValue("pattern", "\\s");
        pattern = Pattern.compile(patternString);

        outputName = config.getConfigValue("outputName", "value");
    }

    @Override
    public void run(Message message) {
        String composed = message.getInput("composed").getString();
        String[] split = pattern.split(composed);

        try {
            double val = Double.parseDouble(split[0]);
            message.output(outputName, val);
        } catch(Exception e) {
            if(composed.equals(""))
                System.err.println("Got empty message string");
            else
                System.err.println("Could not parse: " + composed);
            e.printStackTrace();
        }
    }

    @Override
    public void config(Message message) {
        message.addInput("composed");
    }
}
