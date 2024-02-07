package com.wisehr.wisehr.security.auth.handler;

import com.wisehr.wisehr.security.auth.model.DetailsUser;
import com.wisehr.wisehr.security.common.AuthConstants;
import com.wisehr.wisehr.security.common.utils.ConvertUtil;
import com.wisehr.wisehr.security.common.utils.TokenUtils;
import com.wisehr.wisehr.security.user.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import com.wisehr.wisehr.security.auth.model.dto.TokenDTO;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@Configuration
public class CustomAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        User user  = ((DetailsUser) authentication.getPrincipal()).getUser();
        JSONObject jsonValue = (JSONObject) ConvertUtil.convertObjectToJsonObject(user);
        HashMap<String, Object> responseMap = new HashMap<>();

    JSONObject jsonObject;
        if(user.getMemStatus().equals("N")){
            responseMap.put("userInfo", jsonValue);
            responseMap.put("message","휴먼상태인 계정입니다.");
        }else{
            String token = TokenUtils.generateJwtToken(user);

            // tokenDTO response
            TokenDTO tokenDTO = TokenDTO.builder()
                    .memCode(String.valueOf(user.getMemCode()))
                    .accessToken(token)
                    .grantType(AuthConstants.TOKEN_TYPE)
                    .build();

            jsonValue = (JSONObject) ConvertUtil.convertObjectToJsonObject(tokenDTO);
            responseMap.put("status", 200);
            responseMap.put("userInfo", jsonValue);
            responseMap.put("message", "로그인 성공");

            response.addHeader(AuthConstants.AUTH_HEADER, AuthConstants.TOKEN_TYPE + " " + token);
        }

        jsonObject = new JSONObject(responseMap);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.println(jsonObject);
        printWriter.flush();
        printWriter.close();
    }
}
