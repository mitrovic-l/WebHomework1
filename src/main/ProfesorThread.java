package main;

//iskljucivo dve odbrane u datom trenutku

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;

public class ProfesorThread implements Runnable{

    private int profesorID;
    public ProfesorThread(int profesorID){
        this.profesorID = profesorID;
    }
    public int getProfesorID() {
        return profesorID;
    }
    public void setProfesorID(int profesorID) {
        this.profesorID = profesorID;
    }

    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        while (true){
            try{
                Student student = Main.redProfesor.take();
                if (student.daLiStudentKasni(Main.pocetak, System.currentTimeMillis())){
                    Thread.sleep(student.sacekajStudenta(Main.pocetak, System.currentTimeMillis()));
                }
                Main.cyclicBarrier.await();
                long pocetakOdbrane = System.currentTimeMillis() - Main.pocetak;
               // System.out.println("    [PROFESOR] Zapoceta odbrana za: <Student"+student.getIndeks() + ">");
                Thread.sleep(student.getTrajanjeOdbrane());
                int ocena = new Random().nextInt(6) + 5;
                System.out.println("Thread: <Student" + student.getIndeks() + "> Arrival: " + (student.getVremeDolaska()) + " [PROFESOR " + profesorID + "] " + " TTC: " + student.getTrajanjeOdbrane() +" : " + pocetakOdbrane + " Score: " + ocena );
                Main.suma.addAndGet(ocena);
                Main.brojStudenata.addAndGet(1);
            } catch (BrokenBarrierException | InterruptedException e) {
               // e.printStackTrace();
                System.err.println("Prekidaju se odbrane!");
                break;
            }
        }
    }
}
