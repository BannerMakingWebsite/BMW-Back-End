package com.example.bmw.global.error;

import com.example.bmw.global.error.exception.CustomException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        try {
            chain.doFilter(request, response);
        } catch (CustomException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode().getMessage());

            response.setStatus(e.getErrorCode().getHttpStatus());
            response.setContentType("application/json");
            response.getWriter().write(errorResponse.convertToJson(errorResponse));
        }

    }
}
