package com.isn.network.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.isn.network.entity.Image;
import com.isn.network.repository.ImageRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/image")
public class ImageUploadController {

	@Autowired
	ImageRepository imageRepository;

	@GetMapping("get/all")
	public List<Image> getAll() {
		return imageRepository.findAll();
	}

	// Doesn't return an ID for correct search
	@PostMapping("/postImage")
	public ResponseEntity<String> handleFileUpload(

			@RequestParam("imageFile") MultipartFile file, @RequestParam("userId") String userId,
			@RequestParam("imanename") String imageName, @RequestParam("description") String description,
			@RequestParam("imageurl") String imageurl) {
		String message = "";
		Date date = new Date();
		Timestamp ts = new Timestamp(date.getTime());
		long uuid = Long.parseLong(userId);

		try {
			Image img = new Image(uuid, imageName, description, file.getOriginalFilename(), file.getContentType(),
					compressBytes(file.getBytes()), imageurl, ts);
			imageRepository.save(img);
			// Return the saved Image in response findbyImageName()
			// String fileName = 0053153215image.jpeg
			// findbyImageName(fileName) will stay unique in that context
			// Store the info for usage

			message = "You successfully uploaded " + file.getOriginalFilename() + "!" + userId + "" + imageName + ""
					+ description + "";
			return ResponseEntity.status(HttpStatus.OK).body(message);
		} catch (Exception e) {
			message = "FAIL to upload " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
		}
	}

	@PostMapping("/save")
	public Image saveImage(

			@RequestParam("imageFile") MultipartFile file, @RequestParam("userId") String userId,
			@RequestParam("imanename") String imageName, @RequestParam("description") String description,
			@RequestParam("imageurl") String imageurl) {
		Date date = new Date();
		Timestamp ts = new Timestamp(date.getTime());
		long uuid = Long.parseLong(userId);
		try {
			Image img = new Image(uuid, imageName, description, file.getOriginalFilename(), file.getContentType(),
					compressBytes(file.getBytes()), imageurl, ts);
			imageRepository.save(img);
			return img;
		} catch (Exception e) {
			Image emptyImage = new Image();
			return emptyImage;
		}
	}

	// Exception if no value is found
	@GetMapping(path = { "/get/{imageName}" })
	public Image getImage(@PathVariable("imageName") String imageName) throws IOException {
		Image img = null;
		try {
			final Optional<Image> retrievedImage = imageRepository.findByName(imageName);
			if (retrievedImage != null) {
				img = new Image(retrievedImage.get().getId(), retrievedImage.get().getUser_id(),
						retrievedImage.get().getImage_name(), retrievedImage.get().getDescription(),
						retrievedImage.get().getName(), retrievedImage.get().getType(),
						decompressBytes(retrievedImage.get().getPicByte()), retrievedImage.get().getImageUrl(),
						retrievedImage.get().getCreationDate());
			}

			return img;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return img;
	}

	// @GetMapping(path = { "/get/{userID}/{imageName}"})
	// public Image getImageByUserId(@PathVariable String userID,@PathVariable
	// String imageName) throws IOException {
	// Image img = null;
	// long userId = Long.parseLong(userID);
	// try {
	// //Will return more than 1 should be improved!
	// final Optional<Image> retrievedImage =
	// imageRepository.findByUserIdAndName(userId,imageName);
	// if (retrievedImage != null) {
	// img = new Image(
	// retrievedImage.get().getId(),retrievedImage.get().getUser_id(),
	// retrievedImage.get().getImage_name(),retrievedImage.get().getDescription(),
	// retrievedImage.get().getName(), retrievedImage.get().getType(),
	// decompressBytes(retrievedImage.get().getPicByte()),retrievedImage.get().getCreationDate());
	// }
	//
	// return img;
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return img;
	// }

	// To Test -> Ambiguous handler methods because of previous method
	@GetMapping(path = { "/get/{userID}/{Id}" })
	public Image getImageByUserIdAndId(@PathVariable String userID, @PathVariable String Id) throws IOException {
		Image img = null;
		long userId = Long.parseLong(userID);
		long id = Long.parseLong(Id);
		try {
			// Will return more than 1 should be improved!
			final Optional<Image> retrievedImage = imageRepository.findByUserIdAndId(userId, id);
			if (retrievedImage != null) {
				img = new Image(retrievedImage.get().getId(), retrievedImage.get().getUser_id(),
						retrievedImage.get().getImage_name(), retrievedImage.get().getDescription(),
						retrievedImage.get().getName(), retrievedImage.get().getType(),
						decompressBytes(retrievedImage.get().getPicByte()), retrievedImage.get().getCreationDate());
			}

			return img;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return img;
	}

	@GetMapping("/get-text")
	public @ResponseBody String getText(@RequestParam(name = "name") String param) {
		return "Hello world this is my simple param:" + param;
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
