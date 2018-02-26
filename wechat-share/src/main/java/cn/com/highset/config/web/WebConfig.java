package cn.com.highset.config.web;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;


/**
 * 
* <b>Description:web 配置类</b><br> 
* @author zhaojiabin
* @version 1.0
* @Note
* <b>ProjectName:</b> wechat-share
* <br><b>PackageName:</b> cn.com.highset.config.web
* <br><b>ClassName:</b> WebConfig
* <br><b>Date:</b> 2017年11月1日 下午12:28:10
 */
@Configuration
public class WebConfig {

    /**
     * RequestContextListener注册
     */
    @Bean
    public ServletListenerRegistrationBean<RequestContextListener> requestContextListenerRegistration() {
        return new ServletListenerRegistrationBean<>(new RequestContextListener());
    }
}
