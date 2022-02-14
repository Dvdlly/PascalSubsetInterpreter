import java.io.File;

public class Tests {
    public static void main(String[] args) {
        File sourcecode = new File("/home/max/Developement/Java/PascalInterpreter/pascal.pas");
        Parser testparser = new Parser(sourcecode);
        AST ast = testparser.parseProgram();
        Visitor visitor = new Visitor();
        ast.visit(visitor);

        /*
        Lexer testlexer = new Lexer(sourcecode);
        Token next = testlexer.nextToken();
        while (next.type != Type.EOF) {
            System.out.println("Token : " + next.type + " value : " + next.value);
            next = testlexer.nextToken();
        }

         */
        //AST ast = testparser.parseProgram();
        //ast.print();
    }
}
