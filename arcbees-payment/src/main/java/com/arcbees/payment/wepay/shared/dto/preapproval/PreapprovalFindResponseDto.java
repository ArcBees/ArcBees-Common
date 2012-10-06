package com.arcbees.payment.wepay.shared.dto.preapproval;

import java.util.List;

/**
 * https://www.wepay.com/developer/reference/preapproval
 */
public class PreapprovalFindResponseDto {

    public List<PreapprovalResponseDto> preapprovals;
    
    public PreapprovalFindResponseDto() {
    }

    public List<PreapprovalResponseDto> getPreapprovals() {
        return preapprovals;
    }

    public void setPreapprovals(List<PreapprovalResponseDto> preapprovals) {
        this.preapprovals = preapprovals;
    }
    
}
