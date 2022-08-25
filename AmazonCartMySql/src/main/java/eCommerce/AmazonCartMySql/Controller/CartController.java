package eCommerce.AmazonCartMySql.Controller;


import eCommerce.AmazonCartMySql.Entity.ProductEntity;
import eCommerce.AmazonCartMySql.Entity.ShortUser;
import eCommerce.AmazonCartMySql.Entity.User;
import eCommerce.AmazonCartMySql.Service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/amazon/cart")
public class CartController {
    @Autowired
    CartService cService;

    @GetMapping("/findUserById/{id}")
    public User findById(@PathVariable String id) {
        return cService.userFinder(id);
    }

    @GetMapping("/findProductById/{id}")
    public ProductEntity findProductById(@PathVariable UUID id) {
        return cService.productFinder(id);
    }

    @GetMapping("/getShortUserById/{userId}")
    public User getShortUserById(@PathVariable String userId) {
        return cService.userShortFinderById(userId);
    }


}
