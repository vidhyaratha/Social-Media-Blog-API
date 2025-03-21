package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accountService;
    
    MessageService messageService;

    public SocialMediaController() {
        accountService = new AccountService();
        messageService = new MessageService();
    }



    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);

        app.post("/register", this::postUserAccountRegistrationHandler);
        app.post("/login", this::postUserLoginHandler);
        app.post("/messages", this::postCreateNewMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByMessageId);
        app.delete("/messages/{message_id}" , this::deleteMessageByMessageId);
        app.patch("/messages/{message_id}", this::patchUpdateMessageByIdHandler);  
        app.get("/accounts/{account_id}/messages", this::getAllMessagesFromAccountId);

        return app;
    }




    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }





    //  Post Handler for User Registration
    private void postUserAccountRegistrationHandler(Context context) throws JsonProcessingException 
    {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account registeredUserAccount = accountService.addUserAccount(account);
        if(registeredUserAccount == null)
        {
            context.status(400);
        }
        else
        {
            context.json(mapper.writeValueAsString(registeredUserAccount));
        }
    }





    //  Post Handler for User Login
    private void postUserLoginHandler(Context context) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account loggedInUser =  accountService.userLogin(account);
        if(loggedInUser == null) {
            context.status(401);
        }
        else 
        {
            context.json(mapper.writeValueAsString(loggedInUser));
        }
    }


    //  Post Handler to create new message
    private void postCreateNewMessageHandler(Context context) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message createdMessage = messageService.createNewMessage(message);
        if(createdMessage == null)
        {
            context.status(400);
        }
        else
        {
            context.json(mapper.writeValueAsString(createdMessage));
        }
    }




    //  Get Handler to retrieve all messages
    private void getAllMessagesHandler(Context context) 
    {
        context.json(messageService.getAllMessages());
    }




    //  Get Handler to retreive a message by message id
    private void getMessageByMessageId(Context context)
    {
        int msgId = Integer.parseInt(context.pathParam("message_id"));
        if(messageService.getMessageById(msgId) != null)
        {
            context.json(messageService.getMessageById(msgId));
        }
        else
        {
            context.status(200);
            context.result("");
        }
    }





    //  Delete Handler to delete a message by message id
    private void deleteMessageByMessageId(Context context) throws JsonProcessingException
    {
        int msgId = Integer.parseInt(context.pathParam("message_id"));
        Message deletedMessage = messageService.deleteMessageById(msgId);
        if(deletedMessage != null)
        {
            context.json(deletedMessage);            
        }
        else
        {
            context.status(200).result("");
        }     
    }





    //  Update Handler to update a message by message id
    private void patchUpdateMessageByIdHandler(Context context) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);

        int msgId = Integer.parseInt(context.pathParam("message_id"));
        String newMsg = message.getMessage_text();

        Message updatedMessage = messageService.updateMessageById(newMsg, msgId);
        if(updatedMessage != null)
        {
           context.json(mapper.writeValueAsString(updatedMessage));
        }
        else
        {
            context.status(400);
        }
    }





    //  Get Handler to get All the messages from user given account id
    private void getAllMessagesFromAccountId(Context context)
    {
        int id = Integer.parseInt(context.pathParam("account_id"));
        List<Message> userAccountMessages = messageService.getAllMessagesByAccountId(id);
        context.json(userAccountMessages);
    }

}