package com.abhinav.stockexchangeapplication.controller;

import com.abhinav.stockexchangeapplication.exceptions.NotFoundException;
import com.abhinav.stockexchangeapplication.io.request.IPORequest;
import com.abhinav.stockexchangeapplication.io.response.IPOResponse;
import com.abhinav.stockexchangeapplication.model.IPODetails;
import com.abhinav.stockexchangeapplication.service.impl.IPOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/IPOs")
@CrossOrigin(origins = "*")
public class IPOController {

    @Autowired
    IPOService ipoService;

    @GetMapping(
            path = "/upcoming"
    )
    public ResponseEntity<List<IPOResponse>> getUpcomingIPOs() {
        return new ResponseEntity<>(ipoService.getIPOsByChronology(LocalDateTime.now()), HttpStatus.OK);
    }

    @PutMapping(
            path = "/{id}"
    )
    public ResponseEntity<IPODetails> updateIPO(@PathVariable Long id, @RequestBody IPORequest ipoDetails) throws NotFoundException {
        return new ResponseEntity<>(ipoService.editIPO(id, ipoDetails), HttpStatus.OK);
    }
}
