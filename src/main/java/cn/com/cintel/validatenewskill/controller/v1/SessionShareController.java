package cn.com.cintel.validatenewskill.controller.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: SessionShareController
 * @Description: 测试redis的session共享
 * @Auther: 49092
 * @CreateDate: 2018/9/15 14:39
 * @Version: 1.0
 * @UpdateDate:
 */
@RestController
@RequestMapping(value = "/v1/share")
public class SessionShareController {

    @GetMapping(value = "/first",produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> firstResp(HttpServletRequest httpServletRequest){
        Map<String,Object> map = new HashMap<>();
        httpServletRequest.getSession().setAttribute
                ("request Url",httpServletRequest.getRequestURL());
        map.put("request Url",httpServletRequest.getRequestURL());
        return map;
    }

    @GetMapping(value = "/sessions",produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> sessions(HttpServletRequest httpServletRequest){
        Map<String,Object> map = new HashMap<>();
        map.put("sessionId",httpServletRequest.getSession().getId());
        map.put("message",httpServletRequest.getSession().getAttribute("request Url"));
        return map;
    }

}
