package eCommerce.AmazonCartMySql.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortUser {

    private String firstname;
    private String lastname;
    private String email;
    private String userId;
    private ArrayList<HashMap<String, Object>> cart = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> wishList = new ArrayList<>();
    private long cartSize;
    private long wishlistSize;
    private String dateOfAccountCreation;
    private String timeOfAccountCreation;

}
