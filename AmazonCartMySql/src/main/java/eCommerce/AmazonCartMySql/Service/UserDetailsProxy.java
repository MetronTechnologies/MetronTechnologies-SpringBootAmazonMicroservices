package eCommerce.AmazonCartMySql.Service;


import eCommerce.AmazonCartMySql.Entity.ShortUser;
import eCommerce.AmazonCartMySql.Entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "amazon-user", url = "localhost:8083")
public interface UserDetailsProxy {

    @GetMapping("/amazon/users/getUserById/{userId}")
    User getUserById(@PathVariable String userId);

    @GetMapping("/amazon/users/getShortUserById/{userId}")
    User getShortUserById(@PathVariable String userId);

}
