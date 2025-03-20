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




    //  Delete message by message id
    public Message deleteMessageById(int messageId)
    {
        Message messageToBeDeleted = messageDAO.retrieveMessageById(messageId);
        messageDAO.deleteMessageByMessageId(messageId);
        return messageToBeDeleted;
    }




    //   Update a message by message id
    public Message updateMessageById(String newMsg, int msgId)
    {
        Message messageIdExists = messageDAO.retrieveMessageById(msgId);
        if(messageIdExists != null)
        {
            if(!newMsg.isBlank() && newMsg.length() < 255)
            {
                if(messageDAO.updateMessageById(newMsg, msgId))
                {
                    Message updatedMessage = messageDAO.retrieveMessageById(msgId);
                    return updatedMessage;
                }
            }
        }
        return null;
       
    }
}
