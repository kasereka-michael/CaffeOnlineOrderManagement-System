package com.webgroupEproject.myproject23526.Repository;

import com.webgroupEproject.myproject23526.Model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    @Query("SELECT new Document(d.id, d.name, d.size) FROM Document d ORDER by d.uplaodtime DESC")
    List<Document> findAll();
}
