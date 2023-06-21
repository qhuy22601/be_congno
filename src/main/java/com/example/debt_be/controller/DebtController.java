package com.example.debt_be.controller;

import com.example.debt_be.entity.dto.ResponseDto;
import com.example.debt_be.entity.model.Debt;
import com.example.debt_be.entity.model.UserModel;
import com.example.debt_be.repository.DebtRepo;
import com.example.debt_be.repository.UserRepo;
import com.example.debt_be.service.DebtService;
import com.example.debt_be.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/debt")
public class DebtController {

    @Autowired
    private DebtService debtService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private DebtRepo debtRepo;

    @PostMapping("/save")
    public ResponseEntity<ResponseDto> save(@RequestBody Debt debt){
        return new ResponseEntity<>(debtService.save(debt), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Debt>> getAll(){
        return new ResponseEntity<List<Debt>>(debtService.getAll(),HttpStatus.OK);
    }

    @PostMapping("/save-to-excel/{id}")
    public ResponseEntity<String> saveToExcelAndDeleteRow(@PathVariable("id") Integer id) {
        debtService.saveToExcelAndDeleteRow(id);
        return ResponseEntity.ok("Data saved to Excel and row deleted successfully.");
    }
    @GetMapping("/find")
    public ResponseEntity<List<Debt>> findDebtsByMst(@RequestParam("mst") String mst) {
        try {
            List<UserModel> users = userRepo.findByMst(mst);
            List<Debt> debts = new ArrayList<>();

            for (UserModel user : users) {
                List<Debt> userDebts = user.getDebt();
                if (userDebts != null) {
                    debts.addAll(userDebts);
                }
            }

            return ResponseEntity.ok(debts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<List<Debt>> getByUserId(@PathVariable("id") Integer id){
        return new ResponseEntity<>(debtService.getByUserId(id),HttpStatus.OK);
    }

    @DeleteMapping("/del/{debtId}")
    public ResponseEntity<String> del(@PathVariable("debtId") Integer debtId){
        debtRepo.deleteById(debtId);
        return new ResponseEntity<>("OKKK", HttpStatus.OK);
    }

}
