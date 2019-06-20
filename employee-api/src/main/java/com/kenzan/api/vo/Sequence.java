package com.kenzan.api.vo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "sequences")
public class Sequence {
	@Id
	private String id;
	private int sequence;
}
