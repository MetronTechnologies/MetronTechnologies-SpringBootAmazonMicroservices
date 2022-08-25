package eCommerce.AmazonCartMySql.Entity;

import lombok.Data;

import java.math.BigInteger;
import java.util.UUID;

@Data

public class ProductEntity {

    public BigInteger id;
    public String name;
    public String[] category;
    public double price;
    public double rating;
    public String[] specification;
    public String imageURL;
    public UUID productID;

}
