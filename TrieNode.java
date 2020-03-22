import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class TrieNode {
    private HashMap<Character, TrieNode> children;
    private ArrayList<Stops> stopSearch = new ArrayList<Stops>();

    public TrieNode() {
        children = new HashMap<Character,TrieNode>();
    }

    public HashMap<Character, TrieNode> getChildren(){
        return children;
    }

    public ArrayList<Stops> getStops(){
        return stopSearch;
    }

    public TrieNode getChild(char c){
        return children.get(c);
    }

    public void addChild(char c, TrieNode node){
        children.put(c, node);
    }

    public void addStop(Stops s){
        stopSearch.add(s);
    }
}