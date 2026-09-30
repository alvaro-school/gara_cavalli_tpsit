import java.util.LinkedList;
import java.util.Queue;

public class Classifica {
    private Queue<String> inner = new LinkedList<String>();

    public Classifica() {}

    public synchronized void finish(String nome) {
        inner.add(nome);
    }

    public Queue<String> getResults() {
        return inner;
    }
}
