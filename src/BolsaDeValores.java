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
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqps://zhqjjnph:EdkyKTi_W-8StS1kyqVOh0crCxhZLZmU@hawk.rmq.cloudamqp.com/zhqjjnph");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(broker, "topic");

        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, broker, "#");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        // Cria uma thread para consumir as mensagens da bolsa de valores em segundo plano
        Thread bolsaThread = new Thread(() -> {
            try {
                Consumer consumer = new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope,
                                               AMQP.BasicProperties properties, byte[] body) throws IOException {
                        String message = new String(body, "UTF-8");

                        System.out.println(" [x] Received '" + envelope.getRoutingKey() + ":" + message + "'");

                        processarMensagem(message);
                    }
                };
                channel.basicConsume(queueName, true, consumer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        bolsaThread.start(); // Inicia a thread para consumir as mensagens

        // Continua com o resto do código...
        channel.exchangeDeclare(bolsadevalores, "topic");
        String routingKey = getRouting(argv);
        String message = getMessage(argv);
        channel.basicPublish(bolsadevalores, routingKey, null, message.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");
    }

    private static void processarMensagem(String message) {
        // Lógica para processar a mensagem recebida (exemplo: atualizar livro de ofertas)
        System.out.println(" [x] Processando mensagem: " + message);
    }

    private static String getRouting(String[] strings) {
        if (strings.length < 1)
            return "anonymous.info";
        return strings[0]; // Retorna o primeiro elemento do array
    }

    private static String getMessage(String[] strings) {
        if (strings.length < 2)
            return "Hello World!";
        return joinStrings(strings, " ", 1); // Concatena os elementos a partir do segundo elemento do array
    }

    private static String joinStrings(String[] strings, String delimiter, int startIndex) {
        int length = strings.length;
        if (length == 0) return "";
        if (length < startIndex) return "";
        StringBuilder words = new StringBuilder(strings[startIndex]);
        for (int i = startIndex + 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString(); // Retorna a concatenação dos elementos do array a partir do índice especificado
    }
}
