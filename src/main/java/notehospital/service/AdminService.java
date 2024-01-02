package notehospital.service;

import notehospital.dto.request.FacilityRequest;
import notehospital.dto.request.MedicineRequest;
import notehospital.dto.request.ServiceRequest;
import notehospital.dto.response.AccountResponseDTO;
import notehospital.dto.response.FacilityResponse;
import notehospital.dto.response.ServiceDTO;
import notehospital.dto.response.ServiceResponse;
import notehospital.entity.Account;
import notehospital.entity.Facility;
import notehospital.entity.Medicine;
import notehospital.enums.AccountType;
import notehospital.repository.AccountRepository;
import notehospital.repository.FacilityRepository;
import notehospital.repository.MedicineRepository;
import notehospital.repository.ServiceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    ModelMapper modelMapper = new ModelMapper();
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ServiceRepository   serviceRepository;

    @Autowired
    FacilityRepository facilityRepository;

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


    public List<AccountResponseDTO> getAccountPatient(){
        List<Account> accounts = accountRepository.findPatients();
        List<AccountResponseDTO> accountResponseDTOS = new ArrayList<>();
        for(Account account: accounts){
            accountResponseDTOS.add(modelMapper.map(account, AccountResponseDTO.class));
        }
        return accountResponseDTOS;
    }

    public List<AccountResponseDTO> getAccountDoctor(){
        List<Account> accounts = accountRepository.findDoctors();
        List<AccountResponseDTO> accountResponseDTOS = new ArrayList<>();

        for(Account account: accounts){
            AccountResponseDTO accountResponseDTO = new AccountResponseDTO();
            BeanUtils.copyProperties(account, accountResponseDTO);
            // Tạo một ServiceDTO từ Service
            notehospital.entity.Service service = account.getServiceac();
            ServiceDTO serviceDTO = new ServiceDTO();
            if(service==null){
                accountResponseDTO.setService(null);
            }
            if(service!=null){
                serviceDTO.setId(service.getId());
                serviceDTO.setImage(service.getImage());
                serviceDTO.setName(service.getName());
                serviceDTO.setPrice(service.getPrice());
                serviceDTO.setDescription(service.getDescription());
                accountResponseDTO.setService(serviceDTO); // Gán ServiceDTO vào AccountResponseDTO

                Facility facility = account.getServiceac().getFacilitysv(); // Đây là ví dụ, bạn cần sử dụng phương thức thích hợp để lấy dữ liệu Facility từ Service
                FacilityResponse facilityResponse = new FacilityResponse();
                facilityResponse.setFacility_name(facility.getFacility_name());
                serviceDTO.setFacility(facilityResponse);
            }
            accountResponseDTOS.add(accountResponseDTO);
        }

        return accountResponseDTOS;
    }

    public List<notehospital.entity.Service> getAllService(){
        List<notehospital.entity.Service> services = serviceRepository.findAllServicesWithFacility();
        return services;
    }

    public List<notehospital.entity.Service> getAllServiceWithFacility(){
        List<notehospital.entity.Service> services = serviceRepository.findAllServicesWithFacility();
        return services;
    }

    public notehospital.entity.Service createService(ServiceRequest serviceRequest){
        notehospital.entity.Service service = modelMapper.map(serviceRequest, notehospital.entity.Service.class);
        Optional<Facility>  facility = facilityRepository.findById(serviceRequest.getFacilityac_id());
        service.setFacilitysv(facility.get());
        return serviceRepository.save(service);
    }

    public void deleteService(long serviceId){
        serviceRepository.deleteById(serviceId);
    }

    public Facility createFacility(FacilityRequest facilityRequest){
        Facility facility = modelMapper.map(facilityRequest, Facility.class);
        return facilityRepository.save(facility);
    }

    public Medicine createMedicine(MedicineRequest medicineRequest){
        Medicine medicine = modelMapper.map(medicineRequest, Medicine.class);
        return medicineRepository.save(medicine);
    }

    public void deleteMedicine(long medicineId){
        medicineRepository.deleteById(medicineId);
    }


}
