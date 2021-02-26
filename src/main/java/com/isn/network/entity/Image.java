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
@Table(name = "isn_image")
public class Image {

	public Image() {
	}

	public Image(long userId, String imageName, String description, String originalFilename, String contentType,
			byte[] compressBytes, String image_url, Timestamp ts) {

		this.userId = userId;
		this.image_name = imageName;
		this.description = description;
		this.name = originalFilename;
		this.type = contentType;
		this.picByte = compressBytes;
		this.imageUrl = image_url;
		this.creationDate = ts;
	}

	public Image(long imageId, long userId, String imageName, String description, String originalFilename,
			String contentType, byte[] compressBytes, String image_url, Timestamp ts) {
		this.id = imageId;
		this.userId = userId;
		this.image_name = imageName;
		this.description = description;
		this.name = originalFilename;
		this.type = contentType;
		this.picByte = compressBytes;
		this.imageUrl = image_url;
		this.creationDate = ts;
	}

	public Image(long imageId, long userId, String imageName, String description, String originalFilename,
			String contentType, byte[] compressBytes, Timestamp ts) {
		this.id = imageId;
		this.userId = userId;
		this.image_name = imageName;
		this.description = description;
		this.name = originalFilename;
		this.type = contentType;
		this.picByte = compressBytes;
		this.creationDate = ts;
	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "image_name")
	private String image_name;

	@Column(name = "description")
	private String description;

	@Column(name = "type")
	private String type;
	// File Name
	@Column(name = "original_file_name")
	private String name;

	@Column(name = "picByte")
	private byte[] picByte;

	@Column(name = "image_url")
	private String imageUrl;

	@Column(name = "creation_date", nullable = true, length = 19)
	private Timestamp creationDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUser_id() {
		return userId;
	}

	public void setUser_id(Long user_id) {
		this.userId = user_id;
	}

	public String getImage_name() {
		return image_name;
	}

	public void setImage_name(String image_name) {
		this.image_name = image_name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getPicByte() {
		return picByte;
	}

	public void setPicByte(byte[] picByte) {
		this.picByte = picByte;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

}
