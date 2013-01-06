package com.arcbees.payment.wepay.shared.dto.checkout;

import java.util.List;

/**
 * https://www.wepay.com/developer/reference/checkout
 */
public class CheckoutFindResponseDto {
    private List<CheckoutFindRequestDto> checkouts;
    
    public CheckoutFindResponseDto() {
    }

    public List<CheckoutFindRequestDto> getCheckouts() {
        return checkouts;
    }

    public void setCheckouts(List<CheckoutFindRequestDto> checkouts) {
        this.checkouts = checkouts;
    }
}
