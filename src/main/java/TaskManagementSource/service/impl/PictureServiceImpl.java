package TaskManagementSource.service.impl;

import TaskManagementSource.entities.dto.binding.PictureUploadBindingModel;
import TaskManagementSource.entities.entity.User;
import TaskManagementSource.exceptions.UserNotFoundException;
import TaskManagementSource.repository.UserRepository;
import TaskManagementSource.service.interfaces.PictureService;
import TaskManagementSource.util.PictureHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PictureServiceImpl implements PictureService {

    private static final String BASE_IMAGES_PATH = ".\\src\\main\\resources\\static\\images\\";
    private final UserRepository userRepository;
    private final PictureHelperService pictureHelperService;

    @Override
    public void upload(PictureUploadBindingModel pictureUploadBindingModel) {
        MultipartFile pictureFile = pictureUploadBindingModel.getPicture();
        User user = userRepository.findById(pictureUploadBindingModel.getId())
                .orElseThrow( () -> new UserNotFoundException("User doesn't exist."));
        String picturePath = getPicturePath(pictureFile, user.getUsername());
        try {
            File file = new File(BASE_IMAGES_PATH + picturePath);
            if (file.getParentFile().mkdirs()) {
                System.out.println("Successfully created directory.");
            }
            else {
                System.out.println("Directory already exists.");
            }
            if (file.createNewFile()) {
                System.out.println("Successfully uploaded photo.");
            }
            else {
                System.out.println("Photo could not be uploaded.");
            }
            try (OutputStream outputStream = new FileOutputStream(file)) {
                outputStream.write(pictureFile.getBytes());
            }
            user.setImageUrl(picturePath);
            userRepository.save(user);
        } catch (IOException e) {
            throw new RuntimeException("Error uploading picture", e);
        }
    }
    private String getPicturePath(MultipartFile pictureFile, String username) {
        String ext = getFileExtension(pictureFile.getOriginalFilename());
        String pathPattern = "%s\\%s." + ext;
        return String.format(pathPattern,
                transformUsername(username),
                UUID.randomUUID());
    }
    private String getFileExtension(String fileName) {
        String[] splitPictureName = fileName.split("\\.");
        return splitPictureName[splitPictureName.length - 1];
    }
    private String transformUsername(String username) {
        return username.toLowerCase()
                .replaceAll("\\s+", "_");
    }
}
