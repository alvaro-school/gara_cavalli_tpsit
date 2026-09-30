import java.util.LinkedList;
import java.util.Queue;

public class Classifica {
    boolean b = false;
    long first = 0;

    private Queue<Risultato> inner = new LinkedList<Risultato>();

    public Classifica() {}

    public synchronized void finish(String nome) {
        if (!b) {
            b = true;
            first = System.nanoTime();
            inner.add(new Risultato(nome, 0));
        } else {
            inner.add(new Risultato(nome, System.nanoTime() - first));
        }
    }

    public Queue<Risultato> getResults() {
        return inner;
    }
}
