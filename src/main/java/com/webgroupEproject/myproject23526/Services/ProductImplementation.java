package com.webgroupEproject.myproject23526.Services;

import com.webgroupEproject.myproject23526.Model.RecServices;
import com.webgroupEproject.myproject23526.Model.UserClient;
import com.webgroupEproject.myproject23526.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductImplementation implements ProductService{

    @Autowired
    private ProductRepository productRepository;
    @Override
    public void saveProduct(RecServices recServices) {

        productRepository.save(recServices);
    }

    @Override
    public List<RecServices> getALLProduct() {
        return productRepository.findAll();
    }

    @Override
    public RecServices getProductById(long id) {
        Optional<RecServices> optional = productRepository.findById(id);
        RecServices recServices = null;

        if(optional.isPresent()) {
            recServices = optional.get();
        } else {
            throw new RuntimeException("Non such Product!!! with id " + id);
        }
        return recServices;
    }


    @Override
    public void deleteProductByid(long id) {
        this.productRepository.deleteById(id);
    }
}
