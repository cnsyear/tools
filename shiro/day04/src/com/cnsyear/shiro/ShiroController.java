package com.cnsyear.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 登录控制器
 * @author jiebaobao
 *
 */
@RequestMapping("/shiro")
@Controller
public class ShiroController {

	@RequestMapping("/login")
	public String login(@RequestParam String username, @RequestParam String password) {
		// 1.获取当前的Subject
		Subject currentUser = SecurityUtils.getSubject();
		// 2.测试当前的用户是否已经被认证. 即是否已经登录.
		if (!currentUser.isAuthenticated()) {
			// 3.把用户名和密码封装成UsernamePasswordToken对象
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			// remember me
			token.setRememberMe(true);
			// 4.执行登录
			try {
				System.out.println("111:"+token.hashCode());
				currentUser.login(token);
				
			 } 
   
            // 所有认证时异常的父类. 
            catch (AuthenticationException e) {
                //unexpected condition?  error?
            	System.out.println("登录失败");
            }
		}

		// 登录成功
		return "redirect:/index";
	}
}
