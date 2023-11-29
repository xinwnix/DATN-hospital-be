package notehospital.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import javax.validation.Valid;

import notehospital.dto.request.*;
import notehospital.dto.response.AccountResponseDTO;
import notehospital.dto.response.ResponseDTO;
import notehospital.entity.Account;
import notehospital.entity.Medicine;
import notehospital.enums.AccountStatus;
import notehospital.exception.exception.BadRequest;
import notehospital.service.AccountService;
import notehospital.service.MedicineService;
import notehospital.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@SecurityRequirement(name = "api")
@CrossOrigin(origins = {"https://hospital-be.vercel.app/", "http://localhost:3000/"})
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    ResponseHandler responseHandler;

    @Autowired
    MedicineService medicineService;

    @GetMapping("/account/{userId}")
    public ResponseEntity getUserProfile(@PathVariable long userId){
        AccountResponseDTO accountResponseDTO = accountService.getAccountById(userId);
        return responseHandler.response(201,"Successfully get account!",accountResponseDTO);
    }

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody AccountRequestDTO accountRequestDTO){
        AccountResponseDTO accountResponseDTO = accountService.register(accountRequestDTO);
        return responseHandler.response(201,"Successfully registered new account!",accountResponseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody LoginRequestDTO loginRequestDTO){
        AccountResponseDTO accountResponseDTO = accountService.login(loginRequestDTO);
        if(accountResponseDTO.getAccountStatus() == AccountStatus.INACTIVE){
            throw new BadRequest("Tài khoản chưa được kích hoạt!");
        }
        return responseHandler.response(200,"Successfully login!",accountResponseDTO);
    }

    @PutMapping("/profile")
    public ResponseEntity updateProfile(@Valid @RequestBody AccountRequestDTO accountRequestDTO){
        AccountResponseDTO accountResponseDTO = accountService.updateProfile(accountRequestDTO);
        return responseHandler.response(200,"Successfully update account!",accountResponseDTO);
    }

    @PutMapping("/password/{userId}")
    public ResponseEntity updatePassword(@RequestBody UpdatePassword updatePassword, @PathVariable long userId){
        System.out.println(updatePassword);
        System.out.println(userId);
        AccountResponseDTO accountResponseDTO = accountService.updatePassword(userId, updatePassword);
        return responseHandler.response(200,"Successfully update password!",accountResponseDTO);
    }

    @PostMapping("/active/{userId}")
    public ResponseEntity activeAccount(@RequestBody ActiveAccount account, @PathVariable long userId){
        AccountResponseDTO accountResponseDTO = accountService.activeAccount(userId , account);
        return responseHandler.response(200,"Successfully active account!",accountResponseDTO);
    }

    @GetMapping("/doctor")
    public ResponseEntity getDoctor(){
        List<AccountResponseDTO> doctors = accountService.getDoctor();
        return responseHandler.response(200,"Successfully get doctor account!",doctors);
    }

    @GetMapping("/info/{accountPhone}")
    public ResponseEntity getAccountByPhone(@PathVariable String accountPhone){
        Account account = accountService.getAccountByPhone(accountPhone);
        return responseHandler.response(200,"Successfully get account!", account);
    }

    @PostMapping("reset-password/{accountId}")
    public ResponseEntity resetPassword(@PathVariable long accountId, @RequestBody ResetPassword resetPassword){
        Account account = accountService.resetPassword(resetPassword, accountId);
        return responseHandler.response(200,"Successfully reset password!", account);
    }

    @GetMapping("/check-user/{phone}")
    public ResponseEntity checkPhoneExist(@PathVariable String phone){
        System.out.println(phone);
        Account account = accountService.getAccountByPhone(phone);
        if (account==null){
            System.out.println("đã vào đây");
            return ResponseEntity.ok(new ResponseDTO(200, "available"));
        }
        return responseHandler.response(200,"Số điện thoại đã tồn tại",account);
    }
}
