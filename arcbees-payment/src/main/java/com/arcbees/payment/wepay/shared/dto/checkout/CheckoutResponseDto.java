package com.arcbees.payment.wepay.shared.dto.checkout;

/**
 * https://www.wepay.com/developer/reference/checkout
 */
public class CheckoutResponseDto {

    /**
     * The unique ID of the checkout.
     */
    private int checkout_id; 
    
    /**
     * The unique ID of the account that the money will go into.
     */
    private int account_id;
    
    /**
     * The state the checkout is in. See the API object state section for a listing of all states.
     */
    private String state;   
    
    /**
     * The short description of the checkout. Max 127 chars.
     */
    private String short_description;   
    
    /**
     * The long description of the checkout (if available). Max 2047 chars.
     */
    private String long_description;
    
    /**
     * The currency used (always "USD" for now).
     */
    private String currency;    
    
    /**
     * The dollar amount of the checkout object (ex. 3.20). This will always be the amount you passed in /checkout/create.
     */
    private Double amount;
    
    /**
     * The dollar amount of the WePay fee.
     */
    private Double fee; 
    
    /**
     * The total dollar amount paid by the payer.
     */
    private Double gross;
    
    /**
     * The dollar amount that the application received in fees. App fees go into the API application's WePay account.
     */
    private Double app_fee;
    
    /**
     * Who is paying the fee (either "payer" for the person paying or "payee" for the person receiving the money).
     */
    private String fee_payer;   
    
    /**
     * The unique reference_id passed by the application (if available).
     */
    private String reference_id;
    
    /**
     * The uri the payer will be redirected to after payment (if available). Max 2083 chars.
     */
    private String redirect_uri;
    
    /**
     * The uri which instant payment notifications will be sent to. Max 2083 chars.
     */
    private String callback_uri;
    
    /**
     * The email address of the person paying (only returned if a payment has been made).
     */
    private String payer_email;
    
    /**
     * The name of the person paying (only returned if a payment has been made).
     */
    private String payer_name;
    
    /**
     * If the payment was canceled, the reason why.
     */
    private String cancel_reason;   
    
    /**
     * If the payment was refunded the reason why.
     */
    private String refund_reason;
    
    /**
     * Default is true. If set to false then the payment will not automatically be released to the account and will be held by WePay in payment state 'reserved'. To release funds to the account you must call /checkout/capture 
     */
    private Boolean auto_capture;
    
    /**
     * Default is false. If set to true then the payer will be required to enter their shipping address when paying. 
     */
    private Boolean require_shipping;    
    
    /**
     * If 'require_shipping' was set to true and the payment was made, this will be the shipping address that the payer entered. It will be in the following format: {"address1":"380 Portage Ave","address2":"","city":"Palo Alto","state":"CA","zip":"94306","country":"US"} 
     */
    private Object shipping_address;    
    
    /**
     * The dollar amount of taxes paid.
     */
    private Double tax;
    
    /**
     * If this checkout has been fully or partially refunded, this has the amount that has been refunded so far. 
     */
    private Double amount_refunded;
    
    /**
     * The unixtime when the checkout was created.
     */
    private int create_time;

    public CheckoutResponseDto() {
    }

    public int getCheckout_id() {
        return checkout_id;
    }

    public void setCheckout_id(int checkout_id) {
        this.checkout_id = checkout_id;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getLong_description() {
        return long_description;
    }

    public void setLong_description(String long_description) {
        this.long_description = long_description;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Double getGross() {
        return gross;
    }

    public void setGross(Double gross) {
        this.gross = gross;
    }

    public Double getApp_fee() {
        return app_fee;
    }

    public void setApp_fee(Double app_fee) {
        this.app_fee = app_fee;
    }

    public String getFee_payer() {
        return fee_payer;
    }

    public void setFee_payer(String fee_payer) {
        this.fee_payer = fee_payer;
    }

    public String getReference_id() {
        return reference_id;
    }

    public void setReference_id(String reference_id) {
        this.reference_id = reference_id;
    }

    public String getRedirect_uri() {
        return redirect_uri;
    }

    public void setRedirect_uri(String redirect_uri) {
        this.redirect_uri = redirect_uri;
    }

    public String getCallback_uri() {
        return callback_uri;
    }

    public void setCallback_uri(String callback_uri) {
        this.callback_uri = callback_uri;
    }

    public String getPayer_email() {
        return payer_email;
    }

    public void setPayer_email(String payer_email) {
        this.payer_email = payer_email;
    }

    public String getPayer_name() {
        return payer_name;
    }

    public void setPayer_name(String payer_name) {
        this.payer_name = payer_name;
    }

    public String getCancel_reason() {
        return cancel_reason;
    }

    public void setCancel_reason(String cancel_reason) {
        this.cancel_reason = cancel_reason;
    }

    public String getRefund_reason() {
        return refund_reason;
    }

    public void setRefund_reason(String refund_reason) {
        this.refund_reason = refund_reason;
    }

    public Boolean getAuto_capture() {
        return auto_capture;
    }

    public void setAuto_capture(Boolean auto_capture) {
        this.auto_capture = auto_capture;
    }

    public Boolean getRequire_shipping() {
        return require_shipping;
    }

    public void setRequire_shipping(Boolean require_shipping) {
        this.require_shipping = require_shipping;
    }

    public Object getShipping_address() {
        return shipping_address;
    }

    public void setShipping_address(Object shipping_address) {
        this.shipping_address = shipping_address;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getAmount_refunded() {
        return amount_refunded;
    }

    public void setAmount_refunded(Double amount_refunded) {
        this.amount_refunded = amount_refunded;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }
    
}
