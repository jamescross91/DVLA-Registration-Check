package generateRegs;

import lombok.SneakyThrows;
import nl.flotsam.xeger.Xeger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashSet;

public class Bootstrap {
    public static HashSet<String> registrations  = new HashSet<>();
    public static void main(String args[]) {
        int dups = 0;
        while(dups < 1000000) {
        //for(int i = 0; i < 10000; i++) {
            String reg = generateAnother();

            if(registrations.contains(reg)) {
                dups++;
            }

            registrations.add(reg);

            if((registrations.size() % 10000) == 0) {
                System.out.println(registrations.size());
            }
        }

        writeToFile();
        System.out.println(registrations.size() + " registrations written to file");
        System.out.println();
    }

    private static String generateAnother() {
        String regex = "[A-Z]{2}[0-9]{2}[A-Z]{3}";
        Xeger generator = new Xeger(regex);
        String result = generator.generate();

        return result;
    }

    @SneakyThrows
    private static void writeToFile() {
        File file = new File("~/registrations2.txt");

        // if file doesnt exists, then create it
        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);

        for(String registration : registrations) {
            bw.append(registration + "\n");
        }

        bw.close();

    }
}
