package com.company.auth;

import com.company.auth.dtos.AuthDto;
import com.company.auth.dtos.JwtResponse;
import com.company.auth.dtos.RegistrDto;
import com.company.auth.services.AuthService;
import com.company.auth.services.EmailSenderService;
import com.company.auth.utils.htmlUtil;
import com.company.base.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final EmailSenderService emailSenderService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JwtResponse>> login(
            @Valid @RequestBody AuthDto auth) {
        return ResponseEntity.ok(authService.login(auth));
    }

    @PostMapping("/registr")
    public ResponseEntity<ApiResponse<?>> registr(
            @Valid @RequestBody RegistrDto reg) {
        return ResponseEntity.ok(authService.registr(reg));
    }

    @GetMapping(value = "/verification/email/{jwt}")
    public void registration(@PathVariable String jwt,
                             HttpServletResponse response) {

        ApiResponse<?> apiResponse = emailSenderService.emailVerification(jwt);

        if (apiResponse.status()) {
            try {
                response.setContentType("text/html");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(htmlUtil.getResponse());
                response.getWriter().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            ResponseEntity<ApiResponse<?>> error = new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
