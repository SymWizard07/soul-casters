package soulcasters.server.game.entity;

import java.awt.Color;

import soulcasters.Constants;
import soulcasters.server.game.EntityHandler;

public class StatusMessage extends TextEntity {

    public StatusMessage(EntityHandler entityHandler, double x, double y, String status, boolean isSuccess,
            int ownerId) {
        super(entityHandler, x, y, status, 16,
                (isSuccess) ? new Color(Constants.COLOR_STATUS_SUCCESS) : new Color(Constants.COLOR_STATUS_FAILURE),
                ownerId, 1000);
    }

}
