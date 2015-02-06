package generateRegs;

import fetch.VehicleCheckcouk;
import nl.flotsam.xeger.Xeger;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Bootstrap {
    public static ExecutorService executorService = Executors.newFixedThreadPool(50);
    public static AtomicInteger counter = new AtomicInteger();
    public static AtomicInteger totalDone = new AtomicInteger();
    public static void main(String args[]) {
        VehicleCheckcouk vehicleCheckcouk = new VehicleCheckcouk();

        int dups = 0;
        while(dups < 1000000) {
            //for(int i = 0; i < 10000; i++) {
            String reg = generateAnother();

            if(vehicleCheckcouk.fetchFromDb(reg) != null) {
                dups++;
                continue;
            }

            executorService.submit(loadData(reg, vehicleCheckcouk));
            counter.getAndIncrement();
        }

        while(counter.get() > 0) {
            //do nothing
        }

    }

    private static Runnable loadData(final String reg, final VehicleCheckcouk checker) {
        return new Runnable() {
            @Override
            public void run() {
                checker.loadInfo(reg);
                counter.decrementAndGet();
                int total = totalDone.incrementAndGet();

                if((total % 1000) == 0) {
                    System.out.println(total);
                }
            }
        };
    }

    private static String generateAnother() {
        String regex = "[A-Z]{2}[0-9]{2}[A-Z]{3}";
        Xeger generator = new Xeger(regex);
        String result = generator.generate();

        return result;
    }

//    @SneakyThrows
//    private static void writeToFile() {
//        File file = new File("~/registrations2.txt");
//
//        // if file doesnt exists, then create it
//        if (!file.exists()) {
//            file.createNewFile();
//        }
//
//        FileWriter fw = new FileWriter(file.getAbsoluteFile());
//        BufferedWriter bw = new BufferedWriter(fw);
//
//        for(String registration : registrations) {
//            bw.append(registration + "\n");
//        }
//
//        bw.close();
//
//    }
}
