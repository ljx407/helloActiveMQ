package com.ljx;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by jasonliu on 2016/10/17.
 */
public class JMSProducer {
    private static final String USER = ActiveMQConnection.DEFAULT_USER;
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    private static final String BROKER = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static final int NUM = 10 ;

    public static void main(String[] args) {

        ConnectionFactory connectionFactory ;
        Connection connection = null;
        Session session ;
        Destination destination ;
        MessageProducer messageProducer;

        connectionFactory = new ActiveMQConnectionFactory(JMSProducer.USER, JMSProducer.PASSWORD, JMSProducer.BROKER);
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("HelloWorld");
            messageProducer = session.createProducer(destination);
            sendMessage(session, messageProducer);
            session.commit();
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    private static void sendMessage(Session session, MessageProducer messageProducer) throws JMSException {
        for (int i = 0; i < JMSProducer.NUM ; i++) {
            TextMessage textMessage = session.createTextMessage("activeMQ created : " + i);
            System.out.println("activeMQ created : " + i);
            messageProducer.send(textMessage);
        }
    }
}
