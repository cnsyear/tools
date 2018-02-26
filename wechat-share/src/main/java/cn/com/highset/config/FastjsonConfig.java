package cn.com.highset.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * 
* <b>Description:fastjson配置类</b><br> 
* @author zhaojiabin
* @version 1.0
* @Note
* <b>ProjectName:</b> data-clean
* <br><b>PackageName:</b> cn.com.highset.config
* <br><b>ClassName:</b> FastjsonConfig
* <br><b>Date:</b> 2017年10月18日 上午10:07:58
 */
@Configuration
public class FastjsonConfig {

    @Bean
    public FastJsonHttpMessageConverter4 fastJsonHttpMessageConverter() {
        FastJsonHttpMessageConverter4 converter = new FastJsonHttpMessageConverter4();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.PrettyFormat,
                SerializerFeature.WriteMapNullValue
        );
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        ValueFilter valueFilter = new ValueFilter() {
        	@Override
            public Object process(Object o, String s, Object o1) {
                if (null == o1) {
                    o1 = "";
                }
                return o1;
            }
        };
        fastJsonConfig.setCharset(Charset.forName("utf-8"));
        fastJsonConfig.setSerializeFilters(valueFilter);
        converter.setFastJsonConfig(fastJsonConfig);

        ArrayList<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        converter.setSupportedMediaTypes(mediaTypes);
        return converter;
    }

}
