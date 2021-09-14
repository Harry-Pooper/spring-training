package main.java.dbo;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Ваня on 07.06.2018.
 */
@Getter
@Setter
public class ResponseDbo {
    private Long vrId;
    private Long vcId;
    private Long cvId;
    private String letter;
    private Long status;
    private String dateSent;
}
