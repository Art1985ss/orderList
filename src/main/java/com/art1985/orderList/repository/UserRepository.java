package com.art1985.orderList.repository;

import com.art1985.orderList.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLastName(String name);

    Optional<User> findByEmail(String email);
}
