package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {

    // To create/compose a new Message
    public Message composeNewMessage(Message message)
    {
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO message(posted_by, message_text, time_posted_epoch) VALUES(?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            preparedStatement.executeUpdate();
                
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

                if(resultSet.next())
                {
                int genenerated_message_id =  resultSet.getInt(1);
                return new Message(genenerated_message_id, message.getPosted_by(),message.getMessage_text(), message.getTime_posted_epoch());
                }  
        }   
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }





     //  To retrieve all messages
    public List<Message> retrieveAllMesssages()
    {
        Connection connection  = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try{
            String sql = "SELECT * FROM message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                Message message = new Message(resultSet.getInt("message_id"), resultSet.getInt("posted_by"), resultSet.getString("message_text"), resultSet.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }       
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return messages;   
    }





    //  To get a message by message id
    public Message retrieveMessageById(int messageId)
    {
        Connection connection  = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM message where message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,messageId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                Message message = new Message(resultSet.getInt("message_id"), resultSet.getInt("posted_by"), resultSet.getString("message_text"), resultSet.getLong("time_posted_epoch"));
                return message;                   
            }
        }       
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return null; 
    }






    //  To delete a message by message id
    public void deleteMessageByMessageId(int messageId)
    {
        Connection connection  = ConnectionUtil.getConnection();
        try{
            String sql = "DELETE FROM message where message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, messageId);
            preparedStatement.executeUpdate();
        }       
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }




    //  To update a message by message id
    public boolean updateMessageById(String newMessage, int messageId)
    {
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "UPDATE message set message_text = ? where message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newMessage);
            preparedStatement.setInt(2, messageId);
            int updatedRows = preparedStatement.executeUpdate();
                if(updatedRows > 0)
                {
                    return true;
                }  
        }   
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return false;
    }




    //  To get all the messages from user Account id
    public List<Message> retrieveAllMessagesByAccountId(int accId)
    {
        Connection connection  = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try{
            String sql = "SELECT * FROM message where posted_by = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                Message message = new Message(resultSet.getInt("message_id"), resultSet.getInt("posted_by"), resultSet.getString("message_text"), resultSet.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }       
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return messages; 
    }
    
}
