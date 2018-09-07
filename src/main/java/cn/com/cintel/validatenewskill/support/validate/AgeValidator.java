package cn.com.cintel.validatenewskill.support.validate;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.Validator;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;

/**
 * @Company Name:
 * @Author: sky
 * @CreatDate: 2018/9/7 12:05
 * @ClassName: cn.com.cintel.validatenewskill.support.validate
 * @Description:
 * @Modified By:
 * @ModifyDate: 2018/9/7 12:05
 */
public class AgeValidator extends ValidatorHandler<Integer> implements Validator<Integer> {

    @Override
    public boolean validate(ValidatorContext context, Integer age) {
        if (age == null || age <= 0){
            context.addError(ValidationError.create(String.format("%s不能为空或者小于0！", age))
                    .setErrorCode(-1)
                    .setField("age")
                    .setInvalidValue(age));
            return false;
        }else if(age < 10 || age > 100){
            context.addError(ValidationError.create(String.format("%s不能小于10或者大于100",age))
                    .setErrorCode(-1)
                    .setField("age")
                    .setInvalidValue(age)
            );
        }

        return true;
    }
}
