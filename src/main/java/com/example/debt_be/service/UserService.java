package com.example.debt_be.service;

import com.example.debt_be.entity.dto.IdDTO;
import com.example.debt_be.entity.dto.ResponseDto;
import com.example.debt_be.entity.model.UserModel;
import com.example.debt_be.repository.UserRepo;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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

    public List<UserModel> getAll(){
        return userRepo.findAll();
    }

    public ResponseDto save(UserModel userModel){
        ResponseDto responseDto = new ResponseDto();
//        Optional<UserModel> userModelOpt = userRepo.findById(userModel.getId());
//        UserModel newUser = userModelOpt.get();
//        userModel.setLogo(this.storeFile(file));
        responseDto.setMess("Success");
        responseDto.setStatus("Success");
        responseDto.setPayload(userRepo.save(userModel));
        return responseDto;
    }

    public ResponseDto getById(IdDTO id){
        ResponseDto responseDto = new ResponseDto();
        Optional<UserModel> userModelOpt = userRepo.findById(id.getId());
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

    public void importUsersFromExcel(MultipartFile file) {
        try {
            Workbook workbook = WorkbookFactory.create((file.getInputStream()));
            Sheet sheet = workbook.getSheetAt(0);

            List<UserModel> users = new ArrayList<>();

            int startRow = 4; // Hàng thứ 5

            for (int rowNum = startRow; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);

                Cell mstCell = row.getCell(0);
                if (mstCell == null || mstCell.getCellType() != CellType.STRING || mstCell.getStringCellValue().isEmpty()) {
                    // Bỏ qua nếu trường mst bị trống
                    continue;
                }
                String mst = mstCell.getStringCellValue();
                String username = row.getCell(1).getStringCellValue();

                UserModel existingUser = userRepo.findByMst(mst);
                if (existingUser != null) {
                    // Nếu người dùng đã tồn tại, bỏ qua và tiếp tục với hàng tiếp theo
                    continue;
                }

                // Tạo đối tượng User từ dữ liệu trong tệp Excel
                UserModel user = new UserModel();
                user.setName(username);
                user.setMst(mst);
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
