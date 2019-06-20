package com.kenzan.api.vo;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "employees")
public class EmployeeVO {
	@Id
	private int employeeID;
	
	private String firstName;
	private String middleName;
	private String lastName;
	private Date dob;
	private Date employmentDate;
	private EmployeeStatus status;
	
	
	
}