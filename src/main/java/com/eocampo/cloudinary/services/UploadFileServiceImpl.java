
package com.eocampo.cloudinary.services;

 import java.io.IOException;
 import java.net.MalformedURLException;

 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.core.io.ByteArrayResource;
 import org.springframework.http.ResponseEntity;
 import org.springframework.stereotype.Service;
 import org.springframework.web.multipart.MultipartFile;

 import com.eocampo.cloudinary.consumer.ICloudinaryService;

 /*
  * @author Efrén Ocampo
  * Service implementation that interacts with cloudinary api and it's consumed by controller
  */

 @Service
 public class UploadFileServiceImpl implements IUploadFileService {


 	@Autowired
 	ICloudinaryService cloudinaryService;
 	
 	@Override
 	public String upload(MultipartFile file) throws IOException {
 		return cloudinaryService.upload(file);
 	}
 	
 	@Override
 	public ResponseEntity<ByteArrayResource> download(String nameImage) throws MalformedURLException {
 		ResponseEntity<ByteArrayResource> respuesta = cloudinaryService.downloadImg(nameImage);
 		//For this example, if the image isn't found, you can download the image with the id: noimagen_vifjej
 		//inside the directory called pets
 		if (respuesta == null) {
 			respuesta = cloudinaryService.downloadImg("pets/noimagen_vifjej");
 		}
 		
 		 return respuesta;
 	}

 
 	@Override
 	public void delete(String publicId) {
 		cloudinaryService.delete(publicId);
 		
 	}

 	
 }
