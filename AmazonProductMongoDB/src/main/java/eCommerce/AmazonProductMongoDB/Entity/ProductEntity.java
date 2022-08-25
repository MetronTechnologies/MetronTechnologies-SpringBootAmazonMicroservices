package eCommerce.AmazonProductMongoDB.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;


import java.math.BigInteger;
import java.util.Date;
import java.util.UUID;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductEntity {

    @Id
    public BigInteger id;

    @TextIndexed(weight = 3)
    public String name;

    @TextIndexed(weight = 1)
    public String[] category;

    public double price;

    public double rating;

    @TextIndexed(weight = 2)
    public String[] specification;

    public String imageURL;

    @Indexed(unique=true)
    public UUID productID;


}
