package com.javed.lambda.repository;

import com.javed.lambda.model.Credential;
import com.javed.lambda.model.WorkOut;
import com.javed.lambda.model.WorkOutList;
import com.javed.lambda.utility.ResponseParser;
import com.javed.lambda.utility.WorkoutLoggerConstant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class WorkOutLogRepositoryImpl implements WorkOutLogRepository {

    private static final Logger logger = LogManager.getLogger(WorkOutLogRepositoryImpl.class);

    private DynamoDbClient dynamoDbClient;

    /**
     * log workout for logged in username.
     *
     * @param @{@link WorkOut}
     * @return @{@link WorkOut}
     */
    @Override
    public WorkOut logWorkOut(WorkOut workOut) {
        logger.info("calling dynamo db to log workout with username : {}", workOut.getUsername());

        HashMap<String, AttributeValue> itemValues = new HashMap<String, AttributeValue>();
        ResponseParser.prepareWorkOutInsert(itemValues, workOut);

        PutItemRequest putItemRequest = PutItemRequest
                                                .builder()
                                                .tableName(WorkoutLoggerConstant.workOutTable)
                                                .item(itemValues)
                                                .build();

        try {
            dynamoDbClient = DynamoDbClient.create();
            PutItemResponse putItemResponse = dynamoDbClient.putItem(putItemRequest);
            logger.info("successfully inserted workout data for username : {}", workOut.getUsername());
        } catch (DynamoDbException e) {
            logger.error("error occurred while adding record to dynamo db : {}", e.getMessage());
        } finally {
            if (null != dynamoDbClient) {
                logger.debug("closing dynamo db client");
                dynamoDbClient.close();
            }
        }

        return workOut;
    }

    /**
     * list workout details for logged in user and search parameter.
     *
     * @param @{@link String} username
     * @param @{@link String} pageSize
     * @param @{@link String} pageNUmber
     * @param @{@link String} date
     * @return @{@link WorkOutList}
     */
    @Override
    public WorkOutList getWorkOutDetail(String username, String pageSize, String pageNumber, String date) {
        List<WorkOut> workOuts = new ArrayList<WorkOut>();
        WorkOutList workOutList = new WorkOutList();
        logger.info("calling dynamo db table to get workout for username : {}", username);

        ScanRequest scanRequest = ScanRequest.builder().tableName(WorkoutLoggerConstant.workOutTable).build();

        try {
            dynamoDbClient = DynamoDbClient.create();
            ScanResponse scanResponse = dynamoDbClient.scan(scanRequest);
            workOuts = ResponseParser.retrieveWorkOutList(scanResponse, username, date, pageSize, pageNumber);
            logger.info("successfully pulled workout details for username : {}", username);
        } catch (DynamoDbException e) {
            logger.error("error occurred while retrieving record to dynamo db : {}", e.getMessage());
        } finally {
            if (null != dynamoDbClient) {
                logger.debug("closing dynamo db client");
                dynamoDbClient.close();
            }
        }

        workOutList.setWorkOuts(workOuts);
        return workOutList;
    }
}
