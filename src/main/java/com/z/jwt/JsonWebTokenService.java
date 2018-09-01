/**
 * 
 */
package com.z.jwt;

/**
 * @author Administrator
 *
 */
public interface JsonWebTokenService {

	/**
	 * 
	 * @param customerId
	 * @param ttlMin     有效时间，单位为分，值为<=0则使用默认值30分钟
	 * @return jwt格式的token值
	 */
	String genToken(String customerId, int ttlMins/**/);// 生成token

	String genToken(String customerId);// 生成token

	String/* customerId */ parseToken(String token); // 解析token

	boolean isNotExpired(String token); // 是否过期

	/**
	 * 所谓可用的token指的是过期没有过超过8小时的token
	 * 
	 * @param token
	 * @return
	 */
	boolean useable(String token);// 可用吗

	/**
	 * 只有可用的token才能更换新的token 刷新token延长过期时间
	 * 
	 * @param token
	 * @return 返回新的token
	 */
	String refreshToken(String token);

	String refreshToken(String token, int ttlMins);
}
