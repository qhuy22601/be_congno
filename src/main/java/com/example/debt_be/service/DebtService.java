package com.example.debt_be.service;

import com.example.debt_be.entity.dto.ResponseDto;
import com.example.debt_be.entity.model.Debt;
import com.example.debt_be.entity.model.UserModel;
import com.example.debt_be.repository.DebtRepo;
import com.example.debt_be.repository.UserRepo;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.List;
import java.util.Optional;

@Service
public class DebtService {
    @Autowired
    private DebtRepo debtRepo;
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;
//    public ResponseDto save(Debt debt){
//        boolean exists = userRepo.findAll().stream().anyMatch(userModel -> userModel.getMst().equals(debt.getUser().getMst()));
//        Debt debtSave = new Debt();
//        if(exists) {
//            UserModel userModelOld = userRepo.findAll().stream().filter(userModel -> userModel.getMst().equals( debt.getUser().getMst())).toList().get(0);
//            Optional<Debt> debtOptional = debtRepo.findById(userModelOld.getDebt().getId());
//            Debt debtOld = debtOptional.orElse(null);
//            debtSave.setId(debtOld.getId());
//            debtSave.setDebt(debt.getDebt() + debtOld.getDebt());
//            debtSave.setPay(debt.getPay() + debtOld.getPay());
//            debtSave.setBalance(debtSave.getDebt() - debtSave.getPay());
//            debtSave.setUser(userModelOld);
//            debtSave.getUser().setDebt(debtSave);
//        } else {
//            debtSave.setNote(debt.getNote());
//            debtSave.setDebt(debt.getDebt());
//            debtSave.setPay(debt.getPay());
//            System.out.println(debtSave.getDebt());
//            debtSave.setBalance(debtSave.getDebt() - debtSave.getPay());
//            debtSave.setUser(debt.getUser());
//            UserModel userNew = new UserModel();
//            userNew = debtSave.getUser();
//            userNew.setDebt(debtSave);
//            userRepo.saveAndFlush(debt.getUser());
//            debt.getUser().setDebt(debtSave);
//        }
//        ResponseDto responseDto = new ResponseDto();
//        responseDto.setMess("Success");
//        responseDto.setStatus("Success");
//        responseDto.setPayload(debtRepo.saveAndFlush(debtSave));
//        return responseDto;
//
//    }


    public ResponseDto save(Debt debt){
        ResponseDto responseDto = new ResponseDto();
        UserModel existingUser = userRepo.findUserModelByMst(debt.getUser().getMst());

        if (existingUser != null) {
            debt.setUser(existingUser);
        } else {
            UserModel newUser = userService.save(debt.getUser());
            debt.setUser(newUser);
        }
        debt.setNote(debt.getNote());
        debt.setBalance(debt.getDebt()-debt.getPay());
        responseDto.setMess("Success");
        responseDto.setStatus("Success");
        responseDto.setPayload(debtRepo.save(debt));
        return responseDto;
    }

    public List<Debt> getAll(){
        return debtRepo.findAll();
    }

    public List<Debt> getByUserId(Integer id){
        return debtRepo.findByUserId(id);
    }

    public void saveToExcelAndDeleteRow(Integer id) {
        Optional<Debt> debt = debtRepo.findById(id);
        Debt data = debt.get();

        try (InputStream inputStream = new FileInputStream("upload/file.xlsx");
             Workbook workbook = WorkbookFactory.create(inputStream)) {

            Sheet sheet = workbook.getSheet("Data");

            if (sheet == null) {
                sheet = workbook.createSheet("Data");
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("MST");
                headerRow.createCell(1).setCellValue("Name");
            }

            int nextRowNum = sheet.getLastRowNum() + 1;
            Row dataRow = sheet.createRow(nextRowNum);
            dataRow.createCell(0).setCellValue(data.getUser().getMst());
            dataRow.createCell(1).setCellValue(data.getUser().getName());

            for (int i = 0; i < dataRow.getLastCellNum(); i++) {
                sheet.autoSizeColumn(i);
            }

            try (OutputStream outputStream = new FileOutputStream("upload/file.xlsx")) {
                workbook.write(outputStream);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        debtRepo.deleteById(id);
    }
}
