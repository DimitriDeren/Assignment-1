import java.awt.*;
import java.util.ArrayList;

public class Stops {

    private String ID;
    private String name;
    private double lat;
    private double longi;
    private Location loc;
    private boolean highlight = false;

    ArrayList<Stops> Outgoing = new ArrayList<>();
    ArrayList<Stops> Incoming = new ArrayList<>();






    public Stops(String id, String name, double latitude, double longitude){
        this.ID = id;
        this.name = name;
        this.lat = latitude;
        this.longi = longitude;
        this.loc = Location.newFromLatLon(lat, longi);

    }

    public String getName(){ return this.name; }

    public String getID(){
     return ID;
    }

    public ArrayList getOutgoing(){
        return Outgoing;
    }

    public Location getLocation() {return loc;}



    public void draw(Graphics g, Location origin, double scale, int squareSize, Color colour) {
        g.setColor(colour);
        
        g.fillRect(loc.asPoint(origin, scale).x, loc.asPoint(origin, scale).y, squareSize, squareSize);
    }

}
