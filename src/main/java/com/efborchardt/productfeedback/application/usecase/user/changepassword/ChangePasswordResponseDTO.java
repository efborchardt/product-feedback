package com.efborchardt.productfeedback.application.usecase.user.changepassword;

import com.efborchardt.productfeedback.application.usecase.common.SimpleResponseMessage;

public class ChangePasswordResponseDTO extends SimpleResponseMessage {
    public ChangePasswordResponseDTO() {
        super("Password updated successfully");
    }
}
