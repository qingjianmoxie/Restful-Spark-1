package com.webservice.finalrest;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author dt817
 */

public class School {
    
	private int school_id;
    private String school_name;
    private String school_website;
    private List<Levels> school_levels;
    private String school_gender;
    
    public School(){
        school_levels = new ArrayList<Levels>();
    }
    
    @JsonCreator
    public School(
    		@JsonProperty("school_id") int i,
    		@JsonProperty("school_name") String n, 
    		@JsonProperty("school_website") String w,
    		@JsonProperty("school_levels") List<Levels> l,
    		@JsonProperty("school_gender") String g) {
    	
    	school_id = i;
    	school_name = n;
    	school_website =w;
    	school_levels =l;
    	school_gender=g;
    }
    public void setSchool_id(int i) {
    	school_id = i;
    }
    
    public void setSchool_name(String string){
        school_name = string;
    }
    
    public void setSchool_website(String string){
        school_website = string;
    }
    
    public void addSchool_level(Levels string) {
		school_levels.add(string);
		
	}
    
    public void setLevels (List<Levels> l){
        school_levels = l;
    }
    
    public void setSchool_gender(String string){
        school_gender = string;
    }
    
    public int getSchool_id(){
        return school_id;
    }
    
    public String getSchool_name(){
        return school_name;
    }
    
    public String getSchool_website(){
        return school_website;
    }
    
    public List<Levels> getSchool_levels(){
        return school_levels;
    }
    
    public String getSchool_gender(){
        return school_gender;
    }

	
}