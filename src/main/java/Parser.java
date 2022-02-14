import java.io.File;
import java.util.ArrayList;

public class Parser {
    private Lexer lexer;
    private Token currentToken;
    public Parser(File input) {
        this.lexer = new Lexer(input);
        this.currentToken = lexer.nextToken();
    }
    public void eat(Type type) {
        if (!(currentToken.type == type)) {
            throw new IllegalArgumentException("Error parsing input, expected "
                    + type + " but got " + currentToken.type + " in line " + lexer.lineNo + " column " + lexer.colNo);
        }
        currentToken = lexer.nextToken();
    }

    public AST parseProgram() {
        AST root = parseCompoundStatement();
        eat(Type.DOT);
        return root;
    }

    public AST parseCompoundStatement() {
        eat(Type.BEGIN);
        ArrayList<AST> nodes = parseStatementList();
        eat(Type.END);
        CompoundStatement root = new CompoundStatement();
        for (AST node : nodes) {
            root.addStatement(node);
        }
        return root;
    }

    public ArrayList<AST> parseStatementList(){
        ArrayList<AST> statements = new ArrayList<>();
        statements.add(parseStatement());
        while (currentToken.type == Type.SEMI) {
            eat(Type.SEMI);
            statements.add(parseStatement());
        }
        return statements;
    }

    public AST parseStatement(){
        if (currentToken.type == Type.BEGIN){
            return parseCompoundStatement();
        }
        if (currentToken.type == Type.VARIABLE) {
            return parseAssignmentStatement();
        }
        else
            return new NoOp();
    }

    public AST parseVariable(){
        AST node = new Variable(currentToken);
        eat(Type.VARIABLE);
        return node;
    }

    public AST parseAssignmentStatement(){
        AST left = parseVariable();
        eat(Type.ASSIGNMENT);
        AST right = parseExpression();
        return new Assignment(left,right);
    }

    public AST parseExpression() {
        AST node = parseTerm();
        while (currentToken.type == Type.PLUS || currentToken.type == Type.MINUS) {
            Token current = currentToken;
            if (current.type == Type.PLUS) {
                eat(Type.PLUS);
            }
            if (current.type == Type.MINUS) {
                eat(Type.MINUS);
            }
            node = new BinaryOp(node,parseTerm(),current);
        }
        return node;
    }

    public AST parseTerm() {
        AST node = parseFactor();
        while (currentToken.type == Type.MUL || currentToken.type == Type.DIV) {
            Token current = currentToken;
            if (current.type == Type.MUL)
                eat(Type.MUL);
            if (current.type == Type.DIV)
                eat(Type.DIV);
            node = new BinaryOp(node,parseFactor(),current);
        }
        return node;
    }

    public AST parseFactor() {
        Token token = currentToken;
        if (token.type == Type.INTEGER) {
            eat(Type.INTEGER);
            return new Numerical(token);
        }
        if (token.type == Type.PLUS) {
            eat(Type.PLUS);
            return new UnaryOp(token,parseFactor());
        }
        if (token.type == Type.MINUS) {
            eat(Type.MINUS);
            return new UnaryOp(token,parseFactor());
        }
        else if (token.type == Type.LPAREN) {
            eat(Type.LPAREN);
            AST expression = parseExpression();
            eat(Type.RPAREN);
            return expression;
        }
        else {
            return parseVariable();
        }
    }
}
