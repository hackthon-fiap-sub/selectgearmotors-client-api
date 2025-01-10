package br.com.selectgearmotors.client.application.api.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
        errorAttributes.put("message", "An unexpected error occurred.");
        errorAttributes.put("status", errorAttributes.get("status"));
        errorAttributes.put("timestamp", errorAttributes.get("timestamp"));
        errorAttributes.remove("trace"); // Remove informações sensíveis
        return errorAttributes;
    }
}