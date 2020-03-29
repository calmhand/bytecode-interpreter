import java.util.HashMap;

public class SymbolTable {
    public HashMap<String, Integer> hashTable= new HashMap<>();
    int newIndex= 0;
    int oldIndex= 0;

    public void add(String TokenID) {

        this.hashTable.put(TokenID, newIndex);
        newIndex++;
    }

    public int getAddressOld(String TokenID) {
        int oldAddy= this.hashTable.getOrDefault(TokenID, -1);
        oldIndex++;
        return oldAddy;
    }

    public String toString() {
        return "SymbolTable: "+ hashTable;
    }
}

