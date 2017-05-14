
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.gogpsproject.NavigationProducer;
import org.gogpsproject.Observations;
import org.gogpsproject.ObservationsProducer;
import org.gogpsproject.SatellitePosition;
import org.gogpsproject.parser.rinex.RinexNavigationParser;
import org.gogpsproject.parser.rinex.RinexObservationParser;

/**
 *
 * @author Annette
 */
public class ParseRinex {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Observation Data
        ObservationsProducer roverIn = new RinexObservationParser(new File("C:\\Users\\Annette\\Documents\\Studium Bochum\\Softwareentwicklungsprojekt\\Daten\\COM3_170423_174145.obs"));
        ObservationsProducer masterIn = new RinexObservationParser(new File("C:\\Users\\Annette\\Documents\\Studium Bochum\\Softwareentwicklungsprojekt\\Daten\\4722K06115201705071300A.17O"));
        
        //NavigationData
        NavigationProducer navigationIn = new RinexNavigationParser(new File("C:\\Users\\Annette\\Documents\\Studium Bochum\\Softwareentwicklungsprojekt\\Daten\\4722K06115201705071300A.17N"));
        
        try {
            //Parse Navigation Data
            //Data in eph-Array
            navigationIn.init();
        } catch (Exception ex) {
            Logger.getLogger(ParseRinex.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            //Prepair Observation-Data
            //Rinex-Version 
            roverIn.init();
            masterIn.init();
        } catch (Exception ex) {
            Logger.getLogger(ParseRinex.class.getName()).log(Level.SEVERE, null, ex);
        }
 		
        //Get first Observation epoch
        //Data in obsset
        Observations observationRover = roverIn.getNextObservations();
        Observations observationReferenz = masterIn.getNextObservations();
        
        SatellitePosition sp = navigationIn.getGpsSatPosition(observationReferenz, 2, 'G', 1);
        System.out.println(sp.toString());
    }

}
