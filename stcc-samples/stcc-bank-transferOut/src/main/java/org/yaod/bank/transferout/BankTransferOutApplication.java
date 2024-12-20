package org.yaod.bank.transferout;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableAspectJAutoProxy
@EnableFeignClients
@MapperScan({"org.yaod.stcc.persistence", "org.yaod.bank.transferout"})
public class BankTransferOutApplication {

	public static void main(String[] args) {

		SpringApplication.run(BankTransferOutApplication.class, args);
	}

}
