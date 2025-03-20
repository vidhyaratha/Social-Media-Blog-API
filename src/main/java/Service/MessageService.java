package Service;

import DAO.MessageDAO;
import Model.Message;

import java.util.List;

import DAO.AccountDAO;

public class MessageService {

    MessageDAO messageDAO;

    AccountDAO accountDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public MessageService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }



    // Create New Message
    public Message createNewMessage(Message message)
    {
        if(!message.getMessage_text().isBlank() && message.getMessage_text().length() < 255)
        {
            return messageDAO.composeNewMessage(message);
        }
        return null;
        
    }



    //  Get all messages
    public List<Message> getAllMessages()
    {
        return messageDAO.retrieveAllMesssages();
    }
    
}
