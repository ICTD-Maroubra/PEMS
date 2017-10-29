package org.maroubra.pemsserver.api.models.actuators.requests;

import org.maroubra.pemsserver.api.models.actuators.responses.ActuatorsListResponse;

public class UpdateActuatorRequest {
    public static void ChangeAcuator() {
        if(User.command == "Act"){
            ActuatorsListResponse.activate();
        }
        else if (User.command == "DeAct") {
            ActuatorsListResponse.deactivate();
        }
        else if (User.command == "Show-Actuators") {
            ActuatorsListResponse.ActiveAcutators();
        }
        else {
            ActuatorsListResponse.Error();
        }
    }
}
