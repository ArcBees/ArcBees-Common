package com.arcbees.payment.wepay.shared.dto.checkout;

/**
 * https://www.wepay.com/developer/reference/checkout
 */
public class CheckoutFindRequestDto {

    /**
     * The unique ID of the account whose checkouts you are searching. Is
     * Required.
     */
    private int account_id;

    /**
     * The start position for your search (default 0). Is Optional.
     */
    private int start;

    /**
     * The maximum number of returned entries (default 50). Is Optional.
     */
    private int limit;

    /**
     * The unique reference id of the checkout (set by the application in
     * /checkout/create. Is Optional.
     */
    private String reference_id;

    /**
     * What state the checkout is in (see the API object state reference for
     * details). Is Optional.
     */
    private String state;

    /**
     * The ID of the preapproval that was used to create the checkout. Useful if
     * you want to look up all of the payments for an auto_recurring
     * preapproval. Is Optional.
     */
    private int preapproval_id;

    public CheckoutFindRequestDto() {
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getReference_id() {
        return reference_id;
    }

    public void setReference_id(String reference_id) {
        this.reference_id = reference_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPreapproval_id() {
        return preapproval_id;
    }

    public void setPreapproval_id(int preapproval_id) {
        this.preapproval_id = preapproval_id;
    }

}
