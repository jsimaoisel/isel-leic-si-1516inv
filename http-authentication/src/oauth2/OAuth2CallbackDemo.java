package oauth2;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * - This a demonstration sample to use the authorization code grant flow
 *   and access the Google Tasks API.
 * - It does not handle error situations properly.
 * -  Should use parameter 'state' to avoid cross-site request forgery attacks,
 *   as described in section 10.12 of RFC 6749
 */
public class OAuth2CallbackDemo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            String code = req.getParameter("code");
            System.out.println("Authorization code is = " + code);

            System.out.println("Send code to token endpoint");
            URL url = new URL("https://www.googleapis.com/oauth2/v3/token");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            PrintWriter output = new PrintWriter(
                    new OutputStreamWriter(connection.getOutputStream()));
            output.print("code=" + code + "&");
            output.print("client_id=" + ListGoogleTasks.CLIENT_ID + "&");
            output.print("client_secret=" + ListGoogleTasks.CLIENT_SECRET + "&");
            output.print("redirect_uri=" + ListGoogleTasks.REDIRECT_URI + "&");
            output.print("grant_type=authorization_code");
            output.flush();

            System.out.println("Collect access_token in the response");
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String line;
            String access_token = "";
            // TODO: change this to use a JSON library
            while ((line = input.readLine()) != null) {
                System.out.println(line);
                if (line.contains("access_token")) {
                    access_token = line.split(":")[1].split("\"")[1];
                    System.out.println("Found access_token = " + access_token);
                }
            }
            output.close();
            input.close();

            System.out.println("Collect list of tasks lists and build response to the user-agent");
            url = new URL("https://www.googleapis.com/tasks/v1/users/@me/lists");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + access_token);
            input = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            output = new PrintWriter(resp.getOutputStream());
            while ((line = input.readLine()) != null) {
                System.out.println(line);
                output.write(line);
            }
            output.flush();
            resp.setStatus(200);
        } catch(IOException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
