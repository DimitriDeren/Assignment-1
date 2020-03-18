import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class TripPlanner extends GUI{

    private HashMap<String, Stops> allStops = new HashMap<String, Stops>();
    private HashSet<Stops> stopSet = new HashSet<>();
    private HashSet<Trips> trips = new HashSet<>();
    private HashSet<Connections> allConnections = new HashSet<>();

    private static final int SQUARE_SIZE = 30;



    protected void redraw(Graphics g) {
        for(Stops s : stopSet){
            draw(g, s.getLocation(), 1);
        }
    }

    //use a for loop for all the stops which calls a draw method within the stops class for each one, pass in origin, scale and graphics object
    public void draw(Graphics g, Location origin, double scale) {
        g.setColor(Color.red);
       g.fillRect((int)origin.x, (int)origin.y, SQUARE_SIZE, SQUARE_SIZE);
    }

    /**
     * Is called when the mouse is clicked (actually, when the mouse is
     * released), and is passed the MouseEvent object for that click.
     */
    protected void onClick(MouseEvent e) {

    }

    /**
     * Is called whenever the search box is updated. Use getSearchBox to get the
     * JTextField object that is the search box itself.
     */
    protected void onSearch() {

    }

    /**
     * Is called whenever a navigation button is pressed. An instance of the
     * Move enum is passed, representing the button clicked by the user.
     */
    protected void onMove(Move m) {

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
                Stops incoming = allStops.get(stopSequence.get(i));
                Stops outgoing = allStops.get(stopSequence.get(i + 1));
                Connections connections = new Connections(t.getID(), incoming, outgoing);
                allConnections.add(connections);

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
