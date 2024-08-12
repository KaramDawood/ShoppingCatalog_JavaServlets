import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.EOFException;
import java.util.Map;
import java.util.HashMap;

public class DatabaseManager {
	// Replace below paths with your full path to users.txt and carts.txt in the project database folder.
	private final String USERS_PATH = "E:\\Tomcat\\apache-tomcat-9.0.93\\webapps\\catalog\\WEB-INF\\database\\users.txt";
	private final String CARTS_PATH = "E:\\Tomcat\\apache-tomcat-9.0.93\\webapps\\catalog\\WEB-INF\\database\\carts.txt";

	// deserialize all users from the database
	public Map<String, User> getUsers() {
		try{
			File usersDb = new File(this.USERS_PATH);
			
			// check if designated file exists and is not empty
			if (usersDb.isFile() && usersDb.length() != 0L) {
				FileInputStream fis = new FileInputStream(usersDb);
				ObjectInputStream ois = new ObjectInputStream(fis);
				Map<String, User> users = (Map<String, User>) ois.readObject();
				ois.close();
				return users;
			}
		} catch (FileNotFoundException e) {
			System.out.println("FAILED TO FIND SAVED DATA.");
		} catch (IOException e) {
			System.out.println("FAILED TO SAVE DATA.");
		} catch (Exception e) {
			System.out.println("UNEXPECTED ERROR OCCURED.");
		}
		// otherwise return an empty map
		return new HashMap<>();
	}
	
	//deserialize all carts from the database
	public Map<String, Map<CartItem, Integer>> getUserCarts() {
		try{
			File cartsDb = new File(this.CARTS_PATH);
			
			// check if designated file exists and is not empty
			if (cartsDb.isFile() && cartsDb.length() != 0L) {
				FileInputStream fis = new FileInputStream(cartsDb);
				ObjectInputStream ois = new ObjectInputStream(fis);
				Map<String, Map<CartItem, Integer>> userCarts = (Map<String, Map<CartItem, Integer>>) ois.readObject();
				ois.close();
				return userCarts;
			}
		} catch (FileNotFoundException e) {
			System.out.println("FAILED TO FIND SAVED DATA.");
		} catch (IOException e) {
			System.out.println("FAILED TO SAVE DATA.");
		} catch (Exception e) {
			System.out.println("UNEXPECTED ERROR OCCURED.");
		}
		// otherwise return an empty map
		return new HashMap<>();
	}
	
	// write users into the database
	public void writeUsers(Map<String, User> users) {
		try {
			File usersDb = new File(this.USERS_PATH);
			
			// check if file exists and is not empty to delete and update it with new users
			if(usersDb.isFile() && usersDb.length() != 0L) {
				usersDb.delete();
				usersDb.createNewFile();
			}
			
			FileOutputStream fos = new FileOutputStream(usersDb);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(users);
			
			oos.flush();
			oos.close();
		} catch (IOException e) {
			System.out.println("ERROR SAVING DATA");
		} catch (Exception e) {
			System.out.println("UNEXPECTED ERROR OCCURED.");
		}
	}
	
	// write user's carts to the database
	public void writeUserCarts(Map<String, Map<CartItem, Integer>> userCarts) {
		try {
			File cartsDb = new File(this.CARTS_PATH);
			
			// check if file exists and is not empty
			if(cartsDb.isFile() && cartsDb.length() != 0L) {
				cartsDb.delete();
				cartsDb.createNewFile();
			}
			
			FileOutputStream fos = new FileOutputStream(cartsDb);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(userCarts);
			
			oos.flush();
			oos.close();
		} catch (IOException e) {
			System.out.println("ERROR SAVING DATA");
		} catch (Exception e) {
			System.out.println("UNEXPECTED ERROR OCCURED.");
		}
	}
    
	//Testing
	/*public static void main(String[] args) {
        DatabaseManager dbManager = new DatabaseManager();
        
        // creating new users
        Map<String, User> users = new HashMap<>();
        users.put("user1", new User("Karam", "Dawood"));
        users.put("user2", new User("Isabel", "Dawood"));
        
        // creating new cartItems and a new cart for user1 and user2
        Map<String, Map<CartItem, Integer>> userCarts = new HashMap<>();
        Map<CartItem, Integer> cart1 = new HashMap<>();
        cart1.put(new CartItem("img1.jpg", "Item1", 1099, 2));
        cart1.put(new CartItem("img2.jpg", "Item2", 549, 1));
        userCarts.put("user1", cart1);
        
        Map<CartItem, Integer> cart2 = new HashMap<>();
        cart2.put(new CartItem("img3.jpg", "Item3", 1599, 3));
        userCarts.put("user2", cart2);
        
        // writing information about users and their respective carts into the designated files (serialization)
        dbManager.writeUsers(users);
        dbManager.writeUserCarts(userCarts);
        
        // deserialization of the information
        Map<String, User> readUsers = dbManager.getUsers();
        Map<String, Map<CartItem, Integer>> readUserCarts = dbManager.getUserCarts();
        
        // printing out the deserialized information
        System.out.println("Users from file:");
        for (Map.Entry<String, User> entry : readUsers.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        
        System.out.println("\nCarts from file:");
        for (Map.Entry<String, Map<CartItem, Integer>> entry : readUserCarts.entrySet()) {
            System.out.println(entry.getKey() + "'s cart:");
            for (Map.Entry<CartItem, Integer> cartEntry : entry.getValue().entrySet()) {
                System.out.println(cartEntry.getKey() + ": " + cartEntry.getValue());
            }
        }
    }*/

}