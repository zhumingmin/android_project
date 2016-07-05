package com.avilyne.rest.model;
 
import javax.xml.bind.annotation.XmlRootElement;
 
@XmlRootElement
public class LiZi {
 
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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
     
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
     
    public LiZi() {
         
        id = -1;
        firstName = "";
        lastName = "";
        email = "";
         
    }
 
    public LiZi(long id, String firstName, String lastName, String email) {
 
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
 
     
    private long id;
    private String firstName;
    private String lastName;
    private String email;
         
}