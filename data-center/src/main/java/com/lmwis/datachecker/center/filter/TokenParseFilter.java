package com.lmwis.datachecker.center.filter;

import com.fehead.lang.error.EmBusinessError;
import com.fehead.lang.response.AuthenticationReturnType;
import com.fehead.lang.util.GsonUtil;
import com.lmwis.datachecker.center.compoment.UserContextHolder;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/17 3:03 下午
 * @Version: 1.0
 */
@Component
@AllArgsConstructor
@Order(1)
public class TokenParseFilter extends HttpFilter {

    final UserContextHolder userContextHolder;

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.isNoneBlank(token)){

            if (!userContextHolder.verify(token)){
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(GsonUtil.toString( AuthenticationReturnType
                        .create(EmBusinessError.SERVICE_AUTHENTICATION_INVALID.getErrorMsg()
                                ,EmBusinessError.SERVICE_AUTHENTICATION_INVALID.getErrorCode())));
            }
            chain.doFilter(request,response);
        }else {
            // 为空返回
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(GsonUtil.toString( AuthenticationReturnType
                    .create(EmBusinessError.SERVICE_REQUIRE_AUTHENTICATION.getErrorMsg()
                            ,EmBusinessError.SERVICE_REQUIRE_AUTHENTICATION.getErrorCode())));
        }
    }


}
