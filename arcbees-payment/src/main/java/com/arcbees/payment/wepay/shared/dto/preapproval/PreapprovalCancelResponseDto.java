package com.arcbees.payment.wepay.shared.dto.preapproval;

public class PreapprovalCancelResponseDto {
    /**
     * The unique ID of the pre-approval.
     */
    private int preapproval_id;  
    
    /**
     * The state the pre-approval is now in. Will be 'cancelled'.
     */
    private String state;
    
    public PreapprovalCancelResponseDto() {
    }

    public int getPreapproval_id() {
        return preapproval_id;
    }

    public void setPreapproval_id(int preapproval_id) {
        this.preapproval_id = preapproval_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }    
}
