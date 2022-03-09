package com.eocampo.cloudinary.services;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/*
 * @author Efrén Ocampo
 * Service that interacts with cloudinary api and it's consumed by controller
 */

public interface IUploadFileService {
	public String upload(MultipartFile file) throws IOException;
	public void delete(String publicId);
	public ResponseEntity<ByteArrayResource> download(String nameImage) throws MalformedURLException;
	
}