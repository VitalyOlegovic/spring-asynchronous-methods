package it.codegardenroma.springasync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.CompletableFuture;

@Controller
public class CompletableController {

    private static final Logger logger = LoggerFactory.getLogger(CompletableController.class);

    @Autowired
    AsyncService asyncService;

    @RequestMapping("/completable")
    public String completable() throws InterruptedException {
        CompletableFuture<String> completableFuture = asyncService.completableMethod();
        completableFuture = completableFuture.exceptionally(this::onException);
        completableFuture = completableFuture.thenApply(this::intermediateMethod);
        completableFuture.thenAccept(this::lastMethod);

        return "hello";
    }

    public String onException(Throwable throwable){
        logger.error(throwable.getLocalizedMessage(), throwable);
        return "hello";
    }

    public String intermediateMethod(String string){
        return string;
    }

    public void lastMethod(String string){

    }

}
