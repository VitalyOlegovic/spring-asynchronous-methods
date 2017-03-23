package it.codegardenroma.springasync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Controller
public class CompletableController {

    private static final Logger logger = LoggerFactory.getLogger(CompletableController.class);

    @Autowired
    AsyncService asyncService;

    @RequestMapping("/completable")
    public DeferredResult<ModelAndView> completable(ModelMap model) throws InterruptedException, ExecutionException {
        logger.info("begin completable");
        DeferredResult<ModelAndView> deferredResult = new DeferredResult<ModelAndView>();
        CompletableFuture<String> completableFuture = asyncService.completableMethod();
        logger.info( "The current state of the CompletableFuture is: " + completableFuture.toString());
        completableFuture = completableFuture.exceptionally(this::onException);
        completableFuture = completableFuture.thenApply(this::intermediateMethod);
        completableFuture.thenAccept((String s)->{
            Map<String,String> map = new HashMap<String,String>();
            map.put("message", s);
            ModelAndView modelAndView = new ModelAndView("hello", map);
            deferredResult.setResult(modelAndView);
        });
        model.addAttribute("message", completableFuture.get());
        logger.info("end completable");
        return deferredResult;
    }

    public String onException(Throwable throwable){
        logger.error(throwable.getLocalizedMessage(), throwable);
        return "hello";
    }

    public String intermediateMethod(String string){
        logger.info("intermediateMethod");
        return string;
    }

    public void lastMethod(String string){
        logger.info("lastMethod");
    }

}
