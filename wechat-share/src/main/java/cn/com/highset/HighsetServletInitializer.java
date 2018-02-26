package cn.com.highset;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * 
* <b>Description:Highset Web程序启动类</b><br> 
* @author zhaojiabin
* @version 1.0
* @Note
* <b>ProjectName:</b> data-clean
* <br><b>PackageName:</b> cn.com.highset
* <br><b>ClassName:</b> HighsetServletInitializer
* <br><b>Date:</b> 2017年10月18日 上午9:22:56
 */
public class HighsetServletInitializer extends SpringBootServletInitializer {
	/**
	 * configure
	 */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(HighsetApplication.class);
    }

}
