package de.uni_hamburg.informatik.swt.se2.mediathek.entitaeten;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.concurrent.ArrayBlockingQueue;

import org.junit.Test;

import de.uni_hamburg.informatik.swt.se2.mediathek.entitaeten.medien.CD;
import de.uni_hamburg.informatik.swt.se2.mediathek.entitaeten.medien.Medium;
import de.uni_hamburg.informatik.swt.se2.mediathek.wertobjekte.Kundennummer;

public class VormerkkarteTest
{

    private Medium _medium;
    private Kunde _vormerker1;
    private Kunde _vormerker2;
    private Kunde _vormerker3;
    private Kunde _vormerker4;
    private Vormerkkarte _vormerkkarte;
    private ArrayBlockingQueue<Kunde> _vormerkerSchlange;

    public VormerkkarteTest()
    {
        _vormerker1 = new Kunde(new Kundennummer(123456), "ich", "du");

        _vormerker2 = new Kunde(new Kundennummer(123455), "sebastian",
                "reichel escobar");

        _vormerker3 = new Kunde(new Kundennummer(123454), "liv", "lol");

        _vormerker4 = new Kunde(new Kundennummer(123453), "agata", "haha");

        _medium = new CD("bar", "baz", "foo", 123);

        _vormerkkarte = new Vormerkkarte(_medium);

        _vormerkerSchlange = new ArrayBlockingQueue<>(3);

        _vormerkerSchlange.add(_vormerker1);

        _vormerkerSchlange.add(_vormerker2);

        _vormerkerSchlange.add(_vormerker3);

    }

    @Test
    public void testeKonstruktor() throws Exception
    {
        //assertEquals(_kunde, _vormerkkarte.getVormerker());
        assertEquals(_medium, _vormerkkarte.getMedium());
    }

    @Test
    public void testgetMedium()
    {
        Medium result = _vormerkkarte.getMedium();
        assertEquals(result, _medium);
        assert result != null : "result darf nicht null sein";
    }

    @Test
    public void testaddVormerker()
    {

        //assertFalse(_vormerkerSchlange.offer(_vormerker4));

        _vormerkkarte.addVormerker(_vormerker1);

        _vormerkkarte.addVormerker(_vormerker2);

        _vormerkkarte.addVormerker(_vormerker3);

        assertEquals(3, _vormerkkarte.getVormerkerSchlange()
            .size());
        assertFalse(_vormerkkarte.getVormerkerSchlange()
            .contains(_vormerker4));
    }

    @Test
    public void testgetVormerkerSchlange()
    {

        _vormerkkarte.getVormerkerSchlange()
            .add(_vormerker1);
        _vormerkkarte.getVormerkerSchlange()
            .add(_vormerker2);
        _vormerkkarte.getVormerkerSchlange()
            .add(_vormerker3);
        ArrayBlockingQueue<Kunde> methodResult = _vormerkkarte
            .getVormerkerSchlange();

        //assertNotNull(methodResult);
        assertEquals(methodResult.size(), _vormerkerSchlange.size());
    }

    @Test
    public void testRemoveVormerker()
    {
        _vormerkkarte.removeVormerker(_vormerker1);

        assertFalse(_vormerkkarte.getVormerkerSchlange()
            .contains(_vormerker1));

        _vormerkkarte.addVormerker(_vormerker1);

        _vormerkkarte.removeVormerker(_vormerker3);

        assertEquals(1, _vormerkkarte.getVormerkerSchlange()
            .size());

        _vormerkkarte.removeVormerker(_vormerker2);

        assertFalse(_vormerkkarte.getVormerkerSchlange()
            .contains(_vormerker3));

        assertEquals(1, _vormerkkarte.getVormerkerSchlange()
            .size());

        assertFalse(_vormerkkarte.getVormerkerSchlange()
            .contains(_vormerker2));

    }

    @Test
    public void getErsterVormerker()
    {
        assertEquals(_vormerkkarte.getVormerkerSchlange()
            .peek(), _vormerkkarte.getErsterVormerker());

    }
}
