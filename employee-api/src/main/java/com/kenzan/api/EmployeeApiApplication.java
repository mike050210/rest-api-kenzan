package com.kenzan.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class EmployeeApiApplication {

	
	
	
	public static void main(String[] args) {
		System.setProperty("server.servlet.context-path", "/api");
		SpringApplication.run(EmployeeApiApplication.class, args);
	}

	/**
	 * Initialize employees after application starts
	 */
	@EventListener(ApplicationReadyEvent.class)
	private void doSomethingAfterStartup() {
	    System.out.println("Loading default employees");

	    addEmployees(getEmployeesBody());
	}
	
	/**
	 * Get employees body requests for POST call
	 * @return body request
	 */
	private List<String> getEmployeesBody()
	{
		List<String> employees = new ArrayList<>();
				
		String[][] names = {
				{"Peter",   "J",         "Smith"},
				{"Sean",    "",          "Cooper"},
				{"Valerie", "",          "Cobb"},
				{"Alfred",  "Patrick",   "Ross"},
				{"Anna",    "Kimberlee", "Wolf"},
				{"Joshua",  "James",     "Bell"},
				{"Sarah",   "",          "Baker"},
				{"Louise",  "Moss",      "Allen"},
				{"Andrew",  "Osip",      "Frank"},
				{"Katty",   "",          "Harris"}};
		
		for(int i = 0; i < names.length; i++)
		{
			String jsonInputString = "{\"employeeID\": 0,\"firstName\": \"" + names[i][0]
					+"\",\"middleName\": \"" + names[i][1]
					+"\",\"lastName\": \"" + names[i][2]
					+"\",\"dob\": \"1986-01-" + (10 + i)
					+"\",\"employmentDate\": \"2018-01-" + (10 + i) + "\"}";
			employees.add(jsonInputString);
		}
		
		return employees;
		
	}
	
	
	private void addEmployees(List<String> employeesRequest)
	{
		URL url;
		try
		{
			url = new URL("http://localhost:8080/api/employees");
		} catch (MalformedURLException e1)
		{
			e1.printStackTrace();
			url = null;
		}
		
		for(String request : employeesRequest)
		{
			System.out.println(request);
			try
			{
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("POST");
				con.setRequestProperty("Content-Type", "application/json; utf-8");
				
				con.setDoOutput(true);
				
				try(OutputStream os = con.getOutputStream()) {
				    byte[] input = request.getBytes("utf-8");
				    os.write(input, 0, input.length);           
				}
				
				try(BufferedReader br = new BufferedReader(
						  new InputStreamReader(con.getInputStream(), "utf-8"))) {
						    StringBuilder response = new StringBuilder();
						    String responseLine = null;
						    while ((responseLine = br.readLine()) != null) {
						        response.append(responseLine.trim());
						    }
						    System.out.println(response.toString());
						}
			}catch (IOException e)
			{
				e.printStackTrace();
			}
			
			
		}
		 
			
	}
}
