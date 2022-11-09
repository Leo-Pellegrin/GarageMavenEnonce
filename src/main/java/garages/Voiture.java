package garages;

import java.io.PrintStream;
import java.util.*;

public class Voiture {

	private final String immatriculation;
	private final List<Stationnement> myStationnements = new LinkedList<>();

	public Voiture(String i) {
		if (null == i) {
			throw new IllegalArgumentException("Une voiture doit avoir une immatriculation");
		}

		immatriculation = i;
	}

	public String getImmatriculation() {
		return immatriculation;
	}

	/**
	 * Fait rentrer la voiture au garage
	 * Précondition : la voiture ne doit pas être déjà dans un garage
	 *
	 * @param g le garage où la voiture va stationner
	 * @throws java.lang.Exception Si déjà dans un garage
	 */
	public void entreAuGarage(Garage g) throws Exception {
		// Et si la voiture est déjà dans un garage ?
		if(myStationnements.size() > 0){
			if(myStationnements.get(myStationnements.size() - 1).estEnCours()){
				throw new Exception("Stationnement en cours");
			}
		}
		Stationnement s = new Stationnement(this, g);
		myStationnements.add(s);
	}

	/**
	 * Fait sortir la voiture du garage
	 * Précondition : la voiture doit être dans un garage
	 *
	 * @throws java.lang.Exception si la voiture n'est pas dans un garage
	 */
	public void sortDuGarage() throws Exception {
		if(myStationnements.size() == 0) throw new Exception("Voiture non stationnée");
		if(!(myStationnements.get(myStationnements.size() - 1).estEnCours())){
			throw new UnsupportedOperationException("Stationnement non en cours");
		}
		myStationnements.get(myStationnements.size() - 1).terminer();
		// Trouver le dernier stationnement de la voiture
		// Terminer ce stationnement
	}

	/**
	 * @return l'ensemble des garages visités par cette voiture
	 */
	public Set<Garage> garagesVisites() {
		Set<Garage> tmp = new HashSet<Garage>();
		for(int i = 0; i < myStationnements.size(); i++){
			tmp.add(myStationnements.get(i).getGarage());
		}
		return tmp;
	}

	/**
	 * @return vrai si la voiture est dans un garage, faux sinon
	 */
	public boolean estDansUnGarage() throws Exception {
		if(myStationnements.size() == 0) return false;
		if(myStationnements.get(myStationnements.size() - 1).estEnCours()){
			return true;
		}
		else{
			return false;
		}
		// Vrai si le dernier stationnement est en cours
	}

	/**
	 * Pour chaque garage visité, imprime le nom de ce garage suivi de la liste des
	 * dates d'entrée / sortie dans ce garage
	 * <br>
	 * Exemple :
	 * 
	 * <pre>
	 * Garage Castres:
	 *		Stationnement{ entree=28/01/2019, sortie=28/01/2019 }
	 *		Stationnement{ entree=28/01/2019, en cours }
	 *  Garage Albi:
	 *		Stationnement{ entree=28/01/2019, sortie=28/01/2019 }
	 * </pre>
	 *
	 * @param out l'endroit où imprimer (ex: System.out)
	 */
	public void imprimeStationnements(PrintStream out) {
		Set<Garage> tmp = garagesVisites();
		Iterator<Garage> it = tmp.iterator();
		out.println("<pre>");
		for(Garage g : tmp){
			out.println(it.next().toString());
			if(myStationnements.size() > 0){
				for(Stationnement stationnement : myStationnements){
					if(g == stationnement.getGarage()){
						out.println(stationnement.toString());
					}
				}
			}
		}
		out.println("</pre>");
	}

}
