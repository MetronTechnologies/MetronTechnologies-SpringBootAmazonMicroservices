package eCommerce.AmazonCartMySql.Entity;

import lombok.*;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartEntity {

    @Id
    @GeneratedValue
    public long id;

    public String userId;

    public String productId;

}
