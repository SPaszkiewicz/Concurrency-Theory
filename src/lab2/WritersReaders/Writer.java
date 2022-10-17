package lab2.WritersReaders;

public class Writer implements Runnable {
    private Library library;

    public Writer (Library library) {
        this.library = library;
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.library.addWriter();
                System.out.println("Start writing");
                Thread.sleep(1000);
                System.out.println("Stop writing");
                this.library.removeWriter();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}