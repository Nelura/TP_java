package metier;
import donnees.VillesDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import entities.Vainqueur;
import entities.Villes;

public class Combat {
	public List SelectionJ1(List<Villes> l, int ct, int dp, int ar) {
		List r = new LinkedList();
		for (Villes ville : l) {
			if (ville.getCanton() == ct && ville.getNom_departement() == dp && ville.getNom_arrondissement() == ar) {
				ville.affiche();
				r.add(ville);
			}
		}
		if (r.size() == 0){
			System.out.println("Aucune ville ne correspond à la recherche");
			System.exit(0);
		}
		
		return r;
	}

	public String combatVilles(List<Villes> l) throws SQLException {
		int i = 0;
		int j = l.size();
		Vainqueur vainqueur = null;
		while (vainqueur == null) {
			if ((j % 2) == 0) {
				// nbre pair
				int nombreGagnants = l.size() / 2;
				for (i = 0; i <= 500000; i++) {
					if (l.size() == nombreGagnants) {
						i = 0;
						nombreGagnants = l.size() / 2;
					} else if (l.size() == 1) {
						String gagnant = (l.get(0).getNom_ville());
						return gagnant;
					}
					if (l.size() > 1) {
						Villes villeUn = l.get(i);
						Villes villeDeux = l.get(i + 1);
						Villes perdantMatch = tour(villeUn, villeDeux);
						if (perdantMatch == villeUn) {
							l.remove(i);
						} else if (perdantMatch == villeDeux) {
							l.remove(i + 1);
						}

					}
				}
			} else {
				// nbre impair
				Villes villeUn = l.get(i);
				Villes villeDeux = l.get(i + 1);
				Villes villeTrois = l.get(i + 2);
				Villes perdantImpere = tourImpere(villeUn, villeDeux, villeTrois);
				if (perdantImpere == villeUn) {
					l.remove(i);
				} else if (perdantImpere == villeDeux) {
					l.remove(i + 1);
				} else if (perdantImpere == villeTrois) {
					l.remove(i + 2);
				}
				int nombreGagnants = l.size() / 2;
				for (i = 0; i <= 500000; i++) {
					if (l.size() == nombreGagnants) {
						i = 0;
						nombreGagnants = l.size() / 2;
					} else if (l.size() == 1) {
						String gagnant = (l.get(0).getNom_ville());
						return gagnant;
					}
					if (l.size() > 1) {
						Villes villeUnImp = l.get(i);
						Villes villeDeuxImp = l.get(i + 1);
						Villes perdantMatch = tour(villeUnImp, villeDeuxImp);
						if (perdantMatch == villeUnImp) {
							l.remove(i);
						} else if (perdantMatch == villeDeuxImp) {
							l.remove(i + 1);
						}
					}
				}
			}
		}
		return "il n'y a pas de vainqueur";
	}

	public Villes tour(Villes ville1, Villes ville2) throws SQLException {
		VillesDAO ins = new VillesDAO();
		double pointVille1 = (ville1.getNb_habitant() * ville1.getLatitude() * Math.random() * 100)
				/ ville1.getNom_ville().length();
		double pointVille2 = (ville2.getNb_habitant() * ville2.getLatitude() * Math.random() * 100)
				/ ville2.getNom_ville().length();

		if (pointVille1 < pointVille2) {
			ins.insMatchPair(ville1, ville2, ville2);
			return ville1;
		} else if (pointVille1 > pointVille2) {
			ins.insMatchPair(ville1, ville2, ville1);
			return ville2;
		} else {
			return null;
		}

	}
	public Villes tourImpere(Villes ville1, Villes ville2, Villes ville3) throws SQLException {
		VillesDAO ins = new VillesDAO();
		double pointVille1 = (ville1.getNb_habitant() * ville1.getLatitude() * Math.random() * 100)
				/ ville1.getNom_ville().length();
		double pointVille2 = (ville2.getNb_habitant() * ville2.getLatitude() * Math.random() * 100)
				/ ville2.getNom_ville().length();
		double pointVille3 = (ville3.getNb_habitant() * ville3.getLatitude() * Math.random() * 100)
				/ ville3.getNom_ville().length();

		if (pointVille1 < pointVille2 && pointVille1 < pointVille3) {
			ins.insMatchImp(ville1, ville2, ville3, ville2, ville3);
			return ville1;
		}
		if (pointVille2 < pointVille1 && pointVille2 < pointVille3) {
			ins.insMatchImp(ville1, ville2, ville3, ville1, ville3);
			return ville2;
		}
		if (pointVille3 < pointVille2 && pointVille3 < pointVille1) {
			ins.insMatchImp(ville1, ville2, ville3, ville1, ville2);
			return ville3;
		} else {
			return null;
		}
	}
	public void finCombat(Vainqueur g) {
		System.out.println("La ville qui gagne dans ce canton est : " + g.getNom_ville() + ".");
	}
}
