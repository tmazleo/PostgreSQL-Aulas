package org.example;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        MessageRepository messageRepository = new MessageRepository();
        //messageRepository.insertMessage(new Message(2l, "Segunda Mensagem"));
        //messageRepository.update(new Message(1l, "Novo Valor"));
        messageRepository.delete(1l);
        List<Message> messages = messageRepository.findAll();
        messages.stream().forEach(System.out::println);
    }
}
