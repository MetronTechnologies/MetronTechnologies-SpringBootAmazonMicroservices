package eCommerce.AmazonUserMySql.Entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table(name = "amazonusers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    @Size(max = 20)
    @NotNull
    private String firstname;

    @Column(nullable = false)
    @NotNull
    @Size(max = 20)
    private String lastname;

    @Email
    @NotEmpty(message = "Email is required")
    @Column(nullable = false, unique = true)
    @Size(max = 45)
    @NotNull
    private String email;

    @Column(unique = true)
    @NotNull
    private String userId;

    @Column(nullable = false)
    @NotNull
    private String password;

    @Column(columnDefinition = "LONGBLOB")
    private ArrayList<HashMap<String, Object>> cart = new ArrayList<>();

    @Column(columnDefinition = "LONGBLOB")
    private ArrayList<HashMap<String, Object>> wishList = new ArrayList<>();

    private long cartSize;

    private long wishlistSize;

    private String dateOfAccountCreation;

    private String timeOfAccountCreation;

}
