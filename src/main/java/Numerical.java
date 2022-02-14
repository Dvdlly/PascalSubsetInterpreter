public class Numerical implements AST {
    public Type type;
    public Number value;
    public Numerical(Token token) {
        this.type = token.type;
        value = (Number) token.value;
    }

    @Override
    public void visit(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void print(){
        System.out.println("Node type : "+type +" value : " + value.toString());
    }
}
