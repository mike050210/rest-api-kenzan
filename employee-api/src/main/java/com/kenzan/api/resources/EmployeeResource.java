package com.kenzan.api.resources;

import java.net.URI;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kenzan.api.transaction.ResourceDAO;
import com.kenzan.api.vo.EmployeeVO;

@CrossOrigin
@RestController
@RequestMapping("/employees")
@PermitAll
public class EmployeeResource
{

	@Autowired
	ResourceDAO<EmployeeVO> employeeDAO;

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<EmployeeVO>> getAllEmployees()
	{
		return ResponseEntity.ok(employeeDAO.getAll());
	}

	@GetMapping(value = "/{employee-id}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public EmployeeVO getEmployee(@PathVariable("employee-id") String employee_id)
	{

		int id = 0;
		try
		{
			id = Integer.parseInt(employee_id);
		} catch (NumberFormatException e)
		{
			return null;
		}

		EmployeeVO employee = employeeDAO.getResourceById(id).orElse(null);

		return employee;
	}

	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EmployeeVO> insertEmployee(@RequestBody EmployeeVO employee)
	{
		
		EmployeeVO employeeCreated = employeeDAO.create(employee);
		if (employeeCreated != null && employeeCreated.getEmployeeID() > 0)
		{
			URI uri = URI.create("/employees/" + employeeCreated.getEmployeeID());
			return ResponseEntity.created(uri).body(employeeCreated);
		} else
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PutMapping(value = "/{employee-id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EmployeeVO> updateEmployee(@RequestBody EmployeeVO employee,
			@PathVariable("employee-id") String employee_id)
	{
		EmployeeVO result = employeeDAO.update(employee);
		if (result != null)
		{
			return ResponseEntity.ok().build();
		} else
		{
			return ResponseEntity.notFound().build();
		}
	}

	@RolesAllowed({"ADMIN"})
	@DeleteMapping(value = "/{employee-id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteEmployee(@PathVariable("employee-id") String employee_id)
	{
		int id = 0;
		try
		{
			id = Integer.parseInt(employee_id);
		} catch (NumberFormatException e)
		{
			return null;
		}

		if (employeeDAO.deleteResource(id) > 0)
		{
			return ResponseEntity.noContent().build();
		}
		else
		{
			return ResponseEntity.notFound().build();
		}
		
	}
}
