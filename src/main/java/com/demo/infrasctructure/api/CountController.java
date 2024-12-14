package com.demo.infrasctructure.api;

import com.demo.domain.dto.TransactionDto;
import com.demo.domain.entity.Count;
import com.demo.infrasctructure.service.CountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/counts")
public class CountController {
    @Autowired
    CountService countService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Count details(@PathVariable(name = "id") Long id) {
        return countService.findById(id);
    }


    @PostMapping("/transaction")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> transaction(@RequestBody TransactionDto transactionDto) {
        countService.transference(transactionDto.getCountOrigin(), transactionDto.getCountOrigin(), transactionDto.getCountAmount(), transactionDto.getBankAccountId());
        Map<String, Object> response = new HashMap<>();
        response.put("date", LocalDate.now().toString());
        response.put("status", "Ok");
        response.put("message", "Transaction Successful");
        response.put("transaction", transactionDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Count> getAllData() {
        return countService.findAll();
    }


    @PostMapping("/saveData")
    @ResponseStatus(HttpStatus.CREATED)
    public Count saveData(@RequestBody Count count) {
        return countService.save(count);
    }
}
