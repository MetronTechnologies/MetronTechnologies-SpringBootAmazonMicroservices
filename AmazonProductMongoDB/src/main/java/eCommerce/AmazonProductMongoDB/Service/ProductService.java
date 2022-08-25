package eCommerce.AmazonProductMongoDB.Service;




import eCommerce.AmazonProductMongoDB.Entity.ProductEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public interface ProductService {

    ProductEntity creator(ProductEntity pEntity);
    Collection<ProductEntity> retrieveAll ();
    ProductEntity uuidFinder (UUID productID);
    ArrayList<ProductEntity> nameFinder(String name);
    ArrayList<ProductEntity> categoryFinder(String category);

}
