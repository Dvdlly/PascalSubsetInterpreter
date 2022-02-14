public class BinaryOp implements AST {
    public AST left;
    public AST right;
    public Token operation;

    public BinaryOp(AST left, AST right, Token op) {
        this.left = left;
        this.right = right;
        operation = op;
    }

    @Override
    public void visit(Visitor visitor) {
        left.visit(visitor);
        right.visit(visitor);
    }

    @Override
    public void print(){
        left.print();
        System.out.println("Binary operation of type : " + operation.type);
        right.print();
    }
}
