package com.kenzan.api.transaction;

import java.util.List;
import java.util.Optional;

public interface ResourceDAO<T> {

	Optional<T> getResourceById(int id);
	
	List<T> getAll();
	
	T create(T t);
	
	T update(T t);
	
	int deleteResource(int id);
		
}
