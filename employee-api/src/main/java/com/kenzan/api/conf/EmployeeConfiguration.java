package com.kenzan.api.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.kenzan.api.transaction.EmployeeDAO;
import com.kenzan.api.transaction.ResourceDAO;
import com.kenzan.api.vo.EmployeeVO;

@EnableWebMvc
@Configuration
public class EmployeeConfiguration {

	@Bean
	public ResourceDAO<EmployeeVO> getEmployeeDAO()
	{
		return new EmployeeDAO();
	}
}
