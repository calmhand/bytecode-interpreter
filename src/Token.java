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


    @Override
    public String toString() {
        return "Token{" +
                "keyType='" + keyType + '\'' +
                ", typeValue='" + typeValue + '\'' +
                ", newLine=" + newLine +
                '}';
    }
}
