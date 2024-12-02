package org.yaod.stcc.springboot;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.yaod.stcc.core.serializer.TransactionSerializer;
import org.yaod.stcc.core.transaction.context.ContextHolder;
import org.yaod.stcc.core.utils.Constants;

/**
 * @author Yaod
 **/
public class ReignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        var context = ContextHolder.getForNextRPC();
        String contextStr= TransactionSerializer.INST.serialize(context);
        template.header(Constants.STCC_TRANSACTION_HTTP_HEADER_NAME, contextStr);
    }
}
