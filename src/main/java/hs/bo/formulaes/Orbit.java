/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hs.bo.formulaes;

import java.util.ArrayList;
import org.gogpsproject.EphGps;
import org.gogpsproject.NavigationProducer;
import org.gogpsproject.ObservationSet;
import org.gogpsproject.Observations;
import org.gogpsproject.SatellitePosition;

/**
 *
 * @author Annette
 */
public class Orbit {

    public final double GM = 3986004.418E8;
    public final double c = 299792458;
    public final double OMEGADOTE = 7.2921151467E-5;

    /**
     * Zebhauser Seite 39
     *
     * @param ephData
     * @param observation
     * @param satID
     * @return
     */
    public SatellitePosition calculateOrbit(EphGps ephData, Observations observation, int satID) {
        //Abschnitt 1
        double n0 = Math.sqrt(GM / Math.pow(ephData.getRootA(), 5));
        double n = n0 + ephData.getDeltaN();
        double rk = (-2 * ephData.getE() * Math.sqrt(GM) * ephData.getRootA()) / Math.pow(c, 2);

        //Abschnitt 2
        double tt = observation.getRefTime().getMsec() * 1000; //in Sekunden
        double PR = observation.getSatByID(satID).getPseudorange(0);
        double t0S = tt - (PR / c);

        double dt = t0S - ephData.getToe();
        double mdt = ephData.getM0() + n * dt;
        double eA_last = this.eccentricAnomaly(ephData, dt, mdt, mdt);

        //Korrektur des Satellitenuhrenfehlers
        double deltaTS = ephData.getAf0() + ephData.getAf1() * dt + ephData.getAf2() * Math.pow(dt, 2)
                + rk * Math.sin(eA_last);

        //Sendezeitpunkt
        double dtS = dt - deltaTS;

        //Abschnitt 3
        double mdtS = ephData.getM0() + n * dtS;

        this.eccentricAnomaly(ephData, dtS, mdtS, eA_last);

    }

    /**
     *
     */
    private double eccentricAnomaly(EphGps ephData, double timeDt, double mdt, double e0) {
        //ExzentrischeAnomalie
        ArrayList<Double> eA = new ArrayList<Double>();
        eA.add(e0);//e0  
        int i = 1;
        //iterative Berechnung
        do {
            eA.add(e0 + ephData.getE() * Math.sin(eA.get(i - 1)));//ei
            e0 = eA.get(i) - ephData.getE() * Math.sin(eA.get(i));
        } while ((eA.get(i) - eA.get(i - 1)) <= 10E-10);

        return eA.get(eA.size() - 1);

    }

}
