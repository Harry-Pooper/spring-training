package main.java.dbo;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by mrlz on 08.04.2018.
 */
@Getter
@Setter
public class UserContactsDbo {
    private Long profileId;
    private String contact;
    private Long type;
    private boolean isPrefered;
}
