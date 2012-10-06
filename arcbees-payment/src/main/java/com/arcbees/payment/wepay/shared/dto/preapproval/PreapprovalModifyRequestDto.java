package com.arcbees.payment.wepay.shared.dto.preapproval;

public class PreapprovalModifyRequestDto {

    /**
     * The unique ID of the preapproval you want to modify. Is Required.
     */
    private int preapproval_id;

    /**
     * The uri that any instant payment notifications will be sent to. Needs to
     * be a full uri (ex https://www.wepay.com ) and must NOT be localhost or
     * 127.0.0.1 or include wepay.com. Max 2083 chars. Is Optional.
     */
    private String callback_uri;

    public PreapprovalModifyRequestDto() {
    }

    public int getPreapproval_id() {
        return preapproval_id;
    }

    public void setPreapproval_id(int preapproval_id) {
        this.preapproval_id = preapproval_id;
    }

    public String getCallback_uri() {
        return callback_uri;
    }

    public void setCallback_uri(String callback_uri) {
        this.callback_uri = callback_uri;
    }
    
}
