package com.webservice.finalrest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Levels {

    private String level_name;
	
	 @JsonCreator
	public Levels(@JsonProperty("level_name") String level) {
		level_name = level;
	}
	
	
	
	public void setLevel_name(String level) {
		level_name = level;
	}
	
	public String getLevel_name() {
		return level_name;
	}
}
