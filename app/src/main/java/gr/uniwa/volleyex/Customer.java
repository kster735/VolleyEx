package gr.uniwa.volleyex;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Customer {
    private int custId;
    private String firstName;
    private String lastName;
    private String tel;
    private String mail;
    private String nickName;
    private Calendar regDate;
    private Calendar updDate;
    private boolean activ;
    private Calendar deactDate;
    private String password;

    public Customer() {

    }

    public Customer(String firstName, String lastName, String tel, String mail, String nickName, String regDate, String updDate, String activ, String deactDate, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.tel = tel;
        this.mail = mail;
        this.nickName = nickName;
        this.regDate = stringToCalendar(regDate);
        this.updDate = stringToCalendar(updDate);
        this.activ = (activ.equals("0")) ? false : true;
        this.deactDate = stringToCalendar(deactDate);
        this.password = password;
    }

    
    private Calendar stringToCalendar(String strdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cldr= null;
        Date date;
        try {
            date = sdf.parse(strdate);
            cldr = Calendar.getInstance();
            cldr.setTime(date);
        } catch (ParseException ex) {
            cldr = null;
        }
        return cldr;
    }

    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Calendar getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = stringToCalendar(regDate);
    }

    public Calendar getUpdDate() {
        return updDate;
    }

    public void setUpdDate(String updDate) {
        this.updDate = stringToCalendar(updDate);
    }

    public boolean isActiv() {
        return activ;
    }

    public void setActiv(boolean activ) {
        this.activ = activ;
    }

    public Calendar getDeactDate() {
        return deactDate;
    }

    public void setDeactDate(String deactDate) {
        this.deactDate = stringToCalendar(deactDate);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", regDate='" + regDate.get(Calendar.DAY_OF_MONTH) + "-" + regDate.get(Calendar.MONTH) +"-"+ regDate.get(Calendar.YEAR) + '\'' +
                '}';
    }
}
