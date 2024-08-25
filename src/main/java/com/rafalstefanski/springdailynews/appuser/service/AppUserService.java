package com.rafalstefanski.springdailynews.appuser.service;

import com.rafalstefanski.springdailynews.appuser.entity.AppUser;
import com.rafalstefanski.springdailynews.appuser.entity.VerificationToken;
import com.rafalstefanski.springdailynews.appuser.repository.AppUserRepository;
import com.rafalstefanski.springdailynews.appuser.repository.VerificationTokenRepo;
import com.rafalstefanski.springdailynews.configuration.PasswordConfig;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final PasswordConfig passwordConfig;
    private final VerificationTokenRepo verificationTokenRepo;
    private final MailSenderService mailSenderService;
    private static final Logger logger = Logger.getLogger(AppUserService.class.getName());

    @Autowired
    public AppUserService(AppUserRepository appUserRepository, PasswordConfig passwordConfig,
                          VerificationTokenRepo verificationTokenRepo, MailSenderService mailSenderService) {
        this.appUserRepository = appUserRepository;
        this.passwordConfig = passwordConfig;
        this.verificationTokenRepo = verificationTokenRepo;
        this.mailSenderService = mailSenderService;
    }

    public void addUser(AppUser user, HttpServletRequest request) throws MessagingException {
        user.setPassword(passwordConfig.passwordEncoder().encode(user.getPassword()));

        appUserRepository.save(user);
        logger.info("User registered: " + user.getUsername());

        String token = UUID.randomUUID().toString();

        VerificationToken verificationToken = new VerificationToken(user, token);
        verificationTokenRepo.save(verificationToken);
        logger.info("Verification token created: " + token);

        String url = "http://" + request.getServerName() + ":" +
                request.getServerPort() +
                request.getContextPath() +
                "/verify-token?token=" + token;
        mailSenderService.sendEmail(user.getEmail(), "Verification Token", url, false);
        logger.info("Verification token sent to e-mail: " + user.getEmail());
    }

    public void verifyToken(String token) {
        logger.info("Verifying token: " + token);
        VerificationToken verificationToken = verificationTokenRepo.findByValue(token);
        if (verificationToken != null && verificationToken.getExpiryDate().isAfter(LocalDateTime.now())) {
            AppUser appUser = verificationToken.getAppUser();
            appUser.setEnabled(true);
            appUserRepository.save(appUser);
            logger.info("User activated: " + appUser.getUsername());
        } else {
            logger.warning("User not activated. Invalid or expired token: " + token);
        }
    }
}
