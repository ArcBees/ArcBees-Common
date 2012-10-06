package com.arcbees.payment.wepay.shared.dto.checkout;

/**
 * https://www.wepay.com/developer/reference/checkout
 */
public class CheckoutCreateRequestDto {

    /**
     * The unique ID of the account you want to create a checkout for. Is
     * Required.
     */
    private int account_id;

    /**
     * A short description of what is being paid for. Max 127 chars. Is
     * Required.
     */
    private String short_description;

    /**
     * The the checkout type (one of the following: GOODS, SERVICE, DONATION, or
     * PERSONAL). Is Required.
     */
    private String type;

    /**
     * The amount that the payer will pay. Is Required.
     */
    private Double amount;

    /**
     * A long description of what is being paid for. Max 2047 chars. Is
     * Optional.
     */
    private String long_description;

    /**
     * A short message that will be included in the payment confirmation email
     * to the payer. Is Optional.
     */
    private String payer_email_message;

    /**
     * A short message that will be included in the payment confirmation email
     * to the payee. Is Optional.
     */
    private String payee_email_message;

    /**
     * The unique reference id of the checkout (set by the application in
     * /checkout/create. Is Optional.
     */
    private String reference_id;

    /**
     * The amount that the application will receive in fees. App fees go into
     * the API applications WePay account. Limited to 20% of the checkout
     * amount. Is Optional.
     */
    private Double app_fee;

    /**
     * Who will pay the fees (WePay's fees and any app fees). Set to "Payer" to
     * charge fees to the person paying (Payer will pay amount + fees, payee
     * will receive amount). Set to "Payee" to charge fees to the person
     * receiving money (Payer will pay amount, Payee will receive amount -
     * fees). Defaults to "Payer". Is Optional.
     */
    private String fee_payer;

    /**
     * The uri the payer will be redirected to after paying. Max 2083 chars. Is
     * Optional.
     */
    private String redirect_uri;

    /**
     * The uri that will receive any instant payment notifications sent. Needs
     * to be a full uri (ex https://www.wepay.com ) and must NOT be localhost or
     * 127.0.0.1 or include wepay.com. Max 2083 chars. Is Optional.
     */
    private String callback_uri;

    /**
     * Default is true. If set to false then the payment will not automatically
     * be released to the account and will be held by WePay in payment state
     * 'reserved'. To release funds to the account you must call
     * /checkout/capture. This is a JSON boolean so you can pass
     * "auto_capture":false (no quotes). We will also accept "auto_capture":0 Is
     * Optional.
     */
    private Boolean auto_capture;

    /**
     * A boolean value (0 or 1). If set to 1 then the payer will be asked to
     * enter a shipping address when they pay. After payment you can retrieve
     * this shipping address by calling /checkout. Is Optional.
     */
    private Boolean require_shipping;

    /**
     * The amount that you want to charge for shipping. Is Optional.
     */
    private Double shipping_fee;

    /**
     * A boolean value (0 or 1). If set to 1 and the account has a relevant tax
     * entry (see /account/set_tax), then tax will be charged. Is Optional.
     */
    private Double charge_tax;

    /**
     * What mode the checkout will be displayed in. The options are 'iframe' or
     * 'regular'. Choose 'iframe' if this is an iframe checkout. Mode defaults
     * to 'regular'. Is Optional.
     */
    private String mode;

    /**
     * The ID of a preapproval object. If you include a valid preapproval_id the
     * checkout will be executed immediately, and the payer will be charged
     * without having to go to the checkout_uri. You should not have to send the
     * payer to the checkout_uri. Is Optional.
     */
    private int preapproval_id;

    /**
     * A JSON object that lets you pre fill certain fields in the checkout.
     * Allowed fields are 'name', 'email', 'phone_number', 'address', 'city',
     * 'state', 'zip', Pass the prefill-info as a JSON object like so:
     * {"name":"Bill Clerico","phone_number":"855-469-3729"} Is Optional.
     */
    private Object prefill_info;

    /**
     * What funding sources you want to accept for this checkout. Options are:
     * "bank,cc" to accept both bank and cc payments, "cc" to accept just credit
     * card payments, and "bank" to accept just bank payments. Is Optional.
     */
    private String funding_sources;

    /**
     * If you are using credit card tokenization use the credit_card_id you
     * received from the /credit_card/create call. Is Optional.
     */
    private String payment_method_id;

    /**
     * Set to 'credit_card' for tokenization.
     */
    private String payment_method_type;

    public CheckoutCreateRequestDto() {
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getLong_description() {
        return long_description;
    }

    public void setLong_description(String long_description) {
        this.long_description = long_description;
    }

    public String getPayer_email_message() {
        return payer_email_message;
    }

    public void setPayer_email_message(String payer_email_message) {
        this.payer_email_message = payer_email_message;
    }

    public String getPayee_email_message() {
        return payee_email_message;
    }

    public void setPayee_email_message(String payee_email_message) {
        this.payee_email_message = payee_email_message;
    }

    public String getReference_id() {
        return reference_id;
    }

    public void setReference_id(String reference_id) {
        this.reference_id = reference_id;
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

    public Double getShipping_fee() {
        return shipping_fee;
    }

    public void setShipping_fee(Double shipping_fee) {
        this.shipping_fee = shipping_fee;
    }

    public Double getCharge_tax() {
        return charge_tax;
    }

    public void setCharge_tax(Double charge_tax) {
        this.charge_tax = charge_tax;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public int getPreapproval_id() {
        return preapproval_id;
    }

    public void setPreapproval_id(int preapproval_id) {
        this.preapproval_id = preapproval_id;
    }

    public Object getPrefill_info() {
        return prefill_info;
    }

    public void setPrefill_info(Object prefill_info) {
        this.prefill_info = prefill_info;
    }

    public String getFunding_sources() {
        return funding_sources;
    }

    public void setFunding_sources(String funding_sources) {
        this.funding_sources = funding_sources;
    }

    public String getPayment_method_id() {
        return payment_method_id;
    }

    public void setPayment_method_id(String payment_method_id) {
        this.payment_method_id = payment_method_id;
    }

    public String getPayment_method_type() {
        return payment_method_type;
    }

    public void setPayment_method_type(String payment_method_type) {
        this.payment_method_type = payment_method_type;
    }

}
