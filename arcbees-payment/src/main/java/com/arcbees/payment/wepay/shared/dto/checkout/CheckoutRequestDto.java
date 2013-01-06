package com.arcbees.payment.wepay.shared.dto.checkout;

/**
 * https://www.wepay.com/developer/reference/checkout
 */
public class CheckoutRequestDto {
    /**
     * The unique ID of the checkout you want to look up. Is Required.
     */
    private int checkout_id;

    public CheckoutRequestDto() {
    }

    public int getCheckout_id() {
        return checkout_id;
    }

    public void setCheckout_id(int checkout_id) {
        this.checkout_id = checkout_id;
    }
}
