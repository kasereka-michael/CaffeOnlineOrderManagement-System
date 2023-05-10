package com.webgroupEproject.myproject23526.Repository;


import com.webgroupEproject.myproject23526.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
