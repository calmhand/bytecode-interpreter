import java.util.ArrayList;
public class BytecodeInterpreter {

    public static final int LOAD= 0;
    public static final int LOADI= 1;
    public static final int STORE= 2;

    public ArrayList<Integer> bytecode = new ArrayList<>();
    public ArrayList<Integer> memory = new ArrayList<>();

    private int accumulator= 0;
    private int memoryIndex= 0;
    int memorySize = 10;

    public BytecodeInterpreter() {
        System.out.println("Size of Memory: " + memorySize);
        for (int i = 0; i < memorySize; i++) {
            memory.add(0);
        }
    }

    private void LOAD(int num) {
        accumulator+= num;
    }

    private void LOADI(int address) {
        accumulator+= memory.get(address);
    }

    private void STORE() {
        if (memoryIndex >= memory.size()) {
            System.out.println("Error: Out Of Bounds");
        }
        else {
            memory.set(memoryIndex, accumulator);
            memoryIndex++;
            accumulator= 0;
        }
    }

    public int getLOAD() {
        return LOAD;
    }

    public int getLOADI() {
        return LOADI;
    }

    public int getSTORE() {
        return STORE;
    }

    public void generate(int op, int num) {

        if (op == 0 || op == 1) {
            this.bytecode.add(op);
            this.bytecode.add(num);
        }

        if (op == 2) {
            this.bytecode.add(op);
            this.bytecode.add(num);
        }
    }

    public void run(ArrayList<Integer> bytecode) {

        for (int i= 0; i < bytecode.size(); i++) {

            if (i%2== 0) { //operations are always going to be on an even index number
                if (bytecode.get(i) == 0) { //if LOAD, peek at next index (value) and call its respective method.
                    i++;
                    LOADI(bytecode.get(i));
                    i--;
                }
                else if (bytecode.get(i) == 1) { //if LOADI, peek at next index (value) and call its respective method.
                    i++;
                    LOAD(bytecode.get(i));
                    i--;
                }
                else if (bytecode.get(i) == 2) { //if STORE, peek at next index (value) and call its respective method.
                    i++;
                    STORE();
                    i--;
                }
            }
        }
        System.out.println("Memory: " + memory);
    }
}