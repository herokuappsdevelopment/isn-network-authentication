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
@Table(name = "isn_markers")
public class Marker {

	public Marker() {
	}

	public Marker(long uuid, long imguuid, String originalFilename, String contentType, String marker_url,
			Timestamp ts) {
		this.userId = uuid;
		this.imageId = imguuid;
		this.fileName = originalFilename;
		this.type = contentType;
		this.markerUrl = marker_url;
		this.creationDate = ts;
	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "image_id")
	private Long imageId;

	@Column(name = "type")
	private String type;

	// File Name
	@Column(name = "original_file_name")
	private String fileName;

	@Column(name = "marker_url")
	private String markerUrl;

	@Column(name = "creation_date", nullable = true, length = 19)
	private Timestamp creationDate;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getMarkerUrl() {
		return markerUrl;
	}

	public void setMarkerUrl(String markerUrl) {
		this.markerUrl = markerUrl;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public String toString() {
		return "MarkerModal [id=" + id + ", userId=" + userId + ", imageId=" + imageId + ", type=" + type
				+ ", fileName=" + fileName + ", creationDate=" + creationDate + "]";
	}

}
