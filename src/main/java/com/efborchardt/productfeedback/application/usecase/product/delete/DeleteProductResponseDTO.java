package com.efborchardt.productfeedback.application.usecase.product.delete;

import com.efborchardt.productfeedback.application.usecase.common.SimpleResponseMessage;

public class DeleteProductResponseDTO extends SimpleResponseMessage {
    public DeleteProductResponseDTO() {
        super("Product successfully deleted");
    }
}
