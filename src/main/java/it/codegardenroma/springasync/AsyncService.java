package it.codegardenroma.springasync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class AsyncService {

    private static final Logger logger = LoggerFactory.getLogger(AsyncService.class);

    @Async
    public Future<String> futureMethod(){
        Future<String> future = null;
        try {
            Thread.sleep(1000L);
            future = AsyncResult.forValue("OK from futureMethod()");
        } catch (InterruptedException e) {
            logger.error(e.getLocalizedMessage(), e);
            future = AsyncResult.forExecutionException(e);
        }
        return future;
    }

    @Async
    public ListenableFuture listenableMethod() throws InterruptedException {
        ListenableFuture<String> listenableFuture = null;
        try {
            Thread.sleep(1000L);
            listenableFuture = AsyncResult.forValue("OK from listenableMethod()");
        } catch (InterruptedException e) {
            logger.error(e.getLocalizedMessage(), e);
            listenableFuture = AsyncResult.forExecutionException(e);
        }
        return listenableFuture;
    }

    @Async
    public CompletableFuture<String> completableMethod() throws InterruptedException{
        CompletableFuture<String> completableFuture = new CompletableFuture<String>();
        try {
            Thread.sleep(1000L);
            completableFuture.complete("OK from completableMethod()");
        } catch (InterruptedException e) {
            logger.error(e.getLocalizedMessage(), e);
            completableFuture.completeExceptionally(e);
        }
        return completableFuture;
    }

}
