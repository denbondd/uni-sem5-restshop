package ua.nure.st.patterns.labs.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.nure.st.patterns.labs.controller.dto.CreateProductDto;
import ua.nure.st.patterns.labs.dao.ProductDao;
import ua.nure.st.patterns.labs.entity.Product;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductDao productDao;

    public ProductController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping
    public List<Product> getProducts() {
        return productDao.getAll();
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable String id) {
        return productDao.getById(id);
    }

    @GetMapping("/name/{name}")
    public List<Product> getProductsByName(@PathVariable String name) {
        return productDao.getAllByName(name);
    }

    @GetMapping("/brand/{brandId}")
    public List<Product> getProductsByBrandId(@PathVariable String brandId) {
        return productDao.getAllByBrandId(brandId);
    }

    @GetMapping("/category/{categoryId}")
    public List<Product> getProductsByCategoryId(@PathVariable String categoryId) {
        return productDao.getAllByCategoryId(categoryId);
    }

    @PostMapping
    public boolean addProduct(@RequestBody CreateProductDto<String> dto) {
        return productDao.save(dto.name(), dto.price(), dto.brandId(), dto.categoryId());
    }

    @PutMapping("/{id}")
    public Product updateProduct(@RequestBody Product product) {
        productDao.update(product);
        return product;
    }

    @PutMapping("/{id}/undo")
    public ResponseEntity<String> undo(@PathVariable String id) {
        boolean res = productDao.undo(id);
        if (res) {
            return new ResponseEntity<>("Product was restored", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    "Product was not restored. Product history is empty or product no longer exists in database",
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id) {
        productDao.delete(id);
    }
}
