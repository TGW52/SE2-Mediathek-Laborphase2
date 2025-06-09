package de.uni_hamburg.informatik.swt.se2.mediathek.entitaeten;

import java.util.concurrent.ArrayBlockingQueue;
import de.uni_hamburg.informatik.swt.se2.mediathek.entitaeten.medien.Medium;

/**
 * Mit Hilfe von Vormerkkarte werden beim Vormerken eines Mediums alle relevanten
 * Daten notiert.
 * 
 * 
 * 
 * @author SE2-Team
 * @version SoSe 2021
 */

// TODO Neue Testklasse fuer Vormerkkarte
public class Vormerkkarte
{

    // Eigenschaften einer Vormerkkarte
    private final Medium _medium;
    private ArrayBlockingQueue<Kunde> _vormerkerSchlange; 

    /**
     * Initialisert eine neue Verleihkarte mit den gegebenen Daten.
     * 
     * @param entleiher Ein Kunde, der das Medium ausgeliehen hat.
     * @param medium Ein verliehene Medium.
     *            hat.
     * 
     * @require entleiher != null
     * @require medium != null
     * 
     * @ensure #getEntleiher() == entleiher
     * @ensure #getMedium() == medium
     */
    public Vormerkkarte(Medium medium)
    {
        assert medium != null : "Vorbedingung verletzt: medium != null";
        
        _medium = medium;
        _vormerkerSchlange = new ArrayBlockingQueue<>(3);
        
    }

   


    /**
     * Gibt das Medium, dessen Ausleihe auf der Karte vermerkt ist, zurück.
     * 
     * @return Das Medium, dessen Ausleihe auf dieser Karte vermerkt ist.
     * 
     * @ensure result != null
     */
    public Medium getMedium()
    {
        return _medium;
    }

	/**
     * Gibt die Vormerker eines Mediums zurück.
     * 
     * @return die Vormerker des Mediums.
     * 
     * @ensure result != null
     */
    public ArrayBlockingQueue<Kunde> getVormerkerSchlange()
    {
        return _vormerkerSchlange;
    }
    
    /**
     * Fügt einen Vormerker der Schlange hinzu.
     * 
     * 
     * @require vormerker != null
     * @ensure result != null
     */
    public void addVormerker(Kunde vormerker)
    {
        
       if(_vormerkerSchlange.size() < 3)
       {
        	_vormerkerSchlange.add(vormerker);
        	
       }
    }
    
    /**
     * Löscht einen Vormerker der Schlange.
     * 
     * 
     * @require vormerker != null
     * @ensure result != null
     */
    public void removeVormerker(Kunde vormerker)
    {
       if(_vormerkerSchlange.size() > 0)
       {
        	_vormerkerSchlange.remove(vormerker);
        	
       }
    }
    
    
    
    
    /**
     * Gibt alle Vormerker als Array der Laenge 3 zurueck
     * 
     * @return ein Array Kunde[] mit allen Vormerkern, nciht besetzte Vormerkungen sind null
     * 
     * @ensure result != null
     */
    public Kunde[] getVormerkerArray()
    {
    	return _vormerkerSchlange.toArray(new Kunde[3]);
    	
    }
    
    /**
     * Gibt den ersten Vormerker der aktuellen Verleihkarte zurueck
     * 
     * @return der aktuell erste Vormerker als Kunde
     * 
     * @ensure result =! null
     */
    public Kunde getErsterVormerker ()
    {
    	return getVormerkerArray()[0];
    }
    

	/**
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((_ausleihdatum == null) ? 0 : _ausleihdatum.hashCode());
        result = prime * result
                + ((_entleiher == null) ? 0 : _entleiher.hashCode());
        result = prime * result + ((_medium == null) ? 0 : _medium.hashCode());
        return result;
    }

	
    @Override
    public boolean equals(Object obj)
    {
        boolean result = false;
        if (obj instanceof Verleihkarte)
        {
            Verleihkarte other = (Verleihkarte) obj;

            if (other.getAusleihdatum()
                .equals(_ausleihdatum)
                    && other.getEntleiher()
                        .equals(_entleiher)
                    && other.getMedium()
                        .equals(_medium))

                result = true;
        }
        return result;
    }
	*/
}
