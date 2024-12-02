package org.yaod.stcc.springboot;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.OncePerRequestFilter;
import org.yaod.stcc.core.serializer.TransactionSerializer;
import org.yaod.stcc.core.transaction.context.ContextHolder;
import org.yaod.stcc.core.utils.Constants;

import javax.naming.Context;
import java.io.IOException;

/**
 * @author Yaod
 **/
public class SpringRequestFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var stccHeader = request.getHeader(Constants.STCC_TRANSACTION_HTTP_HEADER_NAME);
        ContextHolder.set(TransactionSerializer.INST.deSerialize(stccHeader));
        filterChain.doFilter(request,response);
    }
}