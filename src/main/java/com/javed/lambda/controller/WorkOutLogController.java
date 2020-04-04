package com.javed.lambda.controller;

import com.javed.lambda.model.WorkOut;
import com.javed.lambda.model.WorkOutList;
import com.javed.lambda.service.WorkOutLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
import java.util.ArrayList;
import java.util.List;

@RestController
public class WorkOutLogController {

    @Autowired
    private WorkOutLogService workOutLogService;

    /**
     * Log work out response entity.
     *
     * @param username the username
     * @param workOut  the work out
     * @return the response entity
     */
    @PutMapping(value = "/users/{username}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkOut> logWorkOut(@PathVariable String username, @RequestBody WorkOut workOut){
        return new ResponseEntity<WorkOut>(workOutLogService.logWorkOut(workOut),HttpStatus.CREATED);
    }

    /**
     * Gets work out detail.
     *
     * @param pagesize   the pagesize
     * @param pagenumber the pagenumber
     * @param date       the date
     * @return the work out detail
     */
    @GetMapping(value = "/users/{username}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkOutList> getWorkOutDetail(@QueryParam(value = "pagesize") String pagesize,
                                                        @QueryParam(value = "pagenumber") String pagenumber,
                                                        @QueryParam(value = "date") String date,
                                                        @PathVariable String username){
        WorkOutList workOutList = workOutLogService.getWorkOutDetail(username, pagesize, pagenumber, date);
        return new ResponseEntity<WorkOutList>(workOutList, HttpStatus.OK);
    }

}
