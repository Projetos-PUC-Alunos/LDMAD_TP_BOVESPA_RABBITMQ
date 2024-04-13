import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQConnection {

    public Connection getConnection(String urlRabbitmq) {
        Connection conn = null;
        try {
            
            ConnectionFactory factory = new ConnectionFactory();
            
            factory.setUri(urlRabbitmq);
            conn = factory.newConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
