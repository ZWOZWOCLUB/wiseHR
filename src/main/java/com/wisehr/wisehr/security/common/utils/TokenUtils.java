package com.wisehr.wisehr.security.common.utils;

import com.wisehr.wisehr.security.user.entity.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/* 토큰을 관리하기 위한 utils 모음 클래스
*
* 설정할것
* yml파일에 jwt-key, jwt-time 설정을 한다.
* ywt lib 번 "io.jsonwebtoken:jjwt:0.9.1" 사용
* */
@Component
public class TokenUtils {

    private static String jwtSecretKey;

    private static Long tokenValidateTime;

    @Value("${jwt.key}")
    public void setJwtSecretKey(String jwtSecretKey) {
        TokenUtils.jwtSecretKey = jwtSecretKey;
    }

    @Value("${jwt.time}")
    public void setTokenValidateTime(Long tokenValidateTime){
        TokenUtils.tokenValidateTime = tokenValidateTime;
    }

    /***
     * header의 token을 분리하는 메소드
     * @param header Authrization의 header값을 가져온다.
     * @return token >>> Authriztion의 token 부분을 반환한다.
     */
    public static String splitHeader(String header) {
        if(!header.equals("")){
            return header.split(" ")[1];
        } else {
            return null;
        }
    }

    /***
     * 유효한 토큰인지 확인하는 메소드
     * @param token 토큰
     * @return 유효여부
     */
    public static boolean isValidToken(String token) {

        try{
            Claims claims = getClaimsFromToken(token);  // 토큰 복호화
            return true;
        }catch(ExpiredJwtException e) {
            e.printStackTrace();
            return false;
        } catch (JwtException e) {
            e.printStackTrace();
            return false;
        } catch (NullPointerException e){
            e.printStackTrace();
            return false;
        }

    }

    /***
     * 토큰을 복호화 하는 메소드
     * @param token
     * @return
     */
    public static Claims getClaimsFromToken(String token) {
        // DatatypeConverter <- 이걸 사용하려면
        // implementation group: 'javax.xml.bind', name: 'jaxb-api', version: '2.3.1' 이걸 추가
        return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(jwtSecretKey)).parseClaimsJws(token).getBody();
    }

    /***
     * token을 생성하는 메소드
     * @param user - userEntity
     * @return token발행
     */
    public static String generateJwtToken(User user) {

        Date expireTime = new Date(System.currentTimeMillis() + tokenValidateTime);
        JwtBuilder builder = Jwts.builder()
                .setHeader(createHeader())
                .setClaims(createClaims(user))
                .setSubject("ohgiraffers token : " + user.getMemCode())
                .signWith(SignatureAlgorithm.HS256, createSignature())
                .setExpiration(expireTime);
        return builder.compact();
    }




    /***
     * token의 header를 설정하는 부분
     * @return Map<String, Object> - header의 설정 정보
     */
    private static Map<String, Object> createHeader() {

        Map<String, Object> header = new HashMap<>();
        header.put("type", "jwt");
        header.put("alg", "HS256");
        header.put("date", System.currentTimeMillis());

        return header;
    }

    /***
     * 사용자 정보를 기반으로 클레임을 생성해주는 메소드
     * @param user 사용자 정보
     * @return Map<String, Object> - cliam 정보
     */
    private static Map<String, Object> createClaims(User user) {

        Map<String, Object> claims = new HashMap<>();

        claims.put("memName", user.getMemName());
        claims.put("memRole", user.getMemRole());
        claims.put("memEmail", user.getMemEmail());
//        claims.put("memPhone", user.getMemPhone());       // 필요시 주석 해제
//        claims.put("memAddress", user.getMemAddress());
//        claims.put("memBirth", user.getMemBirth());
//        claims.put("memHireDate", user.getMemHireDate());
//        claims.put("memEndDate", user.getMemEndDate());
//        claims.put("memStatus", user.getMemStatus());
//        claims.put("memCode", user.getMemCode());

        return claims;
    }

    /***
     * JWT 서명을 발급해주는 메소드
     * @return key
     */
    private static Key createSignature() {

        byte[] secretBytes = DatatypeConverter.parseBase64Binary(jwtSecretKey);
        return new SecretKeySpec(secretBytes, SignatureAlgorithm.HS256.getJcaName());
    }
}
