import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Corretora {
    private static final String broker = "BROKER";
    private static final String bolsadevalores = "BOLSADEVALORES";
    private Connection connection;

    public Corretora(Connection connection) {
        this.connection = connection;
    }

    public String publishCompra(String acao, int quant, double val) {
        return publish(acao, quant, val, "Compra");
    }

    public String publishVenda(String acao, int quant, double val) {
        return publish(acao, quant, val, "Venda");
    }

    String publish(String acao, int quant, double val, String operacao) {
        String message = "";
        try {
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(broker, "topic");

            String routingKey = operacao + "." + acao;
            message = "' Quantidade: " + quant + ", Valor: " + val + "'";

            channel.basicPublish(broker, routingKey, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");
            channel.close();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
        return message;
    }
}

