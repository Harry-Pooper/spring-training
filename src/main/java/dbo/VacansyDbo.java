package main.java.dbo;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Ваня on 21.05.2018.
 */
@Getter
@Setter
public class VacansyDbo {
    private Long profileorgId;
    private String datePublishedvc;
    private String aboutvc;
    private String titlevc;
    private String demands;
    private Long sfrom;
    private Long sto;
    private Long stype;
    private Boolean sndfl;
    private Boolean isPublicvc;
    private Long vcId;
    private Long watches;
    private String vcName = null;
    private String vcSecName = null;
    private String vcEmail = null;
    private String vcTeleph = null;
    private String vcDopTeleph = null;
    private String adress = null;
}
