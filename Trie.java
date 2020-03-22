import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Trie {

    public Trie(){
    }

    private TrieNode root = new TrieNode();

    public void add(char[] stopName, Stops stop) {
        TrieNode current = root;

        for (char c : stopName) {
            if (!(current.getChildren().containsKey(c))) {
                current.addChild(c, new TrieNode());
            }
            current = current.getChild(c);
        }

        current.addStop(stop);
    }

    public ArrayList<Stops> get(char[] word){
        TrieNode current = root;

        for (char c : word) {
            if (!(current.getChildren().containsKey(c))) {
                return null;
            }
            current = current.getChild(c);
        }
        return current.getStops();
    }

    public ArrayList<Stops> getAll(char[] prefix) {
        ArrayList<Stops> results = new ArrayList<Stops>();
        TrieNode current = root;

        for(char c : prefix){
            if(!(current.getChildren().containsKey(c))){
                return null;
            }
            current = current.getChild(c);
        }
        getAllFrom(current, results);
        return results;
    }

    public void getAllFrom(TrieNode node, List<Stops> results){
        results.addAll(node.getStops());

        for(TrieNode n : node.getChildren().values()){
            getAllFrom(n, results);
        }
    }

}
