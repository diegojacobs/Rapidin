/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author Diego Jacobs
 */
public class User {
    private int UserId;
    private String FirstName;
    private String LastName;
    private String Email;
    private String Password;

    public User() {
    }
    
    public User(String FirstName, String LastName, String Email, String Password) {
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Email = Email;
        this.Password = Password;
    }
    
    public User(int UserId, String FirstName, String LastName, String Email, String Password) {
        this.UserId = UserId;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Email = Email;
        this.Password = Password;
    }
    
    public int getUserId() {
        return UserId;
    }

    public void setUserId(int UserId) {
        this.UserId = UserId;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    @Override
    public String toString() {
        return "User{" + "UserId=" + UserId + ", FirstName=" + FirstName + ", LastName=" + LastName + ", Email=" + Email + '}';
    }
}
