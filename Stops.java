import java.util.ArrayList;

public class Stops {

    private String ID;
    private String name;
    private double lat;
    private double longi;
    private Location loc;

    ArrayList<Stops> Outgoing = new ArrayList<>();
    ArrayList<Stops> Incoming = new ArrayList<>();



    public Stops(String id, String name, double lat, double longi){
        this.ID = id;
        this.name = name;
        this.loc = loc;
        this.lat = lat;
        this.longi = longi;
    }

    public String getID(){
     return ID;
    }

    public ArrayList getOutgoing(){
        return Outgoing;
    }
}
