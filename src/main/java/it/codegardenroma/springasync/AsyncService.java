package it.codegardenroma.springasync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service
public class AsyncService {

    private static final Logger logger = LoggerFactory.getLogger(AsyncService.class);

    /***
     *
      * @return
     * @throws InterruptedException
     */
    @Async
    public Future<String> futureMethod() throws InterruptedException {
        Thread.sleep(1000L);
        return new AsyncResult<String>("OK");
    }

    @Async
    public ListenableFuture listenableMethod() throws InterruptedException {
        Thread.sleep(1000L);
        return new AsyncResult<String>("OK");
    }

    @Async
    public CompletableFuture<String> completableMethod() throws InterruptedException{
        Thread.sleep(1000L);
        return CompletableFuture.completedFuture("OK");
    }

}
