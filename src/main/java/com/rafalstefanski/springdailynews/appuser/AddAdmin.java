package com.rafalstefanski.springdailynews.appuser;

import com.rafalstefanski.springdailynews.appuser.entity.AppUser;
import com.rafalstefanski.springdailynews.appuser.repository.AppUserRepository;
import com.rafalstefanski.springdailynews.configuration.PasswordConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.rafalstefanski.springdailynews.appuser.entity.AppUserRole.ROLE_ADMIN;
import static com.rafalstefanski.springdailynews.appuser.entity.AppUserRole.ROLE_USER;


@Component
public class AddAdmin {

    @Autowired
    public AddAdmin(AppUserRepository appUserRepository) {

        PasswordConfig passwordConfig = new PasswordConfig();

        AppUser userRafal = new AppUser();
        userRafal.setId(1L);
        userRafal.setUsername("rafal");
        userRafal.setPassword(passwordConfig.passwordEncoder().encode("raf1"));
        userRafal.setEmail("rafal@rafal.pl");
        userRafal.setRole(ROLE_ADMIN);
        userRafal.setEnabled(true);

        appUserRepository.save(userRafal);

        AppUser userUser = new AppUser();
        userUser.setId(2L);
        userUser.setUsername("user");
        userUser.setPassword(passwordConfig.passwordEncoder().encode("pass"));
        userUser.setEmail("user@user.pl");
        userUser.setRole(ROLE_USER);
        userUser.setEnabled(true);

        appUserRepository.save(userUser);
    }

}
