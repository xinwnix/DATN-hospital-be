package notehospital.entity;


import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import notehospital.enums.AccountStatus;
import notehospital.enums.AccountType;
import notehospital.enums.Gender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Account implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String phone;
    private String password;
    private String fullName;
    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus = AccountStatus.INACTIVE;
    private String address;
    private String avatarURL;
    private LocalDate dateOfBirth;
    private String code;

    @Enumerated(EnumType.STRING)
    private AccountType type;


    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private Set<Order> history;

    @OneToMany(mappedBy = "doctor")
    @JsonIgnore
    private Set<Order> doctorSchedule;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
