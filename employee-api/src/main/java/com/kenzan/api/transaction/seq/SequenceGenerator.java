package com.kenzan.api.transaction.seq;

import static org.springframework.data.mongodb.core.query.Query.*;
import static org.springframework.data.mongodb.core.query.Criteria.*;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.kenzan.api.vo.Sequence;

@Service
public class SequenceGenerator {

	@Autowired
	private MongoOperations mongo;

	public int getNextSequence(String collectionName) {
		Sequence seq = mongo.findAndModify(
				query(where("_id").is(collectionName)),
				new Update().inc("sequence", 1),
				options().returnNew(true).upsert(true), Sequence.class);
		if (seq == null)
			System.out.println("Sequence of " + collectionName + " is null");

		return seq == null? 1 : seq.getSequence();
	}
}
