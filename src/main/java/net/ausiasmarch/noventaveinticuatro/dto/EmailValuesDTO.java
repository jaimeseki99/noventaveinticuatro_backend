package net.ausiasmarch.noventaveinticuatro.dto;

public class EmailValuesDTO {

    private String mailFrom;
    private String mailTo;
    private String mailSubject;
    private String userName;
    private String tokenPassword;

    public EmailValuesDTO(String mailFrom, String mailTo, String mailSubject, String userName, String tokenPassword) {
        this.mailFrom = mailFrom;
        this.mailTo = mailTo;
        this.mailSubject = mailSubject;
        this.userName = userName;
        this.tokenPassword = tokenPassword;
    }

    public String getMailFrom() {
        return mailFrom;
    }

    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    public String getMailTo() {
        return mailTo;
    }

    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }

    public String getMailSubject() {
        return mailSubject;
    }

    public void setMailSubject(String mailSubject) {
        this.mailSubject = mailSubject;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTokenPassword() {
        return tokenPassword;
    }

    public void setTokenPassword(String tokenPassword) {
        this.tokenPassword = tokenPassword;
    }

    public EmailValuesDTO() {

    }

    

}
