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
        hibernateSession.doWork(connection -> connection.setTransactionIsolation(isolationLevel));
        Transaction transaction = hibernateSession.getTransaction();
        try {
            transaction.begin();
            var result = context.proceed();
            transaction.commit();
            return result;
        } catch (Exception exception){
            transaction.rollback();
            throw exception;
        }
        finally {
            hibernateSession.doWork(connection -> connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED));
        }
    }
}
