package ua.nure.st.patterns.labs.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.nure.st.patterns.labs.controller.dto.CreateWithNameDto;
import ua.nure.st.patterns.labs.dao.BrandDao;
import ua.nure.st.patterns.labs.entity.Brand;

import java.util.List;

@RestController
@RequestMapping("/brands")
public class BrandController {

    private final BrandDao brandDao;

    public BrandController(BrandDao brandDao) {
        this.brandDao = brandDao;
    }

    @GetMapping
    public List<Brand> getBrands() {
        return brandDao.getAll();
    }

    @GetMapping("/{id}")
    public Brand getBrand(@PathVariable Long id) {
        return brandDao.getById(id);
    }

    @PostMapping
    public boolean addBrand(@RequestBody CreateWithNameDto dto) {
        return brandDao.save(dto.name());
    }

    @PutMapping("/{id}")
    public Brand updateBrand(@RequestBody Brand brand) {
        brandDao.update(brand);
        return brand;
    }

    @DeleteMapping("/{id}")
    public void deleteBrand(@PathVariable Long id) {
        brandDao.delete(id);
    }

}
