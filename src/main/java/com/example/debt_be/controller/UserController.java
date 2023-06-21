package com.example.debt_be.controller;

import com.example.debt_be.entity.dto.IdDTO;
import com.example.debt_be.entity.dto.ResponseDto;
import com.example.debt_be.entity.dto.UserInfo;
import com.example.debt_be.entity.model.Debt;
import com.example.debt_be.entity.model.UserModel;
import com.example.debt_be.repository.UserRepo;
import com.example.debt_be.service.UserService;
import jakarta.annotation.Nullable;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/get")
    public ResponseEntity<List<UserModel>> getAll(){
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<UserModel> save(@RequestBody UserModel userModel){
        Debt debt = (Debt) userModel.getDebt();
        debt.setBalance(debt.getDebt()-debt.getPay());
        debt.setUser(userModel);
        return new ResponseEntity<>(userService.save(userModel), HttpStatus.OK);
    }

    @GetMapping("/{id}")
        public ResponseEntity<ResponseDto> getById(@PathVariable("id") Integer id){
            return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
        }

    @PostMapping("/import")
    public ResponseEntity<String> importUsers(@RequestParam("file") MultipartFile file) {
        try {
            userService.importUsersFromExcel(file);
            return ResponseEntity.ok("Import thành công");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Import thất bại: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseDto> search(@RequestParam @Nullable String mst, @RequestParam @Nullable  String name){
        return new ResponseEntity<>(userService.findMstName(mst,name),HttpStatus.OK);
    }
//    @GetMapping("/find")
//    public ResponseEntity<UserModel> getUserByMst(@RequestParam("mst") String mst) {
//        UserModel user = userRepo.findByMst(mst);
//        if (user != null) {
//            return ResponseEntity.ok(user);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @PutMapping("/change")
    public ResponseEntity<UserModel> changeInfo(@RequestBody UserInfo userModel){
        return new ResponseEntity<>(userService.changeInfo(userModel),HttpStatus.OK);
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<String> del(@PathVariable Integer id){
        userRepo.deleteById(id);
        return new ResponseEntity<>("Del successfully", HttpStatus.OK);
    }

}
