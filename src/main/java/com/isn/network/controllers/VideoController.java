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
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.isn.network.entity.Video;
import com.isn.network.repository.VideoRepository;
import com.isn.network.storage.StorageFileNotFoundException;
import com.isn.network.storage.StorageService;


/**
 * @author tcts-india
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/video")
@Controller
public class VideoController {

	private final StorageService storageService;

	@Autowired
	VideoRepository videoRepository;

	@Autowired
	public VideoController(StorageService storageService) {
		this.storageService = storageService;
	}

	@GetMapping("/get/all")
	List<Video> all() {
		return videoRepository.findAll();
	}

	@GetMapping("/get/{id}")
	public Optional<Video> byVideoId(@PathVariable long id) {
		return videoRepository.findById(id);
	}
	//Post Video with fileStorage
	@PostMapping("/postVideo")
	public ResponseEntity<String> handleFileUpload(
			@RequestParam("videoFile") MultipartFile file,
			@RequestParam("userId") String userId,
			@RequestParam("imageId") String imageId,
			@RequestParam("videoName") String videoName,
			@RequestParam("description") String description,
			@RequestParam("videourl") String videoUrl,
			RedirectAttributes redirectAttributes) {

		// Read the videoFile params
		// Create a new dir Date(Today)-><userId+VideoID>->mediaName.mp4
		// date(timestamp)+userId+imageID
		String message = "";
		Date date = new Date();
		Timestamp ts = new Timestamp(date.getTime());
		long uuid = Long.parseLong(userId);
		long imgId = Long.parseLong(imageId);
		String original_file_name = file.getOriginalFilename();
		String type = file.getContentType();

		try {
//			storageService.store(file);
//			Date d = new Date();
			//Each day will have new DIR -> 
//			DateFormat sdf = new SimpleDateFormat("HHmmssSSS");
//		       
//			String filePrefixe = sdf.format(d);
//			String name = filePrefixe + storageService.store2(file);
//			   String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
//		                .path("/videos-directory/")
//		                .path(name)
//		                .toUriString();
//			String videoPath = "/hard/Coded/Path";
			Video video = new Video(uuid, imgId, original_file_name, videoName,description, videoUrl, type, ts);
			videoRepository.save(video);

			redirectAttributes.addFlashAttribute("message",
					"You successfully uploaded " + file.getOriginalFilename() + "!");

			message = "Video successfully uploaded " + file.getOriginalFilename() + "! description:"+description;
			return ResponseEntity.status(HttpStatus.OK).body(message);
		} catch (Exception e) {
			message = "FAIL to upload " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
		}
	}
	
	//Post Video to DB
	@PostMapping("/save")
	public ResponseEntity<String> handleFileUploadToDB(
			@RequestParam("videoFile") MultipartFile file,
			@RequestParam("userId") String userId, 
			@RequestParam("imageId") String imageId,
			@RequestParam("videoName") String videoName, 
			@RequestParam("description") String description,
			RedirectAttributes redirectAttributes) {

		// Read the videoFile params
		// Create a new dir Date(Today)-><userId+VideoID>->mediaName.mp4
		// date(timestamp)+userId+imageID
		String message = "";
		Date date = new Date();
		Timestamp ts = new Timestamp(date.getTime());
		long uuid = Long.parseLong(userId);
		long imgId = Long.parseLong(imageId);
		String original_file_name = file.getOriginalFilename();
		String type = file.getContentType();

		try {
			Video vid = new Video(uuid,imgId,description, file.getOriginalFilename(), videoName, file.getContentType(),
					file.getBytes(),ts);
			videoRepository.save(vid);
			message = "You successfully uploaded " + file.getOriginalFilename() + "!"+userId+""+description+"";
			return ResponseEntity.status(HttpStatus.OK).body(message);
		} catch (Exception e) {
			message = "FAIL to upload " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
		}
	}
	@GetMapping("/overlays/{userID}/{imageID}")
	public List<Video> byUserIdImageId(@PathVariable("userID") long userId,@PathVariable("imageID") long imageId) {
		return videoRepository.findByUserIdAndImageId(userId,imageId);
		//Or a customized json object having {"video_id":"video_path"}
	}
	
	

    @GetMapping("/download/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {

        Resource resource = storageService.loadAsResource(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
	
	
    
//    @GetMapping(value = "/videosrc", produces = "video/mp4")
//    @ResponseBody
//    public FileSystemResource videoSource(@RequestParam(value="path", required=true) String path) {
//        return new FileSystemResource(new File(path));
//    }
//	

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
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
