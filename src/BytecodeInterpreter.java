import java.util.ArrayList;
public class BytecodeInterpreter {

    public static final int LOAD= 0;
    public static final int LOADI= 1;
    public static final int STORE= 2;

    public ArrayList<Integer> bytecode = new ArrayList<Integer>();
    public ArrayList<Integer> memory = new ArrayList<Integer>();

    private int accumulator;
    private int sizeMemory;

    public BytecodeInterpreter() {
        System.out.println("constructor running");
    }

    public void generate() {
        System.out.println("Generate Method Running...");
    }

    public static void main(String[] args) {
        BytecodeInterpreter ByteInter = new BytecodeInterpreter();
    }

}