public class Connections {

    String id;
    Stops incoming;
    Stops outgoing;

    public Connections(String idC, Stops in, Stops out){
        this.id = idC;
        this.incoming = in;
        this.outgoing = out;
    }

    public Stops getIncoming(){return incoming;}

    public Stops getOutgoing(){return outgoing;}



}
