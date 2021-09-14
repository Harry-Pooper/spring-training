package main.java.dbo;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Ваня on 21.05.2018.
 */
@Getter
@Setter
public class ResumeDbo {
    private Long profileId;
    private Long cvId;
    private Long paymentId;
    private String cvImage;
    private String phone;
    private String email;
    private Long watches;
    private Long degree;
    private Long canMove;
    private Long businessTrip;
    private Long experience;
    private Boolean isPublic;
    private String datePublished;
    private String about;
    private String title;
}
