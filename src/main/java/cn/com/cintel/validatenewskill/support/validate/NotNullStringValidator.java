package cn.com.cintel.validatenewskill.support.validate;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.Validator;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;

import javax.xml.validation.ValidatorHandler;

/**
 * @Company Name:
 * @Author: sky
 * @CreatDate: 2018/9/7 11:35
 * @ClassName: cn.com.cintel.validatenewskill.support.validate
 * @Description:
 * @Modified By:
 * @ModifyDate: 2018/9/7 11:35
 */
public class NotNullStringValidator extends com.baidu.unbiz.fluentvalidator.ValidatorHandler<String>
        implements Validator<String> {

    /**
     * 校验方法
     * @param checkedString 需要被校验字符串
     */
    @Override
    public boolean validate(ValidatorContext context, String checkedString){
        if (null == checkedString || "" == checkedString) {
            context.addError(ValidationError.create(String.format("%s不能为空！", checkedString))
                    .setErrorCode(-1)
                    .setField("name")
                    .setInvalidValue(checkedString));
            return false;
        }
        return true;
    }

}
