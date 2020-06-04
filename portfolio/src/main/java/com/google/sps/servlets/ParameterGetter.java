package com.google.sps.servlets;
import javax.servlet.http.HttpServletRequest;

/* purpose: fetches parameters from HTML elements that have sent browser requests **/
class ParameterGetter {
    private ParameterGetter() {};

    /* wrapper for the servlet request method, with safety default value*/
    public static String getParameter(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        if (value == null) {
          return "";
        }
        return value;
    }
}