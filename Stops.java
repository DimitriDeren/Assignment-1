import java.awt.*;
import java.util.ArrayList;

public class Stops {

    private String ID;
    private String name;
    private double lat;
    private double longi;
    private Location loc;

    ArrayList<Stops> Outgoing = new ArrayList<>();
    ArrayList<Stops> Incoming = new ArrayList<>();


    private static final int SQUARE_SIZE = 3;



    public Stops(String id, String name, double latitude, double longitude){
        this.ID = id;
        this.name = name;
        this.lat = latitude;
        this.longi = longitude;
        this.loc = Location.newFromLatLon(lat, longi);

    }

    public String getID(){
     return ID;
    }

    public ArrayList getOutgoing(){
        return Outgoing;
    }

    public Location getLocation() {return loc;}


    public void draw(Graphics g, Location origin, double scale) {
        g.setColor(Color.red);
        g.fillRect(loc.asPoint(origin, 10).x, loc.asPoint(origin, 10).y, SQUARE_SIZE, SQUARE_SIZE);
    }


}
