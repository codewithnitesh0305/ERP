package com.springboot.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Entity
@Table(name = "ClaimRequestMast")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClaimRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String claimNo;
    private String claimTitle;
    private LocalDate claim_from_date;
    private LocalDate claim_to_date;
    private LocalDate claim_request_date;
    private String claim_required_amount;
    private String claim_status;
    private String claim_update_by;
    @ManyToOne
    @JoinColumn(name = "Employee_id")
    private Employee employee;
}
