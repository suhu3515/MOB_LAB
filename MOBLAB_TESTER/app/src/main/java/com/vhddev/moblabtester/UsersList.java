package com.vhddev.moblabtester;

public class UsersList
{
    private int UserId;
    private String UserName, UserDob, UserLoc;

    public UsersList(int userId, String userName, String userDob, String userLoc) {
        UserId = userId;
        UserName = userName;
        UserDob = userDob;
        UserLoc = userLoc;
    }

    public int getUserId() {
        return UserId;
    }

    public String getUserName() {
        return UserName;
    }

    public String getUserDob() {
        return UserDob;
    }

    public String getUserLoc() {
        return UserLoc;
    }
}
