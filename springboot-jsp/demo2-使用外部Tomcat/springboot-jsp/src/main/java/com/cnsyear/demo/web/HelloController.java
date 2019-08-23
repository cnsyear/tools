package com.cnsyear.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description 测试控制器
 * @Author jie.zhao
 * @Date 2019/8/23 10:46
 */
@Controller
public class HelloController {

    @RequestMapping(value = {"/", "/index"})
    public String index(Model model) {
        model.addAttribute("name","ZHAOJIE");
        model.addAttribute("age","18");
        return "index";
    }
}
