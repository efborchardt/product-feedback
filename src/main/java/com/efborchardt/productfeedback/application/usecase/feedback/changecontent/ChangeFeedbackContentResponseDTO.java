package com.efborchardt.productfeedback.application.usecase.feedback.changecontent;

public class ChangeFeedbackContentResponseDTO {

    private String productName;
    private String updatedContent;

    public ChangeFeedbackContentResponseDTO(String productName, String updatedContent) {
        this.productName = productName;
        this.updatedContent = updatedContent;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUpdatedContent() {
        return updatedContent;
    }

    public void setUpdatedContent(String updatedContent) {
        this.updatedContent = updatedContent;
    }
}
