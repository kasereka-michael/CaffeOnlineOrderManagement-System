package com.webgroupEproject.myproject23526.Repository;

import com.webgroupEproject.myproject23526.Model.UserClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<UserClient,Long> {
}
