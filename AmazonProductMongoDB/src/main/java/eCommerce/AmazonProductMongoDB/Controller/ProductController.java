package eCommerce.AmazonProductMongoDB.Controller;

import eCommerce.AmazonProductMongoDB.Entity.ProductEntity;
import eCommerce.AmazonProductMongoDB.Entity.Response;
import eCommerce.AmazonProductMongoDB.Service.ProductServiceImplementation.ProductServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.Map.*;
import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/amazon/products")
public class ProductController {

    public final ProductServiceImplementation psi;

    @PostMapping("/save")
    public ResponseEntity<Response> saveProduct(@RequestBody ProductEntity pEntity) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("Product", psi.creator(pEntity)))
                        .message("Product Created")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }

    @GetMapping("/list")
    public ResponseEntity<Response> listProducts(){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("Products", psi.retrieveAll()))
                        .message("Products Retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

//    @GetMapping("/get/{productId}")
//    public ResponseEntity<Response> getByUniqueId(@PathVariable("productId") UUID productId){
//        return ResponseEntity.ok(
//                Response.builder()
//                        .timeStamp(now())
//                        .data(of("Product", psi.finder(productId)))
//                        .message("Product retrieved")
//                        .status(OK)
//                        .statusCode(OK.value())
//                        .build()
//        );
//    }


    @GetMapping("/getByProductId/{productId}")
    public ProductEntity getByUniqueId(@PathVariable("productId") UUID productId) {
        return psi.uuidFinder(productId);
    }


    @GetMapping("/getByName/{name}")
    public ArrayList<ProductEntity> getByName(@PathVariable("name") String name) {
        String trimmedName = name.trim();
        String productName = trimmedName.toLowerCase();
        return psi.nameFinder(productName);
    }

    @GetMapping("/getByText/{text}")
    public List<ProductEntity> getByText(@PathVariable("text") String text) {
        return psi.fullTextSearch(text);
    }



}
