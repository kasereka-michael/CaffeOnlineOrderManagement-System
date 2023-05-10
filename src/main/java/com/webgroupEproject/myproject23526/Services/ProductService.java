package com.webgroupEproject.myproject23526.Services;

import com.webgroupEproject.myproject23526.Model.RecServices;
import com.webgroupEproject.myproject23526.Model.UserClient;

import java.util.List;

public interface ProductService {

    void saveProduct(RecServices recServices);
    List<RecServices> getALLProduct();

    RecServices getProductById(long id);

    void deleteProductByid(long id);
}
