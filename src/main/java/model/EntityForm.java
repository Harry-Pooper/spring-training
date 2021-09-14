package main.java.model;

import main.java.dbo.RegistrationDbo;
import main.java.dbo.UserDbo;
import main.java.dbo.OrgDbo;

/**
 * Created by mrlz on 04.04.2018.
 */
public class EntityForm {
    private RegistrationDbo loggedUser;
    private UserDbo userDbo;
    private OrgDbo orgDbo;

    public RegistrationDbo getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(RegistrationDbo loggedUser) {
        this.loggedUser = loggedUser;
    }

    public UserDbo getUserDbo() {
        return userDbo;
    }

    public void setUserDbo(UserDbo userDbo) {
        this.userDbo = userDbo;
    }

    public OrgDbo getOrgDbo() {
        return orgDbo;
    }

    public void setOrgDbo(OrgDbo orgDbo) {
        this.orgDbo = orgDbo;
    }
}
