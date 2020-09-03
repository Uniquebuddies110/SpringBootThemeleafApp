package com.ajay.api.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ajay.api.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
