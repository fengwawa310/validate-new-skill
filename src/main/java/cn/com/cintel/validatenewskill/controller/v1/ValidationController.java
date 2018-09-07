package cn.com.cintel.validatenewskill.controller.v1;

import cn.com.cintel.validatenewskill.service.BNetPatientService;
import cn.com.cintel.validatenewskill.service.entity.generate.BNetPatient;
import cn.com.cintel.validatenewskill.support.validate.AgeValidator;
import cn.com.cintel.validatenewskill.support.validate.NotNullStringValidator;
import cn.com.cintel.validatenewskill.support.validate.ValidateBean;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.Result;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Auther: sky
 * @Description:
 * @Date: Create in 14:27 2018/9/6
 * @Modified By:
 */
@RestController
public class ValidationController {

//    参考网址：
//    https://github.com/neoremind/fluent-validator

    @Resource
    BNetPatientService bNetPatientService;

    @GetMapping(value = "/getBNetPatientInfo",produces = "application/json;charset=utf-8")
    @ResponseBody
    public BNetPatient getBNetPatientInfo(){
        BNetPatient bNetPatient = bNetPatientService.getBNetPatientInfo();
        return bNetPatient;
    }

    @PostMapping(value = "/testValidator",produces = "application/json;charset=utf-8")
    @ResponseBody
    public Result testValidator(@RequestBody ValidateBean validateBean){
        System.out.println("name:"+validateBean.getName());
        System.out.println("age:"+validateBean.getAge());
        Result validatorResult = FluentValidator.checkAll().failOver()
                .on(validateBean.getAge(),new AgeValidator())
                .on(validateBean.getName(),new NotNullStringValidator())
                .doValidate()
                .result(ResultCollectors.toSimple());

        if (!validatorResult.isSuccess()) {
            System.out.println(validatorResult.getErrors());
        }

        return validatorResult;
    }


}
