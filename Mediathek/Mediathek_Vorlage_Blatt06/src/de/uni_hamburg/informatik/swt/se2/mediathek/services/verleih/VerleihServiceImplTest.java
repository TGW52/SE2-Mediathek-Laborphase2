package de.uni_hamburg.informatik.swt.se2.mediathek.services.verleih;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import de.uni_hamburg.informatik.swt.se2.mediathek.entitaeten.Kunde;
import de.uni_hamburg.informatik.swt.se2.mediathek.entitaeten.Verleihkarte;
import de.uni_hamburg.informatik.swt.se2.mediathek.entitaeten.Vormerkkarte;
import de.uni_hamburg.informatik.swt.se2.mediathek.entitaeten.medien.CD;
import de.uni_hamburg.informatik.swt.se2.mediathek.entitaeten.medien.Medium;
import de.uni_hamburg.informatik.swt.se2.mediathek.services.ServiceObserver;
import de.uni_hamburg.informatik.swt.se2.mediathek.services.kundenstamm.KundenstammService;
import de.uni_hamburg.informatik.swt.se2.mediathek.services.kundenstamm.KundenstammServiceImpl;
import de.uni_hamburg.informatik.swt.se2.mediathek.services.medienbestand.MedienbestandService;
import de.uni_hamburg.informatik.swt.se2.mediathek.services.medienbestand.MedienbestandServiceImpl;
import de.uni_hamburg.informatik.swt.se2.mediathek.wertobjekte.Datum;
import de.uni_hamburg.informatik.swt.se2.mediathek.wertobjekte.Kundennummer;

/**
 * @author SE2-Team
 */
public class VerleihServiceImplTest
{
    private Datum _datum;
    private Kunde _kunde;
    private VerleihService _service;
    private List<Medium> _medienListe;
    private Kunde _vormerkkunde1;
    private Kunde _vormerkkunde2;
    private Kunde _vormerkkunde3;
    private Kunde _vormerkkunde4;
    private Vormerkkarte _vormerkkarte;
    private Medium _medium;

    public VerleihServiceImplTest()
    {
        _medium = new CD("bar", "baz", "foo", 123);
        _datum = new Datum(3, 4, 2009);
        KundenstammService kundenstamm = new KundenstammServiceImpl(
                new ArrayList<Kunde>());
        _kunde = new Kunde(new Kundennummer(123456), "ich", "du");

        _vormerkkunde1 = new Kunde(new Kundennummer(666999), "sebastian", "se");
        _vormerkkunde2 = new Kunde(new Kundennummer(666998), "liv", "se");
        _vormerkkunde3 = new Kunde(new Kundennummer(666997), "agatha", "se");
        _vormerkkunde4 = new Kunde(new Kundennummer(666996), "tim", "se");

        kundenstamm.fuegeKundenEin(_kunde);
        kundenstamm.fuegeKundenEin(_vormerkkunde1);
        kundenstamm.fuegeKundenEin(_vormerkkunde2);
        kundenstamm.fuegeKundenEin(_vormerkkunde3);
        kundenstamm.fuegeKundenEin(_vormerkkunde4);
        MedienbestandService medienbestand = new MedienbestandServiceImpl(
                new ArrayList<Medium>());
        Medium medium = new CD("CD1", "baz", "foo", 123);
        medienbestand.fuegeMediumEin(medium);
        medium = new CD("CD2", "baz", "foo", 123);
        medienbestand.fuegeMediumEin(medium);
        medium = new CD("CD3", "baz", "foo", 123);
        medienbestand.fuegeMediumEin(medium);
        medium = new CD("CD4", "baz", "foo", 123);
        medienbestand.fuegeMediumEin(medium);

        medienbestand.fuegeMediumEin(_medium);

        _medienListe = medienbestand.getMedien();
        _service = new VerleihServiceImpl(kundenstamm, medienbestand,
                new ArrayList<Verleihkarte>());
        _vormerkkarte = new Vormerkkarte(_medium);

    }

    @Test
    public void testeNachInitialisierungIstNichtsVerliehen() throws Exception
    {
        assertTrue(_service.getVerleihkarten()
            .isEmpty());
        assertFalse(_service.istVerliehen(_medienListe.get(0)));
        assertFalse(_service.sindAlleVerliehen(_medienListe));
        assertTrue(_service.sindAlleNichtVerliehen(_medienListe));
    }

    @Test
    public void testeVerleihUndRueckgabeVonMedien() throws Exception
    {
        // Lege eine Liste mit nur verliehenen und eine Liste mit ausschließlich
        // nicht verliehenen Medien an
        List<Medium> verlieheneMedien = _medienListe.subList(0, 2);
        List<Medium> nichtVerlieheneMedien = _medienListe.subList(2, 4);
        _service.verleiheAn(_kunde, verlieheneMedien, _datum);

        // Prüfe, ob alle sondierenden Operationen für das Vertragsmodell
        // funktionieren
        assertTrue(_service.istVerliehen(verlieheneMedien.get(0)));
        assertTrue(_service.istVerliehen(verlieheneMedien.get(1)));
        assertFalse(_service.istVerliehen(nichtVerlieheneMedien.get(0)));
        assertFalse(_service.istVerliehen(nichtVerlieheneMedien.get(1)));
        assertTrue(_service.sindAlleVerliehen(verlieheneMedien));
        assertTrue(_service.sindAlleNichtVerliehen(nichtVerlieheneMedien));
        assertFalse(_service.sindAlleNichtVerliehen(verlieheneMedien));
        assertFalse(_service.sindAlleVerliehen(nichtVerlieheneMedien));
        assertFalse(_service.sindAlleVerliehen(_medienListe));
        assertFalse(_service.sindAlleNichtVerliehen(_medienListe));
        assertTrue(_service.istVerliehenAn(_kunde, verlieheneMedien.get(0)));
        assertTrue(_service.istVerliehenAn(_kunde, verlieheneMedien.get(1)));
        assertFalse(
                _service.istVerliehenAn(_kunde, nichtVerlieheneMedien.get(0)));
        assertFalse(
                _service.istVerliehenAn(_kunde, nichtVerlieheneMedien.get(1)));
        assertTrue(_service.sindAlleVerliehenAn(_kunde, verlieheneMedien));
        assertFalse(
                _service.sindAlleVerliehenAn(_kunde, nichtVerlieheneMedien));

        // Prüfe alle sonstigen sondierenden Methoden
        assertEquals(2, _service.getVerleihkarten()
            .size());

        _service.nimmZurueck(verlieheneMedien, _datum);
        // Prüfe, ob alle sondierenden Operationen für das Vertragsmodell
        // funktionieren
        assertFalse(_service.istVerliehen(verlieheneMedien.get(0)));
        assertFalse(_service.istVerliehen(verlieheneMedien.get(1)));
        assertFalse(_service.istVerliehen(nichtVerlieheneMedien.get(0)));
        assertFalse(_service.istVerliehen(nichtVerlieheneMedien.get(1)));
        assertFalse(_service.sindAlleVerliehen(verlieheneMedien));
        assertTrue(_service.sindAlleNichtVerliehen(nichtVerlieheneMedien));
        assertTrue(_service.sindAlleNichtVerliehen(verlieheneMedien));
        assertFalse(_service.sindAlleVerliehen(nichtVerlieheneMedien));
        assertFalse(_service.sindAlleVerliehen(_medienListe));
        assertTrue(_service.sindAlleNichtVerliehen(_medienListe));
        assertTrue(_service.getVerleihkarten()
            .isEmpty());
    }

    @Test
    public void testVerleihEreignisBeobachter() throws ProtokollierException
    {
        final boolean[] ereignisse = new boolean[1];
        ereignisse[0] = false;
        ServiceObserver beobachter = new ServiceObserver()
        {
            @Override
            public void reagiereAufAenderung()
            {
                ereignisse[0] = true;
            }
        };
        _service.verleiheAn(_kunde,
                Collections.singletonList(_medienListe.get(0)), _datum);
        assertFalse(ereignisse[0]);

        _service.registriereBeobachter(beobachter);
        _service.verleiheAn(_kunde,
                Collections.singletonList(_medienListe.get(1)), _datum);
        assertTrue(ereignisse[0]);

        _service.entferneBeobachter(beobachter);
        ereignisse[0] = false;
        _service.verleiheAn(_kunde,
                Collections.singletonList(_medienListe.get(2)), _datum);
        assertFalse(ereignisse[0]);
    }

    @Test
    public void testVormerken() throws Exception
    {
        List<Medium> vorgemerkteMedien = _medienListe.subList(0, 2);
        List<Medium> nichtVorgemerkteMedien = _medienListe.subList(2, 4);

        // Tests für merkeVor
        _service.merkeVor(_vormerkkunde1, vorgemerkteMedien);
        _service.merkeVor(_vormerkkunde2, vorgemerkteMedien);
        _service.merkeVor(_vormerkkunde3, vorgemerkteMedien);
        assertTrue(_service.getVormerkkarte(vorgemerkteMedien.get(0))
            .getVormerkerSchlange()
            .contains(_vormerkkunde1));
        assertTrue(_service.getVormerkkarte(vorgemerkteMedien.get(1))
            .getVormerkerSchlange()
            .contains(_vormerkkunde1));
        assertTrue(_service.getVormerkkarte(vorgemerkteMedien.get(0))
            .getVormerkerSchlange()
            .contains(_vormerkkunde2));
        assertTrue(_service.getVormerkkarte(vorgemerkteMedien.get(1))
            .getVormerkerSchlange()
            .contains(_vormerkkunde2));
        assertTrue(_service.getVormerkkarte(vorgemerkteMedien.get(0))
            .getVormerkerSchlange()
            .contains(_vormerkkunde3));
        assertTrue(_service.getVormerkkarte(vorgemerkteMedien.get(1))
            .getVormerkerSchlange()
            .contains(_vormerkkunde3));
        assertFalse(_service.getVormerkkarte(nichtVorgemerkteMedien.get(0))
            .getVormerkerSchlange()
            .contains(_vormerkkunde1));
        assertFalse(_service.getVormerkkarte(nichtVorgemerkteMedien.get(1))
            .getVormerkerSchlange()
            .contains(_vormerkkunde1));
        assertFalse(_service.getVormerkkarte(nichtVorgemerkteMedien.get(0))
            .getVormerkerSchlange()
            .contains(_vormerkkunde2));
        assertFalse(_service.getVormerkkarte(nichtVorgemerkteMedien.get(1))
            .getVormerkerSchlange()
            .contains(_vormerkkunde2));
        assertFalse(_service.getVormerkkarte(nichtVorgemerkteMedien.get(0))
            .getVormerkerSchlange()
            .contains(_vormerkkunde3));
        assertFalse(_service.getVormerkkarte(nichtVorgemerkteMedien.get(1))
            .getVormerkerSchlange()
            .contains(_vormerkkunde3));

    }

    @Test
    public void testIstVorgemerkt()
    {
        List<Medium> vorgemerkteMedien = _medienListe.subList(0, 2);
        List<Medium> nichtVorgemerkteMedien = _medienListe.subList(2, 4);

        _service.merkeVor(_vormerkkunde1, vorgemerkteMedien);
        _service.merkeVor(_vormerkkunde2, vorgemerkteMedien);
        _service.merkeVor(_vormerkkunde3, vorgemerkteMedien);

        assertTrue(_service.istVorgemerkt(vorgemerkteMedien.get(0)));
        assertTrue(_service.istVorgemerkt(vorgemerkteMedien.get(1)));
        assertFalse(_service.istVorgemerkt(nichtVorgemerkteMedien.get(0)));
        assertFalse(_service.istVorgemerkt(nichtVorgemerkteMedien.get(1)));
    }

    @Test
    public void testIstVormerkungMoeglich() throws ProtokollierException
    {
        List<Medium> vorgemerkteMedien = _medienListe.subList(0, 2);
        List<Medium> nichtVorgemerkteMedien = _medienListe.subList(2, 4);

        _service.merkeVor(_vormerkkunde1, vorgemerkteMedien);
        _service.merkeVor(_vormerkkunde2, vorgemerkteMedien);
        _service.merkeVor(_vormerkkunde3, vorgemerkteMedien);

        assertTrue(_service.istVormerkungMoeglich(_vormerkkunde1,
                nichtVorgemerkteMedien));
        assertFalse(_service.istVormerkungMoeglich(_vormerkkunde1,
                vorgemerkteMedien));

        _service.verleiheAn(_vormerkkunde1, vorgemerkteMedien, _datum);
        assertFalse(_service.istVormerkungMoeglich(_vormerkkunde1,
                vorgemerkteMedien));
        assertFalse(_service.istVormerkungMoeglich(_vormerkkunde2,
                vorgemerkteMedien));
        assertTrue(_service.istVormerkungMoeglich(_vormerkkunde4,
                vorgemerkteMedien));

    }

    @Test
    public void testIstVerleihenMoeglich() throws ProtokollierException
    {
        Medium medium = new CD("bar", "baz", "foo", 123);
        List<Medium> medien = new ArrayList<>();
        //medien.add(medium);
        MedienbestandService medienbestand = new MedienbestandServiceImpl(
                new ArrayList<Medium>());
        medienbestand.fuegeMediumEin(medium);
        medien = medienbestand.getMedien();

        Vormerkkarte vormerkkarte = new Vormerkkarte(medium);
        vormerkkarte.getVormerkerSchlange()
            .add(_vormerkkunde1);
        vormerkkarte.getVormerkerSchlange()
            .add(_vormerkkunde2);
        vormerkkarte.getVormerkerSchlange()
            .add(_vormerkkunde3);

        assertTrue(vormerkkarte.getErsterVormerker()
            .equals(_vormerkkunde1));
        assertEquals(vormerkkarte.getVormerkerArray()[1], _vormerkkunde2);

        assertFalse(_service.istVerleihenMoeglich(_vormerkkunde2, medien));
        //        assertTrue(_service.istVerleihenMoeglich(_vormerkkunde1, _medienListe));
        //
        //        _service.verleiheAn(_vormerkkunde1, _medienListe, _datum);
        //
        //        assertTrue(_service.istVerliehenAn(_vormerkkunde1, _medium));
    }

}
