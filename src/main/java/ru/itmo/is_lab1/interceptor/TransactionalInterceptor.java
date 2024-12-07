package ru.itmo.is_lab1.interceptor;

import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.itmo.is_lab1.interceptor.annotation.Transactional;

import java.sql.Connection;

@Transactional
@Interceptor
public class TransactionalInterceptor {
    @Inject
    private Session hibernateSession;

    @AroundInvoke
    public Object checkMethod(InvocationContext context) throws Exception {
        int isolationLevel = context.getMethod().getAnnotation(Transactional.class).value();
        boolean isTransactionSource = false;
        Transaction transaction = hibernateSession.getTransaction();

        if (!transaction.isActive()){
            isTransactionSource = true; //  Фиксируем, что это внешний обработчик транзакции
            hibernateSession.doWork(connection -> connection.setTransactionIsolation(isolationLevel));
            transaction.begin();
        }

        try {
            var res = context.proceed();
            if (isTransactionSource) transaction.commit();
            return res;
        } catch (Exception exception){
            if (isTransactionSource) transaction.rollback();
            throw exception;
        }
        finally {
            if (isTransactionSource) hibernateSession.doWork(connection -> connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED));
        }
    }

}
