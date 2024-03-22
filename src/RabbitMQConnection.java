import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQConnection {

    public static Connection getConnection() {
        Connection conn = null;
        try {
            
            ConnectionFactory factory = new ConnectionFactory();
            
            factory.setUri("amqps://zhqjjnph:EdkyKTi_W-8StS1kyqVOh0crCxhZLZmU@hawk.rmq.cloudamqp.com/zhqjjnph");
            
            conn = factory.newConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
