package com.webservice.finalrest;

import spark.*;
//import static com.webservice.finalrest.RequestUtil.*;


import java.sql.SQLException;




public class Filters {
	public static final String AUTHENTICATION_HEADER = "Authorization";
	
	private static boolean authenticationStatus = false;
	
	public static boolean filter(Request req) {
		String header = req.headers(AUTHENTICATION_HEADER);
		assert header.substring(0, 6).equals("Basic ");
		String basicAuthEncoded = header.substring(6);
        AuthenticationService authenticationService = new AuthenticationService();

			try {
				authenticationStatus = authenticationService
						.authenticate(req,basicAuthEncoded);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return authenticationStatus;
		
	}
	
	/*public static boolean verification = (Request req, Response res) -> {
		
		filter(req);
		if (authenticationStatus == true) {
			System.out.println("Passed");//Debug
            req.session().attribute("authenticationStatus", "AUTHORIZED");
            res.redirect(req.pathInfo());
        }else {
        	System.out.println("Failed");//Debug
        
        	//halt(401,"UNAUTHORIZED USER");
        	//req.session().attribute("authenticationStatus", "UNAUTHORIZED");
        	//res.redirect("/access", httpStatusCode);
        }
	};*/
	
	public static Filter addTrailingSlashes = (Request request, Response response) -> {
        if (!request.pathInfo().endsWith("/")) {
            response.redirect(request.pathInfo() + "/");
        }
    };
    
 // Enable GZIP for all responses
    public static Filter addGzipHeader = (Request request, Response response) -> {
        response.header("Content-Encoding", "gzip");
    };

	
}