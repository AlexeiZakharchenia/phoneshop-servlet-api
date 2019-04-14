package com.es.phoneshop.web.filters;

import com.es.phoneshop.dosProtection.DosService;
import com.es.phoneshop.dosProtection.DosServiceImpl;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DosFilter implements Filter {

    private DosService dosService;

    @Override
    public void init(FilterConfig filterConfig) {
        dosService = DosServiceImpl.getInstance();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        if (dosService.isAllowed(request.getRemoteAddr())) {
            filterChain.doFilter(request, response);
        } else {
            ((HttpServletResponse) response).setStatus(429);
        }
    }

    @Override
    public void destroy() {
    }
}
