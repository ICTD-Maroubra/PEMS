package org.maroubra.pemsserver.api.models;

public class UserOffset {
    //Statements are initialised for testing the as the system is not yet integrated with UI
    public static int tempMax = 50;
    public static int tempLow  = 20;
    public static int Wind = 100;
    public static int CO2 = 40;
    public static int O2 = 40;
    public static int getUserOffset() {
        
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is("appleB"));
        query.fields().include("name");
        
        User userTest2 = mongoOperation.findOne(query, User.class);
        System.out.println("userTest2 - " + userTest2);
        
        userTest2.setAge(99);
        
        mongoOperation.save(userTest2);
        
        // ooppss, you just override everything, it caused ic=null and
        // createdDate=null
        
        Query query1 = new Query();
        query1.addCriteria(Criteria.where("name").is("appleB"));
        
        User userTest2_1 = mongoOperation.findOne(query1, User.class);
        System.out.println("userTest2_1 - " + userTest2_1);
    }
}
