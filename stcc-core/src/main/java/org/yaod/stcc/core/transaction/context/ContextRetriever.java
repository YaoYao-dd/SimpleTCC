package org.yaod.stcc.core.transaction.context;

/**
 * @author Yaod
 **/
public interface ContextRetriever {
    Transaction retrieveFromRequest();
}
