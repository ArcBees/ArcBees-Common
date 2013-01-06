package com.arcbees.payment.wepay.shared.dto.preapproval;

public class PreapprovalCancelRequestDto {
    /**
     * The unique ID of the pre-approval you want to cancel. Is Required.
     */
    private int preapproval_id;  
    
    public PreapprovalCancelRequestDto() {
    }

    public int getPreapproval_id() {
        return preapproval_id;
    }

    public void setPreapproval_id(int preapproval_id) {
        this.preapproval_id = preapproval_id;
    }   
}
