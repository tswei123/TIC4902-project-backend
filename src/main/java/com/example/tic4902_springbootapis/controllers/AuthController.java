package com.example.tic4902_springbootapis.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.example.tic4902_springbootapis.security.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.tic4902_springbootapis.models.ERole;
import com.example.tic4902_springbootapis.models.Role;
import com.example.tic4902_springbootapis.models.User;
import com.example.tic4902_springbootapis.payload.request.LoginRequest;
import com.example.tic4902_springbootapis.payload.request.SignupRequest;
import com.example.tic4902_springbootapis.payload.response.JwtResponse;
import com.example.tic4902_springbootapis.payload.response.MessageResponse;
import com.example.tic4902_springbootapis.repository.RoleRepository;
import com.example.tic4902_springbootapis.repository.UserRepository;
import com.example.tic4902_springbootapis.security.jwt.JwtUtils;
import com.example.tic4902_springbootapis.security.services.UserDetailsImpl;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    EmailService emailService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getAddress(),
                userDetails.getContact(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
//        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: Username is already taken!"));
//        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getAddress(),
                signUpRequest.getContact());

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;

                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
    @PostMapping("/forgotPassword")
    public String add(@RequestParam String email){
        emailService.sendEmail(email,
                "reset password",
                "your password reset to 12345");
        return "New password sent to your registered email";
    }
    @GetMapping("/getAllUsers")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/getIndividualUser")
    public List<User> getIndividualUser(@RequestParam String email){
        return userRepository.findIndividualUser(email);
    }

    @PostMapping("/updateParticular")
    public String update(@RequestParam String email, @RequestParam String username, @RequestParam String address, @RequestParam Integer contact){
        List<User> user = userRepository.findIndividualUser(email);
        user.get(0).setUsername(username);
        user.get(0).setAddress(address);
        user.get(0).setContact(contact);

        userRepository.save(user.get(0));
        return "Update successfully";
    }

    @PostMapping("/resetPassword")
    public String resetPwd(@RequestParam String email, @RequestParam String oldpwd, @RequestParam String newpwd1,@RequestParam String newpwd2){
        List<User> user = userRepository.findIndividualUser(email);
        boolean isPasswordMatch = encoder.matches(oldpwd, user.get(0).getPassword());
        if(isPasswordMatch){
            if(newpwd1.equals((newpwd2))){
                user.get(0).setPassword(encoder.encode(newpwd1));
                userRepository.save(user.get(0));
            }
            else{
                return "New password not match";
            }
        }else{
            return "Your existing password not correct";
        }

        return "Password reset successful";
    }
}

