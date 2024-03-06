package com.efborchardt.productfeedback.application.usecase.product.list;

import java.math.BigDecimal;

public class ListProductFilterCriteriaDTO {

    private BigDecimal minimumPrice;
    private BigDecimal maximumPrice;

    public ListProductFilterCriteriaDTO(BigDecimal minimumPrice, BigDecimal maximumPrice) {
        this.minimumPrice = minimumPrice;
        this.maximumPrice = maximumPrice;
    }

    public BigDecimal getMinimumPrice() {
        return minimumPrice;
    }

    public void setMinimumPrice(BigDecimal minimumPrice) {
        this.minimumPrice = minimumPrice;
    }

    public BigDecimal getMaximumPrice() {
        return maximumPrice;
    }

    public void setMaximumPrice(BigDecimal maximumPrice) {
        this.maximumPrice = maximumPrice;
    }
}
