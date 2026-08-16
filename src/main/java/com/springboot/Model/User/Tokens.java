package com.springboot.Model.User;

import io.micrometer.core.annotation.Counted;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tokens")
@NoArgsConstructor
@Data
public class Tokens {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "token")
    private String token;

    @Column(name = "is_log_out")
    private Boolean isLogOut;

    @Column(name = "logout_date_time")
    private String logoutDateTime;

}
