package com.webservice.finalrest;

//import java.util.HashMap;
//import java.util.Map;


import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//import org.apache.velocity.app.VelocityEngine;
//import org.eclipse.jetty.http.HttpStatus;

//import spark.*;

//import spark.template.velocity.VelocityTemplateEngine;

//import static com.webservice.finalrest.RequestUtil.*;


public class Viewer {
	
	private School school;
	private Schools schools;
	
	public Viewer(School school) {
		this.school = school;
	}
	
	public Viewer(Schools schools) {
		this.schools = schools;
	}
	
	@Produces(MediaType.APPLICATION_JSON)
	public Schools displaySchools() {
		return schools;
	}
	
	@Produces(MediaType.APPLICATION_JSON)
	public School displaySchool() {
		return school;
	}
	
	/*
	 public static String render(Request request, Map model, String templatePath) {
	        model.put("msg", new MessageBundle(getSessionLocale(request)));
	        model.put("currentUser", getSessionCurrentUser(request));
	        model.put("WebPath", Path.Web.class); // Access application URLs from templates
	        return strictVelocityEngine().render(new ModelAndView(model, templatePath));
	    }
	 
	 public static Route notAcceptable = (Request request, Response response) -> {
	        response.status(HttpStatus.NOT_ACCEPTABLE_406);
	        return new MessageBundle(getSessionLocale(request)).get("ERROR_406_NOT_ACCEPTABLE");
	    };

	    public static Route notFound = (Request request, Response response) -> {
	        response.status(HttpStatus.NOT_FOUND_404);
	        return render(request, new HashMap<>(), Path.Web.NOT_FOUND);
	    };
	 
	 private static VelocityTemplateEngine strictVelocityEngine() {
		    VelocityEngine configuredEngine = new VelocityEngine();
	        configuredEngine.setProperty("runtime.references.strict", true);
	        configuredEngine.setProperty("resource.loader", "class");
	        configuredEngine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
	        return new VelocityTemplateEngine(configuredEngine);
	    }
*/
}
