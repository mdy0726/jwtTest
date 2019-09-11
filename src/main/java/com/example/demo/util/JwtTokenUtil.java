package com.example.demo.util;

import com.example.demo.entity.User;
import io.jsonwebtoken.*;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenUtil {

    // 过期时间是3600秒
    private static final long EXPIRATION = 3600L;
    //秘钥
    private static final String JWT_SECRET = "qwe1";

    // 创建jwt
    public static String createJWT(Map<String,Object> mapInfo) {

        //签名的算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        //签名算法的秘钥，解析token时的秘钥需要和此时的一样
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(JWT_SECRET);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setClaims(mapInfo)//设置payload的私有声明
                .setIssuedAt(new Date())//签发时间
                .signWith(signatureAlgorithm, signingKey)//设置签名使用的签名算法和签名使用的秘钥
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION * 1000));//设置过期时间

        return builder.compact();
    }

    public static Claims getToken(String jwt) {
        if (StringUtils.isNotEmpty(jwt)) {
            try {
                return Jwts.parser()
                        .setSigningKey(JWT_SECRET)
                        .parseClaimsJws(jwt)
                        .getBody();
            } catch (ExpiredJwtException | MalformedJwtException | SignatureException e) {
                return null;
            }
        }
        return null;
    }

    // 是否已过期
    public static boolean isExpiration(String token){
        return getToken(token).getExpiration().before(new Date());
    }


    public static void main(String[] args) {
        User user = new User();
<<<<<<< HEAD
        user.setUserName("张三444");
=======
        user.setUserName("张三111");
>>>>>>> madongyun
        user.setUserPwd("123465");
        HashMap<String, Object> mapInfo = new HashMap<>();
        mapInfo.put("userInfo",user);
        String jwt = JwtTokenUtil.createJWT(mapInfo);
        System.out.println("====={" + jwt + "}=====");
        Map<String,Object> o = (Map<String, Object>) JwtTokenUtil.getToken(jwt);
        boolean expiration = isExpiration(jwt);
        System.out.println("是否过期"+expiration);
        System.out.println(o);
    }
}
