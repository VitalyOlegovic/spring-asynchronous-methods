# Spring Asynchronous Methods
A Spring 4 application to show how to create and invoke asynchronous methods.

This example is made to show the different usages of asynchronous methods inside Spring.

An asynchronous method can return:

* a [Future](https://docs.oracle.com/javase/6/docs/api/java/util/concurrent/Future.html) (java.util.concurrent.Future);
* a Spring 4 [ListenableFuture](http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/util/concurrent/ListenableFuture.html) (org.springframework.util.concurrent.ListenableFuture) which can be used with Java 6;
* a Java 8 [CompletableFuture](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html) (java.util.concurrent.CompletableFuture).

To run the example:

    mvn jetty:run
    
Then on your browser, go to [localhost:15000/SpringAsynchronousMethods/](http://localhost:15000/SpringAsynchronousMethods/)