package com.arcbees.payment.wepay.server.servlet;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;

public class IpnServlet extends HttpServlet {

    private final Logger logger;
    
    private Map<String, String[]> map;
    
    @Inject
    public IpnServlet(Logger logger) {
        this.logger = logger;
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        map = request.getParameterMap();
        
        Set<String> keys = map.keySet();
        for (String key : keys) {
            logger.warning("IPN debug: map.key=" + key + " = " + getValue(key));
        }
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        map = request.getParameterMap();
        
        Set<String> keys = map.keySet();
        for (String key : keys) {
            logger.warning("IPN debug: map.key=" + key + " = " + getValue(key));
        }
    }
    
    private String getValue(String key) {
        String[] values = map.get(key);
        if (values == null) {
            return "";
        }
        String s = "";
        for (int i = 0; i < values.length; i++) {
            s += values[i];
            if (i + 1 < values.length) {
                s += ",";
            }
        }
        return s;
    }
    
}
