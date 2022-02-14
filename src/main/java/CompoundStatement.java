import java.util.ArrayList;

//TODO: CHECK IF THIS IS A VALID MODEL FOR THE AST
public class CompoundStatement implements AST{
    ArrayList<AST> children;
    public CompoundStatement(){
        children = new ArrayList<>();
    }
    public void addStatement(AST statement) {
        children.add(statement);
    }

    @Override
    public void visit(Visitor visitor) {
        for (AST child : children) {
            child.visit(visitor);
        }
    }

    @Override
    public void print() {
        for (AST child : children) {
            child.print();
        }
    }
}
