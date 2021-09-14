package main.java.service;

import main.java.Dict;
import main.java.config.ProjectProperties;
import main.java.dao.*;
import main.java.dbo.PaymentDbo;
import main.java.dbo.VacansyDbo;
import main.java.dbo.OrgDbo;
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
public class VacansyService {

    @Autowired
    private OrgProfileService orgProfileService;

    @Autowired
    private SearchUtil searchUtil;

    @Autowired
    private ResumeService resumeService;


    public List<VacansyDbo> searchResults(String title, String orgName, String city, String sfrom, String sto, String tagss){
        List<VacansyDbo> res = new ArrayList<>();
        List<Long> vcIds = ResumeDao.searchByQuery(searchUtil.makeQueryForSearch(title, orgName, city, sfrom, sto, tagss), "vacancy");
        for(Long cvId : vcIds){
            res.add(VacansyDao.getOrgVacansyById(cvId));
        }
        return res;
    }


    public Long createVacansy(Long profileorgId, VacansyDbo vacansyDbo, HttpServletRequest request, MultipartFile file){
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        String from = request.getParameter("sFrom");
        if(Objects.equals(from, "")){
            from = "-1";
        }
        String to = request.getParameter("sTo");
        if(Objects.equals(to, "")){
            to = "-1";
        }
        vacansyDbo.setProfileorgId(profileorgId);
        vacansyDbo.setDatePublishedvc(df.format(new Date()));
        vacansyDbo.setIsPublicvc(false);
        vacansyDbo.setSfrom(Long.valueOf(from));
        vacansyDbo.setSto(Long.valueOf(to));
        vacansyDbo.setStype(resumeService.getPaymentType(from, to));

        Long createdVcId = VacansyDao.createVacansy(vacansyDbo);

        List<String> vcTags = new ArrayList<>();
        if(!request.getParameter("tagList").equals("")){
            vcTags = Arrays.asList(request.getParameter("tagList").split(","));
        }
        uploadTags(vcTags, createdVcId);


        return 0L;

    }

    public String publishVacansy(Long profileorgId, Long vcId){
        if(isOkToUpload(VacansyDao.getOrgVacansyById(vcId), OrgDao.findOrgByProfileId(profileorgId))){
            VacansyDao.publishVacansy(vcId);
            return "success";
        }
        return "failure";
    }

    public void updateVacansy(Long profileorgId, VacansyDbo vacansyDbo, HttpServletRequest request, MultipartFile file){

        String from = request.getParameter("sfrom");
        if(Objects.equals(from, "") || Objects.equals(from, "0")){
            from = "-1";
        }
        String to = request.getParameter("sto");
        if(Objects.equals(to, "") || Objects.equals(to, "0")){
            to = "-1";
        }

        VacansyDbo oldVacansy = VacansyDao.getOrgVacansyById(vacansyDbo.getVcId());
        vacansyDbo.setWatches(oldVacansy.getWatches());
        vacansyDbo.setIsPublicvc(oldVacansy.getIsPublicvc());
        vacansyDbo.setDatePublishedvc(oldVacansy.getDatePublishedvc());

        vacansyDbo.setSfrom(oldVacansy.getSfrom());
        vacansyDbo.setSto(oldVacansy.getSto());
        vacansyDbo.setStype(oldVacansy.getStype());

        //Обновляем с концами всё резюме
        VacansyDao.updateVacansy(vacansyDbo);
        List<String> vcTags = new ArrayList<>();
        if(!request.getParameter("tagList").equals("")){
            vcTags = Arrays.asList(request.getParameter("tagList").split(","));
        }
        uploadTags(vcTags, vacansyDbo.getVcId());
    }


    public void uploadTags(List<String> vcTags, Long vcId){
        vcTags.replaceAll(String::toLowerCase);
        TagsDao.deleteTagsForVc(vcId);
        if(!vcTags.isEmpty()) {
            TagsDao.createTag(vcTags);
            TagsDao.createTagsForVc(vcTags, vcId);
        }
    }


    public Long countNotPublished(List<VacansyDbo> rdbo){
        Long count = 0L;
        for(VacansyDbo rdbo1 : rdbo){
            if(!rdbo1.getIsPublicvc()){
                count++;
            }
        }
        return count;
    }

    public Long countPublished(List<VacansyDbo> rdbo){
        Long count = 0L;
        for(VacansyDbo rdbo1 : rdbo){
            if(rdbo1.getIsPublicvc()){
                count++;
            }
        }
        return count;
    }


    public boolean isOkToUpload(VacansyDbo rdbo, OrgDbo udbo){
        if(udbo.getorgName().isEmpty()){
            return false;
        }
        if(udbo.getAboutOrg().isEmpty()){
            return false;
        }
        if(rdbo.getAboutvc().isEmpty()){
            return false;
        }
        if(rdbo.getVcName().isEmpty() && rdbo.getVcSecName().isEmpty()){
            return false;
        }
        if(rdbo.getVcEmail().isEmpty() || rdbo.getVcTeleph().isEmpty()){
            return false;
        }
        return !TagsDao.getVcTags(rdbo.getVcId()).isEmpty();
    }
}
