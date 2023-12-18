package TaskManagementSource.util;

import TaskManagementSource.entities.entity.Picture;
import TaskManagementSource.entities.entity.User;
import TaskManagementSource.repository.PictureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class PictureHelperService {

    private final PictureRepository pictureRepository;

    public void create(User user, String picturePath){
        Picture picture = new Picture();
        picture.setUsers(new ArrayList<>());
        picture.setTitle(user.getUsername());
        picture.setUrl(picturePath);
        picture.getUsers().add(user);
        pictureRepository.save(picture);
    }
}
