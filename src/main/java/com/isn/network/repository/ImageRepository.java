package com.isn.network.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isn.network.entity.Image;

/**
 * @author tcts-india
 *
 */
public interface ImageRepository extends JpaRepository<Image, Long> {

	Optional<Image> findByName(String name);

	// native query
	// @Query(value = "SELECT * FROM USERS WHERE EMAIL_ADDRESS = ?1", nativeQuery =
	// true)
	// User findByEmailAddress(String emailAddress);
	// Or Image findOrderByCreationDateDesc()
	// @Query("select img from Image img order by img.creation_date desc ")
	// Image findBylastUploadDate();
	// last uploaded image, query should be with userId
	List<Image> findByOrderByCreationDateDesc();

	List<Image> findByUserId(Long user_id);

	Optional<Image> findByUserIdAndName(long userId, String imageName);

	Optional<Image> findByUserIdAndId(long userId, long imageId);

}