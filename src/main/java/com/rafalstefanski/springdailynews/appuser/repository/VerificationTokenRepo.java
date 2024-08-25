package com.rafalstefanski.springdailynews.appuser.repository;

import com.rafalstefanski.springdailynews.appuser.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepo extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByValue(String value);
}
