package com.springboot.Repository.User;

import com.springboot.Model.User.UserOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserOtpRepository extends JpaRepository<UserOtp,Long> {

    Optional<UserOtp> findByReferenceNoAndOtp(String referenceNo,String otp);
    Optional<UserOtp> findByReferenceNo(String referenceNo);
}
