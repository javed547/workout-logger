package com.javed.lambda.controller;

import com.javed.lambda.model.WorkOut;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
import java.util.ArrayList;
import java.util.List;

@RestController
public class WorkOutLogController {

    @PutMapping(value = "/users/{username}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkOut> logWorkOut(@PathVariable String username, @RequestBody WorkOut workOut){
        return new ResponseEntity<WorkOut>(new WorkOut(),HttpStatus.CREATED);
    }

    @GetMapping(value = "/users/{username}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WorkOut>> getWorkOutDetail(@QueryParam(value = "pagesize") String pagesize,
                                                          @QueryParam(value = "pagenumber") String pagenumber,
                                                          @QueryParam(value = "date") String date){
        List<WorkOut> workOutList = new ArrayList<WorkOut>();
        return new ResponseEntity<List<WorkOut>>(workOutList, HttpStatus.OK);
    }

}
