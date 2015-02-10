package generateRegs;

import fetch.VehicleCheckcouk;
import lombok.SneakyThrows;
import nl.flotsam.xeger.Xeger;

import java.util.concurrent.atomic.AtomicInteger;

public class Bootstrap {
    public static AtomicInteger counter = new AtomicInteger();
    public static AtomicInteger totalDone = new AtomicInteger();
    public static AtomicInteger totalChecked = new AtomicInteger();
    public static ModernValidator validator = new ModernValidator();

    @SneakyThrows
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

            loadData(reg, vehicleCheckcouk).run();
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
                String upper = reg.toUpperCase();
                if(validator.validateReg(upper)) {
                    checker.loadInfo(upper);
                    counter.decrementAndGet();
                    int total = totalDone.incrementAndGet();

                    if((total % 100) == 0) {
                        System.out.println("Total valid and checked: " + total);
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
