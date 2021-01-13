import org.infai.ses.senergy.exceptions.NoValueException;
import org.infai.ses.senergy.models.DeviceMessageModel;
import org.infai.ses.senergy.models.MessageModel;
import org.infai.ses.senergy.operators.Config;
import org.infai.ses.senergy.operators.Helper;
import org.infai.ses.senergy.operators.Message;
import org.infai.ses.senergy.testing.utils.JSONHelper;
import org.infai.ses.senergy.utils.ConfigProvider;
import org.json.simple.JSONArray;
import org.junit.Assert;
import org.junit.Test;


public class StringDecomposerTest {

    @Test
    public void run() {
        Config config = new Config(new JSONHelper().parseFile("config.json").toString());
        JSONArray messages = new JSONHelper().parseFile("messages.json");
        String topicName = config.getInputTopicsConfigs().get(0).getName();
        ConfigProvider.setConfig(config);
        Message message = new Message();
        MessageModel model = new MessageModel();
        StringDecomposer decomposer = new StringDecomposer();
        decomposer.configMessage(message);
        message.addInput("decomposed");
        for (Object msg : messages) {
            DeviceMessageModel deviceMessageModel = JSONHelper.getObjectFromJSONString(msg.toString(), DeviceMessageModel.class);
            assert deviceMessageModel != null;
            model.putMessage(topicName, Helper.deviceToInputMessageModel(deviceMessageModel, topicName));
            message.setMessage(model);
            decomposer.run(message);

            try {
                double expected = message.getInput("decomposed").getValue();
                double actual = (double) message.getMessage().getOutputMessage().getAnalytics().get("value");
                Assert.assertEquals(expected, actual, 0.001);
                System.out.println("Successfully decomposed.");
            } catch (NoValueException | NullPointerException | IndexOutOfBoundsException e) {
                System.out.println("Skipped test because no expected values were provided.");
            } catch (NumberFormatException e) {
                Assert.fail("Failed test: Could not read value");
            }
        }
    }
}
