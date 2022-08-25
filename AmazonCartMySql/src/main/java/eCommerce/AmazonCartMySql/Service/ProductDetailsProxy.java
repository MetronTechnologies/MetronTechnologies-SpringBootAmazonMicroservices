package eCommerce.AmazonCartMySql.Service;

import eCommerce.AmazonCartMySql.Entity.ProductEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.UUID;


@FeignClient(name = "product-details", url = "localhost:8082")
public interface ProductDetailsProxy {

    @GetMapping("/amazon/products/getByProductId/{productId}")
    ProductEntity getByUniqueId(@PathVariable("productId") UUID productId);

}
