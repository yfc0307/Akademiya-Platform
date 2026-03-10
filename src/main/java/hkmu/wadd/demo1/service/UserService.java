package hkmu.wadd.demo1.service;

import hkmu.wadd.demo1.model.AppUser;
import hkmu.wadd.demo1.repository.AppUserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final AppUserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(AppUserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().name());
        return new User(user.getUsername(), user.getPassword(), Collections.singletonList(authority));
    }

    public AppUser findByUsername(String username) {
        return userRepo.findByUsername(username).orElse(null);
    }

    public List<AppUser> findAll() {
        return userRepo.findAll();
    }

    public AppUser findById(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    @Transactional
    public AppUser register(String username, String password, String fullName, String email, String phone, AppUser.Role role) {
        if (userRepo.existsByUsername(username)) {
            throw new RuntimeException("Username already exists");
        }
        AppUser user = new AppUser(username, passwordEncoder.encode(password), fullName, email, phone, role);
        return userRepo.save(user);
    }

    @Transactional
    public AppUser updateUser(Long id, String fullName, String email, String phone, AppUser.Role role, String password) {
        AppUser user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setRole(role);
        if (password != null && !password.isBlank()) {
            user.setPassword(passwordEncoder.encode(password));
        }
        return userRepo.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    @Transactional
    public AppUser updateProfile(String username, String fullName, String email, String phone, String password) {
        AppUser user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhone(phone);
        if (password != null && !password.isBlank()) {
            user.setPassword(passwordEncoder.encode(password));
        }
        return userRepo.save(user);
    }
}

