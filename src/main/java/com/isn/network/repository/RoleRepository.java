package com.isn.network.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isn.network.entity.ERole;
import com.isn.network.entity.Role;


/**
 * @author tcts-india
 *
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);
}
