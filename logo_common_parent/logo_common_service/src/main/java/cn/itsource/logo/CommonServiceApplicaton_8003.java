package cn.itsource.logo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
//@EnableFeignClients(basePackages = "cn.itsource.logo.client")//负载均衡根据暴露的接口去找路径
public class CommonServiceApplicaton_8003 {
     public static void main(String[] args) {
         SpringApplication.run(CommonServiceApplicaton_8003.class);

     }
}
