package net.ausiasmarch.noventaveinticuatro.filter;

import org.springframework.stereotype.Component;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.ausiasmarch.noventaveinticuatro.helper.JWTHelper;

@Component
public class JWTFilter implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            String auth = request.getHeader("Authorization");

            if (auth != null && auth.startsWith("Bearer ")) {
                String token = auth.substring(7);
                String username = JWTHelper.validateJWT(token);
                if (username != null) {
                    request.setAttribute("username", username);
                } 
            }
        }
        
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }


}
