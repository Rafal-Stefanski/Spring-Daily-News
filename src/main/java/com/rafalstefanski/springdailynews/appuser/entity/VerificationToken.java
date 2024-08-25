package com.rafalstefanski.springdailynews.appuser.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private AppUser appUser;

    private String value;

    private LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(15);

    public VerificationToken(AppUser appUser, String value) {
        this.appUser = appUser;
        this.value = value;
    }
}
