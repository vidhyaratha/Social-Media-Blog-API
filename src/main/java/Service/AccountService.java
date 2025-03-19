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
        String username = account.getUsername();
        if((!account.getUsername().isBlank()) && (account.getPassword().length()>=4))
        {
            return accountDAO.registerUserAccount(account);
        }
        else
        {
        return null;
        }
        
    }


}
