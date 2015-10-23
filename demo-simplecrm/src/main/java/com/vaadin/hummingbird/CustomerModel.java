package com.vaadin.hummingbird;

import java.util.Date;

import com.vaadin.ui.Template.Model;

public interface CustomerModel extends Model {
    public String getId();
    
    public void setId(String id);
	
	public String getFirstName();

    public void setLastName(String lastName);

    public String getLastName();

    public void setFirstName(String firstName);
    
    public String getEmail();
    
    public void setEmail(String email);
    
    public String getGender();
    
    public void setGender(String gender);
    
    public String getBirthDate();
    
    public void setBirthDate(String birthDate);
    
    public String getStatus();
    
    public void setStatus(String status);
}
