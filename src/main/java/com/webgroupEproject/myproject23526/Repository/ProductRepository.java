package com.webgroupEproject.myproject23526.Repository;

import com.webgroupEproject.myproject23526.Model.RecServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface ProductRepository extends JpaRepository<RecServices, Long> {

}
