package com.springboot.Model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Leave_mast")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Leave {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
//	@NotEmpty(message = "From field required...")
	@Column(name = "From_Date")
	private LocalDate from;
	@Column(name = "To_Date")
	private LocalDate to;
	private String leaveStatus;
	private String approveBy;
	@ManyToOne
	@JoinColumn(name = "Employee_Id")
	private Employee employee;
}
