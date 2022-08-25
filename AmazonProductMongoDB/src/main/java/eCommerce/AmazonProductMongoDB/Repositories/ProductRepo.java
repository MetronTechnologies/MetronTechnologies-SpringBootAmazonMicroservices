package eCommerce.AmazonProductMongoDB.Repositories;



import eCommerce.AmazonProductMongoDB.Entity.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepo extends MongoRepository<ProductEntity, Long> {

    ProductEntity findByProductID(UUID productID);
    ArrayList<ProductEntity> findByName(String name);
    ArrayList<ProductEntity> findByCategory(String category);

}
