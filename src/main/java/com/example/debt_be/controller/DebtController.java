package com.example.debt_be.controller;

import com.example.debt_be.entity.dto.ResponseDto;
import com.example.debt_be.entity.model.Debt;
import com.example.debt_be.repository.DebtRepo;
import com.example.debt_be.service.DebtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/debt")
public class DebtController {

    @Autowired
    private DebtService debtService;

    @PostMapping("/save")
    public ResponseEntity<ResponseDto> save(@RequestBody Debt debt){
        return new ResponseEntity<>(debtService.save(debt), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Debt>> getAll(){
        return new ResponseEntity<List<Debt>>(debtService.getAll(),HttpStatus.OK);
    }
}
