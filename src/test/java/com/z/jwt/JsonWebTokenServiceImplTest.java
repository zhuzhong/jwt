/**
 * 
 */
package com.z.jwt;

import org.junit.BeforeClass;
import org.junit.Test;

import com.z.jwt.support.JsonWebTokenServiceImpl;

/**
 * @author Administrator
 *
 */
public class JsonWebTokenServiceImplTest {

    private static JsonWebTokenService jwtService;

    @BeforeClass
    public static void init() {
        jwtService = new JsonWebTokenServiceImpl();
    }

    @Test
    public void loopTest() {
    		long currentTime=System.currentTimeMillis();
    		System.out.println(String.format("currentTime=%d",currentTime));
    		for(long i=0L;i<10000L;i++) {
    			 String token = jwtService.genToken("1234567890"+i);
    		        System.out.println(token);
    		        System.out.println(token.length());
    		}
    		System.out.println(String.format("elapsed time=%d millis", System.currentTimeMillis()-currentTime));
    }
    
    @Test
    public void genToken() {
        String token = jwtService.genToken(123456789000L+"");
        System.out.println(token);
        System.out.println(token.length());

        String customerId = jwtService.parseToken(token);
        System.out.println("customerId=" + customerId);
        System.out.println("isNotExpired=" + jwtService.isNotExpired(token));
      
    }

    @Test
    public void refrshToken() {
        String oldToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1NjU3OCIsImlhdCI6MTQ5OTc2MDg4MSwiaXNzIjoiemh1emhvbmciLCJhdWQiOiJ6aHV6aG9uZyIsImV4cCI6MTQ5OTc2MjY4MSwibmJmIjoxNDk5NzYwODgxfQ.T9DvTjscUAzwQ7uGlc9aePx618Usd3Vcm-DGCHnVda0";
        String newToken=jwtService.refreshToken(oldToken);
        System.out.println("newToken="+newToken);

        System.out.println("isNotExpired=" + jwtService.isNotExpired(oldToken));

        
        
        String customerId = jwtService.parseToken(newToken);
        System.out.println("customerId=" + customerId);
        System.out.println("isNotExpired=" + jwtService.isNotExpired(newToken));
        
        
        // System.out.println("isNotExpired=" + jwtService.isNotExpired(token));
    }
}
