package eCommerce.AmazonCartMySql.Entity;

import lombok.Data;

import java.util.ArrayList;

@Data
public class CartDetails {

    private String userid;

    private ArrayList<ProductEntity> list;

}
