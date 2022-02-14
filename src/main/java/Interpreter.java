import java.io.File;

public class Interpreter {
    private Parser parser;

    public Interpreter(File sourcecode) {
        parser = new Parser(sourcecode);
    }

}
