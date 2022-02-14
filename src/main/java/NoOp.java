public class NoOp implements AST{

    @Override
    public void visit(Visitor visitor) {

    }

    @Override
    public void print() {
        System.out.println("No Operation.");
    }
}
