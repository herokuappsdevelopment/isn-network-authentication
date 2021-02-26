package com.isn.network.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isn.network.entity.Video;

/**
 * @author tcts-india
 *
 */
public interface VideoRepository extends JpaRepository<Video, Long> {

	List<Video> findByUserIdAndImageId(long userId, long imageId);

}
