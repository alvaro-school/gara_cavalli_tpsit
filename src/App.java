import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class App {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        System.out.println("=== Umamusume derby ===");
        
        System.out.println("numero di cavalli: ");
        int n = sc.nextInt();

        System.out.println("distanza gara: ");
        long distanza = sc.nextLong();

        Cavallo[] cavalli = new Cavallo[n];

        Rand global = new Rand(System.nanoTime());

        CountDownLatch latch = new CountDownLatch(1);
        Classifica classifica = new Classifica();

        for (int i = 0; i < n; ++i) {
            System.out.println("nome cavallo #"+i+" : ");
            String nome = sc.next();
            cavalli[i] = new Cavallo(nome, distanza, classifica, latch, global.next());
            cavalli[i].start();
        }

        System.out.println("PRONTI?");
        sc.nextLine();
        sc.nextLine();

        System.out.println("\n\n");
        
        for (int i = 3; i > 0; --i) {
            System.out.println("" + i + "...\n");
            Thread.sleep(1000);
        }
        System.out.println("VIA!\n");
        latch.countDown();

        for (int i = 0; i < n; ++i) {
            cavalli[i].join();
        }

        System.out.println("\nRISULTATI:");
        Queue<Risultato> clas = classifica.getResults();
        Risultato r = clas.poll();
        int i = 1;
        while (r != null) {
            double msDelta = r.ns() / 1_000_000.0;

            if (i == 1) {
                System.out.println(String.format("#%d : %s (VINCITORE)", i, r.name()));
            } else {
                System.out.println(String.format("#%d : %s (+%.3f ms)", i, r.name(), msDelta));
            }
            r = clas.poll();
            i++;
        }
    }
}
