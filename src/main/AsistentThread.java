package main;

//samo jednu odbranu u datom trenutku

import java.util.Random;

public class AsistentThread implements Runnable{

    public AsistentThread(){
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
                Student student = Main.redAsistent.take();
                if (student.daLiStudentKasni(Main.pocetak, System.currentTimeMillis())){
                    Thread.sleep(student.sacekajStudenta(Main.pocetak, System.currentTimeMillis()));
                }
                long pocetakOdbrane = System.currentTimeMillis() - Main.pocetak;
                //System.out.println("    [ASISTENT] Zapoceta odbrana za: <Student"+student.getIndeks() + ">");
                Thread.sleep(student.getTrajanjeOdbrane()); //izvrsava se odbrana
                int ocena = new Random().nextInt(5) + 5;
                System.out.println("Thread: <Student" + student.getIndeks() + "> Arrival: " + (student.getVremeDolaska()) + " [ASISTENT]" + " TTC: " + student.getTrajanjeOdbrane() +" : " + pocetakOdbrane + " Score: " + ocena );
                Main.suma.addAndGet(ocena);
                Main.brojStudenata.addAndGet(1);
            } catch (InterruptedException e){
                //e.printStackTrace();
                System.err.println("Prekidaju se odbrane!");
                break;
            }
        }
    }
}
