package com.arcbees.payment.wepay.shared.dto.preapproval;

/**
 * https://www.wepay.com/developer/reference/preapproval
 */
public class PreapprovalFindRequestDto {

    /**
     * The state of the pre-approval you are searching for. Is Optional.
     */
    private String state;

    /**
     * The reference ID of the pre-approval you are searching for (set by the
     * app in in /preapproval/create call). Is Optional.
     */
    private String reference_id;

    public PreapprovalFindRequestDto() {
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getReference_id() {
        return reference_id;
    }

    public void setReference_id(String reference_id) {
        this.reference_id = reference_id;
    }

}
