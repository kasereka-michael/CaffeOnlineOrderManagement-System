package com.webgroupEproject.myproject23526.Repository;

import com.webgroupEproject.myproject23526.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
