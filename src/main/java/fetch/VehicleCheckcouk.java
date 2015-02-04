package fetch;

import entities.Vehicle;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class VehicleCheckcouk extends GenericDataSource implements VehicleDataSource {

    private static final String BASE_URL = "https://www.vehiclecheck.co.uk/?SC=132&ConfirmVRM=Continue&VRM=";

    @Override
    public Vehicle loadInfo(String registration) {
        Vehicle fromDb = fetchFromDb(registration);
        if(fromDb != null) {
            return fromDb;
        }

        String detailString = loadPage(registration);
        Vehicle vehicle = parseDetailString(registration, detailString);

        storeInDb(vehicle);
        return vehicle;
    }

    @SneakyThrows
    private String loadPage(String registration) {
        String path = BASE_URL + registration;

        Document doc = Jsoup.connect(path).get();
        Elements detail = doc.getElementsByClass("vehicleDetail");

        if(detail.size() != 1) {
            log.info(detail.size() + " elements found, I dont know what to do");
        }

        Element detailElement = detail.get(0);
        String detailString = detailElement.getAllElements().get(0).ownText();

        return detailString;
    }

    private Vehicle parseDetailString(String registration, String detailString) {
        Vehicle vehicle = new Vehicle();
        vehicle.setRegistration(registration);

        String[] split = detailString.split(",");
        String colour = split[0].trim();
        String description = split[1].trim();

        vehicle.setColour(colour);
        vehicle.setDescription(description);

        return vehicle;
    }
}
