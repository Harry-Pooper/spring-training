package main.java.dbo;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by mrlz on 08.04.2018.
 */
@Getter
@Setter
public class PaymentDbo {
    private Long paymentId;
    private Long paymentFrom;
    private Long paymentTo;
    private Long paymentType;
}
