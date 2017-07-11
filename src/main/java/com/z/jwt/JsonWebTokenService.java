/**
 * 
 */
package com.z.jwt;

/**
 * @author Administrator
 *
 */
public interface JsonWebTokenService {

    String genToken(Long customerId);//生成token

    Long/* customerId */parseToken(String token); //解析token

    boolean isNotExpired(String token); //是否过期

    boolean useable(String token);// 可用吗

    /**
     * 刷新token延长过期时间
     * 
     * @param token
     * @return
     */
    String refreshToken(String token);
}
