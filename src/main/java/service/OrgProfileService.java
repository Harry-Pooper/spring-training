package main.java.service;

import javafx.util.Pair;
import main.java.Dict;
import main.java.config.ProjectProperties;
import main.java.dao.TagsDao;
import main.java.dao.OrgDao;
import main.java.dao.UserDao;
import main.java.dbo.CitiesDbo;
import main.java.dbo.OrgContactsDbo;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrlz on 08.04.2018.
 */
@Service
public class OrgProfileService {
    @Autowired
    private UserProfileService userProfileService;

    public void uploadImage(MultipartFile file, Long profileId){
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                if(userProfileService.isExtensionCorrect(FilenameUtils.getExtension(file.getOriginalFilename()))) {
                    File dir = ProjectProperties.profilePicturesPathDir;
                    File serverFile = new File(dir.getAbsolutePath()
                            + File.separator + Long.toString(profileId) + "."
                            + FilenameUtils.getExtension(file.getOriginalFilename()));
                    BufferedOutputStream stream = new BufferedOutputStream(
                            new FileOutputStream(serverFile));
                    stream.write(bytes);
                    stream.close();
                    dir = ProjectProperties.profileWarImgPathDir;
                    serverFile = new File(dir.getAbsolutePath()
                            + File.separator + Long.toString(profileId) + "."
                            + FilenameUtils.getExtension(file.getOriginalFilename()));
                    stream = new BufferedOutputStream(
                            new FileOutputStream(serverFile));
                    stream.write(bytes);
                    stream.close();
                    OrgDao.updateOrgProfilePicUrl(Long.toString(profileId) + "."
                                    + FilenameUtils.getExtension(file.getOriginalFilename()),
                            profileId);
                } else {
                    throw new Exception("Image extension is not right");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            }
        }
    }
}
