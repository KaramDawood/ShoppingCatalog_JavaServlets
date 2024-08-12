import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.security.InvalidParameterException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CartServlet extends HttpServlet{

    private CartManager cartManager;
    private DatabaseManager db;

    private static final long serialVersionUID = 100L;

    @Override
    public void init(){
        db = new DatabaseManager();
        this.cartManager = new CartManager(db.getUserCarts());
    }

    @Override
    public void destroy(){
        db.writeUserCarts(this.cartManager.getUserCarts());
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession(false);

        if(session == null){
            try {
                response.sendRedirect("/catalog/login.html");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        String requestEmail = (String) session.getAttribute("email");
        if(requestEmail == null){
            session.invalidate();
        try{
            response.sendRedirect("/catalog/login.html");
        }catch (IOException e){
            e.printStackTrace();
        }
        return;    
    }            
            Map<CartItem, Integer> userCart = cartManager.getUserCart(requestEmail);
 
            try{
                PrintWriter out = response.getWriter();
                if(userCart == null || userCart.isEmpty()){
                
                    out.println("<html><body>");
                    out.println("<h1>Your cart is empty</h1>");
                    out.println("<a href='/catalog/catalog.html'>Back to Catalog</a>");
                    out.println("</body></html>");
                
                }else{
                    String cartHtml = CartSummaryHtmlGenerator.getCartSummaryPage(userCart);
                    out.write(cartHtml);

                }
        }catch (IOException e){
            e.printStackTrace();
        }
    }         


    public void doPost(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession(false);

        if(session == null){
            try {
                response.sendRedirect("/catalog/login.html");
                return;
            } catch (IOException e) {
                    e.printStackTrace();
            }
        }

        String sessionEmail = (String) session.getAttribute("email");

        if(sessionEmail == null){
            session.invalidate();
            try {
                response.sendRedirect("/catalog/login.html");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        String imgAddress = request.getParameter("imgAddress");
        String itemName = request.getParameter("itemName");
        int itemPrice = Integer.parseInt(request.getParameter("itemPrice"));

        CartItem cartItem = new CartItem(imgAddress, itemName, itemPrice);
        cartManager.addToCart(sessionEmail, cartItem);

        try {
            response.sendRedirect("/catalog/catalog.html");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}