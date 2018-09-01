# jwt 

jwt全称 json web token，应用jwt可以解决前后端分离的框架方案的登陆与认证问题。

这个demo,演示了如何使用jwt api.


## 场景
前后端分离时的用户与token的绑定，代替session的使用；




## demo实现功能

本demo只实现了上面场景中的关于token的部分，即3,6.

	  	String genToken(String customerId);//生成token
	
	    String/* customerId */parseToken(String token); //解析token
	
	    boolean isNotExpired(String token); //是否过期
	
	    boolean useable(String token);// 可用吗
	
	    String refreshToken(String token); //刷新token
