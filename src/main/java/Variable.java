public class Variable implements AST {
    private Token value;

    public Variable(Token token) {
        value=token;
    }

    @Override
    public void visit(Visitor visitor) {
    }

    @Override
    public void print() {
        System.out.println("Variable with value : " + value.value);
    }
}
