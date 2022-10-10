public class Counter {
    private int number;
    public Counter() {
        number = 0;
    }

    public void increment() {
        this.number += 1;
    }

    public void decreament() {
        this.number -= 1;
    }

    public void show() {
        System.out.println(this.number);
    }
}
