package com.example.debt_be.service;

import com.example.debt_be.entity.dto.IdDTO;
import com.example.debt_be.entity.dto.ResponseDto;
import com.example.debt_be.entity.dto.UserInfo;
import com.example.debt_be.entity.model.Debt;
import com.example.debt_be.entity.model.UserModel;
import com.example.debt_be.repository.DebtRepo;
import com.example.debt_be.repository.UserRepo;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private DebtRepo debtRepo;

    public List<UserModel> getAll(){
        return userRepo.findAll();
    }

    public void saveDebt(Debt debt) {
        debtRepo.save(debt);
    }

    public UserModel save(UserModel userModel){
//        ResponseDto responseDto = new ResponseDto();
//        Optional<UserModel> userModelOpt = userRepo.findById(userModel.getId());
//        UserModel newUser = userModelOpt.get();
//        userModel.setLogo(this.storeFile(file));
//        responseDto.setMess("Success");
//        responseDto.setStatus("Success");
//        responseDto.setPayload(userRepo.save(userModel));
//        return responseDto;
        return userRepo.save(userModel);
    }

    public UserModel changeInfo(UserInfo userModel){
        Optional<UserModel> userModelOpt = userRepo.findById(userModel.getId());
        if(userModelOpt.isEmpty()){
            return null;
        }else{
            UserModel newUser = userModelOpt.get();
            newUser.setMst(userModel.getMst());
            newUser.setName(userModel.getName());
            newUser.setAddress(userModel.getAddress());
            newUser.setCity(userModel.getCity());
            newUser.setDistrict(userModel.getDistrict());
            newUser.setWard(userModel.getWard());
            newUser.setFoundatingDate(userModel.getFoundatingDate());
            newUser.setContactTitle(userModel.getContactTitle());
            newUser.setContactEmail(userModel.getContactEmail());
            newUser.setContactNumber(userModel.getContactNumber());
            newUser.setContactName(userModel.getContactName());
            return userRepo.save(newUser);
        }
    }

    public ResponseDto getById(Integer id){
        ResponseDto responseDto = new ResponseDto();
        Optional<UserModel> userModelOpt = userRepo.findById(id);
        if(userModelOpt.isPresent()){
            responseDto.setMess("Success");
            responseDto.setStatus("Success");
            responseDto.setPayload(userModelOpt.get());
        }else{
            responseDto.setMess("Fail");
            responseDto.setStatus("Fail");
            responseDto.setPayload(null);
        }
        return responseDto;
    }

//    @Transactional
//    public void importUsersFromExcel(MultipartFile file) {
//        try {
//            Workbook workbook = WorkbookFactory.create(file.getInputStream());
//            Sheet sheet = workbook.getSheetAt(0);
//
//            List<UserModel> users = new ArrayList<>();
//
//            int startRow = 4; // Hàng thứ 5
//
//            for (int rowNum = startRow; rowNum <= sheet.getLastRowNum(); rowNum++) {
//                Row row = sheet.getRow(rowNum);
//
//                Cell mstCell = row.getCell(0);
//                if (mstCell == null || mstCell.getCellType() != CellType.STRING || mstCell.getStringCellValue().isEmpty()) {
//                    // Bỏ qua nếu trường mst bị trống
//                    continue;
//                }
//                String mst = mstCell.getStringCellValue();
//
//                Cell usernameCell = row.getCell(1);
//                if (usernameCell == null || usernameCell.getCellType() != CellType.STRING || usernameCell.getStringCellValue().isEmpty()) {
//                    // Bỏ qua nếu trường username bị trống
//                    continue;
//                }
//                String username = usernameCell.getStringCellValue();
//
//                List<UserModel> existingUser = userRepo.findByMst(mst);
//                if (!existingUser.isEmpty()) {
//                    // Nếu người dùng đã tồn tại, bỏ qua và tiếp tục với hàng tiếp theo
//                    continue;
//                }
//
//                // Tạo đối tượng User từ dữ liệu trong tệp Excel
//                UserModel user = new UserModel();
//                user.setName(username);
//                user.setMst(mst);
//                userRepo.save(user);
//            }
//        } catch (IOException | EncryptedDocumentException e) {
//            // Xử lý exception khi đọc và xử lý tệp Excel
//            e.printStackTrace();
//        }
//    }

    @Transactional
    public void importUsersFromExcel(MultipartFile file) {
        try {
            Workbook workbook = WorkbookFactory.create(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);

            int startRow = 4; // Hàng thứ 5

            for (int rowNum = startRow; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);

                Cell mstCell = row.getCell(0);
                if (mstCell == null || mstCell.getCellType() != CellType.STRING || mstCell.getStringCellValue().isEmpty()) {
                    // Bỏ qua nếu trường mst bị trống
                    continue;
                }
                String mst = mstCell.getStringCellValue();

                Cell usernameCell = row.getCell(1);
                if (usernameCell == null || usernameCell.getCellType() != CellType.STRING || usernameCell.getStringCellValue().isEmpty()) {
                    // Bỏ qua nếu trường username bị trống
                    continue;
                }
                String username = usernameCell.getStringCellValue();

                List<UserModel> existingUser = userRepo.findByMst(mst);
                if (!existingUser.isEmpty()) {
                    // Nếu người dùng đã tồn tại, bỏ qua và tiếp tục với hàng tiếp theo
                    continue;
                }

                // Tạo đối tượng User từ dữ liệu trong tệp Excel
                UserModel user = new UserModel();
                user.setName(username);
                user.setMst(mst);

                userRepo.save(user);

                List<Debt> debts = new ArrayList<>();

//                for (int cellNum = 2; cellNum < row.getLastCellNum(); cellNum++) {
//                    Cell debtCell = row.getCell(cellNum);
//                    if (debtCell == null || debtCell.getCellType() != CellType.NUMERIC) {
//                        // Bỏ qua nếu trường debt không phải kiểu số
//                        continue;
//                    }
//
//                    double debtValue = debtCell.getNumericCellValue();
//
//                    Debt debt = new Debt();
//                    debt.setUser(user);
//                    debt.setDebt(debtValue);
//
//                    debts.add(debt);
//                }
                    Debt debt = new Debt();
                    debt.setUser(user);
                    debt.setDebt(0);

                    debts.add(debt);
                user.setDebt(debts);

                userRepo.save(user);
            }
        } catch (IOException | EncryptedDocumentException e) {
            // Xử lý exception khi đọc và xử lý tệp Excel
            e.printStackTrace();
        }
    }

    public ResponseDto findMstName(String mst, String name){
        ResponseDto responseDto = new ResponseDto();
        Optional<List<UserModel>> listOpt = userRepo.findByMstContainingOrNameContaining(mst,name);
        if(listOpt.isEmpty()){
            responseDto.setStatus("Fail");
            responseDto.setMess("Fail");
            responseDto.setPayload(null);
        }else{
            responseDto.setMess("Success");
            responseDto.setStatus("Success");
            responseDto.setPayload(listOpt);
        }
        return responseDto;
    }

    @Value("${upload.folder}")
    private String uploadFolder;

    public String storeFile(MultipartFile file) {
        try {
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadFolder, fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException e) {
            throw new FileStorageException("Failed to store file", e);
        }
    }

}
