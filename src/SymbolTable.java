import java.util.HashMap;

public class SymbolTable {
    public HashMap<String, Integer> hashTable= new HashMap<>();
    int index= 0;

    public void add(String TokenID) {

        this.hashTable.put(TokenID, index);
        index++;
    }

    public Integer getAddress(String TokenID) {

        return this.hashTable.getOrDefault(TokenID, -1);
    }

    public String toString() {
        return "SymbolTable{" + "hashTable=" + hashTable + ", index=" + index + '}';
    }
}

