package DAO;

import Util.ConnectionUtil;
import java.sql.*;

import Model.Account;


public class AccountDAO
{

    // To add a new User
    public Account registerUserAccount(Account account)
    {
        Connection connection = ConnectionUtil.getConnection();
        try{
        String sql = "INSERT INTO account(username,password) VALUES(?,?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        
        preparedStatement.setString(1,account.getUsername());
        preparedStatement.setString(2, account.getPassword());

        preparedStatement.executeUpdate();
        
        ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if(resultSet.next())
            {
            int genenerated_account_id = (int) resultSet.getLong(1);
            return new Account(genenerated_account_id , account.getUsername(), account.getPassword());
            }
        }       
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }



    // To login the User
    public Account logIntoUserAccount(Account account)
    {
        Connection connection  = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM account where username = ? and password = ?";
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
        
            preparedStatement.setString(1,account.getUsername());
            preparedStatement.setString(2, account.getPassword());
    
            ResultSet resultSet = preparedStatement.executeQuery();
    
                while(resultSet.next())
                {
                    Account acc = new Account(resultSet.getInt("account_id"), resultSet.getString("username"), resultSet.getString("password"));
                    return acc;
                }
            }     
            catch(SQLException e)
            {
                System.out.println(e.getMessage());
            }
            return null;
            
        }




    // To retrieve the User by username
    public Account retrieveUserByUsername(String username)
    {
        Connection connection  = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM account where username = ?";
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
        
            preparedStatement.setString(1, username);
    
            ResultSet resultSet = preparedStatement.executeQuery();
    
            while(resultSet.next())
            {
                Account account = new Account(resultSet.getInt("account_id"), resultSet.getString("username"), resultSet.getString("password"));
                return account;
            }
            }       
            catch(SQLException e)
            {
                System.out.println(e.getMessage());
            }
            return null;   
        }





       





}



