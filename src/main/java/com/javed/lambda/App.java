package com.javed.lambda;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.javed.lambda.config.MvcConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * App acts as application handler class.
 * It initializes spring lambda container with @{@link MvcConfig} with in turns enable Spring MVC in back ground
 * With the help of this setup, application can be developed as Spring MVC with annotation driven.
 *
 * @author Mohd Javed
 * @since 0.0.1
 * @version 0.0.1
 *
 */
public class App implements RequestHandler<AwsProxyRequest, AwsProxyResponse>
{
    private static SpringLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    @Override
    public AwsProxyResponse handleRequest(AwsProxyRequest awsProxyRequest, Context context) {
        if (handler == null) {
            try {
                handler = SpringLambdaContainerHandler.getAwsProxyHandler(MvcConfig.class);
            } catch (ContainerInitializationException e) {
                logger.error("Unable to create handler {}", e.getMessage());
                return null;
            }
        }

        return handler.proxy(awsProxyRequest, context);
    }

}
