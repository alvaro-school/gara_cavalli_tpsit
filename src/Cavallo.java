import java.util.concurrent.CountDownLatch;

public class Cavallo extends Thread {
    private final CountDownLatch startLatch;
    private final String nome;
    private final long distanza;
    private final Classifica classifica;
    private final Rand r;

    public Cavallo(String nome, long distanza, Classifica classifica, CountDownLatch startLatch, long seed) {
        this.nome = nome;
        this.distanza = distanza;
        this.classifica = classifica;
        this.startLatch = startLatch;
        this.r = new Rand(seed);
    }

    public void run() {
        try {
            // ready, set ....
            startLatch.await(); 

            // ... go!
            for (long i = 0; i <= distanza; ++i) {
                if (i % 10 == 0 || i == distanza) {
                    System.out.println("[" + nome + "] ha percorso " + i + "m!");
                }

                Thread.sleep(20); 

                long rand = r.next();
                if (Math.abs(rand % 8) == 1) {
                    long time = Math.abs(r.next() % 400) + 400;
                    System.out.println("[" + nome + "] e' stanco e si riposa per " + time + "ms!");
                    Thread.sleep(time);
                }
            }

            // winner?
            classifica.finish(nome);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}