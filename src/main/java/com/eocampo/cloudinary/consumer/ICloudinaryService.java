
package com.eocampo.cloudinary.consumer;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * Services to interact with cloudinary repository
 * @author eocampo
 *
 */
public interface ICloudinaryService {
	public String upload(MultipartFile file);
	public void delete(String publicId);
	public ResponseEntity<ByteArrayResource> downloadImg(String publicId);
}
