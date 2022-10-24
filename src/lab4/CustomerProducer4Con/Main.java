package lab4.CustomerProducer4Con;

import java.util.concurrent.ThreadLocalRandom;

public class Main {
    static int bufforSize = 10000;
    static int customerNumber = 1;
    static int producentNumber =  3;

    static int foodPortion = 5000;

    public static void main(String[] args) {
        manyCustomerManyProducerManyBuffor();
    }

    public static int randomPortion(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
    public static void manyCustomerManyProducerManyBuffor () {
        Bakery bakery = new Bakery(bufforSize, foodPortion);
        new Thread(new Producent(bakery, foodPortion, -1)).start();
        for (int i = 0; i < customerNumber; i++) {
            new Thread(new Customer(bakery, foodPortion, i)).start();
        }
        for (int i = 0; i < producentNumber; i++) {
            new Thread(new Producent(bakery, 1, i)).start();
        }
    }
}
// na hasWaiter laduje za duza ilosc procesow dlaczego?
// Wchodzi producent i nikogo nie ma wiec wiesza sie na pierwszym , wchodzi konsument zjada zasobow czesc i wysyla sygnaly
// wysyla pierwszy sygnal do przyciwnika, drugi do reszty konsumentow, ten reszty ma przepisac tu i sam opuszcza locka i producent
// pierwszy zostaje zwolniony i przechodzi na locka i ten z reszty na razie pojdzie w powietrze, to jest najwazniejsze ze konsument wychdozac
// zwalnia pierwszego producenta i zlosliwie nie wpuszczamy producenta. Pierwszy while czy nie istnieje ktos na pierwszym
// jezeli zamiast zmiennej boolowskiej zastosujemy has waiters (java) to to jebnie i chuj. Zaglodzenie jak nie wiem co
// A gdy nie ma zasobow to dochodzi do zakleszczenia BOOOM!
// Watki konsumentow sie wywalaja