package main.java.dbo;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by mrlz on 16.04.2018.
 */
@Getter
@Setter
public class UserWorkHistoryDbo {
    private Long historyId = 0L;
    private Long profileId = 0L;
    private String orgName = null;
    private String emplDate = null;
    private String unemplDate = null;
    private String position = null;
}
