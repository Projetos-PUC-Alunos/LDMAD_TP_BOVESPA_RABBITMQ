import com.rabbitmq.client.Connection;

public class ServicoMensageria {
    private static final String URL_RABBITMQ = "amqps://zhqjjnph:EdkyKTi_W-8StS1kyqVOh0crCxhZLZmU@hawk.rmq.cloudamqp.com/zhqjjnph";

    public static Connection getConnection(boolean compra) {
        Connection conn = null;
        try {
            // Cria uma nova conexão com o RabbitMQ
            RabbitMQConnection rabbitMQConnection = new RabbitMQConnection();
            conn = rabbitMQConnection.getConnection(URL_RABBITMQ);

            // Determina o serviço de mensageria com base na escolha do usuário
            if (compra) {
                // Lógica para retornar o serviço de compra
                System.out.println("Conectado ao serviço de compra");
            } else {
                // Lógica para retornar o serviço de venda
                System.out.println("Conectado ao serviço de venda");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
