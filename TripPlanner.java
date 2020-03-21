import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static java.lang.Double.MAX_VALUE;

public class TripPlanner extends GUI{

    private HashMap<String, Stops> allStops = new HashMap<String, Stops>();
    private HashMap<String, Stops> stopNames = new HashMap<String, Stops>();
    private HashSet<Stops> stopSet = new HashSet<>();
    private HashSet<Trips> trips = new HashSet<>();
    private HashSet<Connections> allConnections = new HashSet<>();

    Location origin = new Location(-24,19);
    Location clickLocation;

    double scale = 10;
    double ZOOM_FACTOR = 1.3;
    double distanceShort = MAX_VALUE;
    private static int SQUARE_SIZE = 3;
    private JTextArea text = getTextOutputArea();

    String input;


    protected void redraw(Graphics g) {

        for(Stops s : stopSet) {
            s.draw(g, origin, scale, SQUARE_SIZE, Color.red);
            }

        double distance = 10000;
        String append = "";
        if(clickLocation != null) {
            Stops closestStop = null;
            for (Stops stop : allStops.values()) {
                if (stop.getLocation().distance(clickLocation) < distance) {
                    closestStop = stop;
                    distance = stop.getLocation().distance(clickLocation);
                }
            }

            closestStop.draw(g, origin, scale, SQUARE_SIZE, Color.blue);
            for (Trips t : trips) {
                ArrayList<Stops> stopSequence = t.getStops();
                if(stopSequence.contains(closestStop)){
                    append += t.getID() + " ";
                    text.setText("Stop name : " + closestStop.getName() + "\nTrip IDs: " + append );

                }

            }
        }

            for (Connections c : allConnections) {
                c.draw(g, origin, scale, Color.gray);
                if(input != null){
                    if (c.getIncoming().getName().equals(input) || c.getOutgoing().getName().equals(input)) {
                     c.draw(g, origin, scale, Color.blue);
                     c.getIncoming().draw(g, origin, scale, SQUARE_SIZE, Color.blue);
                     c.getOutgoing().draw(g, origin, scale, SQUARE_SIZE, Color.blue);
                }

            }
        }


    }

    //use a for loop for all the stops which calls a draw method within the stops class for each one, pass in origin, scale and graphics object



    /**
     * Is called when the mouse is clicked (actually, when the mouse is
     * released), and is passed the MouseEvent object for that click.
     */
    protected void onClick(MouseEvent e) {
     Point click = e.getPoint();
       clickLocation = Location.newFromPoint(click, origin, scale);
        System.out.println(clickLocation);
    }

    /**
     * Is called whenever the search box is updated. Use getSearchBox to get the
     * JTextField object that is the search box itself.
     */
    protected void onSearch() {
        JTextField userInput = getSearchBox();
        input = userInput.getText();
    }

    /**
     * Is called whenever a navigation button is pressed. An instance of the
     * Move enum is passed, representing the button clicked by the user.
     */
    protected void onMove(Move m) {
        Dimension screenSize = getDrawingAreaDimension();
        double screenWidth = screenSize.getWidth();
        double screenHeight = screenSize.getHeight();

        Location topLeft = Location.newFromLatLon(0,0);
        Location topRight = Location.newFromLatLon(screenWidth, 0);
        Location botLeft = Location.newFromLatLon(0, screenHeight);
        Location botRight = Location.newFromLatLon(screenWidth, screenHeight);

        Point tL = topLeft.asPoint(origin, scale);
        Point tR = topRight.asPoint(origin, scale);
        Point bL = botLeft.asPoint(origin, scale);
        Point bR = botRight.asPoint(origin, scale);

        tL.setLocation(0, 0);
        tR.setLocation(screenWidth, 0);
        bL.setLocation(0, screenHeight);
        bR.setLocation(screenWidth, screenHeight);

        double width = topRight.newFromPoint(tR, origin, scale).x - topLeft.newFromPoint(tL, origin, scale).x;
        double height = botLeft.newFromPoint(bL, origin, scale).y - topLeft.newFromPoint(tL, origin, scale).y;

        if(m == Move.SOUTH){
            origin = origin.moveBy(0, 1);
            redraw();
        }

        if(m == Move.WEST){
            origin = origin.moveBy(1, 0);
            redraw();
        }

        if(m == Move.NORTH){
            origin = origin.moveBy(0, -1);
            redraw();
        }

        if(m == Move.EAST){
            origin = origin.moveBy(-1, 0);
            redraw();
        }

        if(m == Move.ZOOM_IN){
            scale *= ZOOM_FACTOR;
            width /= ZOOM_FACTOR;
            height /= ZOOM_FACTOR;
            origin = origin.moveBy((width - width / ZOOM_FACTOR) / 2, (height - height / ZOOM_FACTOR) / 2);

            SQUARE_SIZE += 1;

        }

        if(m == Move.ZOOM_OUT){

            origin = origin.moveBy(-(width - width / ZOOM_FACTOR) / 2, -(height - height / ZOOM_FACTOR) / 2);
            scale /= ZOOM_FACTOR;
            width *= ZOOM_FACTOR;
            height *= ZOOM_FACTOR;

            SQUARE_SIZE -= 1;
        }


    }

    /**
     * Is called when the user has successfully selected a directory to load the
     * data files from.
     *
     * @param stopFile the stops.txt file
     * @param tripFile the trips.txt file
     */
    protected void onLoad(File stopFile, File tripFile) throws IOException {
        FileReader fr = new FileReader(stopFile);
        BufferedReader br = new BufferedReader(fr);

        br.readLine();
        String line;

        while((line = br.readLine()) != null){
            String[] a = line.split("\t");
            Stops stop = new Stops(a[0], a[1],Double.parseDouble(a[2]), Double.parseDouble(a[3]));
            allStops.put(a[0], stop);
            stopNames.put(a[1], stop);
            stopSet.add(stop);
        }

        FileReader tf = new FileReader(tripFile);
        BufferedReader bf = new BufferedReader(tf);

        bf.readLine();
        String lines;

        while((lines = bf.readLine()) != null){
            ArrayList<Stops> temp = new ArrayList<Stops>();

            String[] b = lines.split("\t");
            for(int i = 1; i < b.length; i++){
                temp.add(allStops.get(b[i]));
            }
            Trips trip = new Trips(b[0], temp);
            trips.add(trip);
        }

        //get trip from set, which gives you the arraylist. Loop through that sequence, doing connections for that trip ID.
        for(Trips t : trips){
            ArrayList<Stops> stopSequence = t.getStops();
            for(int i = 0; i < stopSequence.size() - 1; i++){
                Stops incoming = allStops.get(stopSequence.get(i).getID());
                Stops outgoing = allStops.get(stopSequence.get(i + 1).getID());

                Connections connections = new Connections(t.getID(), incoming, outgoing);
                allConnections.add(connections);

                stopSequence.get(i+1).Incoming.add(incoming);
                stopSequence.get(i).Outgoing.add(outgoing);
            }
        }

        System.out.println("Stops: " + stopSet.size());
        System.out.println("Trips: "+ trips.size());
        System.out.println("Connections: "+ allConnections.size());

        //Two for loops, one for looping through id, another for looping through the arraylist.
        //in the arraylist, use the map to access those stop objects and do connections.

    }

    public static void main(String args[]){
        new TripPlanner();
    }




}
