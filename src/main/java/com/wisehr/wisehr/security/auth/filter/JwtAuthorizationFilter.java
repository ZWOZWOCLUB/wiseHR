package com.wisehr.wisehr.security.auth.filter;

import com.wisehr.wisehr.security.auth.model.DetailsUser;
import com.wisehr.wisehr.security.common.AuthConstants;
import com.wisehr.wisehr.security.common.ZzclubRole;
import com.wisehr.wisehr.security.common.utils.TokenUtils;
import com.wisehr.wisehr.security.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    /* 매개변수 있는 생성자 */
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        /*
        * 권한이 필요없는 리소스
        * */
        List<String> roleLessList = Arrays.asList(
                "/signup", "/member/member", "approval/annual"
        );

        if(roleLessList.contains(request.getRequestURI())){
            chain.doFilter(request, response);
            return;
        }
        /* BEARER lsdkjflskdfjlsdkfjlsdjflsdkfjsd=*/
        String header = request.getHeader(AuthConstants.AUTH_HEADER);

        try{
            if(header != null && !header.equalsIgnoreCase("")){
                String token = TokenUtils.splitHeader(header);

                if(TokenUtils.isValidToken(token)){

                    Claims claims = TokenUtils.getClaimsFromToken(token);

                    DetailsUser authentication = new DetailsUser();
                    User user = new User();
                    user.setMemName(claims.get("userName").toString());
                    user.setMemEmail(claims.get("userEmail").toString());
                    user.setMemRole(ZzclubRole.valueOf(claims.get("Role").toString()));
                    authentication.setUser(user);

                    AbstractAuthenticationToken authenticationToken
                            = UsernamePasswordAuthenticationToken.authenticated(authentication, token, authentication.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    chain.doFilter(request, response);

                } else {
                    throw new RuntimeException("토큰이 유효하지 않습니다.");
                }
            }
        } catch (Exception e) {

            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();
            JSONObject jsonObect = jsonresponseWrapper(e);

            printWriter.println(jsonObect);
            printWriter.flush();
            printWriter.close();
        }
    }

    /***
     * 토큰 관련된 Exception 발생 시 예외 응답
     * @param e 예외
     * @return  결과값
     */
    private JSONObject jsonresponseWrapper(Exception e) {

        String resultMsg = "";
        if(e instanceof ExpiredJwtException){
            resultMsg = "Token Expired";
        } else if(e instanceof SignatureException) {
            resultMsg = "Token SignatureException Login";
        } else if(e instanceof JwtException) { // Jwt 토큰 내에서 오류 발생 시
            resultMsg = "Token Parsing JwtException";
        } else { // 이외의 Jwt 토큰애에서 오류 발생
            resultMsg = "Other Token Error";
        }

        HashMap<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("status", 401);
        jsonMap.put("msessage", resultMsg);
        jsonMap.put("reason", e.getMessage());
        JSONObject jsonObject = new JSONObject(jsonMap);
        return jsonObject;

    }
}
