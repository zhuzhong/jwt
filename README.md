#jwt 

jwt全称 json web token，应用jwt可以解决前后端分离的框架方案的登陆与认证问题。

这个demo,演示了如何使用jwt api.


##场景



1. 前端用户输入相应的登陆信息，然后提交请求；
2. 后端服务接口接收到相应的登陆请求，认证用户登陆信息的正确性，并获取到用户customerId;
3. 应用customerId,及相应的签名算法，压缩算法生成相应的token;
4. 将相应的token返回前端用户
4. 前端用户在请求其他的api接口时，需要在头部(header)提交相应的token;
5. 后端服务对于token的有效性校验，及过期token的刷新等操作



##demo实现功能

本demo只实现了上面场景中的关于token的部分，即3,6.

	  	String genToken(Long customerId);//生成token
	
	    Long/* customerId */parseToken(String token); //解析token
	
	    boolean isNotExpired(String token); //是否过期
	
	    boolean useable(String token);// 可用吗
	
	    String refreshToken(String token); //刷新token