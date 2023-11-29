package notehospital.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import notehospital.dto.request.MedicineRequest;
import notehospital.dto.request.ServiceRequest;
import notehospital.dto.response.AccountResponseDTO;
import notehospital.entity.Medicine;
import notehospital.entity.Service;
import notehospital.service.AccountService;
import notehospital.service.AdminService;
import notehospital.service.MedicineService;
import notehospital.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "api")
@CrossOrigin(origins = "https://hospital-be.vercel.app/")
public class AdminController {
    @Autowired
    AdminService adminService;

    @Autowired
    ResponseHandler responseHandler;

    @Autowired
    MedicineService medicineService;

    @GetMapping("/accounts")
    public ResponseEntity getAllAccount(){
        List<AccountResponseDTO> accountResponseDTOList = adminService.getAllAccount();
        return responseHandler.response(200,"Successfully get all account!", accountResponseDTOList);
    }

    @GetMapping("/service")
    public ResponseEntity getAllService(){
        List<Service> accountResponseDTOList = adminService.getAllService();
        return responseHandler.response(200,"Successfully get all service!", accountResponseDTOList);
    }

    @PostMapping("/service")
    public ResponseEntity createService(@RequestBody ServiceRequest serviceRequest){
        Service service = adminService.createService(serviceRequest);
        return responseHandler.response(201,"Successfully!", service);
    }

    @DeleteMapping("/service/{serviceId}")
    public ResponseEntity deleteService(@PathVariable long serviceId){
        adminService.deleteService(serviceId);
        return responseHandler.response(201,"Successfully delete service!", null);
    }

    @GetMapping("/medicine")
    public ResponseEntity getMedicine(){
        List<Medicine> doctors = medicineService.getAllMedicine();
        return responseHandler.response(200,"Successfully get doctor account!",doctors);
    }

    @PostMapping("/medicine")
    public ResponseEntity createMedicine(@RequestBody MedicineRequest medicineRequest){
        Medicine medicine = adminService.createMedicine(medicineRequest);
        return responseHandler.response(201,"Successfully!", medicine);
    }

    @DeleteMapping("/medicine/{serviceId}")
    public ResponseEntity deleteMedicine(@PathVariable long serviceId){
        adminService.deleteMedicine(serviceId);
        return responseHandler.response(201,"Successfully delete medicine!", null);
    }

}
