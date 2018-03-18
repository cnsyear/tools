package com.cnsyear.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.realm.AuthenticatingRealm;
/**
 * 自定义认证
 * @author jiebaobao
 *
 */
public class ShiroRealm extends AuthenticatingRealm{

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//测试。。。
		System.out.println("doGetAuthenticationInfo...."+token.hashCode());
	
		return null;
	}

 
}
