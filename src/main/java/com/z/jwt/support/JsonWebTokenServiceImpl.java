/**
 * 
 */
package com.z.jwt.support;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.z.jwt.JsonWebTokenService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * jwt 为json web token的缩写 它的格式 HEADER.CLAIMS(OR PAYLOAD).SIGNATURE 三部分构成
 * 
 * @author Administrator
 *
 */

public class JsonWebTokenServiceImpl implements JsonWebTokenService {

    private static final String JWT = "JWT";// token类型

    private static SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256; // 签名算法

    private static final String base64Security = "123456"; // 签名密钥

   // private static final String issuer = "zhuzhong";// 签发者
    //private static final String audience = "zhuzhong";// 审阅者

    private static final int TTLMins = 30 ;// 30分钟

    private static final long LASTTTLMills = 8 * 60 * 60 * 1000;// 过期8小时之内都可以使用

    @Override
    public boolean isNotExpired(String token) {
        Claims claims = parseJWT(token);
        if (claims != null) {
            return System.currentTimeMillis() <= claims.getExpiration().getTime();
        }

        return false;
    }
    @Override
    public String genToken(String customerId) {
    	return genToken(customerId,TTLMins);
    }
    @Override
    public String genToken(String customerId,int ttlMin) {
        if (customerId == null) {
            return null;
        }
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        // 生成签名密钥 就是一个base64加密后的字符串？
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        // 添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam(Header.TYPE, JWT) // token类型
                
                //--claim
        		  .setIssuedAt(now) // 创建时间
                .setSubject(customerId) // 主题，也差不多是个人的一些信息
                //.setIssuer(issuer) // 签发者
                .setIssuer(customerId.toString())
                //.setAudience(audience) // 个人签名
                .setAudience(customerId.toString())
                
                //-----
                .signWith(signatureAlgorithm, signingKey) // 签名算法及密钥
                //.compressWith(CompressionCodecs.GZIP)
                .compressWith(io.jsonwebtoken.CompressionCodecs.DEFLATE)
        ;
        // 添加Token过期时间
        if (ttlMin > 0) {
        	// 过期时间
            long expMillis = nowMillis + ttlMin*60*1000;
            // 过期时间
            Date exp = new Date(expMillis);
            // 系统时间之前的token都是不可以被承认的
            builder.setExpiration(exp).setNotBefore(now);//claim
        }else {
        	// 过期时间
            long expMillis = nowMillis + TTLMins*60*1000;
            // 过期时间
            Date exp = new Date(expMillis);
            // 系统时间之前的token都是不可以被承认的
            builder.setExpiration(exp).setNotBefore(now);//claim
        }
        // 生成JWT
        String token = builder.compact();
        return token;
    }

    @Override
    public String parseToken(String token) {
        Claims claims = parseJWT(token);
        if (claims != null) {
            return claims.getSubject();
        }

        return null;
    }

    private Claims parseJWT(String jsonWebToken) {
        try {
            Claims claims = Jwts.parser()
            // 使用签名的时候，使用这个解析
                    .setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
                    .parseClaimsJws(jsonWebToken)
                    // 不使用签名的时候，应用这个解析
                    // .parseClaimsJwt(jsonWebToken)
                    .getBody();
            return claims;
        } catch (Exception ex) {
            return null;
        }
    }
    @Override
    public String refreshToken(String token,int ttlMins) {
    	 if (useable(token)) {
             Claims claims = parseJWT(token);
             return genToken(claims.getSubject(),ttlMins);

         }

         return null;
    }
    @Override
    public String refreshToken(String token) {
        return refreshToken(token, TTLMins);
    }

    @Override
    public boolean useable(String token) {
        if (isNotExpired(token)) {
            return true;
        } else {
            // 如果过期了，但不超8小时，则可以使用
            Claims claims = parseJWT(token);
            if (claims != null) {
                long expiredDate = claims.getExpiration().getTime();
                long nowMillis = System.currentTimeMillis();
                return expiredDate + LASTTTLMills >= nowMillis;

            }
        }
        return false;
    }

    /**
     * 使用jwt作为前后端分离的登陆认证方式, 交互
     */
}
