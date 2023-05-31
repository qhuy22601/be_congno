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
