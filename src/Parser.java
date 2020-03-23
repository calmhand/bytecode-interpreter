import java.util.ArrayList;
import java.util.Scanner;

public class Parser {
    public ArrayList<Token> tokenArray;
    public SymbolTable tokenMap= new SymbolTable();
    int index= 0;

    /*
    * Constructor for Class Parser
    * Runs Lexer and send each token into an Array of tokens.
    */
    public Parser(String fileName){
        Lexer parseLexer = new Lexer(fileName);
        this.tokenArray = parseLexer.getAllTokens();
    }

    /*
    * First checks if the first token in a program is an identifier (error if there isn't).
    * Loops parseAssignment method to parse each individual token and make
    * sure their placements result in a valid program.
    */
    public void parseProgram() { // calls parseAssignment in a loop.
        if (!this.tokenArray.get(0).getKeyType().equals("ID")) {
            System.out.println("ERROR EXPECTING OPENING ID");
        }
        else{
            while (index < this.tokenArray.size()) {
                parseAssignment();
                nextToken(); //ADDS TO OVERALL INDEX (NOT USED FOR CHECKING NEXT TOKEN)
            }
        }
    }

    /*
    * Checks each token in the tokenArray.
    * Looped by parseProgram method.
    * If the last token is reached (EOF), the program will be valid.
    */
    private void parseAssignment() {

        /*
        * Checks if the program is valid.
        */
        if (this.tokenArray.get(index).getKeyType().equals("EOF")){ // CHECKS IF EOF
            System.out.println("\n");
            System.out.println("Program is Valid.");
        }

        /*
        * Checks if a token at an index of the tokenArray is an INT, Used ID, or PLUS.
        */
        if (this.tokenArray.get(index).getKeyType().equals("INT") || this.tokenArray.get(index).getKeyType().equals("ID") &&
                this.tokenMap.hashTable.containsKey(this.tokenArray.get(index).getTypeValue()) ||
                this.tokenArray.get(index).getKeyType().equals("PLUS")){
            parseExpression();
        }

        /*
        *Checks if a token at an index of the tokenArray is an Unused ID.
        */
        if (this.tokenArray.get(index).getKeyType().equals("ID") &&
                !this.tokenMap.hashTable.containsKey(this.tokenArray.get(index).getTypeValue())) {
            parseId();
        }

        /*
        * Checks if a token at an index of the tokenArray is an Assignment Operator (=).
        */
        if (this.tokenArray.get(index).getKeyType().equals("ASSMT")) { //CHECKS IF ASSMT
            parseOperator();
        }

    }

    /*
    * Parses Unused ID tokens.
    * If an Unused ID is found while parseExpression is running, this method will be called within parseExpression.
    */
    private void parseId() { //parses ID tokens on left or right side. called by parseExpression if on right side.

        tokenMap.add(this.tokenArray.get(index).getTypeValue());
        System.out.println("New ID Added To Map ***: " + this.tokenArray.get(index).getTypeValue());
        nextToken();

        if (!this.tokenArray.get(index).getKeyType().equals("ASSMT")) { //CHECKS IF TOKEN AFTER UNUSED ID IS AN ASSMT
            System.out.println("Expecting Assignment Operator: Error Line " + this.tokenArray.get(index).getNewLine());
            index= this.tokenArray.size() - 1; // breaks loop
        }
        else{
            putToken(); //RETURNS TO INITIAL INDEX
        }
    }

    /*
    * Parses Assignment tokens.
    * Determines when an expression begins it's right side.
    * Causes parseExpression to be ran.
    */
    private void parseOperator() { //parses "=". Decides if expression is on left or right side.
        System.out.println("Equals Assignment: " + this.tokenArray.get(index).getTypeValue());
        nextToken(); //CHECKING NEXT TOKEN IN ARRAY

        if (this.tokenArray.get(index).getKeyType().equals("INT") || this.tokenArray. get(index).getKeyType().equals("ID")) { //CHECKS IF NEXT TOKEN IS AN INT OR ID
            putToken(); //RETURNS TO INITIAL INDEX
        }
        else{
            System.out.println("Expecting INT/ID: Assignment Operator Error");
            index= this.tokenArray.size() - 1;
        }
    }

    /*
    * Parses INT, PLUS, Used & Unused ID tokens.
    * Called after parseOperator if program is valid.
    */
    private void parseExpression() {
        /*
        * Checks if a PLUS token was received.
        * It also checks if the next token is *NOT* a INT or a Used ID token.
        * An error will be shown if the next token is not a INT or Used ID token.
        */
        if (this.tokenArray.get(index).getKeyType().equals("PLUS")) { //CHECKS IF PLUS TOKEN WAS RECIEVED
            System.out.println("Plus Operator: " + this.tokenArray.get(index).getTypeValue());
            nextToken(); //CHECKS IF NEXT TOKEN IS AN INT OR USED ID

            if (!this.tokenArray.get(index).getKeyType().equals("INT")) {
                if (!this.tokenArray.get(index).getKeyType().equals("ID") &&
                        this.tokenMap.hashTable.containsKey(this.tokenArray.get(index).getTypeValue())){
                    System.out.println("ERROR EXPECTING INT OR UNUSED ID AFTER PLUS");
                    index= this.tokenArray.size() - 1;
                }
            }
            else{
                putToken();
            }
        }

        /*
        * Checks if an INT token was received.
        * It also checks if the next token is supposed to be a PLUS token.
        * If the next token is neither a ID, PLUS, or EOF, an error will be shown.
        */
        if (this.tokenArray.get(index).getKeyType().equals("INT")) { //CHECKS IF INT TOKEN WAS RECIEVED
            System.out.println("Integer: " + this.tokenArray.get(index).getTypeValue());
            nextToken(); //CHECKS IF NEXT TOKEN IS PLUS

            if (!this.tokenArray.get(index).getKeyType().equals("PLUS") &&
                    !this.tokenArray.get(index).getKeyType().equals("ID") && !this.tokenArray.get(index).getKeyType().equals("EOF")) {
                System.out.println("ERROR EXPECTING PLUS OR ID AFTER INT");
                index= this.tokenArray.size() - 1;
            }
            else{
                putToken();
            }

        }

        /*
        * Checks to see if a used ID token was received.
        * Used meaning if it's in the HashMap already, it was parsed once before.
        */
        if (this.tokenArray.get(index).getKeyType().equals("ID") &&
                this.tokenMap.hashTable.containsKey(this.tokenArray.get(index).getTypeValue())) {
            System.out.println("Used ID: " + this.tokenArray.get(index).getTypeValue());
            nextToken();

            if (!this.tokenArray.get(index).getKeyType().equals("PLUS") && !this.tokenArray.get(index).getKeyType().equals("EOF")) {
                System.out.println("ERROR EXPECTING PLUS AFTER USED ID");
                index= this.tokenArray.size() - 1;
            }
            else{
                putToken();
            }
        }

        /*
        * Checks to see if an ID token is not present in the HashMap was received.
        * Not present meaning this ID has not been parsed before.
        */
        if (this.tokenArray.get(index).getKeyType().equals("ID") &&
                !this.tokenMap.hashTable.containsKey(this.tokenArray.get(index).getTypeValue())) {

            System.out.println("Unused ID: " + this.tokenArray.get(index).getTypeValue());
            parseId(); //CHECKS IF NEXT TOKEN IS AN ASSMT. INDEX REDUCTION NOT NEEDED AFTER CALL.
        }
    }

    /*
    * Puts data members of Parse class into a string then prints.
    */
    public String toString() {
        return "Parser{" + "tokenArray=" + tokenArray + ", tokenMap=" + tokenMap + ", index=" + index + '}';
    }

    private void nextToken() {
        this.index++; //Increments Index.
    }

    private void putToken(){
        this.index--; // Decrements Index.
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter The File You Wish To Parse: ");
        String fileName= in.next();

        System.out.println("Parser Starts Here.");
        System.out.println("Parsing Selected File: " + fileName);

        Parser parser = new Parser(fileName);
        parser.parseProgram();
    }
}

