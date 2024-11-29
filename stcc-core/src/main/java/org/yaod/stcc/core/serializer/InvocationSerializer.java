package org.yaod.stcc.core.serializer;

import com.esotericsoftware.kryo.kryo5.Kryo;
import com.esotericsoftware.kryo.kryo5.io.Input;
import com.esotericsoftware.kryo.kryo5.io.Output;
import org.yaod.stcc.core.exception.StccRuntimeException;
import org.yaod.stcc.core.transaction.context.Invocation;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 *  serialize/de-serialize invocation object.
 *  invocation object is used to cancel/confirm action,
 *  because it is object.method(arguments), have to utilize binary serialization mechanism.
 *
 * @author Yaod
 **/
public class InvocationSerializer {

    //need make sure if kryo is thread-safe;
    static Kryo kryo = new Kryo();
    static {
        kryo.register(Invocation.class);
        kryo.setRegistrationRequired(false);
    }
    public static InvocationSerializer INST=new InvocationSerializer();
    private  InvocationSerializer(){}
    public synchronized byte[] serialize(Invocation Innovation){
        byte[] bytes;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             Output output = new Output(outputStream)) {
            kryo.writeObject(output, Innovation);
            bytes = output.toBytes();
            output.flush();
        } catch (Exception ex) {
            throw new StccRuntimeException("kryo serialize error", ex);
        }
        return bytes;
    }

    public synchronized  Invocation deSerialize(byte[] invocationBytes){
        if(invocationBytes==null||invocationBytes.length==0){
            return null;
        }
        Invocation invocation;
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(invocationBytes);
                Input input = new Input(inputStream)) {
            invocation = kryo.readObject(input, Invocation.class);
        } catch (Exception e) {
            throw new StccRuntimeException("kryo de-Serialize error" ,e);
        }
        return invocation;
    }
}
