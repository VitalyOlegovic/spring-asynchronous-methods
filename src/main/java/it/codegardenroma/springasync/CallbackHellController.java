package it.codegardenroma.springasync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CallbackHellController {

    private static final Logger logger = LoggerFactory.getLogger(CallbackHellController.class);

    @Autowired
    AsyncService asyncService;

    @RequestMapping("/callbackhell")
    public String callBackHell() throws InterruptedException {
        ListenableFuture<String> listenable = asyncService.listenableMethod();
        listenable.addCallback(new ListenableFutureCallback<String>(){

            @Override
            public void onSuccess(String s) {
                try {
                    ListenableFuture lf = asyncService.listenableMethod();
                    lf.addCallback(new ListenableFutureCallback() {
                        @Override
                        public void onFailure(Throwable throwable) {
                            try {
                                ListenableFuture lf2 = asyncService.listenableMethod();
                            } catch (InterruptedException e) {
                                logger.error(e.getLocalizedMessage(), e);
                            }
                        }

                        @Override
                        public void onSuccess(Object o) {

                        }
                    });
                } catch (InterruptedException e) {
                    logger.error(e.getLocalizedMessage(), e);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });

        return "hello";
    }

}
