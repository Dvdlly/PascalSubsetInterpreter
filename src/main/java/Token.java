enum Type {
    VARIABLE,
    PLUS,
    MINUS,
    MUL,
    DIV,
    INTEGER,
    DOUBLE,
    BEGIN,
    END,
    PROGRAM,
    SEMI,
    DOT,
    LPAREN,
    RPAREN,
    EOF,
    ASSIGNMENT
    // TBC...
}

public class Token {
    Type type;
    Object value;

    public Token(Type type, Object value) {
        this.type = type;
        this.value = value;
    }
}
