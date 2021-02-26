package com.isn.network.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author tcts-india
 *
 */
@Entity
@Table(name = "isn_videos")
public class Video {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "image_id")
	private Long imageId;

	@Column(name = "original_file_name")
	private String name;

	@Column(name = "video_name")
	private String video_name;

	@Column(name = "description")
	private String description;

	@Column(name = "video_path")
	private String video_path;

	@Column(name = "type")
	private String type;

	@Column(name = "creation_date", nullable = true, length = 19)
	private Timestamp creationDate;

	public Video() {
	}

	public Video(long uuid, long imgId, String original_file_name, String videoName, String description,
			String videoPath, String type, Timestamp timestamp) {
		this.userId = uuid;
		this.imageId = imgId;
		this.name = original_file_name;
		this.video_name = videoName;
		this.description = description;
		this.video_path = videoPath;
		this.type = type;
		this.creationDate = timestamp;

	}

	public Video(long uuid, long imgId, String description2, String originalFilename, String videoName,
			String contentType, byte[] compressBytes, Timestamp ts) {
		this.userId = uuid;
		this.imageId = imgId;
		this.description = description2;
		this.name = originalFilename;
		this.video_name = videoName;
		this.type = contentType;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVideo_name() {
		return video_name;
	}

	public void setVideo_name(String video_name) {
		this.video_name = video_name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVideo_path() {
		return video_path;
	}

	public void setVideo_path(String video_path) {
		this.video_path = video_path;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public String toString() {
		return "VideoModel [id=" + id + ", userId=" + userId + ", imageId=" + imageId + ", name=" + name
				+ ", video_name=" + video_name + ", description=" + description + ", video_path=" + video_path
				+ ", type=" + type + ", creationDate=" + creationDate + "]";
	}

}
