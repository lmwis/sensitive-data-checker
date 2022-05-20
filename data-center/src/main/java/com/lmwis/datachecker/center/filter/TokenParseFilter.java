package com.lmwis.datachecker.center.filter;

import com.fehead.lang.error.EmBusinessError;
import com.fehead.lang.response.AuthenticationReturnType;
import com.fehead.lang.util.GsonUtil;
import com.lmwis.datachecker.center.compoment.UserContextHolder;
import com.lmwis.datachecker.common.perperties.CommonConfigProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

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
@Slf4j
public class TokenParseFilter extends HttpFilter {

    final UserContextHolder userContextHolder;

    final CommonConfigProperties commonConfigProperties;

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        // acl无校验放行链接
        if (aclVerify(request.getRequestURI())){
            chain.doFilter(request,response);
            return;
        }

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

    /**
     * 校验是否在acl列表中
     * true - 在列表中，无需token校验
     * @param url
     * @return
     */
    private boolean aclVerify(String url){
        AntPathMatcher matcher = new AntPathMatcher();
        return commonConfigProperties.getAclList().stream().anyMatch(k->matcher.match(k,url));
    }


}
