package eCommerce.AmazonCartMySql.Service;


import eCommerce.AmazonCartMySql.Entity.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class CartService {

    @Autowired
    ProductDetailsProxy pdp;

    @Autowired
    UserDetailsProxy udp;

    public ArrayList<HashMap<String, Object>> ProductDisplayer(ArrayList<HashMap<String, Object>> cart) {
        ArrayList<HashMap<String, Object>> myCart = new ArrayList<>();
        log.info("{}", cart);
        for(int x=0; x<cart.size(); x++) {
            HashMap<String, Object> product = cart.get(x);
            Object productId = product.get("productId");
            UUID productUUID = UUID.fromString(productId.toString());
            log.info("{} -- this is the uuid", productUUID);
            ProductEntity theProduct = pdp.getByUniqueId(productUUID);
            product.remove("productId");
            product.put("product", theProduct);
            myCart.add(product);
        }
        return myCart;
    }

    public double Price(ArrayList<HashMap<String, Object>> cart) {
        ArrayList<Double> prices = new ArrayList<>();
        for(int x=0; x<cart.size(); x++) {
            HashMap<String, Object> product = cart.get(x);
            Object productId = product.get("productId");
            UUID productUUID = UUID.fromString(String.valueOf(productId));
            ProductEntity theProduct = pdp.getByUniqueId(productUUID);
            prices.add(theProduct.getPrice());
        }
        double subTotal = 0.0;
        for(Double d : prices) {
            subTotal = subTotal + d;
        }
        return subTotal;
    }

    public double SubTotal(ArrayList<HashMap<String, Object>> cart){
        ArrayList<Double> prices = new ArrayList<>();
        for(int x=0; x<cart.size(); x++) {
            HashMap<String, Object> product = cart.get(x);
            Object productId = product.get("productId");
            Object quantity = product.get("quantity");
            UUID productUUID = UUID.fromString(String.valueOf(productId));
            ProductEntity theProduct = pdp.getByUniqueId(productUUID);
            double theQuantity = Integer.parseInt(String.valueOf(quantity));
            double price = theProduct.getPrice();
            double total = theQuantity*price;
            prices.add(total);
        }
        double subTotal = 0.0;
        for(Double d : prices) {
            subTotal = subTotal + d;
        }
        return subTotal;
    }

    public User userFinder(String id) {
        User userById = udp.getUserById(id);
        ArrayList<HashMap<String, Object>> userCart = userById.getCart();
        ArrayList<HashMap<String, Object>> wishList = userById.getWishList();

        ArrayList<HashMap<String, Object>> myCart = ProductDisplayer(userCart);
        ArrayList<HashMap<String, Object>> myWish = ProductDisplayer(wishList);

        double cartSubtotal = Price(udp.getUserById(id).getCart());

        return new User(userById.getFirstname(), userById.getLastname(), userById.getEmail(), userById.getUserId(), myCart, myWish, userById.getCartSize(), userById.getWishlistSize(), cartSubtotal, userById.getDateOfAccountCreation(), userById.getTimeOfAccountCreation());
    }

    public User userShortFinderById(String id) {
        log.info("Getting user with id {} ", id);
        User userId = udp.getShortUserById(id);
        ArrayList<HashMap<String, Object>> cart = userId.getCart();
        double subTotal = SubTotal(cart);
        ArrayList<HashMap<String, Object>> myCart = ProductDisplayer(cart);
        log.info("{}", myCart);
        return new User(userId.getFirstname(), userId.getLastname(), userId.getEmail(), userId.getUserId(), myCart, userId.getWishList(), userId.getCartSize(), userId.getWishlistSize(), subTotal, userId.getDateOfAccountCreation(), userId.getTimeOfAccountCreation());
    }



    public ProductEntity productFinder(UUID id) {
        ProductEntity product = pdp.getByUniqueId(id);
        return product;
    }


}
