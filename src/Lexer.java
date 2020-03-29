import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Class to build an array of Tokens from an input file
 * @author wolberd
 * @see Token
 * @see Parser
 * @see SymbolTable //ByteCodeInterpreter
 */
public class Lexer {

    String buffer;
    int index = 0;
    int newlineCount= 1;
    public static final String INTTOKEN="INT";
    public static final String IDTOKEN="ID";
    public static final String ASSMTTOKEN="ASSMT";
    public static final String PLUSTOKEN="PLUS";
    public static final String EOFTOKEN="EOF";

    /**
     * call getInput to get the file data into our buffer
     * @param fileName the file we'll open
     */
    public Lexer(String fileName) {
        getInput(fileName);
    }

    /*
    * Checks if a character at a specific index is a PLUS, ASSMT, INT, or ID.
    * If a whitespace is found, the index increments and the loop proceeds.
    */
    public Token getNextToken() {

        if (this.buffer.charAt(this.index) == '\n'){
            this.newlineCount++;
            this.index++;
        }

        if (this.buffer.charAt(this.index) == '+') {
            Token tokenPLUS = new Token(PLUSTOKEN, "+", this.newlineCount);
            this.index++;
            return tokenPLUS;
        }

        if (this.buffer.charAt(this.index) == '=') {
            Token tokenASSMT= new Token(ASSMTTOKEN, "=", this.newlineCount);
            this.index++;
            return tokenASSMT;
        }

        if (Character.isDigit(this.buffer.charAt(this.index))){
            return getInteger();
        }

        if (Character.isLetter(this.buffer.charAt(this.index))){
            return getIdentifier();
        }

        if (Character.isWhitespace(this.buffer.charAt(this.index))){
            this.index++;
        }

        return null;
    }

    /*
    * Creates an ID token. Called from getNextToken.
    */
    private Token getIdentifier() {
        String IntId = "";
        int numLocator= 1;
        IntId+= this.buffer.charAt(this.index);

        if (this.index + 1 < this.buffer.length()){
            while (this.index + numLocator < this.buffer.length()){
                if (Character.isLetter(this.buffer.charAt(this.index + numLocator)) ||
                    Character.isLetter(this.buffer.charAt(this.index)) && Character.isDigit(this.buffer.charAt(this.index + numLocator))){
                    IntId+= this.buffer.charAt(this.index + numLocator);
                    numLocator++;
                }
                else if (Character.isWhitespace(this.buffer.charAt(this.index+numLocator))){
                    this.index++;
                    break;
                }
                else if (this.buffer.charAt(this.index+numLocator) == '\n'){
                    this.newlineCount++;
                    this.index++;
                    break;
                }
                else{
                    break;
                }
            }
        }

        this.index= this.index+numLocator;

        return new Token(IDTOKEN, IntId, this.newlineCount);
    }

    /*
    * Creates an Integer token. Called from getNextToken.
    */
    private Token getInteger() {
        String IntOp = "";
        int numLocator= 1;
        IntOp+= this.buffer.charAt(this.index);

        if (this.index + 1 < this.buffer.length()){ //avoids index error
            while (this.index + numLocator < this.buffer.length()){
                if (Character.isDigit(this.buffer.charAt(this.index + numLocator))) {
                    IntOp += this.buffer.charAt(this.index + numLocator);
                    numLocator++;
                }
                else if (this.buffer.charAt(this.index+numLocator) == '\n'){
                    this.newlineCount++;
                    this.index++;
                    break;
                }
                else if (Character.isWhitespace(this.buffer.charAt(this.index+numLocator))){
                    this.index++;
                    break; // goes to next index if there is a space
                }
                else{
                    break; // breaks if no space or digit
                }
            }
        }

        this.index= this.index+numLocator;

        return new Token(INTTOKEN, IntOp, this.newlineCount);
    }

    /*
    Creates ArrayList of all collected Tokens. After the loop breaks, it creates an EOF token.
    */
    public ArrayList<Token> getAllTokens() {
        ArrayList<Token> tokenList= new ArrayList<>();

        while (this.index < this.buffer.length()){
            tokenList.add(getNextToken());
        }

        tokenList.add(new Token(EOFTOKEN,"EndOfFile",0));

        while (tokenList.contains(null)){
            tokenList.remove(null);
        }

        return tokenList;
    }


    /**
     * Reads given file into the data member buffer
     *
     * @param fileName name of file to parse
    */
    private void getInput(String fileName) {
        try {
            Path filePath = Paths.get(fileName);
            byte[] allBytes = Files.readAllBytes(filePath);
            buffer = new String (allBytes);
        } catch (IOException e) {
            System.out.println ("You did not enter a valid file name in the run arguments.");
            System.out.print ("Please enter a string to be parsed: ");
            Scanner scanner = new Scanner(System.in);
            buffer=scanner.nextLine();
        }
    }


    public static void main(String[] args) {
        String fileName= "testwhitespace.txt";

        if (args.length==0) {
            System.out.println("You must specify a file name");
        } else {

            fileName=args[0];
        }

        Lexer lexer = new Lexer(fileName);
        System.out.println("Filename: " + fileName);
        System.out.println("Buffer: " + lexer.buffer);

        ArrayList<Token> listTokens= lexer.getAllTokens();

        System.out.println(listTokens);

    }
}

