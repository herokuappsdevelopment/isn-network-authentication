package com.isn.network.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.isn.network.entity.Marker;
import com.isn.network.repository.MarkerRepository;

/**
 * @author tcts-india
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/marker")
public class MarkerController {

	@Autowired
	MarkerRepository markerRepository;

	@GetMapping(value = "/get-file", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public @ResponseBody byte[] getFile() throws IOException {
		InputStream in = getClass().getResourceAsStream("/markers/1.patt");
		return IOUtils.toByteArray(in);

	}

	@PostMapping("/save")
	public ResponseEntity<String> handleMarkerUpload(

			@RequestParam("markerFile") MultipartFile file, @RequestParam("userId") String userId,
			@RequestParam("imageId") String imageId, @RequestParam("markerUrl") String marker_url) {
		String message = "";
		Date date = new Date();
		Timestamp ts = new Timestamp(date.getTime());
		long uuid = Long.parseLong(userId);
		long imguuid = Long.parseLong(imageId);

		try {
			Marker marker = new Marker(uuid, imguuid, file.getOriginalFilename(), file.getContentType(), marker_url,
					ts);
			markerRepository.save(marker);
			// Return the saved Image in response findbyImageName()
			// String fileName = 0053153215image.jpeg
			// findbyImageName(fileName) will stay unique in that context
			// Store the info for usage

			message = "You successfully uploaded marker file " + file.getOriginalFilename() + "!" + userId + ""
					+ file.getOriginalFilename() + "";
			return ResponseEntity.status(HttpStatus.OK).body(message);
		} catch (Exception e) {
			message = "FAIL to upload " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
		}
	}

	// compress the image bytes before storing it in the database
	public static byte[] compressBytes(byte[] data) {
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		deflater.finish();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		try {
			outputStream.close();
		} catch (IOException e) {
		}
		System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
		return outputStream.toByteArray();
	}

	// uncompress the image bytes before returning it to the angular application
	public static byte[] decompressBytes(byte[] data) {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		try {
			while (!inflater.finished()) {
				int count = inflater.inflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			outputStream.close();
		} catch (IOException ioe) {
		} catch (DataFormatException e) {
		}
		return outputStream.toByteArray();
	}

}
