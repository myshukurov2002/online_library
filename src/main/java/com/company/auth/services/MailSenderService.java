package com.company.auth.services;

import com.company.auth.entities.EmailHistoryEntity;
import com.company.auth.repositories.EmailAddressRepository;
import com.company.auth.repositories.EmailHistoryRepository;
import com.company.auth.utils.htmlUtil;
import com.company.base.ApiResponse;
import com.company.config.security.utils.jwtUtil;
import com.company.user.entities.UserEntity;
import com.company.user.enums.Role;
import com.company.user.repositories.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class MailSenderService {

    private final JavaMailSender javaMailSender;
    private final EmailAddressRepository emailAddressRepository;
    private final EmailHistoryRepository emailHistoryRepository;
    private final UserRepository userRepository;
    @Value("${server.url}")
    private String serverUrl;
    @Value("${spring.mail.username}")
    private String fromEmail;

    void sendMimeEmail(String toAccount, String subject, String text) {
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            msg.setFrom(fromEmail);
            helper.setTo(toAccount);
            helper.setSubject(subject);
            helper.setText(text, true);
            javaMailSender.send(msg);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public ApiResponse<?> sendEmailVerification(String toAccount) {

        ExecutorService executor = Executors.newSingleThreadExecutor();

        UserEntity user = userRepository
                .findByEmailAndVisibilityTrue(toAccount)
                .orElseThrow(IllegalThreadStateException::new);

        ArrayList<Role> roles = new ArrayList<>(user.getRoles());

        String jwt = jwtUtil.encode(user.getId(), roles);

        String url = serverUrl + "/api/v1/auth/verification/email/" + jwt;

        executor.submit(() -> {
            sendMimeEmail(toAccount, "Library VERIFICATION", htmlUtil.getRegistrationButton(url));

            EmailHistoryEntity entity = new EmailHistoryEntity();
            entity.setEmail(toAccount);
            entity.setMessage(url);
            emailHistoryRepository.save(entity);
            executor.shutdown();
        });
        return new ApiResponse<>(true, "SUCCESS SEND VERIFICATION PAGE!");
    }
}
