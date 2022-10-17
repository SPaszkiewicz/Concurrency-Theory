package lab2.WritersReaders;

public class Reader implements Runnable {
    private Library library;

    public Reader (Library library) {
        this.library = library;
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.library.addReader();
                System.out.println("Start reading");
                Thread.sleep(1000);
                System.out.println("Stop reading");
                this.library.removeReader();
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
