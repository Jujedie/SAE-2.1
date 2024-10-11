package routes;

import routes.metier.*;

import routes.vue.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Controleur {

    private Metier         metier;

    private FramePlateau   framePlateau;
    private FrameVille     frameVille;
    private FrameRoute     frameRoute;
    private String         lienFichier;
    private FrameAllVilles frameAllVilles;

    public Controleur()
    {
        this.metier = new Metier();
        this.framePlateau = new FramePlateau(this);

        this.lienFichier = "../routes/ensemble.data";
        this.chargerFichier();
    }

    public Ville getVille(int num)
    {
        return this.metier.getVille(num);
    }

    public void chargerFichier()
    {

        Scanner sc;
        String typeLecture = null;
        String ligne;

        if (this.lienFichier == null)
            return;

        this.metier.resetVille();
        this.metier.resetRoute();

        try
        {
            sc = new Scanner(new FileInputStream(this.lienFichier));

            while (sc.hasNextLine())
            {
                int dernierIndex = 0;
                int dernierIndex2 = 3;

                ligne = sc.nextLine();

                String str1 = ligne.substring(dernierIndex2);
                if (ligne.substring(dernierIndex, dernierIndex2).equals("[V]"))// ligne = [ville]num;nom;x;y
                {
                    typeLecture = "ville";
                }
                else
                {
                    if (ligne.substring(dernierIndex, dernierIndex2).equals("[R]"))// ligne =
                                                                                   // [route]troncons;numVA;numVB
                    {
                        typeLecture = "route";
                    }
                }
                if (typeLecture.equals("ville"))
                {
                    int idVille;
                    String nomVille;
                    int posXVille;
                    int posYVille;
                    int numUtilisateur;
                    String typeRegion;

                    Region region;

                    dernierIndex = str1.indexOf(';');
                    idVille = Integer.parseInt(str1.substring(0, dernierIndex));

                    str1 = str1.substring(dernierIndex + 1);
                    dernierIndex2 = str1.indexOf(';');

                    nomVille = str1.substring(0, dernierIndex2);

                    str1 = str1.substring(dernierIndex2 + 1);
                    dernierIndex = str1.indexOf(';');

                    posXVille = Integer.parseInt(str1.substring(0, dernierIndex));

                    str1 = str1.substring(dernierIndex + 1);
                    dernierIndex2 = str1.indexOf(';');

                    posYVille = Integer.parseInt(str1.substring(0, dernierIndex2));

                    str1 = str1.substring(dernierIndex2 + 1);
                    dernierIndex = str1.indexOf(';');

                    numUtilisateur = Integer.parseInt(str1.substring(0, dernierIndex));

                    typeRegion = str1.substring(dernierIndex + 1);

                    region = switch (typeRegion) {
                        case "BLEU"     -> Region.BLEU;
                        case "ROUGE"    -> Region.ROUGE;
                        case "VERT"     -> Region.VERT;
                        case "JAUNE"    -> Region.JAUNE;
                        case "GRIS"     -> Region.GRIS;
                        case "MARRON"   -> Region.MARRON;
                        case "ROME"     -> Region.ROME;
                        default -> null;
                    };

                    this.metier.ajouterVille(Ville.creerVille(nomVille, posXVille, posYVille, region, numUtilisateur));

                }
                else
                {
                    if (typeLecture.equals("route"))
                    {
                        int trancons;
                        int numVA;
                        int numVB;

                        dernierIndex2 = str1.indexOf(';');

                        trancons = Integer.parseInt(str1.substring(0, dernierIndex2));

                        str1 = str1.substring(dernierIndex2 + 1);
                        dernierIndex = str1.indexOf(';');

                        numVA = Integer.parseInt(str1.substring(0, dernierIndex)) - 1;

                        numVB = Integer.parseInt(str1.substring(dernierIndex + 1)) - 1;

                        this.metier.ajouterRoute(
                        Route.creerRoute(trancons, this.metier.getVille(numVA), this.metier.getVille(numVB)));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void ouvrirFrameRoute()
    {
        this.frameRoute = new FrameRoute(this);
    }

    public void ouvrirFrameVille()
    {
        this.frameVille = new FrameVille(this);
    }

    public void sauvegarderDansFichier()
    {

        List<Ville> ensVille = this.metier.getEnsVille();
        List<Route> ensRoute = this.metier.getEnsRoute();

        System.out.println("AFFICHAGE :" + this.metier.getNbVille());

        try {

            PrintWriter pw = new PrintWriter(new FileOutputStream(this.lienFichier));

            for (Ville v : ensVille)
            {

                String txt = "[V]" + v.getNumVille() + ";" + v.getNom() + ";" + v.getCoordX() + ";" + v.getCoordY() + ";" + v.getNumUtilisateur() + ";" + v.getRegion().name();

                pw.println(txt);

            }

            for (Route r : ensRoute)
            {

                String txt = "[R]" + r.getTroncons() + ";" + r.getVilleDep().getNumVille() + ";" + r.getVilleArr().getNumVille();

                pw.println(txt);

            }

            pw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * le main 
     * @param args les arguments du main
     */
    public static void main(String[] args) {
        new Controleur();
    }

    /**
     * renvoie le nombre de villes
     * @return le nombre de villes
     */
    public int getNbVille() {
        return this.metier.getNbVille();
    }

    /**
     * renvoie le nombre de routes
     * @return le nombre de routes
     */
    public int getNbRoute() {
        return this.metier.getNbRoute();
    }

    /**
     * renvoie l'ensemble des routes
     * @return renvoie l'ensemble des routes
     */
    public List<Route> getEnsRoute() {
        return this.metier.getEnsRoute();
    }

    /**
     * renvoie l'ensemble des villes
     * @return renvoie l'ensemble des villes
     */
    public List<Ville> getEnsVille() {
        return this.metier.getEnsVille();
    }

    /**
     * donne l'indice de la ville qui se trouve aux coordonnées x et y
     * @param x la coordonnée x
     * @param y la coordonnée y
     * @return l'indice de la ville si elle existe sinon null
     */
    public Integer getIndiceVille(int x, int y) {
        return this.metier.getIndiceVille(x, y);
    }

    /**
     * renvoie le lien du fichier
     * @return retourne le lien du fichier
     */
    public String getLienFichier() {
        return this.lienFichier;
    }

    /**
     * modifie le lien du fichier
     * @param s le lien du fichier à entrer
     */
    public void setLienFichier(String s) {
        this.lienFichier = s;
    }

    /**
     * charge le fichier permettant d'avoir la carte du jeu
     */
    public void ouvrirFichier() {

        this.chargerFichier();

    }

    /**
     * deplace une ville en fonction des arguments donnés
     * @param indiceFig l'indice de la ville à déplacer
     * @param posX position X de la ville
     * @param posY position Y de la ville
     */
    public void deplacerVille(int indiceFig, int posX, int posY)
    {

        this.metier.getVille(indiceFig).deplacerX(posX);
        this.metier.getVille(indiceFig).deplacerY(posY);

    }

    /**
     * met à jour l'ihm
     */
    public void majIHM()
    {
        this.framePlateau.majIHM();

        this.majTableau();

        this.majDdlst();
    }

    /**
     * ouvre la frame all ville
     */
    public void ouvrirAllVille()
    {
        this.frameAllVilles = new FrameAllVilles(this);
    }

    /**
     * met à jour le tableau de la frameAllVilles
     */
    public void majTableau()
    {
        if (this.frameAllVilles != null) this.frameAllVilles.majTableau();
    }

    /**
     * met à jour la liste deroulante
     */
    public void majDdlst()
    {
        if (this.frameRoute != null) this.frameRoute.majDdlst();
    }

    /**
     * ajoute une ville à l'attribut ensVille de Metier
     * @param v la ville à ajouter
     */
    public void ajouterVille(Ville v)
    {
        this.metier.ajouterVille(v);
    }

    public void ajouterRoute(Route r) { this.metier.ajouterRoute(r); }

    /**
     * trouve une ville par son nom
     * @param nom le nom de la ville recherchée
     * @return la ville recherché ou nul si elle n'est pas trouvée
     */
    public Ville trouverVilleNom(String nom)
    {
        return this.metier.trouverVilleNom(nom);
    }

    /**
     * Supprime la ville en parametre
     * @param villeSelected la ville à supprimer
     * @return si la ville a été supprimée
     */
    public boolean supprimerVille(Ville villeSelected)
    {
        return this.metier.supprimerVille(villeSelected);
    }

    /**
     * ouvre la frame pour modifier une ville
     * @param villeSelected la ville sélectionnée
     */
    public void ouvrirFrameModifierVille(Ville villeSelected)
    {
        new FrameModifierVille(this, villeSelected);
    }


    public void modifierVille(Ville villeModifier, String nom, int x, int y, Region region, int numVille)
    {

        this.metier.modifierVille(villeModifier, nom, x, y, region, numVille);

        this.majIHM();

    }

    /**
     * créer un nouveau fichier
     */
    public void nouveauFichier()
    {

        this.lienFichier = "../routes/ensemble.data";
        this.metier.resetRoute();
        this.metier.resetVille();

        this.majIHM();

    }

}
