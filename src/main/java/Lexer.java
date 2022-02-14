import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

public class Lexer {
    Scanner scanner;
    private TreeMap<String,Command> reservedKeywords = new TreeMap<>();
    BufferedReader reader;
    int c;
    char currentChar;
    char nextChar;
    int lineNo = 0;
    int colNo = 0;
    private final String PROGRAM = "PROGRAM";
    private final String VAR = "VAR";
    private final String BEGIN = "BEGIN";
    private final String END = "END";
    private final String INTEGER = "INTEGER";
    private final String DOUBLE = "DOUBLE";

    public Lexer(File sourcecode) {
        try {
            reader = new BufferedReader(new InputStreamReader
                    (new FileInputStream(sourcecode), StandardCharsets.UTF_8));
            scanner = new Scanner(new FileReader(sourcecode));
            reservedKeywords.put(VAR, () -> new Token(Type.VARIABLE,"VAR"));
            reservedKeywords.put(BEGIN, () -> new Token(Type.BEGIN,"BEGIN"));
            reservedKeywords.put(END, () -> new Token(Type.END,"END"));
            reservedKeywords.put(PROGRAM, () -> new Token(Type.PROGRAM,"PROGRAM"));
            //TODO: WHAT ABOUT INTEGER AND DOUBLE KEYWORDS?
            advance();
            advance();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void advance() {
        try {
            c = reader.read();
            currentChar = nextChar;
            nextChar = (char) c;
            if (currentChar == '\n') {
                lineNo++;
                colNo = 0;
            }
            else {
                colNo++;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void skipWhitespaces() {
        while (currentChar == ' ') {
            advance();
        }
    }

    public Token parseNumber(){
        StringBuilder result = new StringBuilder();
        while (Character.isDigit(currentChar)) {
            result.append(currentChar);
            advance();
        }
        if (currentChar == '.') {
            result.append(currentChar);
            advance();
            while (Character.isDigit(currentChar)) {
                result.append(currentChar);
                advance();
            }
            return new Token(Type.DOUBLE,Double.parseDouble(result.toString()));
        }
        return new Token(Type.INTEGER,Integer.parseInt(result.toString()));
    }

    public Token parseID() {
        String result = "";
        while (Character.isLetter(currentChar) || Character.isDigit(currentChar)) {
            result += currentChar;
            advance();
        }
        if (reservedKeywords.containsKey(result))
            return reservedKeywords.get(result).runCommand();
        return new Token(Type.VARIABLE,result);
    }


    public Token nextToken() {
        if (currentChar == (char)-1) {
            return new Token(Type.EOF,"");
        }
        if (currentChar == '\n'){
            advance();
            return nextToken();
        }
        if (currentChar == ' ')
            skipWhitespaces();
        if (Character.isDigit(currentChar))
            return parseNumber();
        if (Character.isLetter(currentChar))
            return parseID();
        if (currentChar == '(') {
            advance();
            return new Token(Type.LPAREN, '(');
        }
        if (currentChar == ')') {
            advance();
            return new Token(Type.RPAREN, ')');
        }
        if (currentChar == '*') {
            advance();
            return new Token(Type.MUL, '*');
        }
        if (currentChar == '/') {
            advance();
            return new Token(Type.DIV, '/');
        }
        if (currentChar == '+') {
            advance();
            return new Token(Type.PLUS, '+');
        }
        if (currentChar == '-') {
            advance();
            return new Token(Type.MINUS, '-');
        }
        if (currentChar == ';'){
            advance();
            return new Token(Type.SEMI,';');
        }
        if (currentChar == ':' && nextChar == '=') {
            advance();
            advance();
            return new Token(Type.ASSIGNMENT, ":=");
        }
        if (currentChar == '.') {
            advance();
            return new Token(Type.DOT,'.');
        }
        return new Token(Type.EOF,"");
    }
}
