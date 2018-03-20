package com.cnsyear.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;
/**
 * 自定义认证
 * @author jiebaobao
 *
 */
public class ShiroRealmSecond extends AuthenticatingRealm{

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		
		//测试。。。
		System.out.println("22222222222222222 doGetAuthenticationInfo...."+token.hashCode());
		//1.将AuthenticationToken转换成UsernamePasswordToken
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)token;
		//2.从UsernamePasswordToken中取出username
		String username = usernamePasswordToken.getUsername();
		//3.这里应该调用数据库进行查询username对应的数据  这里模拟一下
		System.out.println("从数据库中获取 username: " + username + " 所对应的用户信息.");
		//4.这里模拟测试一下 用户不存在的异常  UnknownAccountException 
		if("unknown".equals(username)){
			throw new UnknownAccountException("用户不存在!");
		}
		//5. 这里模拟测试一下  根据用户信息的情况, 决定是否需要抛出其他的 AuthenticationException 异常. 
		if("lock".equals(username)){
			throw new LockedAccountException("用户被锁定");
		}
		//6. 根据用户的情况, 来构建 AuthenticationInfo 对象并返回. 通常使用的实现类为: SimpleAuthenticationInfo
		//以下信息是从数据库中获取的.
		//1). principal: 认证的实体信息. 可以是 username, 也可以是数据表对应的用户的实体类对象. 
		Object principal = username;
		//2). credentials: 密码. 
		Object credentials = "";//模拟测试
		//模拟加盐操作 
		if("admin".equals(username)) {
			Object result = new SimpleHash("SHA1", "123456", ByteSource.Util.bytes(username), 1024);
			
			credentials = result.toString();
		}
		
		//3). realmName: 当前 realm 对象的 name. 调用父类的 getName() 方法即可
		String realmName = getName();
		// 4).盐值
		ByteSource salt = ByteSource.Util.bytes(username);//这里使用用户名做盐值，开发中一般生成随机数
		SimpleAuthenticationInfo info = null; //new SimpleAuthenticationInfo(principal, credentials, realmName);
		info = new SimpleAuthenticationInfo(principal, credentials,salt, realmName);
		return info;
	}
	
}
