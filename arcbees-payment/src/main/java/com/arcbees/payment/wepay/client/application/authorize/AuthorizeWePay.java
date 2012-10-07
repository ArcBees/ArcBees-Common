package com.arcbees.payment.wepay.client.application.authorize;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.Widget;

public class AuthorizeWePay extends Composite {

    private static AuthorizePopupUiBinder uiBinder = GWT.create(AuthorizePopupUiBinder.class);

    public interface AuthorizePopupUiBinder extends UiBinder<Widget, AuthorizeWePay> {
    }

    @UiField
    Frame frame;

    public AuthorizeWePay() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public void redirect(String client_id, String redirect_uri, String scope) {
        String url = "https://stage.wepay.com/v2/oauth2/authorize?" + "client_id=" + client_id + "&redirect_uri="
                + redirect_uri + "&scope=" + scope;
        frame.setUrl(url);
    }

}
