/*
* Creates a token when called.
*/
public class Token {
    private String keyType;
    private String typeValue;
    private Integer newLine;


    public Token(String lexType, String lexValue, Integer lexLine){
        this.keyType= lexType;
        this.typeValue= lexValue;
        this.newLine= lexLine;
        createString();
    }

    public String getKeyType() {
        return keyType;
    }

    public String getTypeValue() {
        return typeValue;
    }

    public Integer getNewLine() {
        return newLine;
    }

    /*
    * When a token is created, this prints its Type, Value, and Location.
    */
    private void createString(){
        System.out.println("Token Type: " + this.keyType);
        System.out.println("Token Value: " + this.typeValue);
        System.out.println("Line: " + this.newLine);
    }
}
