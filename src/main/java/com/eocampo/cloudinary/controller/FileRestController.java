package com.eocampo.cloudinary.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eocampo.cloudinary.services.IUploadFileService;

/*
 * @author Efrén Ocampo
 * Rest Controller that consumes api to interact with cloudinary repository
 */

@CrossOrigin(origins = {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/file")
public class FileRestController {
	
	@Autowired
	private IUploadFileService uploadFileService;
	
	
	@PostMapping("/upload")
	public ResponseEntity<?> upload(@RequestParam("image") MultipartFile image){
		
		
		Map<String, Object> response = new HashMap<String, Object>();
		
		
		if (!image.isEmpty()) {
			
			String fileName = null;
			try {
				fileName = uploadFileService.upload(image);
			} catch (IOException e) {
				e.printStackTrace();
				response.put("error", e.getMessage());
				response.put("mensaje", "Error uploading file" );
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		
			}
			
			response.put("nombreArchivo", fileName);
			response.put("mensaje", "Image uploaded successful" );
		}
		
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
	}
	

	@GetMapping("/download") 
	public @ResponseBody ResponseEntity<ByteArrayResource>  downloadImage(@RequestParam String imgName){
		
		ResponseEntity<ByteArrayResource>  resource = null;
		
		try {
			resource = uploadFileService.download(imgName);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resource;
	}
	
	@GetMapping("/delete") 
	public ResponseEntity<?>  deleteImage(@RequestParam String imgName){
		
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			uploadFileService.delete(imgName);
		}catch (Exception ex){
			ex.printStackTrace();
			response.put("error", ex.getMessage());
			response.put("mensaje", "Error deleting file");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Image deleted successful");

		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
		
	}
}
