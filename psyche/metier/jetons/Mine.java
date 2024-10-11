package psyche.metier.jetons;

import psyche.metier.Dictionnaire;
import psyche.metier.Joueur;
import psyche.metier.Route;

import java.util.ArrayList;
import java.util.List;

public class Mine extends Jeton
{
    private static int   nbJeton = 0;
    private static int   nbmine = 0;

    private int         numMine;
    private int         valeur;
    private Region      region;
    private Ressource   typeRessource;
    private int         x;
    private int         y;
    private List<Route> ensRoute;
    private Joueur      joueurPossede;

    private boolean     estSelectionne;

    public  Mine(int valeur , Region region, int x, int y)
    {
        super(++nbJeton);

        this.numMine = ++Mine.nbmine;
        this.valeur = valeur;
        this.region = region;
        this.typeRessource = null;
        this.ensRoute = new ArrayList<>();
        this.joueurPossede = null;

        this.x = x;
        this.y = y;

        this.estSelectionne = false;
    }

    /**
     * ajoute une ressource à la mine
     * @param r la ressource à ajouter
     * @return true si la ressource a été ajoutée, false sinon
     */
    public boolean ajouterRessource(Ressource r)
    {
        if (this.typeRessource != null) return false;

        this.typeRessource = r;
        return true;
    }

    /**
     * Permet de changer l'état de la mine (Sélectionné ou non)
     */
    public void toggleSelectionne()
    {
        this.estSelectionne = !estSelectionne;
    }

    /**
     * Permet de savoir si la mine est selectionné ou non
     * @return
     */
    public boolean estSelectionne()
    {
        return this.estSelectionne;
    }

    /**
     * retire la ressource de la mine
     * @return la ressource retirée
     */
    public Ressource retirerRessource()
    {
        Ressource r = null;
        if (this.typeRessource != null)
        {
            r = this.typeRessource;
            this.typeRessource = null;
        }

        return r;
    }

    public int       getNum      () {return this.numMine      ;}
    public Ressource getRessource() {return this.typeRessource;}
    public int       getValeur   () {return this.valeur       ;}
    public Region    getRegion   () {return this.region       ;}

    public int       getCoordX   () {return this.x            ;}
    public int       getCoordY   () {return this.y            ;}
    public Joueur    getJoueur   () {return this.joueurPossede;}

    public void setRessource(Ressource m) { this.typeRessource = m; }

    /**
     * ajoute une route à la mine
     * @param r la route à ajouter
     */
    public void ajouterRoute(Route r)
    {
        this.ensRoute.add(r);
    }

    /**
     * set le joueur possesseur de la mine
     * @param joueur le numéro du joueur possesseur
     */
    public void setJoueur(Joueur joueur){this.joueurPossede = joueur;}

    public List<Route> getEnsRoute()
    {
        return this.ensRoute;
    }

    public String toString()
    {

        String sRet = "";

        sRet += Dictionnaire.getNomSommet() + " (" + this.numMine + ")" + this.region.name() + ", Nombre : " + this.valeur + " (" + this.x + ";" + this.y + ") ";

        if (this.typeRessource != null) {
            sRet += "Type ressource : " + this.typeRessource.getMinerai().name() + " ";
        }

        if (this.joueurPossede != null)
        {
            sRet += "Appartient au joueur " + this.joueurPossede;
        }

        return sRet;

    }

    /**
     * détermine si un point est dans la zone de la mine
     * @param x la coordonnée x du point
     * @param y la coordonnée y du point
     * @return true si le point est dans la zone de la mine, false sinon
     */
    public boolean zoneMine(int x, int y)
    {

        return  x >= this.x && x <= this.x + 30 &&
                y >= this.y && y <= this.y + 56;

        /*double pas = Math.PI / 16;

        x -= 15;
        y -= 15;

        double rayon = 24;

        double minX, minY, maxX, maxY;

        for (double ang = 0; ang <= Math.PI * 2; ang += pas) {
            minX = Math.min(this.getCoordX(), this.getCoordX() + Math.cos(ang) * rayon);
            maxX = Math.max(this.getCoordX(), this.getCoordX() + Math.cos(ang) * rayon);

            minY = Math.min(this.getCoordY(), this.getCoordY() + Math.sin(ang) * rayon);
            maxY = Math.max(this.getCoordY(), this.getCoordY() + Math.sin(ang) * rayon);

            if (x >= minX && x <= maxX && y >= minY && y <= maxY)
                return true;
        }

        return false;*/

    }

}
