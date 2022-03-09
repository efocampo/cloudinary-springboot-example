# cloudinary-springboot-example
Upload, delete and download an image using Cloudinary repository. The client is a spring boot app using rest services

# Classes
## Direct interaction with cloudinary
- ICloudinaryService
- CloudinaryServiceImpl
  - public String upload(MultipartFile file)
  - public void delete(String publicId)
  - public ResponseEntity<ByteArrayResource> downloadImg(String publicId)
## Client services. Controls exceptions produced in the Cloudinary Services and implements aditional programming logic, like setting a default imagen in case of error
- IUploadFileService
- UploadFileServiceImpl
  - public String upload(MultipartFile file) throws IOException
  - public void delete(String publicId)
  - public ResponseEntity<ByteArrayResource> download(String nameImage) throws MalformedURLException
## Rest controller
- FileRestController
  
# Consume
  - For the example : "pets/hcyqmd9v1qfc5a63hq4l", pets is just a folder in the Cloudinary repository and hcyqmd9v1qfc5a63hq4l is the code assigned by cloudinary to the uploaded image.
  ## Upload
  - POST: http://localhost:8080/api/file/upload
  - Multipart form:
    - Attribute name:  image
  - Output:
  {
  "nombreArchivo": "pets/hcyqmd9v1qfc5a63hq4l",
  "mensaje": "Image uploaded successful"
  }
  ![Image uploaded successful](https://raw.githubusercontent.com/efocampo/cloudinary-springboot-example/main/upload.png)

   ## Download
  - GET: http://localhost:8080/api/file/download/
  - Url preview:
    - http://localhost:8080/api/file/download/?imgName=pets%2Fhcyqmd9v1qfc5a63hq4l
    - Attribute name:  imgName, Attribute value for example: pets/hcyqmd9v1qfc5a63hq4l
  ![Image downloaded successful](https://raw.githubusercontent.com/efocampo/cloudinary-springboot-example/main/download.png)
    #### Image on Cloudinary
  ![Image on Cloudinary](https://raw.githubusercontent.com/efocampo/cloudinary-springboot-example/main/cloudinary.png)
   ## Delete
  - GET: http://localhost:8080/api/file/delete
  - Url preview:
    - http://localhost:8080/api/file/delete?imgName=pets%2Fhcyqmd9v1qfc5a63hq4l
    - Attribute name:  imgName, Attribute value for example: pets/hcyqmd9v1qfc5a63hq4l
  ![Image deleted successful](https://raw.githubusercontent.com/efocampo/cloudinary-springboot-example/main/delete.png)
