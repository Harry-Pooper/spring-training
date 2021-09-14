package main.java.service;

import javafx.util.Pair;
import main.java.Dict;
import main.java.config.ProjectProperties;
import main.java.dao.PositionDao;
import main.java.dao.TagsDao;
import main.java.dao.UserContactsDao;
import main.java.dao.UserDao;
import main.java.dbo.CitiesDbo;
import main.java.dbo.UserContactsDbo;
import org.apache.commons.io.FilenameUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mrlz on 08.04.2018.
 */
@Service
public class UserProfileService {

    public boolean isUserCorrect(String id) {
        Long profileId = Long.getLong(id);
        return SecurityContextHolder.getContext() != null && UserDao.findUserByAccId(SecurityService.getLoggedInUser()).getProfileId().equals(profileId);
    }

    public List<String> listToMap(List<CitiesDbo> citiesList){
        List<String> cities = new ArrayList<>();
        for(CitiesDbo city : citiesList){
            cities.add(city.getCityName());
        }
        return cities;
    }

    public List<Pair<Long, String>> citiesToPairList(List<CitiesDbo> citiesDboList){
        List<Pair<Long, String>> citiesList = new ArrayList<>();
        for(CitiesDbo city : citiesDboList){
            Long key = city.getCityId();
            String val = city.getCityName();
            citiesList.add(new Pair<>(key, val));
        }
        return citiesList;
    }

    public void uploadTags(List<String> userTags, Long profileId){
        userTags.replaceAll(String::toLowerCase);
        TagsDao.deleteTagsForUser(profileId);
        TagsDao.createTag(userTags);
        TagsDao.createTagsForUser(userTags, profileId);
    }

    public void uploadPositions(List <String> userPositions, Long profileId){
        PositionDao.deletePositionsForUser(profileId);
        PositionDao.createPositionsForUser(profileId, userPositions);
    }

    public void uploadImage(MultipartFile file, Long profileId){
        if (!file.isEmpty()) {
            try {
                //todo - Нужно добавить проверку на тип файла
                byte[] bytes = file.getBytes();
                if(isExtensionCorrect(FilenameUtils.getExtension(file.getOriginalFilename()))) {
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
                    UserDao.updateUserProfilePicUrl(Long.toString(profileId) + "."
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

    public List<UserContactsDbo> fillUserContacts(HttpServletRequest request){
        List<UserContactsDbo> userContactsDboList = new ArrayList<>();
        if(!request.getParameter("phoneCounter").equals("0")){
            int count = Integer.parseInt(request.getParameter("phoneCounter"));
            for(int i = 0; i < count; i++){
                if(!request.getParameter("phone" + i).equals("")) {
                    UserContactsDbo userContactsDbo = new UserContactsDbo();
                    userContactsDbo.setContact(request.getParameter("phone" + i));
                    userContactsDbo.setPrefered(false);
                    userContactsDbo.setProfileId(UserDao.findUserByAccId(SecurityService.getLoggedInUser()).getProfileId());
                    userContactsDbo.setType(Dict.PHONE);
                    userContactsDboList.add(userContactsDbo);
                }
            }
        }
        if(!request.getParameter("emailCounter").equals("0")){
            int count = Integer.parseInt(request.getParameter("emailCounter"));
            for(int i = 0; i < count; i++){
                if(!request.getParameter("email" + i).equals("")) {
                    UserContactsDbo userContactsDbo = new UserContactsDbo();
                    userContactsDbo.setContact(request.getParameter("email" + i));
                    userContactsDbo.setPrefered(false);
                    userContactsDbo.setProfileId(UserDao.findUserByAccId(SecurityService.getLoggedInUser()).getProfileId());
                    userContactsDbo.setType(Dict.EMAIL);
                    userContactsDboList.add(userContactsDbo);
                }
            }
        }
        if(!request.getParameter("faxCounter").equals("0")){
            int count = Integer.parseInt(request.getParameter("faxCounter"));
            for(int i = 0; i < count; i++){
                if(!request.getParameter("fax" + i).equals("")) {
                    UserContactsDbo userContactsDbo = new UserContactsDbo();
                    userContactsDbo.setContact(request.getParameter("fax" + i));
                    userContactsDbo.setPrefered(false);
                    userContactsDbo.setProfileId(UserDao.findUserByAccId(SecurityService.getLoggedInUser()).getProfileId());
                    userContactsDbo.setType(Dict.FAX);
                    userContactsDboList.add(userContactsDbo);
                }
            }
        }
        return userContactsDboList;
    }

    public boolean isExtensionCorrect(String ext){
        ext = ext.toLowerCase();
        List<String> extensions = new ArrayList<>();
        extensions.add("png");
        extensions.add("jpg");
        extensions.add("jpeg");
        extensions.add("gif");
        extensions.add("bmp");
        return extensions.contains(ext);
    }
}
