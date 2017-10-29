package org.maroubra.pemsserver.api.models.events.responses;

import org.maroubra.pemsserver.User;
import org.maroubra.pemsserver.api.models.sensors.requests.UpdateSensorRequest;
import org.maroubra.pemsserver.api.models.sensors.responses.SensorListResponse;

public class EventListResponse {
        private static int prediction;
        public static void predict() {
                int array[] =SensorListResponse.getdata();
                int i =0;
                while(i<array.length){
                        if(array[tempMax]> User.TempSlider()){

                                Query query6 = new Query();
                                query6.addCriteria(Criteria.where("name").is("appleF"));
                                
                                Update update6 = new Update();
                                update6.set("age", 101);
                                update6.set("ic", 1111);
                                
                                //FindAndModifyOptions().returnNew(true) = newly updated document
                                //FindAndModifyOptions().returnNew(false) = old document (not update yet)
                                User userTest6 = mongoOperation.findAndModify(
                                                query6, update6,
                                                new FindAndModifyOptions().returnNew(true), User.class);
                                System.out.println("userTest6 - " + userTest6);
                        }
                }

        }
}

