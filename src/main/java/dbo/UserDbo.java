package main.java.dbo;

import java.util.List;

/**
 * Created by mrlz on 02.04.2018.
 */
public class UserDbo {

    private Long profileId = 0L;
    private Long accountId = 0L;
    private String firstName = null;
    private String secondName = null;
    private String lastName = null;
    private String birthDate = "";
    private boolean hideBirthDate = false;
    private Long age = 0L;
    private String aboutUser = null;
    private Long degree = 0L;
    private Long paymentId = 0L;
    private String photoUrl = null;
    private Long canMove = 0L;
    private Long businessTrips = 0L;
    private Long cityId = 0L;

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isHideBirthDate() {
        return hideBirthDate;
    }

    public void setHideBirthDate(boolean hideBirthDate) {
        this.hideBirthDate = hideBirthDate;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getAboutUser() {
        return aboutUser;
    }

    public void setAboutUser(String aboutUser) {
        this.aboutUser = aboutUser;
    }

    public Long getDegree() {
        return degree;
    }

    public void setDegree(Long degree) {
        this.degree = degree;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Long getCanMove() {
        return canMove;
    }

    public void setCanMove(Long canMove) {
        this.canMove = canMove;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getBusinessTrips() {
        return businessTrips;
    }

    public void setBusinessTrips(Long businessTrips) { this.businessTrips = businessTrips; }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }
}
