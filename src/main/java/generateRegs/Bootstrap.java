package generateRegs;

import fetch.VehicleCheckcouk;
import nl.flotsam.xeger.Xeger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Bootstrap {
    public static ExecutorService executorService = Executors.newFixedThreadPool(50);
    public static AtomicInteger counter = new AtomicInteger();
    public static AtomicInteger totalDone = new AtomicInteger();
    public static AtomicInteger totalChecked = new AtomicInteger();
    public static ModernValidator validator = new ModernValidator();
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
                if(validator.validateReg(reg)) {
                    checker.loadInfo(reg);
                    counter.decrementAndGet();
                    int total = totalDone.incrementAndGet();

                    if((total % 1) == 0) {
                        System.out.println("Total added to db: " + total);
                    }
                }

                int checked = totalChecked.getAndIncrement();
                if((checked % 10000) == 0) {
                    System.out.println("Total Checked: " + checked);
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
}
