package cn.com.cintel.validatenewskill;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"cn.com.cintel.validatenewskill"})
@MapperScan({"cn.com.cintel.validatenewskill.service.dao.generate"})
@EnableTransactionManagement
@EnableSpringHttpSession
public class ValidateNewSkillApplication {

    public static void main(String[] args) {
        SpringApplication.run(ValidateNewSkillApplication.class, args);
    }
}
