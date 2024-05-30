import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // Генерация 3 массивов целых положительных чисел
        int[] store1Revenue = IntStream.range(0, 100).map(num -> (int) (Math.random() * 100)).toArray();
        int[] store2Revenue = IntStream.range(0, 100).map(num -> (int) (Math.random() * 100)).toArray();
        int[] store3Revenue = IntStream.range(0, 100).map(num -> (int) (Math.random() * 100)).toArray();

        // Создание атомика для подсчета выручки
        LongAdder totalRevenue = new LongAdder();

        // Создание трех потоков, которые суммируют выручку (каждый по своему массиву) в общий отчет
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(() -> sumRevenue(store1Revenue, totalRevenue));
        executorService.submit(() -> sumRevenue(store2Revenue, totalRevenue));
        executorService.submit(() -> sumRevenue(store3Revenue, totalRevenue));

        // Главный поток ждет завершения всех расчетов
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        // Печать общего итога в консоль
        System.out.println("Общий итог: " + totalRevenue.sum());
    }

    private static void sumRevenue(int[] revenue, LongAdder totalRevenue) {
        for (int amount : revenue) {
            totalRevenue.add(amount);
        }
    }
}
