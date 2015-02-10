package generateRegs;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ModernValidator {
    private final Set<String> prefixes = new HashSet<>();
    private final Set<String> years = new HashSet<>();

    @SneakyThrows
    public ModernValidator() {
        init();
    }

    public boolean validateReg(String registration) {
        String prefix = registration.substring(0, 2);
        String year = registration.substring(2, 4);

        boolean prefixOk = prefixes.contains(prefix);
        boolean yearOk = years.contains(year);
        if((!prefixOk) || (!yearOk)) {
            return false;
        } else {
            return true;
        }
    }

    private void init() throws IOException {
        initPrefixes();
        initYears();
    }

    private void initPrefixes() throws IOException {
        URL url = Resources.getResource("modernprefix");
        String file = Resources.toString(url, Charsets.UTF_8);
        String[] prefixesArray = file.split("\n");

        prefixes.addAll(Arrays.asList(prefixesArray));
    }

    private void initYears() throws IOException {
        URL url = Resources.getResource("modernyears");
        String file = Resources.toString(url, Charsets.UTF_8);
        String[] yearsArray = file.split("\n");

        years.addAll(Arrays.asList(yearsArray));
    }
}
