package routes.metier;

import java.util.ArrayList;
import java.util.List;

public class Ville {

    private static int nbVille = 0;

    private int numVille;
    private String nomVille;
    private int coordX;
    private int coordY;
    private List<Route> ensRoute;
    private Region  region;
    private int     numUtilisateur;

    private Ville(String nomVille, int coordX, int coordY, Region region, int num) {

        this.nomVille = nomVille;
        this.coordY = coordY;
        this.coordX = coordX;
        this.region = region;
        this.numUtilisateur = num;

        this.numVille = ++Ville.nbVille;
        this.ensRoute = new ArrayList<>();

    }

    /**
     * Factory permettant de créer une ville avec les bon paramètres
     * 
     * @param nomVille {String} Nom de la ville
     * @param coordX   {int} Position X de la ville
     * @param coordY   {int} Position Y de la ville
     * @return {null} Null si les paramètres ne sont pas valides, sinon une nouvelle
     *         Ville
     */
    public static Ville creerVille(String nomVille, int coordX, int coordY, Region region, int num) {

        if (coordX < 0 || coordX > 1000)    return null;
        if (coordY < 0 || coordY > 800)     return null;
        if (region == null)                 return null;
        if (num < 0 || num > 8)             return null;

        return new Ville(nomVille, coordX, coordY, region, num);

    }

    /**
     * Retourne la position X de la ville
     * 
     * @return {int} Coordonnés de la ville
     */
    public int getCoordX() {
        return this.coordX;
    }

    /**
     * Retourne la position Y de la ville
     * 
     * @return {int} Coordonnées Y de la ville
     */
    public int getCoordY() {
        return this.coordY;
    }

    /**
     * Retourne le nom de la ville
     * 
     * @return {String} Nom de la ville
     */
    public String getNom() {
        return this.nomVille;
    }

    /**
     * Retourne le numéro de la ville
     * 
     * @return {int} Numéro de ville
     */
    public int getNumVille() {
        return this.numVille;
    }

    /**
     * Retourne l'ensemble des routes
     * @return {List<Route>} Ensemble de Route
     */
    public List<Route> getRoutes() {
        return this.ensRoute;
    }


    /**
     * Retour le numéro de la ville saisie par l'utilisateur
     * @return Numéro de l'utilisateur
     */
    public int getNumUtilisateur()
    {
        return this.numUtilisateur;
    }

    /**
     * Retourne la région de la ville
     * @return La région
     */
    public Region getRegion()
    {
        return this.region;
    }

    /**
     * Permet d'ajouter des Routes à la liste de Route
     * 
     * @param r {Route} Route à ajouter
     */
    public void ajouterRoute(Route r) {

        this.ensRoute.add(r);

    }

    /**
     * Dépalcer horizontalement la ville
     * @param x Abscisse à dépalcer
     */
    public void deplacerX(int x) {
        if (this.coordX + x <= 1000 && this.coordX + x >= 0)
        {
            this.coordX += x;

        }
    }

    /**
     * Dépalcer verticalement la ville
     * @param y Ordonnée à déplacer
     */
    public void deplacerY(int y) {
        if (this.coordY + y <= 800 && this.coordY + y >= 0)
        {
            this.coordY += y;
        }
    }

    /**
     * Vérifier si la ville est sélectionnée lorsque l'utilisateur clique sur une ville
     * @param x Clique X
     * @param y Clique Y
     * @return Vrai si la ville est sélectionné, sinon faux
     */
    public boolean possede(int x, int y) {
        double pas = Math.PI / 16;

        x -= 15;
        y -= 15;

        double rayon = 15;

        double minX, minY, maxX, maxY;

        for (double ang = 0; ang <= Math.PI * 2; ang += pas) {
            minX = Math.min(this.getCoordX(), this.getCoordX() + Math.cos(ang) * rayon);
            maxX = Math.max(this.getCoordX(), this.getCoordX() + Math.cos(ang) * rayon);

            minY = Math.min(this.getCoordY(), this.getCoordY() + Math.sin(ang) * rayon);
            maxY = Math.max(this.getCoordY(), this.getCoordY() + Math.sin(ang) * rayon);

            if (x >= minX && x <= maxX && y >= minY && y <= maxY)
                return true;
        }

        return false;

    }

    /**
     * Permet de remettre à 0 le compteur
     */
    public static void resetCompteur() {
        Ville.nbVille = 0;
    }

    /**
     * Permet de modifier le nom de la ville
     * @param nom Nom de la ville
     */
    public void setNomVille(String nom)
    {
        this.nomVille = nom;
    }

    /**
     * Permet de modifier les coordonées X de la ville
     * @param x Nouvelle coordonée X
     */
    public void setCoordX(int x)
    {
        this.coordX = x;
    }

    /**
     * Permet de modifier les coordonées Y de la ville
     * @param y Nouvelle coordonée Y
     */
    public void setCoordY(int y)
    {
        this.coordY = y;
    }

    public void setRegion(Region r)
    {
        this.region = r;
    }

    public void setNumVille(int num)
    {
        this.numUtilisateur = num;
    }



}