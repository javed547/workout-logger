package com.javed.lambda.utility;

import com.javed.lambda.model.Credential;
import com.javed.lambda.model.User;
import com.javed.lambda.model.WorkOut;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;

import java.util.*;

public class ResponseParser {

    private static final Logger logger = LogManager.getLogger(ResponseParser.class);

    public static HashMap<String, AttributeValue> prepareUserInsert(HashMap<String, AttributeValue> itemValues, User user) {
        itemValues.put("username", AttributeValue.builder().s(user.getUsername()).build());
        itemValues.put("password", AttributeValue.builder().s(user.getPassword()).build());
        itemValues.put("firstname", AttributeValue.builder().s(user.getFirstname()).build());

        itemValues.put("lastname", AttributeValue.builder().s(user.getLastname()).build());
        itemValues.put("email", AttributeValue.builder().s(user.getEmail()).build());
        itemValues.put("address", AttributeValue.builder().s(user.getAddress()).build());

        itemValues.put("phone", AttributeValue.builder().s(String.valueOf(user.getPhone())).build());
        logger.debug("user object created with details as {}", user.toString());
        return itemValues;
    }

    public static HashMap<String, AttributeValue> prepareWorkOutInsert(HashMap<String, AttributeValue> itemValues, WorkOut workOut) {
        String id = String.valueOf(UUID.randomUUID());
        System.out.println("uuid generated as : " + id);
        itemValues.put("id", AttributeValue.builder().s(id).build());

        itemValues.put("username", AttributeValue.builder().s(workOut.getUsername()).build());
        itemValues.put("muscleGroup", AttributeValue.builder().s(workOut.getMuscleGroup()).build());
        itemValues.put("exercise", AttributeValue.builder().s(workOut.getExercise()).build());

        itemValues.put("setNo", AttributeValue.builder().s(workOut.getSetNo().toString()).build());
        itemValues.put("repetitions", AttributeValue.builder().s(workOut.getRepetitions().toString()).build());
        itemValues.put("date", AttributeValue.builder().s(workOut.getDate()).build());

        logger.debug("workout object created with details as {}", workOut.toString());
        return itemValues;
    }

    public static Credential castToUsers(List<Map<String, AttributeValue>> items) {
        Credential credential = new Credential();
        credential.setUsername(items.get(0).get("username").s());
        credential.setPassword(items.get(0).get("password").s());
        logger.debug("credential object created with details as {}", credential.toString());
        return credential;
    }

    public static List<User> retrieveUsers(ScanResponse scanResponse) {
        List<User> users = new ArrayList<User>();
        scanResponse.items().forEach((Map<String, AttributeValue> map) -> users.add(new User(map.get("username").s(),
                map.get("password").s(), map.get("firstname").s(), map.get("lastname").s(),
                map.get("email").s(), map.get("address").s(), Integer.parseInt(map.get("phone").s()))));

        logger.debug("scanned users table with number of results as {}", scanResponse.items().size());
        return users;
    }

    public static List<WorkOut> retrieveWorkOutList(ScanResponse scanResponse, String username, String date, String pageSize, String pageNumber) {
        List<WorkOut> workOuts = new ArrayList<WorkOut>();
        Integer counter = 1;
        int max = Integer.parseInt(pageSize)*Integer.parseInt(pageNumber);
        int min = ((Integer.parseInt(pageNumber) - 1)) * Integer.parseInt(pageSize) + 1;
        scanResponse
                .items()
                .stream()
                .filter(stringAttributeValueMap -> stringAttributeValueMap.get("username").s().equalsIgnoreCase(username))
                .filter(stringAttributeValueMap -> stringAttributeValueMap.get("date").s().equalsIgnoreCase(date))
                .forEach(map -> workOuts.add(new WorkOut(map.get("username").s(), map.get("muscleGroup").s(), map.get("exercise").s(),
                        Integer.parseInt(map.get("setNo").s()), Integer.parseInt(map.get("repetitions").s()), map.get("date").s())));

        List<WorkOut> workOutList = new ArrayList<WorkOut>();
        for (int i = min; i <= Math.min(workOuts.size(), max); i++) {
            workOutList.add(workOuts.get(i-1));
        }
        logger.debug("{} records received", scanResponse.items().size());
        return workOutList;
    }

}
