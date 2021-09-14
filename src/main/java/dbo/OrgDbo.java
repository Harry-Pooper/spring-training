package main.java.dbo;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by mrlz on 02.04.2018.
 */
public class OrgDbo {

    private Long profileorgId = 0L;
    private Long accountId = 0L;
    private String orgName = null;
    private String adress = null;
    private String link = null;
    private Long teleph = 0L;
    private String aboutOrg = null;
    private Long orgType = 0L;
    private Long cityId = 0L;
    private Long countMem = 0L;
    private String orgEmail = null;
    private String contactName = null;
    private String contactSecName = null;
    private String contactEmail = null;
    private String contactTeleph = null;
    private String contactDopTeleph = null;
    private Long contactId = 0L;
    private Long transport = 0L;
    private Long career = 0L;
    private Long spirit = 0L;
    private Long salary = 0L;
    private Long projects = 0L;
    private String imageUrl = null;

    public Long gettransport() {
        return transport;
    }

    public void settransport(Long transport) { this.transport = transport; }

    public Long getcareer() {
        return career;
    }

    public void setcareer(Long career) { this.career = career; }

    public Long getspirit() {
        return spirit;
    }

    public void setspirit(Long spirit) { this.spirit = spirit; }

    public Long getsalary() {
        return salary;
    }

    public void setsalary(Long salary) { this.salary = salary; }

    public Long getprojects() {
        return projects;
    }

    public void setprojects(Long projects) { this.projects = projects; }




    public Long getcontactId() {
        return contactId;
    }

    public void setcontactId(Long contactId) { this.contactId = contactId; }

    public void setorgEmail(String orgEmail) {
        this.orgEmail = orgEmail;
    }

    public String getorgEmail() {
        return orgEmail;
    }

    public void setcontactName(String contactName) {
        this.contactName = contactName;
    }

    public String getcontactName() {
        return contactName;
    }

    public void setcontactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getcontactEmail() { return contactEmail; }

    public void setcontactSecName(String contactSecName) {
        this.contactSecName = contactSecName;
    }

    public String getcontactSecName() {
        return contactSecName;
    }

    public String getcontactTeleph() {
        return contactTeleph;
    }

    public void setcontactTeleph(String contactTeleph) { this.contactTeleph = contactTeleph; }

    public String getcontactDopTeleph() {
        return contactDopTeleph;
    }

    public void setcontactDopTeleph(String contactDopTeleph) { this.contactDopTeleph = contactDopTeleph; }



    public Long getcountMem() {
        return countMem;
    }

    public void setcountMem(Long countMem) { this.countMem = countMem; }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getOrgType() {
        return orgType;
    }

    public void setOrgType(Long orgType) { this.orgType = orgType; }

    public Long getProfileorgId() {
        return profileorgId;
    }

    public void setProfileorgId(Long profileorgId) {
        this.profileorgId = profileorgId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getorgName() {
        return orgName;
    }

    public void setorgName(String orgName) {
        this.orgName = orgName;
    }

    public String getAboutOrg() {
        return aboutOrg;
    }

    public void setAboutOrg(String aboutOrg) {
        this.aboutOrg = aboutOrg;
    }

    public Long getTeleph() {
        return teleph;
    }

    public void setTeleph(Long teleph) {
        this.teleph = teleph;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
