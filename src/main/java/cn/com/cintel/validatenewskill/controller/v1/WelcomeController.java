package cn.com.cintel.validatenewskill.controller.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @company Name:
 * @author: sky
 * @creatDate: 2018/12/3 10:41 AM
 * @className: WelcomeController
 * @description: 测试启动接口
 * @modified By:
 * @modifyDate:
 */
@RestController
public class WelcomeController {

    @GetMapping(value = "/welcome")
    public void welcome(){
        //测试提交代码到分支 branch
        System.out.println("Hello World!");
    }

}
