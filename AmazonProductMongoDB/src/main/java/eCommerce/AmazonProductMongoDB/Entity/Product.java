package eCommerce.AmazonProductMongoDB.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Product {

    public String name;

    public String[] category;

    public double price;

    public double rating;

    public String shortDescription;

    public String longDescription;

    public String imageURL;

    public UUID productID;

}
