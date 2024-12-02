package org.yaod.stcc.springboot.autoconfigure;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.SqlSessionFactoryBeanCustomizer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.yaod.stcc.core.objectholder.SingletonObjectHolder;
import org.yaod.stcc.core.StccTransactionAspect;
import org.yaod.stcc.core.persistence.TxnRepositoryHolder;
import org.yaod.stcc.core.serializer.TransactionSerializer;
import org.yaod.stcc.core.transaction.context.ContextHolder;
import org.yaod.stcc.core.transaction.context.ContextRetriever;
import org.yaod.stcc.core.transaction.context.Transaction;
import org.yaod.stcc.core.utils.Constants;
import org.yaod.stcc.persistence.TccTransactionMapper;
import org.yaod.stcc.persistence.TccTransactionRepository;
import org.yaod.stcc.persistence.InvocationTypeHandler;
import org.yaod.stcc.springboot.ReignInterceptor;
import org.yaod.stcc.core.config.StccConfig;
import org.yaod.stcc.springboot.SpringObjectHolder;
import org.yaod.stcc.springboot.SpringRequestFilter;
import org.yaod.stcc.springboot.feign.OpenFeignPostProcessor;

/**
 * @author Yaod
 **/
@AutoConfiguration
public class StccAutoConfiguration {

    @Configuration(proxyBeanMethods = false)
    public static class FeignConfiguration {



        @ConfigurationProperties(prefix = "stcc")
        @Bean
        public StccConfig stccConfig(){
            return new StccConfig();
        }




        @Bean
        public StccTransactionAspect stccTransAspect(TccTransactionRepository persistenceRepo, SpringObjectHolder springObjectHolder){

            //initialize singleton objects.
            TxnRepositoryHolder.INST.setTxnPersistence(persistenceRepo);
            SingletonObjectHolder.INST.setObjectHolder(springObjectHolder);

            return new StccTransactionAspect();
        }


        @Bean
        public TccTransactionRepository txnRepository(TccTransactionMapper mapper){
            return new TccTransactionRepository(mapper);
        }
        @Bean
        public SpringObjectHolder springObjectHolder(ApplicationContext appContext){
            return new SpringObjectHolder(appContext);
        }

        @Bean
        public ReignInterceptor reignInterceptor() {
            return new ReignInterceptor();
        }

        @Bean
        public SpringRequestFilter setupTxnFilter(){
            return new SpringRequestFilter();
        }
        @Bean
        public OpenFeignPostProcessor openFeignPostProcessor(){
            return new OpenFeignPostProcessor();
        }
        @Bean
        public SqlSessionFactoryBeanCustomizer sqlSessionFactoryBeanCustomizer() {
            return new SqlSessionFactoryBeanCustomizer() {
                @Override
                public void customize(SqlSessionFactoryBean factoryBean) {
                    factoryBean.addTypeHandlers(new InvocationTypeHandler());
                }
            };
        }

    }

}