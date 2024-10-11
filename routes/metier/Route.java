package routes.metier;

public class Route {


    private int troncons;
    private Ville villeDep;
    private Ville villeArr;

    private Route(int nbTronc, Ville villeDep, Ville villeArr) {
        this.villeArr = villeArr;
        this.villeDep = villeDep;
        this.villeDep.ajouterRoute(this);
        this.villeArr.ajouterRoute(this);
        this.troncons = nbTronc;
    }

    /**
     * Permet de créer une Route avec les bons paramètres
     * 
     * @param nbTronc  {int} Nombre de troncons
     * @param villeDep {Ville} Ville de départ
     * @param villeArr {Ville} Ville d'arrivé
     * @return {null} Null si les paramètres ne sont pas valides, sinon une nouvelle
     *         Route
     */
    public static Route creerRoute(int nbTronc, Ville villeDep, Ville villeArr) {
        if (nbTronc < 0 || nbTronc > 10)
            return null;
        if (villeArr == null || villeDep == null)
            return null;

        return new Route(nbTronc, villeDep, villeArr);
    }

    /**
     * Retourne la ville de départ
     * 
     * @return {Ville} La ville de départ
     */
    public Ville getVilleDep() {
        return this.villeDep;
    }

    /**
     * Retourne la ville d'arrivée
     * 
     * @return {Ville} La ville d'arrivée
     */
    public Ville getVilleArr() {
        return this.villeArr;
    }

    /**
     * Retourne le nombre de tronçons
     * 
     * @return {int} Nombre de tronçons
     */
    public int getTroncons() {
        return this.troncons;
    }
}