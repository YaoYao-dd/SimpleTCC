package org.yaod.stcc.springboot.autoconfigure;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.SqlSessionFactoryBeanCustomizer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yaod.stcc.core.StccTransactionAspect;
import org.yaod.stcc.core.persistence.TccTransactionRepository;
import org.yaod.stcc.core.persistence.TccTransactionPersistence;
import org.yaod.stcc.persistence.InvocationTypeHandler;
import org.yaod.stcc.springboot.ReighInterceptor;
import org.yaod.stcc.core.config.StccConfig;

/**
 * @author Yaod
 **/
@AutoConfiguration
public class StccAutoConfiguration {

    @Configuration(proxyBeanMethods = false)
    //@ConditionalOnClass(SomeService.class)
    public static class FeighConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public ReighInterceptor reighInterceptor() {

            return new ReighInterceptor();
        }

        @ConfigurationProperties(prefix = "stcc")
        @Bean
        public StccConfig stccConfig(){
            return new StccConfig();
        }

        @Bean
        public StccTransactionAspect stccTransAspect(TccTransactionPersistence tPersistence){
            TccTransactionRepository.INST.setTccPersistence(tPersistence);
            return new StccTransactionAspect();
        }


        @Bean
        SqlSessionFactoryBeanCustomizer sqlSessionFactoryBeanCustomizer() {
            return new SqlSessionFactoryBeanCustomizer() {
                @Override
                public void customize(SqlSessionFactoryBean factoryBean) {
                    factoryBean.addTypeHandlers(new InvocationTypeHandler());
                }
            };
        }

    }

}