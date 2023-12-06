package notehospital.service;

import notehospital.dto.EmailDetail;
import notehospital.dto.request.*;
import notehospital.dto.response.AccountResponseDTO;
import notehospital.dto.response.ServiceResponse;
import notehospital.entity.Account;
import notehospital.entity.Facility;
import notehospital.enums.AccountStatus;
import notehospital.enums.AccountType;
import notehospital.enums.Gender;
import notehospital.exception.exception.BadRequest;
import notehospital.exception.exception.EntityNotFound;
import notehospital.repository.AccountRepository;
import notehospital.repository.FacilityRepository;
import notehospital.repository.ServiceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccountService implements UserDetailsService {

    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    EmailService emailService;

    @Autowired
    FacilityRepository facilityRepository;

    @Autowired
    ServiceRepository serviceRepository;

    public AccountResponseDTO register(AccountRequestDTO accountRequestDTO) {
        Account account = modelMapper.map(accountRequestDTO, Account.class);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setCode(UUID.randomUUID().toString().replace("-", ""));
        Account newAccount = accountRepository.save(account);
        EmailDetail emailDetail = new EmailDetail();
        emailDetail.setPassword(accountRequestDTO.getPassword());
        emailDetail.setName(account.getFullName());
        emailDetail.setRecipient(account.getEmail());
        emailDetail.setSubject("Xác nhận tài khoản");
        emailDetail.setMsgBody("aaa");
        emailDetail.setCode(account.getId() + "/" + account.getCode());
        emailService.sendMailTemplate(emailDetail);
        return modelMapper.map(newAccount, AccountResponseDTO.class);
    }

    public AccountResponseDTO login(LoginRequestDTO loginRequestDTO) {
        Authentication authentication = null;

        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequestDTO.getPhone(),
                    loginRequestDTO.getPassword()
            ));
            return modelMapper.map(authentication.getPrincipal(), AccountResponseDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new EntityNotFound("Username or password invalid!");
        }
    }

    public AccountResponseDTO updateProfile(AccountRequestDTO accountRequestDTO) {
        Account oldAccount = accountRepository.findByUsernameOrPhoneOrEmail(accountRequestDTO.getPhone());
        if (oldAccount == null) {
            throw new EntityNotFound("Account not found!");
        }
        oldAccount.setPhone(accountRequestDTO.getPhone());
        oldAccount.setFullName(accountRequestDTO.getFullName());
        oldAccount.setEmail(accountRequestDTO.getEmail());
        oldAccount.setGender(accountRequestDTO.getGender());
        oldAccount.setAddress(accountRequestDTO.getAddress());
        oldAccount.setDateOfBirth(accountRequestDTO.getDateOfBirth());
        Account newAccount = accountRepository.save(oldAccount);
        return modelMapper.map(newAccount, AccountResponseDTO.class);
    }

    public AccountResponseDTO getAccountById(long id) {
        Account account = accountRepository.findAccountById(id);
        return modelMapper.map(account, AccountResponseDTO.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findByUsernameOrPhoneOrEmail(username);
    }

    public AccountResponseDTO updatePassword(long userId, UpdatePassword updatePassword) {
        Account account = accountRepository.findAccountById(userId);
        account.setPassword(passwordEncoder.encode(updatePassword.getNewPassword()));
        return modelMapper.map(accountRepository.save(account), AccountResponseDTO.class);
    }

    public AccountResponseDTO activeAccount(long userId, ActiveAccount activeAccount) {
        Account account = accountRepository.findAccountById(userId);
        System.out.println(account.getCode());
        System.out.println(activeAccount.getCode());
        if (account.getCode().equals(activeAccount.getCode())) {
            account.setAccountStatus(AccountStatus.ACTIVE);
        } else {
            throw new BadRequest("Code is incorrect!");
        }

        return modelMapper.map(accountRepository.save(account), AccountResponseDTO.class);
    }

    public List<Facility> getFacility() {
        List<Facility> facilities = facilityRepository.findAll();
        return facilities;
    }

    //    public List<AccountResponseDTO> getDoctor(){
//        List<Account> accounts = accountRepository.findAccountsByType(AccountType.DOCTOR);
//        List<AccountResponseDTO> accountResponseDTOS = new ArrayList<>();
//        for(Account account: accounts){
//            accountResponseDTOS.add(modelMapper.map(account, AccountResponseDTO.class));
//        }
//        return accountResponseDTOS;
//    }

    public Map<String, Object> getServicesByFacilityId(Long facilityId) {
        Facility facility = facilityRepository.findById(facilityId).orElse(null);
        if (facility == null) {
        }

        // Truy vấn thông tin về dịch vụ từ cơ sở y tế dựa trên facilityId
        List<notehospital.entity.Service> services = serviceRepository.findServicesByFacilityId(facilityId);
        List<ServiceResponse> serviceDTOs = services.stream()
                .map(service -> modelMapper.map(service, ServiceResponse.class))
                .collect(Collectors.toList());

        Map<String, Object> facilityAndServices = new HashMap<>();
        facilityAndServices.put("services", serviceDTOs);
        return facilityAndServices;
    }

    public List<AccountResponseDTO> getDoctorsByServiceId(Long serviceId) {
        List<Account> doctors = accountRepository.findDoctorsByServiceId(AccountType.DOCTOR, serviceId);
        return doctors.stream()
                .map(doctor -> modelMapper.map(doctor, AccountResponseDTO.class))
                .collect(Collectors.toList());
    }


    public Account getAccountByPhone(String phone) {
        Account account = accountRepository.findByUsernameOrPhoneOrEmail(phone);
        if (account == null) {
            throw new EntityNotFound("Account not found");
        }
        return account;
    }

    public Account resetPassword(ResetPassword resetPassword, long accountId) {
        Account account = accountRepository.findAccountById(accountId);
        account.setPassword(passwordEncoder.encode(resetPassword.getNewPassword()));
        return accountRepository.save(account);
    }
}
