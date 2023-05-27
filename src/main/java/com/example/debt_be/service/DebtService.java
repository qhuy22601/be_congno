package com.example.debt_be.service;

import com.example.debt_be.entity.dto.ResponseDto;
import com.example.debt_be.entity.model.Debt;
import com.example.debt_be.entity.model.UserModel;
import com.example.debt_be.repository.DebtRepo;
import com.example.debt_be.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DebtService {
    @Autowired
    private DebtRepo debtRepo;

    @Autowired
    private UserService userService;
    public ResponseDto save(Debt debt){
        ResponseDto responseDto = new ResponseDto();
        UserModel user = userService.save(debt.getUser());
        debt.setBalance(debt.getDebt()-debt.getPay());
        debt.setUser(user);
        responseDto.setMess("Success");
        responseDto.setStatus("Success");
        responseDto.setPayload(debtRepo.save(debt));
        return responseDto;
    }

    public List<Debt> getAll(){
        return debtRepo.findAll();
    }
}
