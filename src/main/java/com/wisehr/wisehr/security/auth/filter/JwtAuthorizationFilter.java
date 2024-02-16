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
import java.util.regex.Pattern;

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
        List<String> roleLessList = Arrays.asList(  // 권한없이 누구나 접근 가능한 경로
                "/signup", "/member/member", "approval/annual","/login",
                "/swagger-ui/(.*)",        //swagger 설정
                "/swagger-ui/index.html",  //swagger 설정
                "/v3/api-docs",              //swagger 설정
                "/v3/api-docs/(.*)",         //swagger 설정
                "/swagger-resources",        //swagger 설정
                "/swagger-resources/(.*)"    //swagger 설정
        );

        // 현재 요청의 URI가 권한이 필요 없는 리소스에 해당하는 경우, 요청을 다음 필터로 넘기고 메서드를 종료합니다.
        // 이는 정의된 경로에 대해서는 추가적인 인증 처리를 하지 않겠다
        if(roleLessList.stream().anyMatch(uri -> roleLessList.stream().anyMatch(pattern -> Pattern.matches(pattern, request.getRequestURI())))){
            chain.doFilter(request,response);
            return;
        }
        /* BEARER lsdkjflskdfjlsdkfjlsdjflsdkfjsd=*/
        // HTTP 요청 헤더에서 Authorization 값을 가져옵니다. 이 값은 일반적으로 "Bearer [토큰]" 형태로 제공
        String header = request.getHeader(AuthConstants.AUTH_HEADER);

        try{
            if(header != null && !header.equalsIgnoreCase("")){
                String token = TokenUtils.splitHeader(header);

                if(TokenUtils.isValidToken(token)){

                    Claims claims = TokenUtils.getClaimsFromToken(token);

                    DetailsUser authentication = new DetailsUser();
                    User user = new User();
                    user.setMemName(claims.get("memName").toString());
                    user.setMemEmail(claims.get("memEmail").toString());
                    user.setMemCode(Integer.parseInt(claims.get("memCode").toString()));
                    user.setMemRole(ZzclubRole.valueOf(claims.get("memRole").toString()));
//                    user.setMemAddress(claims.get("memAddress").toString());  // 필요시 주석 해제
//                    user.setMemPhone(claims.get("memPhone").toString());
//                    user.setMemBirth(claims.get("memBirth").toString());
//                    user.setMemHireDate(claims.get("memHireDate").toString());
//                    user.setMemBirth(claims.get("memBirth").toString());
//                    user.setMemStatus(claims.get("memStatus").toString());
                    authentication.setUser(user);
                    // 토큰에서 클레임을 추출하고, 이를 바탕으로 사용자의 상세 정보를 설정합니다.
                    // 여기서는 사용자 이름, 이메일, 역할 등의 정보를 설정합니다.

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
