package cn.com.highset;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/***
 * 
* <b>Description:SpringBoot方式启动类</b><br> 
* @author zhaojiabin
* @version 1.0
* @Note
* <b>ProjectName:</b> wechat-share
* <br><b>PackageName:</b> cn.com.highset
* <br><b>ClassName:</b> HighsetApplication
* <br><b>Date:</b> 2017年11月1日 下午12:36:09
 */
@SpringBootApplication
@EnableScheduling
public class HighsetApplication extends WebMvcConfigurerAdapter{

   /**
    * 
   * <b>Description:主运行方法</b><br> 
   * @param args
   * @Note
   * <b>Author:</b>zhaojiabin</a>
   * <br><b>Date:</b> 2017年11月1日 下午12:36:01
   * <br><b>Version:</b> 1.0
    */
    public static void main(String[] args) {
        SpringApplication.run(HighsetApplication.class, args);
        System.out.println("HighsetApplication is sussess!");

    }
}
