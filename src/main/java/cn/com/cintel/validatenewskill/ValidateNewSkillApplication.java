package cn.com.cintel.validatenewskill;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackages = {"cn.com.cintel.validatenewskill"})
@MapperScan({"cn.com.cintel.validatenewskill.service.dao.generate"})
@EnableTransactionManagement
public class ValidateNewSkillApplication {

    public static void main(String[] args) {
        SpringApplication.run(ValidateNewSkillApplication.class, args);
    }
}
