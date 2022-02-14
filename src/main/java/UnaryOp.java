
public class UnaryOp implements AST{
    private Token type;
    private AST value;

    public UnaryOp(Token token, AST value) {
        this.type = token;
        this.value = value;
    }

    @Override
    public void visit(Visitor visitor) {
    }

    @Override
    public void print() {
        System.out.println("Unary operation of sign : " + type.value);
    }
}
