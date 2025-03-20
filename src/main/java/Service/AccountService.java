package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {

    AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    //  Adding/registering a new user account
    public Account addUserAccount(Account account) {
        Account usernameAlreadyExists = accountDAO.retrieveUserByUsername(account.getUsername());
        if(usernameAlreadyExists == null)
        {
           if((!account.getUsername().isBlank()) && (account.getPassword().length()>=4))
            {
                return accountDAO.registerUserAccount(account);
            }
        }
        return null;
    }



    //  User Login
    public Account userLogin(Account account) {
            return accountDAO.logIntoUserAccount(account);
    }


}
