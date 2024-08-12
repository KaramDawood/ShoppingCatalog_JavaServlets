import java.io.Serializable;

public class User implements Serializable{

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private static final long SerialVersionUID = 100L;

    public User(String email, String password, String firstName, String lastName){
        this.email=email;
        this.password=password;
        this.firstName=firstName;
        this.lastName=lastName;
    }

    public User(String email, String password){
        this.email=email;
        this.password=password;
    }

    public String getEmail(){
        return this.email;
    }

    public String getPassword(){
        return this.password;
    } 

    public String getFirstName(){
        return this.firstName;
    }

    public String getLastName(){
        return this.lastName;
    }

    public void setEmail(String newEmail){
        this.email=newEmail;
    }

    public void setPassword(String newPassword){
        this.password= newPassword;
    }

    public void setFirstName(String firstName){
        this.firstName=firstName;
    }

    public void setLastName(String lastName){
        this.lastName=lastName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
    

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
    
        User otherUser = (User) obj;
    
        if (this.email != null) {
            return this.email.equals(otherUser.getEmail());
        } else {
            return otherUser.getEmail() == null;
        }
    }

    @Override
public int hashCode() {

    if (email != null) {
        return email.hashCode();
    } else {
  
        return 0;
    }
}
  
    // testing 
    /*public static void main(String[] args) {
        
        //creating new useres
        User u1 = new User("dawoodkaram@hotmail.com", "Res1!", "Karam", "Dawood");
        User u2 = new User("kami.daikonene@gmail.com", "Ras1!", "Kami", "Daikoenen");

        // testing overwritten equals method
        System.out.println(u1.equals(u2));

        User u3 = new User("hottyfloppy@gmail.com", "Resi1!", "Karam", "Dawood");
        User u4 = new User("hottyfloppy@gmail.com", "Rasi1!", "Kami", "Daikoenen");

        System.out.println(u3.equals(u4));

        // checking overwritten hashCode method
        System.out.println(u1.hashCode());
        System.out.println(u2.hashCode());
        System.out.println(u3.hashCode());
        System.out.println(u4.hashCode());

    }*/

}