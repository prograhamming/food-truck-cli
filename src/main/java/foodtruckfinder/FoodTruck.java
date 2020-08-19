package foodtruckfinder;


public class FoodTruck {
    public String dayorder;
    public String dayofweekstr;
    public String location;
    public String start24;
    public String end24;
    public String applicant;


    FoodTruck() {
    }

    public void foodTruckName()  {
        System.out.println( this.applicant + " " + this.location);
    }
}