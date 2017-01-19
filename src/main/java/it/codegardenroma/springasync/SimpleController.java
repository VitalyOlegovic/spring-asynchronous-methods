package it.codegardenroma.springasync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Controller

public class SimpleController {

    private static final Logger logger = LoggerFactory.getLogger(SimpleController.class);

    @Autowired
    AsyncService asyncService;

    @RequestMapping("/simple")
    public String simple(ModelMap model) throws InterruptedException, ExecutionException {
        // Start the clock
        long start = System.currentTimeMillis();

        Future<String> result = asyncService.someMethod();

        // Wait until they are all done
        while (!(result.isDone())) {
            Thread.sleep(10); //10-millisecond pause between each check
        }

        // Print results, including elapsed time
        logger.info("Elapsed time: " + (System.currentTimeMillis() - start));
        logger.info("--> " + result.get());

        model.addAttribute("message", "Hello Spring MVC Framework!");

        return "hello";
    }

}
