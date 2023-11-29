package notehospital.service;

import notehospital.dto.request.MedicineRequest;
import notehospital.dto.request.ServiceRequest;
import notehospital.dto.response.AccountResponseDTO;
import notehospital.entity.Account;
import notehospital.entity.Medicine;
import notehospital.repository.AccountRepository;
import notehospital.repository.MedicineRepository;
import notehospital.repository.ServiceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {
    ModelMapper modelMapper = new ModelMapper();
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    MedicineRepository medicineRepository;

    public List<AccountResponseDTO> getAllAccount(){
        List<Account> accounts = accountRepository.findAll();
        List<AccountResponseDTO> accountResponseDTOS = new ArrayList<>();
        for(Account account: accounts){
            accountResponseDTOS.add(modelMapper.map(account, AccountResponseDTO.class));
        }
        return accountResponseDTOS;
    }

    public List<notehospital.entity.Service> getAllService(){
        List<notehospital.entity.Service> services = serviceRepository.findAll();
        return services;
    }

    public notehospital.entity.Service createService(ServiceRequest serviceRequest){
        notehospital.entity.Service service = modelMapper.map(serviceRequest, notehospital.entity.Service.class);
        return serviceRepository.save(service);
    }

    public void deleteService(long serviceId){
        serviceRepository.deleteById(serviceId);
    }

    public Medicine createMedicine(MedicineRequest medicineRequest){
        Medicine medicine = modelMapper.map(medicineRequest, Medicine.class);
        return medicineRepository.save(medicine);
    }

    public void deleteMedicine(long medicineId){
        medicineRepository.deleteById(medicineId);
    }
}
