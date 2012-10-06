package com.arcbees.payment.wepay.shared.dto.preapproval;

public class PreapprovalResponseDto {

    /**
     * The unique ID of the pre-approval.
     */
    private Integer preapproval_id;

    /**
     * The uri that you send the user to so they can enter their payment info
     * and approve the pre-approval.
     */
    private String preapproval_uri;

    /**
     * A uri that you can send the user to if they need to update their payment
     * method.
     */
    private String manage_uri;

    /**
     * The unique ID of the WePay account where the money will go.
     */
    private Integer account_id;

    /**
     * A short description of what the payer is being charged for.
     */
    private String short_description;

    /**
     * A longer description of what the payer is being charged for (if set).
     */
    private String long_description;

    /**
     * The currency that any charges will take place in (for now always USD).
     */
    private String currency;

    /**
     * The amount in dollars that the application can charge the payer
     * automatically every period.
     */
    private Double amount;

    /**
     * Who pays the fees, either payer or payee (default payer).
     */
    private String fee_payer;

    /**
     * The state that the pre-approval is in (new, approved, revoked, expired,
     * canceled).
     */
    private String state;

    /**
     * The uri that the payer will be redirected to after approving the
     * pre-approval.
     */
    private String redirect_uri;

    /**
     * The fee that will go to the API application's account (if set). Limited
     * to 20% of the pre-approval amount.
     */
    private Double app_fee;

    /**
     * String How often the API application can execute payments for a payer
     * with this pre-approval. Can be: hourly, daily, weekly, biweekly, monthly,
     * bimonthly, quarterly, yearly, and once. Once period is if you only want
     * to get authorization for a future charge and don't need it to be
     * recurring.
     */
    private String period;

    /**
     * The number of times the API application can execute payments per period.
     */
    private int frequency;

    /**
     * When the API application can begin executing payments with this
     * pre-approval. Will be a unix timestamp.
     */
    private int start_time;

    /**
     * The last time that the API application can execute a payment with this
     * pre-approval. Will be a unix timestamp.
     */
    private int end_time;

    /**
     * The reference_id passed by the application (if set).
     */
    private String reference_id;

    /**
     * The uri which instant payment notifications will be sent to.
     */
    private String callback_uri;

    /**
     * The shipping address that the payer entered (if applicable).
     */
    private Object shipping_address;

    /**
     * The amount that was paid in shipping fees (if any).
     */
    private Double shipping_fee;

    /**
     * The dollar amount of taxes paid (if any).
     */
    private Double tax;

    /**
     * Whether or not the pre-approval automatically executes the payments every
     * period.
     */
    private Boolean auto_recur;

    /**
     * The name of the payer.
     */
    private String payer_name;

    /**
     * The email of the payer.
     */
    private String payer_email;

    /**
     * The unixtime when the pre-approval was created.
     */
    private int create_time;

    public PreapprovalResponseDto() {
    }

    public Integer getPreapproval_id() {
        return preapproval_id;
    }

    public void setPreapproval_id(Integer preapproval_id) {
        this.preapproval_id = preapproval_id;
    }

    public String getPreapproval_uri() {
        return preapproval_uri;
    }

    public void setPreapproval_uri(String preapproval_uri) {
        this.preapproval_uri = preapproval_uri;
    }

    public String getManage_uri() {
        return manage_uri;
    }

    public void setManage_uri(String manage_uri) {
        this.manage_uri = manage_uri;
    }

    public Integer getAccount_id() {
        return account_id;
    }

    public void setAccount_id(Integer account_id) {
        this.account_id = account_id;
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

    public String getFee_payer() {
        return fee_payer;
    }

    public void setFee_payer(String fee_payer) {
        this.fee_payer = fee_payer;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRedirect_uri() {
        return redirect_uri;
    }

    public void setRedirect_uri(String redirect_uri) {
        this.redirect_uri = redirect_uri;
    }

    public Double getApp_fee() {
        return app_fee;
    }

    public void setApp_fee(Double app_fee) {
        this.app_fee = app_fee;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
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

    public String getReference_id() {
        return reference_id;
    }

    public void setReference_id(String reference_id) {
        this.reference_id = reference_id;
    }

    public String getCallback_uri() {
        return callback_uri;
    }

    public void setCallback_uri(String callback_uri) {
        this.callback_uri = callback_uri;
    }

    public Object getShipping_address() {
        return shipping_address;
    }

    public void setShipping_address(Object shipping_address) {
        this.shipping_address = shipping_address;
    }

    public Double getShipping_fee() {
        return shipping_fee;
    }

    public void setShipping_fee(Double shipping_fee) {
        this.shipping_fee = shipping_fee;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Boolean getAuto_recur() {
        return auto_recur;
    }

    public void setAuto_recur(Boolean auto_recur) {
        this.auto_recur = auto_recur;
    }

    public String getPayer_name() {
        return payer_name;
    }

    public void setPayer_name(String payer_name) {
        this.payer_name = payer_name;
    }

    public String getPayer_email() {
        return payer_email;
    }

    public void setPayer_email(String payer_email) {
        this.payer_email = payer_email;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

}
