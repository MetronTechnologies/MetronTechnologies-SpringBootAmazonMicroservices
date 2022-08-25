package eCommerce.AmazonUserMySql.Services;


import eCommerce.AmazonUserMySql.Entity.*;
import eCommerce.AmazonUserMySql.Exceptions.UserException;
import eCommerce.AmazonUserMySql.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.time.Month;
import java.time.ZonedDateTime;
import java.util.*;

import static java.util.Collections.*;

@AllArgsConstructor
@Service
@Transactional
@Slf4j
public class UserService {

    private final UserRepository uRepo;
    private final PasswordEncoder passwordEncoder;

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private boolean passwordMatcher(String password, String encodedPassword) {
        return passwordEncoder.matches(password, encodedPassword);
    }

    private String GetDate() {
        ZonedDateTime now = ZonedDateTime.now();
        int day = now.getDayOfMonth();
        Month month = now.getMonth();
        int year = now.getYear();
        return day + "/" + month + "/" + year;
    }

    private String GetTime() {
        ZonedDateTime now = ZonedDateTime.now();
        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getSecond();
        return hour + ":" + minute + ":" + second;
    }

    public UserEntity creator(Register register) {
        UserEntity uEntity = new UserEntity();
        log.info("Saving user with name {}, {}", register.getLastname(), register.getFirstname());
        uEntity.setFirstname(register.getFirstname());
        uEntity.setLastname(register.getLastname());
        uEntity.setEmail(register.getEmail());
        uEntity.setPassword(encodePassword(register.getPassword()));
        UUID id = UUID.randomUUID();
        uEntity.setUserId(id.toString());
        uEntity.setDateOfAccountCreation(GetDate());
        uEntity.setTimeOfAccountCreation(GetTime());
        return uRepo.save(uEntity);
    }

    public User login(LoginEntry lEntity) {
        String email = lEntity.getEmail();
        UserEntity userEmail = uRepo.findByEmail(email).orElseThrow(() -> new UserException("User Not Found with email - " + email));
        String rawPassword = lEntity.getPassword();
        String encodedPassword = userEmail.getPassword();
        ArrayList<HashMap<String, Object>> cart = userEmail.getCart();
        log.info("Getting user with email {} and password {}", email, rawPassword);
        if((Objects.equals(userEmail.getEmail(), email)) && (passwordMatcher(rawPassword, encodedPassword))) {
            return new User(userEmail.getFirstname(), userEmail.getLastname(), userEmail.getEmail(), userEmail.getUserId(), userEmail.getCart(), userEmail.getWishList(), userEmail.getCartSize(), userEmail.getWishlistSize(), userEmail.getDateOfAccountCreation(), userEmail.getTimeOfAccountCreation());
        } else {
            return null;
        }
    }

    public ArrayList<HashMap<String, Object>> ProductAdder(String userId, String productId, String cartType) {
        UserEntity ID = uRepo.findByUserId(userId).orElseThrow(() -> new UserException("User Not Found with id - " + userId));
        if (Objects.equals(cartType, "cart")) {
            ArrayList<HashMap<String, Object>> userCart = ID.getCart();
            int cartSize = userCart.size();
            if (cartSize < 1) {
                String time = GetTime();
                String date = GetDate();
                HashMap<String, Object> hMap = new HashMap<>();
                hMap.put("productId", productId);
                hMap.put("dateAdded", date);
                hMap.put("timeAdded", time);
                hMap.put("index", cartSize);
                userCart.add(hMap);
            } else {
                Object index = userCart.get(cartSize - 1).get("index");
                int cartNumber = Integer.parseInt(String.valueOf(index)) + 1;
                String time = GetTime();
                String date = GetDate();
                HashMap<String, Object> hMap = new HashMap<>();
                hMap.put("productId", productId);
                hMap.put("dateAdded", date);
                hMap.put("timeAdded", time);
                hMap.put("index", cartNumber);
                userCart.add(hMap);
            }
            return userCart;
        } else {
            ArrayList<HashMap<String, Object>> userWish = ID.getWishList();
            int wishSize = userWish.size();
            if (wishSize < 1) {
                String time = GetTime();
                String date = GetDate();
                HashMap<String, Object> hMap = new HashMap<>();
                hMap.put("productId", productId);
                hMap.put("dateAdded", date);
                hMap.put("timeAdded", time);
                hMap.put("index", wishSize);
                userWish.add(hMap);
            } else {
                for (HashMap<String, Object> value : userWish) {
                    if(value.containsValue(productId)) {
                        return userWish;
                    }
                }
                HashMap<String, Object> value = new HashMap<>();
                Object index = userWish.get(wishSize - 1).get("index");
                int wishNumber = Integer.parseInt(String.valueOf(index)) + 1;
                String time = GetTime();
                String date = GetDate();
                value.put("productId", productId);
                value.put("dateAdded", date);
                value.put("timeAdded", time);
                value.put("index", wishNumber);
                log.info("{}", value);
                userWish.add(value);
            }
            return userWish;
        }
    }

    public User userFinderById(String id) {
        log.info("Getting user with id {} ", id);
        UserEntity userId = uRepo.findByUserId(id).orElseThrow(() -> new UserException("User Not Found with id - " + id));
        return new User(userId.getFirstname(), userId.getLastname(), userId.getEmail(), userId.getUserId(), userId.getCart(), userId.getWishList(), userId.getCartSize(), userId.getWishlistSize(), userId.getDateOfAccountCreation(), userId.getTimeOfAccountCreation());
    }

    public User shortUserFinder(String id) {
        log.info("Getting user with id {} ", id);
        UserEntity userId = uRepo.findByUserId(id).orElseThrow(() -> new UserException("User Not Found with id - " + id));
        ArrayList<HashMap<String, Object>> userCart = userId.getCart();
        ArrayList<String> newCart = new ArrayList<>();
        ArrayList<HashMap<String, Object>> latestCart = new ArrayList<>();
        for(HashMap<String, Object> s : userCart) {
            newCart.add(String.valueOf(s.get("productId")));
        }
        Set<String> productFrequency = new HashSet<>();
        for(String s : newCart) {
            HashMap<String, Object> latestMap = new HashMap<>();
            if(productFrequency.add(s)) {
                int frequency = frequency(newCart, s);
                latestMap.put("quantity", frequency);
                latestMap.put("productId", s);
                latestCart.add(latestMap);
            }
        }
        return new User(userId.getFirstname(), userId.getLastname(), userId.getEmail(), userId.getUserId(), latestCart, userId.getWishList(), userId.getCartSize(), userId.getWishlistSize(), userId.getDateOfAccountCreation(), userId.getTimeOfAccountCreation());
    }

    public Collection<UserEntity> retrieveAll() {
        log.info("Retrieving all users");
        return uRepo.findAll();
    }

    public UserEntity addToCart(String cart, String userId, String productId, String quantity) {
        log.info("{}", quantity);
        UserEntity ID = uRepo.findByUserId(userId).orElseThrow(() -> new UserException("User Not Found with id - " + userId));
        for (int x = 0; x < Integer.parseInt(quantity); x++) {
            ArrayList<HashMap<String, Object>> userCart = ProductAdder(userId, productId, cart);
            uRepo.save(
                    new UserEntity(ID.getId(), ID.getFirstname(), ID.getLastname(), ID.getEmail(), ID.getUserId(), ID.getPassword(), userCart, ID.getWishList(), userCart.size(), ID.getWishlistSize(), ID.getDateOfAccountCreation(), ID.getTimeOfAccountCreation())
            );
        }
        return ID;
    }


    public UserEntity removeFromCart(String userId, String index) {
        UserEntity ID = uRepo.findByUserId(userId).orElseThrow(() -> new UserException("User Not Found with id - " + userId));
        ArrayList<HashMap<String, Object>> userCart = ID.getCart();
        Iterator<HashMap<String, Object>> cart = userCart.iterator();
        while (cart.hasNext()) {
            HashMap<String, Object> value = cart.next();
            String productIndex = String.valueOf(value.get("index"));
            if (Objects.equals(productIndex, index)) {
                cart.remove();
            }
            uRepo.save(
                    new UserEntity(ID.getId(), ID.getFirstname(), ID.getLastname(), ID.getEmail(), ID.getUserId(), ID.getPassword(), userCart, ID.getWishList(), userCart.size(), ID.getWishlistSize(), ID.getDateOfAccountCreation(), ID.getTimeOfAccountCreation())
            );
        }
        return ID;
    }

    public UserEntity addToWishList(String cart, String userId, String productId) {
        UserEntity ID = uRepo.findByUserId(userId).orElseThrow(() -> new UserException("User Not Found with id - " + userId));
        ArrayList<HashMap<String, Object>> userWishList = ProductAdder(userId, productId, cart);
        uRepo.save(
                new UserEntity(ID.getId(), ID.getFirstname(), ID.getLastname(), ID.getEmail(), ID.getUserId(), ID.getPassword(), ID.getCart(), userWishList, ID.getCartSize(), userWishList.size(), ID.getDateOfAccountCreation(), ID.getTimeOfAccountCreation())
        );
        return ID;
    }

    public UserEntity removeFromWishList(String userId, String index) {
        UserEntity ID = uRepo.findByUserId(userId).orElseThrow(() -> new UserException("User Not Found with id - " + userId));
        ArrayList<HashMap<String, Object>> userCart = ID.getWishList();
        Iterator<HashMap<String, Object>> cart = userCart.iterator();
        while (cart.hasNext()) {
            HashMap<String, Object> value = cart.next();
            String productIndex = String.valueOf(value.get("index"));
            if (Objects.equals(productIndex, index)) {
                cart.remove();
            }
            uRepo.save(
                    new UserEntity(ID.getId(), ID.getFirstname(), ID.getLastname(), ID.getEmail(), ID.getUserId(), ID.getPassword(), ID.getCart(), userCart, ID.getCartSize(), userCart.size(), ID.getDateOfAccountCreation(), ID.getTimeOfAccountCreation())
            );
        }
        return ID;
    }


}
