package eCommerce.AmazonProductMongoDB.Service.ProductServiceImplementation;


import eCommerce.AmazonProductMongoDB.Entity.ProductEntity;
import eCommerce.AmazonProductMongoDB.Repositories.ProductRepo;
import eCommerce.AmazonProductMongoDB.Service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ProductServiceImplementation implements ProductService {

    private final ProductRepo pRepo;
    private final MongoTemplate mTemplate;

    @Override
    public ProductEntity creator(ProductEntity pEntity) {
        log.info("Saving new product: {}", pEntity.getName());
        pEntity.setProductID(UUID.randomUUID());
        return pRepo.save(pEntity);
    }

    @Override
    public Collection<ProductEntity> retrieveAll() {
        log.info("Retrieving all products");
        return pRepo.findAll();
    }

    @Override
    public ProductEntity uuidFinder(UUID productID) {
        log.info("Retrieving product by unique id: {}", productID);
        return pRepo.findByProductID(productID);
    }
    @Override
    public ArrayList<ProductEntity> nameFinder(String name) {
        log.info("Retrieving product by name: {} ", name);
        ArrayList<ProductEntity> productEntities = pRepo.findByName(name);
        return productEntities;
    }

    @Override
    public ArrayList<ProductEntity> categoryFinder(String category) {
        return null;
    }

    public List<ProductEntity> fullTextSearch(String searchPhrase) {
        TextCriteria criteria = TextCriteria.forDefaultLanguage().matching(searchPhrase);
        Query query = TextQuery.queryText(criteria).sortByScore();
        List<ProductEntity> productEntities = mTemplate.find(query, ProductEntity.class);
        return productEntities;
    }



}
