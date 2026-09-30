import java.util.concurrent.CountDownLatch;

public class Cavallo extends Thread {
    private final CountDownLatch startLatch;
    private final String nome;
    private final long distanza;
    private final Classifica classifica;
    private final Rand r;

    private static final String[] EVENTI_STANCHEZZA = {
        "e' stanco e si riposa",
        "ha visto un quadrifoglio e si è fermato a mangiarlo",
        "inciampa nelle sue stesse zampe e rotola per un po'",
        "si ferma a fare un selfie con i fan",
        "ha un crampo improvviso e urla nel vento",
        "subisce la maledizione della partenza lenta e si deconcentra",
        "si distrae a guardare gli altri cavalli sorpassarlo",
        "decide che è un ottimo momento per un pisolino tattico"
    };

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
                    
                    int indiceFrase = (int) Math.abs(r.next() % EVENTI_STANCHEZZA.length);
                    String eventoDivertente = EVENTI_STANCHEZZA[indiceFrase];

                    System.out.println("[" + nome + "] " + eventoDivertente + " per " + time + "ms!");
                    
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