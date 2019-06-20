package com.kenzan.api.transaction;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;

import com.kenzan.api.transaction.seq.SequenceGenerator;
import com.kenzan.api.vo.EmployeeStatus;
import com.kenzan.api.vo.EmployeeVO;
import com.mongodb.client.result.UpdateResult;

public class EmployeeDAO implements ResourceDAO<EmployeeVO> {

	@Autowired
	private MongoOperations mongo;

	@Autowired
	private SequenceGenerator seqGen;

	@Override
	public Optional<EmployeeVO> getResourceById(int employeeId) {

		EmployeeVO employee = mongo.findOne(query(where("_id").is(employeeId).and("status").is(EmployeeStatus.ACTIVE)),
				EmployeeVO.class);

		return Optional.ofNullable(employee);
	}

	@Override
	public List<EmployeeVO> getAll() {

		List<EmployeeVO> employees = mongo.find(query(where("status").is(EmployeeStatus.ACTIVE)), EmployeeVO.class);
		return employees;
	}

	@Override
	public EmployeeVO create(EmployeeVO employee) {

		employee.setEmployeeID(seqGen.getNextSequence("employees"));
		employee.setStatus(EmployeeStatus.ACTIVE);

		return mongo.insert(employee);

	}

	@Override
	public EmployeeVO update(EmployeeVO employee) {

		return mongo.findAndReplace(query(where("_id").is(employee.getEmployeeID())), employee);

	}

	@Override
	public int deleteResource(int employeeId) {

		UpdateResult result = mongo.updateFirst(
				query(where("_id").is(employeeId).and("status").is(EmployeeStatus.ACTIVE)),
				new Update().set("status", EmployeeStatus.INACTIVE), EmployeeVO.class);

		//System.out.println("Deleted: " + result.getModifiedCount());
		return (int)result.getModifiedCount();

	}

}
