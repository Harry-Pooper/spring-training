package main.java.service;

import main.java.Dict;
import main.java.config.ProjectProperties;
import main.java.dao.*;
import main.java.dbo.PaymentDbo;
import main.java.dbo.ResumeDbo;
import main.java.dbo.UserDbo;
import main.java.util.SearchUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Ваня on 22.05.2018.
 */
@Service
public class ResumeService {

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private SearchUtil searchUtil;

    public List<ResumeDbo> searchResults(String firstName, String secondName, String lastName, String title, String position, String paymentFrom, String paymentTo, String tags){
        List<ResumeDbo> res = new ArrayList<>();
        List<Long> cvIds = ResumeDao.searchByQuery(searchUtil.makeQueryForSearch(firstName, secondName, lastName, title, position, paymentFrom, paymentTo, tags), "resume");
        for(Long cvId : cvIds){
            res.add(ResumeDao.getUserResumeById(cvId));
        }
        return res;
    }

    public Long createResume(Long profileId, ResumeDbo resumeDbo, HttpServletRequest request, MultipartFile file){
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        String from = request.getParameter("paymentFrom");
        if(Objects.equals(from, "")){
            from = "-1";
        }
        String to = request.getParameter("paymentTo");
        if(Objects.equals(to, "")){
            to = "-1";
        }
        if(resumeDbo.getExperience() == null){
            resumeDbo.setExperience(0L);
        }

        resumeDbo.setPaymentId(PaymentDao.createPayment(Long.valueOf(from), Long.valueOf(to), getPaymentType(from, to)));
        resumeDbo.setProfileId(profileId);
        resumeDbo = fillResumeDbo(resumeDbo);
        resumeDbo.setDatePublished(df.format(new Date()));
        resumeDbo.setIsPublic(false);
        resumeDbo.setWatches(0L);

        Long createdCvId = ResumeDao.createResume(resumeDbo);
        List<String> cvTags = new ArrayList<>();
        if(!request.getParameter("tagList").equals("")){
            cvTags = Arrays.asList(request.getParameter("tagList").split(","));
        }
        uploadTags(cvTags, createdCvId);
        uploadImage(file, createdCvId);

        if(!request.getParameter("pos").isEmpty()){
            PositionDao.deletePositionsForCv(createdCvId);
            PositionDao.createPositionsForCv(createdCvId, Long.valueOf(request.getParameter("pos")));
        }
        return 0L;
    }

    public String publishResume(Long profileId, Long cvId){
        if(isOkToUpload(ResumeDao.getUserResumeById(cvId), UserDao.findUserByProfileId(profileId))){
            ResumeDao.publishResume(cvId);
            return "success";
        }
        return "failure";
    }

    public void updateResume(Long profileId, ResumeDbo resumeDbo, HttpServletRequest request, MultipartFile file){

        String from = request.getParameter("paymentFrom");
        if(Objects.equals(from, "") || Objects.equals(from, "0")){
            from = "-1";
        }
        String to = request.getParameter("paymentTo");
        if(Objects.equals(to, "") || Objects.equals(to, "0")){
            to = "-1";
        }

        if(resumeDbo.getExperience() == null){
            resumeDbo.setExperience(0L);
        }

        //Перетягиваем инфу со старого резюме, но тут всё равно поменяются поля, которые меняются обычно
        ResumeDbo oldResume = ResumeDao.getUserResumeById(resumeDbo.getCvId());
        resumeDbo.setPaymentId(oldResume.getPaymentId());
        resumeDbo = fillResumeDbo(resumeDbo);
        resumeDbo.setCvImage(oldResume.getCvImage());
        resumeDbo.setWatches(oldResume.getWatches());
        resumeDbo.setIsPublic(oldResume.getIsPublic());
        resumeDbo.setDatePublished(oldResume.getDatePublished());

        //Заполняем инфу о зарплате
        PaymentDbo paymentDbo = new PaymentDbo();
        paymentDbo.setPaymentId(resumeDbo.getPaymentId());
        paymentDbo.setPaymentFrom(Long.valueOf(from));
        paymentDbo.setPaymentTo(Long.valueOf(to));
        paymentDbo.setPaymentType(getPaymentType(from, to));
        resumeDbo.setPaymentId(PaymentDao.createPayment(Long.valueOf(from), Long.valueOf(to), getPaymentType(from, to)));

        //Обновляем с концами всё резюме
        ResumeDao.updateResume(resumeDbo);
        List<String> cvTags = new ArrayList<>();
        if(!request.getParameter("tagList").equals("")){
            cvTags = Arrays.asList(request.getParameter("tagList").split(","));
        }
        uploadTags(cvTags, resumeDbo.getCvId());
        uploadImage(file, resumeDbo.getCvId());
        if(!request.getParameter("pos").isEmpty()){
            PositionDao.deletePositionsForCv(resumeDbo.getCvId());
            PositionDao.createPositionsForCv(resumeDbo.getCvId(), Long.valueOf(request.getParameter("pos")));
        }
    }

    public Model prepareModelForPreview(Model model, Long profileId, ResumeDbo resumeDbo, HttpServletRequest request, MultipartFile file){
        String from = request.getParameter("paymentFrom");
        if(Objects.equals(from, "") || Objects.equals(from, "0")){
            from = "-1";
        }
        String to = request.getParameter("paymentTo");
        if(Objects.equals(to, "") || Objects.equals(to, "0")){
            to = "-1";
        }
        List<String> cvTags = Arrays.asList(request.getParameter("tagList").split(","));
        return model;
    }

    public void uploadTags(List<String> cvTags, Long cvId){
        cvTags.replaceAll(String::toLowerCase);
        TagsDao.deleteTagsForCv(cvId);
        if(!cvTags.isEmpty()) {
            TagsDao.createTag(cvTags);
            TagsDao.createTagsForCv(cvTags, cvId);
        }
    }

    public void uploadImage(MultipartFile file, Long cvId){
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                if(userProfileService.isExtensionCorrect(FilenameUtils.getExtension(file.getOriginalFilename()))) {
                    File dir = ProjectProperties.profilePicturesPathDir;
                    File serverFile = new File(dir.getAbsolutePath()
                            + File.separator + Long.toString(cvId) + "."
                            + FilenameUtils.getExtension(file.getOriginalFilename()));
                    BufferedOutputStream stream = new BufferedOutputStream(
                            new FileOutputStream(serverFile));
                    stream.write(bytes);
                    stream.close();
                    dir = ProjectProperties.profileWarImgPathDir;
                    serverFile = new File(dir.getAbsolutePath()
                            + File.separator + Long.toString(cvId) + "."
                            + FilenameUtils.getExtension(file.getOriginalFilename()));
                    stream = new BufferedOutputStream(
                            new FileOutputStream(serverFile));
                    stream.write(bytes);
                    stream.close();
                    ResumeDao.updateCvPicUrl(Long.toString(cvId) + "."
                                    + FilenameUtils.getExtension(file.getOriginalFilename()),
                            cvId);
                } else {
                    throw new Exception("Image extension is not right");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            }
        }
    }

    public ResumeDbo fillResumeDbo(ResumeDbo resumeDbo){
        resumeDbo.setBusinessTrip(0L);
        resumeDbo.setDegree(0L);
        return resumeDbo;
    }

    public Long countNotPublished(List<ResumeDbo> rdbo){
        Long count = 0L;
        for(ResumeDbo rdbo1 : rdbo){
            if(!rdbo1.getIsPublic()){
                count++;
            }
        }
        return count;
    }

    public Long countPublished(List<ResumeDbo> rdbo){
        Long count = 0L;
        for(ResumeDbo rdbo1 : rdbo){
            if(rdbo1.getIsPublic()){
                count++;
            }
        }
        return count;
    }

    public Long getPaymentType(String from, String to){
        if(Objects.equals(from, "-1") && Objects.equals(to, "-1")){
            return Dict.NOT_DETERMINED;
        }
        if(!Objects.equals(from, "-1") && !Objects.equals(to, "-1")){
            return Dict.FROM_TO;
        }
        if(Objects.equals(from, "-1") && !Objects.equals(to, "-1")){
            return Dict.TO;
        }
        if(!Objects.equals(from, "-1") && Objects.equals(to, "-1")){
            return Dict.FROM;
        }
        return Dict.NOT_DETERMINED;
    }

    public boolean isOkToUpload(ResumeDbo rdbo, UserDbo udbo){
        if(udbo.getFirstName().isEmpty() && udbo.getSecondName().isEmpty() && udbo.getLastName().isEmpty()){
            return false;
        }
        if(PositionDao.getPositionForCv(rdbo.getCvId()).equals(null)){
            return false;
        }
        if(rdbo.getPhone().isEmpty()){
            return false;
        }
        if(rdbo.getEmail().isEmpty()){
            return false;
        }
        if(rdbo.getAbout().isEmpty()){
            return false;
        }
        if(TagsDao.getCvTags(rdbo.getCvId()).isEmpty()){
            return false;
        }
        return true;
    }
}
