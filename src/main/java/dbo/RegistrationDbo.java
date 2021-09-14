package main.java.dbo;

/**
 * Created by mrlz on 31.03.2018.
 */
public class RegistrationDbo {

    private Long accountId = 0L;
    private String login = null;
    private String password = null;
    private String email = null;
    private String secretQuestion = null;
    private String secretAnswer = null;
    private Long accType = 0L;


    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSecretQuestion() {
        return secretQuestion;
    }

    public void setSecretQuestion(String secretQuestion) {
        this.secretQuestion = secretQuestion;
    }

    public String getSecretAnswer() {
        return secretAnswer;
    }

    public void setSecretAnswer(String secretAnswer) {
        this.secretAnswer = secretAnswer;
    }

    public Long getAccType() {
        return accType;
    }

    public void setAccType(Long accType) {
        this.accType = accType;
    }
}
