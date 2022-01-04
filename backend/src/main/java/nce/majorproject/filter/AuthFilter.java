package nce.majorproject.filter;

import lombok.extern.slf4j.Slf4j;
import nce.majorproject.context.ContextHolderServices;
import nce.majorproject.exception.RestException;

import nce.majorproject.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class AuthFilter implements Filter {
private JwtTokenUtil jwtTokenUtil;
private ContextHolderServices contextHolderServices;

    @Autowired
    public AuthFilter(JwtTokenUtil jwtTokenUtil, ContextHolderServices contextHolderServices) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.contextHolderServices = contextHolderServices;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("Inside JWT Filter");
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        HttpServletResponse response= (HttpServletResponse) servletResponse;
        final String requestTokenHeader=request.getHeader("Authorization");
        String url=request.getRequestURI();
        if (!(isByPassUrl(url))){
            if (requestTokenHeader!=null&&requestTokenHeader.startsWith("Bearer")){
                String jwtToken=requestTokenHeader.substring(7);
                String username=jwtTokenUtil.getUsernameFromToken(jwtToken);
                String userType=jwtTokenUtil.getByKey(jwtToken,"userType");
                if(userType.equalsIgnoreCase("Admin")){
                    contextHolderServices.setContextForAdmin(username);
                }
                else{
                    contextHolderServices.setContextForUser(username);
                }
                log.info("Username from token:: " + username + "" + userType);

//                System.out.println(contextHolderServices.getContext().getFullName());
            }else {
                log.info(url);
                log.error("JWT Token does not begin with Bearer String");
                throw new RestException("Invalid access token");
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
    private boolean isByPassUrl(String url) {
        final String ADMIN_REGISTER_URL= "/v1/api/admin/add";
        final String ADMIN_LOGIN_URL= "/v1/api/auth/admin";
        final String USER_REGISTER_URL= "/v1/api/user/register";
        final String USER_LOGIN_URL= "/v1/api/auth/user";
        final String PRODUCT = "/v1/api/product/latest-added";
        final String RECOMMENDATION = "/v1/api/recommendation/all";
        final String PRODUCTID = "/v1/api/product/get-product-by-id";
        final String HITTER = "/v1/api/recommendation/hiter";
        final String FILTER = "/v1/api/product/filter";
        final String TOPPRODUCT = "/v1/api/product/top-5";

        List<String> byPassUrl = Arrays.asList(ADMIN_REGISTER_URL,
                ADMIN_LOGIN_URL,USER_REGISTER_URL,USER_LOGIN_URL,PRODUCT,RECOMMENDATION,PRODUCTID,HITTER,FILTER,TOPPRODUCT);

        return byPassUrl.stream().anyMatch(url::equalsIgnoreCase);
    }
}
