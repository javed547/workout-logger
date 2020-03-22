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

/**
 * Hello world!
 *
 */
public class Launcher implements RequestHandler<AwsProxyRequest, AwsProxyResponse>
{
    private static SpringLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;
    private static final Logger logger = LoggerFactory.getLogger(Launcher.class);

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
