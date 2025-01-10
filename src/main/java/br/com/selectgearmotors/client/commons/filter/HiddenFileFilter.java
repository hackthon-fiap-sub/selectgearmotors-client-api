package br.com.selectgearmotors.client.commons.filter;

import jakarta.servlet.Filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HiddenFileFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String uri = httpRequest.getRequestURI();

        if (uri.startsWith("/api") && uri.matches(".*/\\..*")) { // Bloqueia arquivos ocultos
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
        } else {
            chain.doFilter(request, response);
        }
    }
}