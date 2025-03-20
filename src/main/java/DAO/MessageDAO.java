package DAO;

import java.sql.*;

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
    
}
