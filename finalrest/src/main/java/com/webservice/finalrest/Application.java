package com.webservice.finalrest;
import static spark.Spark.*;

public class Application {
	// Declare dependencies
    public static Schools schools;

    public static void main(String[] args) {

        // Instantiate your dependencies
      

        // Configure Spark
        port(4567);
        staticFiles.location("/public");
        staticFiles.expireTime(600L);
        

        // Set up before-filters (called before each get/post)
        before("*", (request, response) -> {
            boolean authenticated = Filters.filter(request);
            // ... check if authenticated
            if (!authenticated) {
                halt(401, "UNAUTHORIZED USER");
            }
        });
        before("*",                  Filters.addTrailingSlashes);

        get("/schools/",          SchoolResource.getSchools);
        get("/schools/:admin_id/",       SchoolResource.getSchool);
        post("/schools/:admin_id/levels/",       SchoolResource.postSchool_levels);
        delete("/schools/:admin_id/levels/",       SchoolResource.deleteSchool_levels);
        put("/schools/:admin_id/name/",       SchoolResource.putSchool_name);
        put("/schools/:admin_id/website/",       SchoolResource.putSchool_website);
        put("/schools/:admin_id/gender/",       SchoolResource.putSchool_gender);
        
        //get("*",                     Viewer.notFound);

        //Set up after-filters (called after each get/post)
        after("*",                   Filters.addGzipHeader);

    }

}