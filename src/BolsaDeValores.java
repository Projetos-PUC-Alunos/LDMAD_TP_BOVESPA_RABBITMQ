import java.io.IOException;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class BolsaDeValores {
     private static final String broker = "BROKER";
     private static final String bolsadevalores = "BOLSADEVALORES";

    public static void main(String[] argv) throws Exception {
        //argv[1] = "#";
        ConnectionFactory factory = new ConnectionFactory();
        
        // Configuração da URI para se conectar ao CloudAMQP
        factory.setUri("amqps://zhqjjnph:EdkyKTi_W-8StS1kyqVOh0crCxhZLZmU@hawk.rmq.cloudamqp.com/zhqjjnph");
        
        Connection connection = factory.newConnection();
             Channel channel = connection.createChannel(); 
             channel.exchangeDeclare(broker, "topic");
            String queueName = channel.queueDeclare().getQueue();
            /*if (argv.length < 1) {
                System.err.println("Usage: ReceiveLogsTopic [binding_key]...");
                System.exit(1);
            }*/

            for (String bindingKey : argv) {
                channel.queueBind(queueName, broker, bindingKey);
            }

            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body) throws IOException {
                  String message = new String(body, "UTF-8");
                  
                  System.out.println(" [x] Received '"+ envelope.getRoutingKey() + ":" + message + "'");
                  
                }
              };
              channel.basicConsume(queueName, true, consumer);
            
        
            channel.exchangeDeclare(bolsadevalores, "topic");

            String routingKey = getRouting(argv);
            String message = getMessage(argv);

            channel.basicPublish(bolsadevalores, routingKey, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");
        
    }

    private static String getRouting(String[] strings) {
        if (strings.length < 1)
            return "anonymous.info";
        return strings[0];
    }

    private static String getMessage(String[] strings) {
        if (strings.length < 2)
            return "Hello World!";
        return joinStrings(strings, " ", 1);
    }

    private static String joinStrings(String[] strings, String delimiter, int startIndex) {
        int length = strings.length;
        if (length == 0) return "";
        if (length < startIndex) return "";
        StringBuilder words = new StringBuilder(strings[startIndex]);
        for (int i = startIndex + 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }
}
