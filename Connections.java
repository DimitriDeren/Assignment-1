import java.awt.*;

public class Connections {

    String id;
    Stops incoming;
    Stops outgoing;

    Point in;
    Point out;

    Boolean highlight = false;

    private static final int SQUARE_SIZE = 3;

    Location coords = new Location(-10,10);

    public Connections(String idC, Stops in, Stops out){
        this.id = idC;
        this.incoming = in;
        this.outgoing = out;
    }

    public Stops getIncoming(){return incoming;}

    public Stops getOutgoing(){return outgoing;}

    public void draw(Graphics g, Location origin, double scale) {
        this.in = incoming.getLocation().asPoint(origin, scale);
        this.out = outgoing.getLocation().asPoint(origin, scale);
        if(!highlight) {
            g.setColor(Color.gray);
            g.drawLine(in.x, in.y, out.x, out.y);
        }

        if(highlight) {
            g.setColor(Color.red);
            g.drawLine(in.x, in.y, out.x, out.y);
        }

    }

    public Point getIncomingPoint(){
        return in;
    }

    public void setHightlight(Boolean b){
        highlight = b;
    }







}
