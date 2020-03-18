import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Trips {

    HashMap<String, Trips> allTrips = new HashMap<String, Trips>();
    String id;
    ArrayList<Stops> stops = new ArrayList<Stops>();

    public Trips(String idT, ArrayList<Stops> stop){
        this.id = idT;
        this.stops = stop;
    }

    public ArrayList<Stops> getStops(){
        return this.stops;
    }

    public String getID(){
        return this.id;
    }


}
