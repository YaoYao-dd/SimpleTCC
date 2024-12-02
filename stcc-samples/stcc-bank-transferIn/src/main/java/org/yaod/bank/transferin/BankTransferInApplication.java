package org.yaod.bank.transferin;

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
@MapperScan({"org.yaod.stcc.persistence", "org.yaod.bank.transferin"})
public class BankTransferInApplication {

	public static void main(String[] args) {

		SpringApplication.run(BankTransferInApplication.class, args);
	}

}
