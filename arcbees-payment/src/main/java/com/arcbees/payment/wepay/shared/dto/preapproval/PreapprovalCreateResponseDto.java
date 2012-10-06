package com.arcbees.payment.wepay.shared.dto.preapproval;

public class PreapprovalCreateResponseDto {

    /**
     * The unique ID of the pre-approval.
     */
    private String preapproval_id;

    /**
     * The uri that you will sent the payer to to approve the pre-approval.
     */
    private String preapproval_uri;

    public PreapprovalCreateResponseDto() {
    }

    public String getPreapproval_id() {
        return preapproval_id;
    }

    public void setPreapproval_id(String preapproval_id) {
        this.preapproval_id = preapproval_id;
    }

    public String getPreapproval_uri() {
        return preapproval_uri;
    }

    public void setPreapproval_uri(String preapproval_uri) {
        this.preapproval_uri = preapproval_uri;
    }
    
}
