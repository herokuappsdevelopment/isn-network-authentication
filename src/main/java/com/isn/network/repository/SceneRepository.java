package com.isn.network.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isn.network.entity.Scene;

/**
 * @author tcts-india
 *
 */
public interface SceneRepository extends JpaRepository<Scene, Long> {

	List<Scene> findByUserId(String uid);

}
