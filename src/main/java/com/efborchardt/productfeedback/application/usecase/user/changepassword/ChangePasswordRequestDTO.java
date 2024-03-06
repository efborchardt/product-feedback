package com.efborchardt.productfeedback.application.usecase.user.changepassword;

public class ChangePasswordRequestDTO {

    private String newPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
