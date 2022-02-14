public class Visitor {
    public void visit(CompoundStatement compoundStatement) {
        for (AST child : compoundStatement.children) {
            visit(child);
        }
    }


    //TODO: HOW TO TRANSMIT THE INFORMATION WHETHER ITS INT OR DOUBLE?
    public Number visit(Numerical num) {
        return num.value;
    }

    // THE VISIT METHOD MAY RETURN EITHER INT OR DOUBLE HOW CAN WE HANDLE THIS?
    public Number visit(BinaryOp binop){
        if (binop.operation.type == Type.PLUS) {
            return visit(binop.left) + visit(binop.right);
        }
    }
}
