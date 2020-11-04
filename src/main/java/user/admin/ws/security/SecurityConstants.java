package user.admin.ws.security;

import user.admin.ws.SpringApplicationContext;

public class SecurityConstants {

		//token validation --> 10 days
	    public static final long EXPIRATION_TIME = 864000000;
	    
	    // passed in header string authorization
	    public static final String TOKEN_PREFIX = "Bearer "; 
	    
	    public static final String HEADER_STRING = "Authorization";
	    
	    public static final String SIGN_UP_URL = "/users";
	   
	    // public static final String TOKEN_SECRET ="jf914jgu8dnmkje3nf10";	
	    
	    // Method that uses App properties to read tokenSecret value
	    
	    public static String getTokenSecret() {
	    	AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
	    	return appProperties.getTokenSecret();
	    }
}