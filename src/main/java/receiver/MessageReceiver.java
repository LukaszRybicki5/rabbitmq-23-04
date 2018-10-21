package receiver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import receiver.jsonParser.JSONFileMaker;
import receiver.messageObject.MessageObject;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
/*
Klasa obbiera MessaggeObject, zamienia go na plik JSON i zapisuje we wskazanum folderze
 */
public class MessageReceiver {

    private static String EXCHANGE_NAME = "TopicDurable";
    private static String QUEUE_NAME = "DummyLoad";

    public static void main(String[] args) throws InterruptedException, IOException, TimeoutException {

        String Path = "C:\\Users\\User\\Desktop\\newJson\\";
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "topic", true);
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME, true, queueingConsumer);

        ObjectMapper mapper = new ObjectMapper();
        while (true) {
            Delivery delivery = queueingConsumer.nextDelivery();
            if (delivery.getBody()[0] == 123) {

                MessageObject messageObject = mapper.readValue(delivery.getBody(), MessageObject.class);
                System.out.println("Odebrano plik: " + messageObject.getFileName());

                // System.out.println(messageObject.getCSVList());

                JSONFileMaker jsonFileMaker = new JSONFileMaker();
                jsonFileMaker.putDataToPersonList(messageObject.getCSVList(), messageObject.getFileName(), Path);
                System.out.println("Stworzono plik JSON!");
            }
        }
    }
}

