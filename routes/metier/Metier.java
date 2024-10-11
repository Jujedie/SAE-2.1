package routes.metier;

import java.util.ArrayList;
import java.util.List;

public class Metier {
    private List<Ville> lstVille;
    private List<Route> lstRoute;

    public Metier() {
        this.lstVille = new ArrayList<Ville>();
        this.lstRoute = new ArrayList<Route>();
    }

    public void resetVille()
    {
        this.lstVille = new ArrayList<>();
        Ville.resetCompteur();
    }

    public void resetRoute()
    {
        this.lstRoute = new ArrayList<>();
    }

    public List<Ville> getEnsVille() {
        return this.lstVille;
    }

    public int getNbVille() {
        return this.lstVille.size();
    }

    public Integer getIndiceVille(int x, int y) {
        for (int cpt = 0; cpt < this.lstVille.size(); cpt++)
            if (this.lstVille.get(cpt).possede(x, y))
                return cpt;

        return null;
    }

    public Ville getVille(int num) {
        if (num < this.lstVille.size()) {
            return this.lstVille.get(num);
        }
        return null;
    }

    public boolean ajouterVille(Ville ville) {
        if (ville != null) {
            this.lstVille.add(ville);
            return true;
        }
        return false;
    }

    public List<Route> getEnsRoute() {
        return this.lstRoute;
    }

    public int getNbRoute() {
        return this.lstRoute.size();
    }

    public boolean ajouterRoute(Route route) {
        if (route != null) {
            this.lstRoute.add(route);
            return true;
        }
        return false;
    }

    public Ville trouverVilleNom(String nom)
    {

        for (Ville v : this.lstVille)
        {

            if (v.getNom().equals(nom))
            {
                return v;
            }

        }

        return null;

    }

    public boolean supprimerVille(Ville villeSelected)
    {

        if (villeSelected == null) return false;

        for (Route r : villeSelected.getRoutes())
        {

            this.lstRoute.remove(r);

        }

        this.lstVille.remove(villeSelected);

        return true;

    }

    public void modifierVille(Ville villeModifier, String nom, int x, int y, Region region, int numVille)
    {

        if (villeModifier != null)
        {

            villeModifier.setNomVille(nom);
            villeModifier.setCoordX(x);
            villeModifier.setCoordY(y);
            villeModifier.setRegion(region);
            villeModifier.setNumVille(numVille);

        }

    }


}
