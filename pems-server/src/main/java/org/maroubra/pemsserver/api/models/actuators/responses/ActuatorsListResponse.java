package org.maroubra.pemsserver.api.models.actuators.responses;

import org.maroubra.pemsserver.User;

public class ActuatorsListResponse {
    public static int activate() {
            return 1;
    }
    public static int deactivate() {
            return 0;
    }
    public static int ActiveAcutators() {
        return User.ActuatorActivate();
    }
    public static String Error() {
        return "";
    }
}
