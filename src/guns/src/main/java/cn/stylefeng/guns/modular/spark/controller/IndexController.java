package cn.stylefeng.guns.modular.spark.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2018/11/26 0026.
 */
@Controller
public class IndexController {

    @RequestMapping("/spark/index")
    public String Main(){
        return "/spark/index.html";
    }

}
