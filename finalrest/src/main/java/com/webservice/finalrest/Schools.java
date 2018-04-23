package com.webservice.finalrest;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dt817
 */

public class Schools {
    
    private final List<School> schools;
    
    public Schools(){
        schools = new ArrayList<School>();
    }
    
    public void addSchool(School s){
        schools.add(s);
    }
    
    public List<School> getSchools(){
        return schools;
    }
    
    //public School getSchoolById(int id) {
    //    return schools.stream().filter(b -> b.getSchool_id() == id).findFirst().orElse(null);
    //}
}
