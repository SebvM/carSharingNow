package com.csn.carSharingNow.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import 	com.csn.carSharingNow.models.User;

public interface UserRepository extends JpaRepository < User, Long > {
		User findByUsername(String username);
		User findByEmail(String email);
}
