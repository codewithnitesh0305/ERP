package com.springboot.Model;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@MappedSuperclass
public class Common {
    private String createdBy;
    private LocalDate createAt;
    private String createMachine;
    private String updateBy;
    private LocalDate updateAt;
    private String updateMachine;
}
