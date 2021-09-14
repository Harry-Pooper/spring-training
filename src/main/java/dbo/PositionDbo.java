package main.java.dbo;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Ваня on 26.05.2018.
 */
@Getter
@Setter
public class PositionDbo {
    private Long positionId;
    private String positionName;

    @Override
    public boolean equals(Object object){
        if (object != null && object instanceof PositionDbo){
            PositionDbo pDbo = (PositionDbo) object;
            return this.positionId.equals(pDbo.positionId);
        }
        return false;
    }
}
