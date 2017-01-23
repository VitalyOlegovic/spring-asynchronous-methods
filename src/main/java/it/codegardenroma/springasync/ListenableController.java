package it.codegardenroma.springasync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ListenableController {

    private static final Logger logger = LoggerFactory.getLogger(ListenableController.class);

    @Autowired
    AsyncService asyncService;

    @RequestMapping("/listenable")
    public String listenable() throws InterruptedException {
        ListenableFuture<String> listenable = asyncService.listenableMethod();
        ListenableFutureCallback<String> callback = new ExampleListenableFutureCallback();
        listenable.addCallback(callback);

        return "hello";
    }

    public class ExampleListenableFutureCallback implements ListenableFutureCallback<String> {

        @Override
        public void onFailure(Throwable throwable) {
            logger.error(throwable.getLocalizedMessage(), throwable);
        }

        @Override
        public void onSuccess(String s) {
            logger.info("Success! Result: " + s);
        }
    }

}
