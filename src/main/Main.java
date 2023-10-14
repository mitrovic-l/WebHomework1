package main;

import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
    public static AtomicInteger suma = new AtomicInteger(0);
    public static AtomicInteger brojStudenata = new AtomicInteger(0);
    public static List<Student> listaStudenata = new ArrayList<>();
    public static BlockingQueue<Student> redProfesor;
    public static BlockingQueue<Student> redAsistent;
    public static long pocetak = 0;

    public static void main(String[] args) {

        System.out.println("Unesite broj studenata za odbranu: ");
        Scanner sc = new Scanner(System.in);
        int unos = Integer.parseInt(sc.next());

        int redAVelicina = 0;
        int redPVelicina = 0;
        for (int i = 0; i < unos; i++){
            Student noviStudent = new Student(i);
            if (noviStudent.getOdabir() == 0){
                redAVelicina++;
            } else {
                redPVelicina++;
            }
            listaStudenata.add(noviStudent);
        }
        listaStudenata.sort((o1, o2) -> {
            if (o1.getVremeDolaska() > o2.getVremeDolaska())
                return 1;
            if (o1.getVremeDolaska() == o2.getVremeDolaska())
                return 0;
            return -1;
        });
        redAsistent = new LinkedBlockingQueue<>();
        redProfesor = new LinkedBlockingQueue<>();
        for (int i  = 0; i < unos; i++){
            if (listaStudenata.get(i).getOdabir() == 0){
                redAsistent.add(listaStudenata.get(i));
            } else {
                redProfesor.add(listaStudenata.get(i));
            }
        }
        if (redPVelicina % 2 == 1 && redAVelicina > 0){
            try{
                redProfesor.add(redAsistent.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        ExecutorService threadPool = Executors.newFixedThreadPool(3); //dve niti za profesora, jedna nit za asistenta
        pocetak = System.currentTimeMillis();
        threadPool.submit( new AsistentThread());
        threadPool.submit( new ProfesorThread(1));
        threadPool.submit( new ProfesorThread(2));
        //System.out.println("Pocinje odbrana...");
        try{
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdownNow();   //posle 5s prekidaju se i aktivne odbrane
        }

        DecimalFormat df = new DecimalFormat("####0.00");
        System.out.println("Prosek: " + df.format(suma.get()*1.0 /brojStudenata.get()) + '\t' + "Broj izvrsenih odbrana: " + brojStudenata.get() + "/" + unos);
    }
}
