package main.java.com.devrevolhope.mywallet.model;

import java.io.Serializable;

public enum AppUserRoleType implements Serializable{
    USER("USER"),
    DBA("DBA"),
    ADMIN("ADMIN");
     
    String userRoleType;
     
    private AppUserRoleType (String userRoleType){
        this.userRoleType = userRoleType;
    }
     
    public String getUserRoleType(){
        return userRoleType;
    }
}