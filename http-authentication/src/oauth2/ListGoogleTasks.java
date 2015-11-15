package oauth2;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;


/**
 * - This a demonstration sample to use the authorization code grant flow
 *   and access the Google Tasks API.
 * - Must set CLIENT_ID, CLIENT_SECRET and REDIRECT_URI
 * - Should use parameter 'state' to avoid cross-site request forgery attacks,
 *   as described in section 10.12 of RFC 6749
 */
public class ListGoogleTasks extends HttpServlet {

    public static final String CLIENT_ID = "";
    public static final String CLIENT_SECRET = "";
    public static final String REDIRECT_URI = "";

	@Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    	
    	System.out.println("--New request was received --");
        System.out.println(req.getRequestURI()); 
        System.out.println(req.getMethod());
        System.out.println(req.getHeader("Accept"));
        
        resp.setStatus(302);
        resp.setHeader("Location",
                // google's authorization endpoint
        		"https://accounts.google.com/o/oauth2/auth?"+
        		"scope=https://www.googleapis.com/auth/tasks.readonly&"+
        		// =some.security.state&
        		"redirect_uri="+REDIRECT_URI+"&"+
        		"response_type=code&"+
        		"client_id="+CLIENT_ID +
        		"&approval_prompt=force");
    }
}



