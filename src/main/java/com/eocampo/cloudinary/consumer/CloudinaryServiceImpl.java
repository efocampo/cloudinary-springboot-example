package com.eocampo.cloudinary.consumer;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;

import java.io.InputStream;

import java.net.URL;

import org.springframework.http.HttpHeaders;

/**
 * Services to interact with cloudinary repository
 * @author eocampo
 *
 */

@Service
public class CloudinaryServiceImpl implements ICloudinaryService {
	
	private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

	private final Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
			  "cloud_name", "YOUR_CLOUD_NAME",
			  "api_key", "YOUR_API_KEY",
			  "api_secret", "YOUR_API_SECRET",
			  "secure", true));
	
	@SuppressWarnings("rawtypes")
	@Override
	public String upload(MultipartFile file) {
		try {
			Map<String, String> param = new HashMap<String,String>();
			param.put("folder", "pets");
			
			Map uploadResult = cloudinary.uploader().upload(file.getBytes(), param);
			String publicId = uploadResult.get("public_id").toString();
			logger.info("The user successfully uploaded the file: " + publicId);
			return publicId;
		} catch (Exception ex) {
			logger.error("The userfailed to load to Cloudinary the image file: " + file.getName());
			logger.error(ex.getMessage());
			return null;
		}
	}
	
	@Override
	public void delete(String publicId) {
		try {
			Map<String, String> param = new HashMap<String,String>();
			param.put("folder", "pets");
			param.put("invalidate", "true");
		cloudinary.uploader().destroy(publicId, param);
		} catch (Exception ex) {
			logger.error("The userfailed to remove to Cloudinary the image file: " + publicId);
			logger.error(ex.getMessage());
			
		}
		
	}

	@SuppressWarnings({ "rawtypes", "null" })
	@Override
	public ResponseEntity<ByteArrayResource> downloadImg(String publicId) {
		
		if (publicId != null || publicId.trim().length()>0) {
			String format = "jpg";
			Transformation transformation = new Transformation();
			
			String cloudUrl = cloudinary.url().secure(true).format(format)
	                .transformation(transformation)
	                .publicId(publicId)
	                .cloudName("petscare")
	                .generate();

	        logger.debug("Generated URL of the image to be downloaded: " + cloudUrl);
	        
	        try {
	            // Get a ByteArrayResource from the URL
	            URL url = new URL(cloudUrl);
	            InputStream inputStream = url.openStream();
	            byte[] out = org.apache.commons.io.IOUtils.toByteArray(inputStream);
	            ByteArrayResource resource = new ByteArrayResource(out);

	            // Creates the headers
	            HttpHeaders responseHeaders = new HttpHeaders();
	            responseHeaders.add("content-disposition", "attachment; filename=image.jpg");
	            responseHeaders.add("Content-Type", "image/jpeg");

	            return ResponseEntity.ok()
	                    .headers(responseHeaders)
	                    .contentLength(out.length)
	                    .body(resource);

	        } catch (Exception ex) {
	        	//ex.printStackTrace();
	            logger.error("FAILED to download the file: " + publicId);
	            return null;
	        }
		}else {
			return null;
		}
		
		
	}

}
