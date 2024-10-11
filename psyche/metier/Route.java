package psyche.metier;

import psyche.metier.jetons.Mine;

public class Route
{
    private int    section         ;
    private Mine   mineDep         ;
    private Mine   mineArr         ;
    private Joueur appartientJoueur;


    private Route(int section, Mine mineDep, Mine mineArr)
    {
        this.mineArr = mineArr;
        this.mineDep = mineDep;

        this.section = section;

        this.mineDep.ajouterRoute(this);
        this.mineArr.ajouterRoute(this);

        this.appartientJoueur = null;
    }

    /**
     * Permet de créer une Route avec les bons paramètres
     *
     * @param nbSection  {int} Nombre de section
     * @param mineDep {Ville} Ville de départ
     * @param mineArr {Ville} Ville d'arrivé
     * @return {null} Null si les paramètres ne sont pas valides, sinon une nouvelle
     *         Route
     */
    public static Route creerRoute(int nbSection, Mine mineDep, Mine mineArr)
    {

        if (nbSection < 0 || nbSection > 2)     return null;
        if (mineArr == null || mineDep == null) return null;

        return new Route(nbSection, mineDep, mineArr);
    }

    /**
     * Retourne la mine de départ
     *
     * @return {Mine} La mine de départ
     */
    public Mine getMineDep()
    {
        return this.mineDep;
    }
    public void resetJoueur()
    {
        this.appartientJoueur = null;
    }

    /**
     * Retourne la mine d'arrivée
     *
     * @return {Mine} La mine d'arrivée
     */
    public Mine getMineArr() {
        return this.mineArr;
    }

    /**
     * retourne le nombre de section de la route
     *
     * @return {int} Le nombre de section de la route
     */
    public int    getNbSection       (){return this.section;}

    /**
     * retourne le joueur à qui appartient la route
     * @return {Joueur} le joueur à qui appartient la route
     */
    public Joueur getAppartientJoueur(){return this.appartientJoueur;}

    /**
     * Attribution de la route à un joueur
     * @param j {Joueur} le joueur à qui on veut attribuer la route
     * @return {boolean} true si le joueur est attribué, false sinon
     */
    public boolean setAppartientJoueur (Joueur j)
    {
        if ( j == null  || this.appartientJoueur != null ) return false;
        this.appartientJoueur = j;
        return true;
    }

    public String toString()
    {
        return this.mineDep.toString() + " -> " + this.mineArr.toString();
    }
}