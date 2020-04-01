package com.javed.lambda.controller;

import com.javed.lambda.model.WorkOut;
import com.javed.lambda.model.WorkOutList;
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
        return new ResponseEntity<WorkOut>(workOut,HttpStatus.CREATED);
    }

    @GetMapping(value = "/users/{username}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkOutList> getWorkOutDetail(@QueryParam(value = "pagesize") String pagesize,
                                                        @QueryParam(value = "pagenumber") String pagenumber,
                                                        @QueryParam(value = "date") String date){
        List<WorkOut> workOuts = new ArrayList<WorkOut>();
        workOuts.add(new WorkOut("javed547", "Chest", "Parallel Chest Press", 3, 15, "22-12-2019"));
        workOuts.add(new WorkOut("javed547", "Chest", "Parallel Chest Press", 4, 15, "22-12-2019")) ;
        WorkOutList workOutList = new WorkOutList();
        workOutList.setWorkOuts(workOuts);

        return new ResponseEntity<WorkOutList>(workOutList, HttpStatus.OK);
    }

}
