package com.springboot.Repository.User;

import com.springboot.Model.User.Tokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Tokens,Long> {

    @Query(nativeQuery = true,value = "Select * from tokens t Inner Join users u on u.id = t.user_id where t.user_id = :userId and is_logout_out is false")
    List<Tokens> findAllTokensByUser(@Param("userId") Integer userId);

    Optional<Tokens> findByToken(String token);

    @Modifying
    @Query(nativeQuery = true, value = " UPDATE tokens SET is_log_out = true,logout_date_time = NOW() WHERE user_id = :userId AND is_log_out = false")
    void logoutAllExistingTokensByUserId(@Param("userId") Long userId);




}
