package com.arcbees.payment.wepay.server.guice;

import com.arcbees.payment.wepay.server.servlet.IpnServlet;
import com.google.inject.servlet.ServletModule;

public class DispatchServletModule extends ServletModule {    
    @Override
    public void configureServlets() {
        serve("/payment_ipn_wepay*").with(IpnServlet.class);
    }
}
