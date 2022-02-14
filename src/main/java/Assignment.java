public class Assignment implements AST{
    private AST variable;
    private AST expression;

    public Assignment(AST variable, AST expression) {
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public void visit(Visitor visitor) {
        expression.visit(visitor);
    }

    @Override
    public void print() {
        System.out.print("Assignment of variable : ");
        variable.print();
        expression.print();
    }
}
