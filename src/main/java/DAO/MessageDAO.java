package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.Account;
import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {

    // To create/compose a new Message
    public Message composeNewMessage(Message message)
    {
        Connection connection = ConnectionUtil.getConnection();
        try{
            Account account = null;

            String query = "SELECT * FROM account where account_id = ?";

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1,message.getPosted_by());
            
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                account = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
            }
            if(account != null)
            {
                String sql = "INSERT INTO message(posted_by, message_text, time_posted_epoch) VALUES(?,?,?)";

                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                
                preparedStatement.setInt(1,message.getPosted_by());
                preparedStatement.setString(2, message.getMessage_text());
                preparedStatement.setLong(3, message.getTime_posted_epoch());

                preparedStatement.executeUpdate();
                
                ResultSet resultSet = preparedStatement.getGeneratedKeys();

                    if(resultSet.next())
                    {
                    int genenerated_message_id = (int) resultSet.getLong(1);
                    return new Message(genenerated_message_id, message.getPosted_by(),message.getMessage_text(), message.getTime_posted_epoch());
                    }
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







    
}
