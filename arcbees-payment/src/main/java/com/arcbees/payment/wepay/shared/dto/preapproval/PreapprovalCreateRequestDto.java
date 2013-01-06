package com.arcbees.payment.wepay.shared.dto.preapproval;

/**
 * https://www.wepay.com/developer/reference/preapproval
 */
public class PreapprovalCreateRequestDto {
    /**
     * The WePay account where the money will go when you use this pre-approval
     * to execute a payment. Is Required.
     */
    private int account_id;

    /**
     * The amount for the pre-approval. The API application can charge up to
     * this amount every period. Is Optional.
     */
    private Double amount;

    /**
     * A short description of what the payer is paying for. Is Required.
     */
    private String short_description;

    /**
     * Can be: hourly, daily, weekly, biweekly, monthly, bimonthly, quarterly,
     * yearly, or once. The API application can charge the payer every period.
     * Is Required;
     */
    private String period;

    /**
     * The reference id of the pre-approval. Can be any string, but must be
     * unique for the application/user pair. Is Optional;
     */
    private String reference_id;

    /**
     * The application fee that will go to the API application's account. Is
     * Optional.
     */
    private Double app_fee;

    /**
     * Who will pay the WePay fees and app fees (if set). Can be payee or payer.
     * Defaults to payer. Is Optional.
     */
    private String fee_payer;

    /**
     * The uri the payer will be redirected to after approving the pre-approval.
     * Is Optional.
     */
    private String redirect_uri;

    /**
     * The uri that any instant payment notifications will be sent to. Needs to
     * be a full uri (ex https://www.wepay.com) and must NOT be localhost or
     * 127.0.0.1 or include wepay.com. Max 2083 chars. Is Optional.
     */
    private String callback_uri;

    /**
     * If set to true then the payer will be require to enter their shipping
     * address when they approve the pre-approval. Is Optional Default is false
     */
    private Boolean require_shipping;

    /**
     * The dollar amount of shipping fees that will be charged. Is Optional.
     */
    private Double shipping_fee;

    /**
     * If set to true then any applicable taxes will be charged. Is Optional.
     * Defaults is false.
     */
    private Boolean charge_tax;

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
     * An optional longer description of what the payer is paying for. Is
     * Optional.
     */
    private String long_description;

    /**
     * How often per period the API application can charge the payer. Is
     * Optional.
     */
    private int frequency;

    /**
     * When the API application can start charging the payer. Can be a
     * unix_timestamp or a parse-able date-time. Is Optional. 
     * TODO type can be Number or String ???
     */
    private int start_time;

    /**
     * The last time the API application can charge the payer. Can be a
     * unix_timestamp or a parse-able date-time. The default value is five (5)
     * years from the preapproval creation time. Is Optional. 
     * TODO type can be Number or String ???
     */
    private int end_time;

    /**
     * Set to true if you want the payments to automatically execute every
     * period. Useful for subscription use cases. Default value is false. Only
     * the following periods are allowed if you set auto_recur to true: Weekly,
     * Biweekly, Monthly, Quarterly, Yearly. Is Optional.
     */
    private Boolean auto_recur;

    /**
     * What mode the pre-approval confirmation flow will be displayed in. The
     * options are 'iframe' or 'regular'. Choose 'iframe' if this is an iframe
     * pre-approval. Mode defaults to 'regular'. Is Optional.
     */
    private String mode;

    /**
     * A JSON object that lets you pre fill certain fields in the pre-approval
     * flow. Allowed fields are 'name', 'email', 'phone_number', 'address',
     * 'city', 'state', 'zip', Pass the prefill-info as a JSON object like so:
     * {"name":"Bill Clerico","phone_number":"855-469-3729"}. 
     * Is Optional.
     */
    private Object prefill_info;

    /**
     * What funding sources you want to accept for this checkout. Options are:
     * "bank,cc" to accept both bank and cc payments, "cc" to accept just credit
     * card payments, and "bank" to accept just bank payments. Is Optional.
     */
    private String funding_sources;

    public PreapprovalCreateRequestDto() {
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
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

    public Boolean getCharge_tax() {
        return charge_tax;
    }

    public void setCharge_tax(Boolean charge_tax) {
        this.charge_tax = charge_tax;
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

    public String getLong_description() {
        return long_description;
    }

    public void setLong_description(String long_description) {
        this.long_description = long_description;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getStart_time() {
        return start_time;
    }

    public void setStart_time(int start_time) {
        this.start_time = start_time;
    }

    public int getEnd_time() {
        return end_time;
    }

    public void setEnd_time(int end_time) {
        this.end_time = end_time;
    }

    public Boolean getAuto_recur() {
        return auto_recur;
    }

    public void setAuto_recur(Boolean auto_recur) {
        this.auto_recur = auto_recur;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
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
}
