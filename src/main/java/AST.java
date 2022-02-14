public interface AST {
    public void visit(Visitor visitor);
    public void print();
}
