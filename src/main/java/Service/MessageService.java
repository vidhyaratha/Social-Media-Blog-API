package Service;

import DAO.MessageDAO;
import Model.Message;
import Model.Account;

import java.util.List;

import DAO.AccountDAO;

public class MessageService {

    MessageDAO messageDAO;

    AccountDAO accountDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
        accountDAO =new AccountDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }


    // Create New Message
    public Message createNewMessage(Message message)
    {
        Account existingAccount = accountDAO.retrieveUserAccountByAccountId(message.getPosted_by());
        System.out.println("Existing Acc :" + existingAccount);
        if(existingAccount != null)
        {
            if(!message.getMessage_text().isBlank() && message.getMessage_text().length() < 255)
            {
                 return messageDAO.composeNewMessage(message);
            }
            else
            {
                return null;
            }
         }  
        return null;
    }



    //  Get all messages
    public List<Message> getAllMessages()
    {
        return messageDAO.retrieveAllMesssages();
    }
    



    //  Get a message by message id
    public Message getMessageById(int messageId)
    {
        return messageDAO.retrieveMessageById(messageId);
    }
}
