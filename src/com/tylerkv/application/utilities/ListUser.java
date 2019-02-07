package com.tylerkv.application.utilities;

public class ListUser {
    private String userName;
    private String password;
    private String permissionLevel;

    public ListUser(String userName, String password) {
        this.setUserName(userName);
        this.setPassword(userName);
    }

    // GETTERS AND SETTERS

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPermissionLevel() {
        return this.permissionLevel;
    }

    public void setPermissionLevel(String permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    // METHODS

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if(!(obj instanceof ListUser)) {
            return false;
        }

        ListUser typeCastedObj = (ListUser) obj;

        return typeCastedObj.getUserName() == this.getUserName();
    }
}
