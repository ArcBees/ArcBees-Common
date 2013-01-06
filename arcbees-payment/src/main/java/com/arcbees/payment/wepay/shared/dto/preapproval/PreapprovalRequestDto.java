package com.arcbees.payment.wepay.shared.dto.preapproval;

/**
 * https://www.wepay.com/developer/reference/preapproval
 */
public class PreapprovalRequestDto {
    /**
     * The unique ID of the preapproval you want to look up. Is Required.
     */
    private int preapproval_id;

    public void setPreapprovalId(int preapproval_id) {
        this.preapproval_id = preapproval_id;
    }

    public int getPreapprovalId() {
        return preapproval_id;
    }
}
