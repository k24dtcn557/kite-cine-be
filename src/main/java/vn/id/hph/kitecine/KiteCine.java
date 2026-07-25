package vn.id.hph.kitecine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableFeignClients
@EnableTransactionManagement
@EnableScheduling
public class KiteCine {
    static void main(String[] args) {
        SpringApplication.run(KiteCine.class, args);
    }
}
