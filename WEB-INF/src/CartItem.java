import java.io.Serializable;

public class CartItem implements Serializable{

    private String imgAdress;
    private String name;
    private int price;

    private static final long serialVersionUID = 1000L;

    public CartItem(String imgAdress, String name, int price){
        this.imgAdress=imgAdress;
        this.name=name;
        this.price=price;
    }

    public String getImgAddress(){
        return this.imgAdress;
    }

    public String getName(){
        return this.name;
    }

    public int getPrice(){
        return this.price;
    }

    public void setImgAdress(String newImgAdress){
        this.imgAdress=newImgAdress;
    }

    public void setName(String newName){
        this.name=newName;
    }

    public void setPrice(int newPrice){
        this.price=newPrice;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }

        if(obj == null || obj.getClass() != this.getClass()){
            return false;
        }

        CartItem cartItem = (CartItem) obj;

        if(this.name != null){
            return this.name.equals(cartItem.getName());
        }else{
            return cartItem.getName() == null;
        }
    }

    @Override
    public int hashCode(){
        if(name != null){
            return this.name.hashCode();
        }else{
            return 0;
        }
    }
}