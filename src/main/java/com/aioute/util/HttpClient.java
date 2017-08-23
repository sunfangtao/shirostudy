package com.aioute.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HttpClient {

    private Map<String, String> parameter = new HashMap<String, String>();
    private HttpServletResponse response;

    public HttpClient(HttpServletRequest req, HttpServletResponse res) {
        this.response = res;
        setParameterMap(req.getParameterMap());
    }

    public void setParameter(String key, String value) {
        this.parameter.put(key, value);
    }

    private void setParameterMap(Map<String, String[]> map) {
        if (map != null) {
            for (String key : map.keySet()) {
                this.parameter.put(key, map.get(key)[0]);
            }
        }
    }

    public void sendByPost(String url) throws IOException {
        this.response.setContentType("text/html");
        PrintWriter out = this.response.getWriter();
        out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
        out.println("<HTML>");
        out.println(" <HEAD><TITLE>sender</TITLE></HEAD>");
        out.println(" <BODY>");
        out.println("<form name=\"submitForm\" action=\"" + url + "\" method=\"post\">");
        Iterator<String> it = this.parameter.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            out.println("<input type=\"hidden\" name=\"" + key + "\" value=\"" + this.parameter.get(key) + "\"/>");
        }
        out.println("</from>");
        out.println("<script>window.document.submitForm.submit();</script> ");
        out.println(" </BODY>");
        out.println("</HTML>");
        out.flush();
        out.close();
    }
}