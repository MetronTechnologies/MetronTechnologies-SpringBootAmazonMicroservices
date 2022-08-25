package eCommerce.AmazonUserMySql.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginEntry {

    private String email;
    private String password;

}
