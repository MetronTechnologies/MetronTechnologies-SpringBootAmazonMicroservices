package eCommerce.AmazonUserMySql.Controller;


import eCommerce.AmazonUserMySql.Entity.*;
import eCommerce.AmazonUserMySql.Services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static java.util.Map.*;
import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;


@RestController
@RequestMapping("/amazon/users")
@AllArgsConstructor
public class UserController {

    private final UserService us;

    @PostMapping("/saveUserDetails")
    public ResponseEntity<Response> saveUserDetails(@RequestBody Register reg) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(
                                of("User", us.creator(reg))
                        )
                        .message("User Created")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<Response> getAllUsers() {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("Users", us.retrieveAll()))
                        .message("User Created")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @PutMapping(value = "/addToCart/{cart}/{userId}/{productId}/{quantity}", produces = {"application/json"})
    public @ResponseBody UserEntity addToCart (@PathVariable("cart") String cart, @PathVariable("userId") String userId, @PathVariable("productId") String productId, @PathVariable("quantity") String quantity) {
        return us.addToCart(cart, userId, productId, quantity);
    }

    @PutMapping(value = "/addToWishList/{wishlist}/{userId}/{productId}", produces = {"application/json"})
    public @ResponseBody UserEntity addToWishList (@PathVariable("wishlist") String cart, @PathVariable("userId") String userId, @PathVariable("productId") String productId) {
        return us.addToWishList(cart, userId, productId);
    }

    @PutMapping(value = "/removeFromCart/{userId}/{index}", produces = {"application/json"})
    public @ResponseBody UserEntity removeFromCart(@PathVariable("userId") String userId, @PathVariable("index") String index) {
        return us.removeFromCart(userId, index);
    }

    @PutMapping(value = "/removeFromWishList/{userId}/{index}", produces = {"application/json"})
    public @ResponseBody UserEntity removeFromWishList(@PathVariable("userId") String userId, @PathVariable("index") String index) {
        return us.removeFromWishList(userId, index);
    }

    @PostMapping("/login")
    public User loginUser (@RequestBody LoginEntry lEntry) {
        return us.login(lEntry);
    }

    @GetMapping("/getUserById/{userId}")
    public User getUserById(@PathVariable String userId) {
        return us.userFinderById(userId);
    }

    @GetMapping("/getShortUserById/{userId}")
    public User getShortUserById(@PathVariable String userId) {
        return us.shortUserFinder(userId);
    }


}
