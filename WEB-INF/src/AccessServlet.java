import java.io.IOException;
import java.security.InvalidParameterException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AccessServlet extends HttpServlet{

    private UserManager userManager;
    private DatabaseManager db;

    private static final long serialVersionUID = 100L;

    @Override
    public void init(){

        db = new DatabaseManager();
        this.userManager = new UserManager(db.getUsers());

    }

    @Override
    public void destroy(){

        db.writeUsers(this.userManager.getUsers());

    }

    public void loginAction(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String requestEmail = request.getParameter("email");
        String requestPassword = request.getParameter("password");
    
        // Logging zur Überprüfung der empfangenen Parameter
        System.out.println("Received email: " + requestEmail);
        System.out.println("Received password: " + requestPassword);
    
        // Überprüfen auf leere Parameter
        if (requestEmail == null || requestPassword == null || requestEmail.trim().isEmpty() || requestPassword.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Email and password are required.");
            return;
        }
    
        System.out.println("Current users in UserManager: ");
        this.userManager.printUsers();

        HttpSession session = request.getSession(false);
    
        if (session != null) {
            String prevEmail = (String) session.getAttribute("email");
    
            if (prevEmail == null) {
                session.invalidate();
                response.sendRedirect("/catalog/login.html");
                return;
            } else {
                if (prevEmail.equals(requestEmail)) {
                    response.sendRedirect("/catalog/catalog.html");
                    return;
                } else {
                    session.invalidate();
                    response.sendRedirect("/catalog/login.html");
                    return;
                }
            }
        }
    
        User userToLogin = new User(requestEmail, requestPassword);
        try {
            User loggedInUser = this.userManager.loginUser(userToLogin);
            HttpSession newSession = request.getSession(true);
            newSession.setAttribute("email", loggedInUser.getEmail());
            response.sendRedirect("/catalog/catalog.html");
        } catch (IllegalArgumentException | IllegalStateException e) {
            e.printStackTrace();  // Ausgabe auf der Konsole zur Fehlerdiagnose
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Login failed: " + e.getMessage());
        }
    }

    public void registerAction(HttpServletRequest request, HttpServletResponse response)throws IOException{
        String requestEmail = request.getParameter("email");
        String requestFirstName = request.getParameter("firstName");
        String requestLastName = request.getParameter("lastName");
        String requestPassword = request.getParameter("password");

        // for testing/logging purposes
        System.out.println("Received email: " + requestEmail);
        System.out.println("Received first name: " + requestFirstName);
        System.out.println("Received last name: " + requestLastName);
        System.out.println("Received password: " + requestPassword);

        // get but not create the user's session
        HttpSession session = request.getSession(false);

        if(session != null){
            session.invalidate();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Try to register again.");
            return;
        }

        try
        {
        //creating the user that's to be registered
        User registeringUser = new User(requestEmail, requestPassword, requestFirstName, requestLastName);

        this.userManager.registerUser(registeringUser);
        
        // for testing/loggin purposes
        this.userManager.printUsers();
        
        response.sendRedirect("/catalog/login.html");
    }catch(InvalidParameterException e){
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Try to register again " + e.getMessage());
    }
    }

    public void logOutAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
         
        // get but not create the user's session
        HttpSession session = request.getSession(false);

        if(session == null){
            try {
                response.sendRedirect("/catalog/login.html");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            session.invalidate();
            
            try {
                response.sendRedirect("/catalog/login.html");
            } catch (IOException e) {
                e.printStackTrace();
            }

            return;
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException{

        String action = request.getParameter("action");

        if(action == null){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Something went wrong");
            return;
        }

        // checking what action the user is trying to do
        switch(action){
            case "login":
            loginAction(request, response);
            break;

            case "register":
            registerAction(request, response);
            break;

            case "logout":
            logOutAction(request, response);
            break;

            default:
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown error");
        }

    }

}
