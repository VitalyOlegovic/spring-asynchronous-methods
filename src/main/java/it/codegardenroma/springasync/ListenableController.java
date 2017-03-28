package it.codegardenroma.springasync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Controller
public class ListenableController {

    private static final Logger logger = LoggerFactory.getLogger(ListenableController.class);

    @Autowired
    AsyncService asyncService;

    @RequestMapping("/listenable")
    public DeferredResult<ModelAndView> listenable(ModelMap model) throws InterruptedException, ExecutionException {
        ListenableFuture<String> listenable = asyncService.listenableMethod();
        DeferredResult<ModelAndView> deferredResult = new DeferredResult<ModelAndView>();
        ExampleListenableFutureCallback callback = new ExampleListenableFutureCallback();
        callback.setDeferredResult(deferredResult);
        listenable.addCallback(callback);
        return deferredResult;
    }

    public class ExampleListenableFutureCallback implements ListenableFutureCallback<String> {

        private DeferredResult<ModelAndView> deferredResult;

        public void setDeferredResult(DeferredResult<ModelAndView> deferredResult) {
            this.deferredResult = deferredResult;
        }

        @Override
        public void onFailure(Throwable throwable) {
            logger.error(throwable.getLocalizedMessage(), throwable);
        }

        @Override
        public void onSuccess(String s) {
            Map<String,String> map = new HashMap<>();
            map.put("message",s);
            ModelAndView modelAndView = new ModelAndView("hello", map);
            deferredResult.setResult(modelAndView);
            logger.info("Success! Result: " + s);
        }
    }

}
