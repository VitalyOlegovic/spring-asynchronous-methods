package it.codegardenroma.springasync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

@Controller
public class CallbackHellController {

    private static final Logger logger = LoggerFactory.getLogger(CallbackHellController.class);

    @Autowired
    AsyncService asyncService;

    @RequestMapping("/callbackhell")
    public DeferredResult<ModelAndView> callBackHell(Model model) throws InterruptedException, ExecutionException {
        logger.info("begin callbackhell");

        DeferredResult<ModelAndView> deferredResult = new DeferredResult<ModelAndView>();
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
                            logger.info("success");
                        }
                    });

                    Map<String,String> map = new HashMap<String,String>();
                    map.put("message",s);
                    ModelAndView modelAndView = new ModelAndView("hello",map);
                    deferredResult.setResult(modelAndView);
                } catch (InterruptedException e) {
                    logger.error(e.getLocalizedMessage(), e);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                logger.error(throwable.getLocalizedMessage(),throwable);
            }
        });
        model.addAttribute("message", listenable.get());
        logger.info("end callbackhell");
        return deferredResult;
    }

    @ExceptionHandler
    @ResponseBody
    public String handleException(IllegalStateException ex) {
        return "Handled exception: " + ex.getMessage();
    }

}
