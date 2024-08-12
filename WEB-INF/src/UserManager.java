import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

public class UserManager{

    private Map<String, User> users; 

    public UserManager(Map<String, User> users){
        this.users=users;
    }

    public UserManager(){
        this.users = new HashMap<>();
    }

    public Map<String, User> getUsers(){
        return users;
    }

    public void registerUser(User userToRegister){
        if (userToRegister == null ||
        userToRegister.getEmail() == null || userToRegister.getEmail().isEmpty() ||
        userToRegister.getPassword() == null || userToRegister.getPassword().isEmpty() ||
        userToRegister.getFirstName() == null || userToRegister.getFirstName().isEmpty() ||
        userToRegister.getLastName() == null || userToRegister.getLastName().isEmpty()) {

        throw new InvalidParameterException("User fields can't be empty!");
    }

    String userMail = userToRegister.getEmail();
    if (users.containsKey(userMail)) {
        throw new IllegalStateException("User is already registered!");
    }

    // this is mainly for debuggin/logging 
    System.out.println("Registering user with email: " + userMail);
    users.put(userMail, userToRegister);
    System.out.println("User registered successfully.");
}

    public User loginUser(User userToLogin) {
    if (userToLogin == null ||
        userToLogin.getEmail() == null || userToLogin.getEmail().isEmpty() ||
        userToLogin.getPassword() == null || userToLogin.getPassword().isEmpty()) {
        throw new InvalidParameterException("Email or password cannot be empty.");
    }

    String userMail = userToLogin.getEmail();
    String password = userToLogin.getPassword();

    System.out.println("Attempting to login with email: " + userMail);
    if (!users.containsKey(userMail)) {
        System.out.println("User does not exist.");
        throw new IllegalArgumentException("User does not exist.");
    }

    User storedUser = users.get(userMail);
    
    // mainly for debugging
    System.out.println("Stored password for user: " + storedUser.getPassword());
    System.out.println("Provided password: " + password);

    if (!password.equals(storedUser.getPassword())) {
        throw new IllegalStateException("Email and password do not match.");
    }

    return storedUser;
}

    // mainly for debuggin/logging
    public void printUsers() {
        System.out.println("Users in map:");
        for (Map.Entry<String, User> entry : users.entrySet()) {
            User user = entry.getValue();
            System.out.println("Email: " + entry.getKey() + ", First Name: " + user.getFirstName() + ", Last Name: " + user.getLastName());
        }
    }

    //testing
    /* 
    public static void main(String[] args) {
        UserManager userManager = new UserManager();
    
        // Test 1: Erfolgreiche Registrierung
        try {
            User user1 = new User("alice@example.com", "password123", "Alice", "Smith");
            userManager.registerUser(user1);
            System.out.println("User erfolgreich registriert: " + user1);
        } catch (Exception e) {
            System.out.println("Fehler bei der Registrierung: " + e.getMessage());
        }
    
        // Test 2: Fehlversuch bei doppelter Registrierung
        try {
            User duplicateUser = new User("alice@example.com", "newpassword", "Alice", "Smith");
            userManager.registerUser(duplicateUser);
        } catch (IllegalStateException e) {
            System.out.println("Fehler bei der Registrierung: " + e.getMessage());
        }
    
        // Test 3: Erfolgreiche Anmeldung
        try {
            User loggedInUser = userManager.loginUser(new User("alice@example.com", "password123", null, null));
            System.out.println("Erfolgreich angemeldet: " + loggedInUser);
        } catch (Exception e) {
            System.out.println("Fehler beim Anmelden: " + e.getMessage());
        }
    
        // Test 4: Fehlversuch bei Anmeldung mit falschem Passwort
        try {
            User failedLogin = userManager.loginUser(new User("alice@example.com", "wrongpassword", null, null));
            System.out.println("Erfolgreich angemeldet: " + failedLogin);
        } catch (IllegalStateException e) {
            System.out.println("Fehler beim Anmelden: " + e.getMessage());
        }
    
        // Ausgabe aller Benutzer
        System.out.println("Alle Benutzer:");
        Map<String, User> users = userManager.getUsers();
        for (Map.Entry<String, User> entry : users.entrySet()) {
            System.out.println("Email: " + entry.getKey() + ", User: " + entry.getValue());
        }
    }*/
}