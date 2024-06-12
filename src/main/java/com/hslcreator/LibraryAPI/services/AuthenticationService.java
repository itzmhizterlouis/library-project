package com.hslcreator.LibraryAPI.services;

//
//import com.hslcreator.LibraryAPI.models.entities.User;
//import com.hslcreator.LibraryAPI.models.requests.LoginRequest;
//import com.hslcreator.LibraryAPI.models.requests.SignupRequest;
//import com.hslcreator.LibraryAPI.models.responses.LoginResponse;
//import com.hslcreator.LibraryAPI.models.responses.UserResponse;
//import com.hslcreator.LibraryAPI.repositories.UserRepository;
//import com.hslcreator.LibraryAPI.security.JwtTokenProvider;
//import lombok.AllArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import lombok.SneakyThrows;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import static com.hslcreator.LibraryAPI.utils.UserUtil.getLoggedInUser;
//
//@RequiredArgsConstructor
//@Service
//public class AuthenticationService {
//
//    private final PasswordEncoder passwordEncoder;
//    private final UserRepository userRepository;
//    private final AuthenticationManager authenticationManager;
//    private final JwtTokenProvider jwtTokenProvider;
//
//    @Value("${app.jwt.expiryTimeInMs}") private long expiryTimeInMs;
//
//    @Transactional
//    @SneakyThrows
//    public UserResponse signup(SignupRequest request) {
//
//        String passwordHash = passwordEncoder.encode(request.getPassword());
//
//        return userRepository.save(toUser(request, passwordHash)).toDto();
//
//    }
//
//    @SneakyThrows
//    public LoginResponse login(LoginRequest request) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(request.getMatricNumber(), request.getPassword()));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        User user = getLoggedInUser().orElseThrow();
//
//        String accessToken = jwtTokenProvider.generateTokenValidForPeriod(Long.toString(user.getUserId()), expiryTimeInMs);
//
//        return LoginResponse.builder().token(accessToken).build();
//    }
//
//    private User toUser(SignupRequest request, String passwordHash) {
//
//        return User.builder()
//                .role(request.getRole())
//                .matricNumber(request.getMatricNumber())
//                .password(passwordHash)
//                .build();
//    }
//}


import com.hslcreator.LibraryAPI.models.entities.User;
import com.hslcreator.LibraryAPI.models.requests.LoginRequest;
import com.hslcreator.LibraryAPI.models.requests.SignupRequest;
import com.hslcreator.LibraryAPI.models.responses.LoginResponse;
import com.hslcreator.LibraryAPI.config.JwtService;
import com.hslcreator.LibraryAPI.repositories.UserRepository;
import com.hslcreator.LibraryAPI.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public LoginResponse signup(SignupRequest request) {

         var user = User.builder()
                 .matricNumber(request.getMatricNumber())
                 .role(request.getRole())
                 .password(passwordEncoder.encode(request.getPassword()))
                 .department(request.getDepartment())
                 .enabled(true)
                 .build();

         userRepository.save(user);

         var jwtToken = jwtService.generateToken(user);

         return LoginResponse.builder()
                 .userId(user.getUserId())
                 .token(jwtToken)
                 .build();
    }

    public LoginResponse login(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getMatricNumber(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = UserUtil.getLoggedInUser().orElseThrow();

        var jwtToken = jwtService.generateToken(user);

        return LoginResponse.builder()
                .userId(user.getUserId())
                .token(jwtToken)
                .build();
    }
}
