package psyche;

import psyche.metier.*;
import psyche.metier.jetons.*;
import psyche.vue.FramePlateau;
import psyche.vue.FrameScenario;
import psyche.vue.FrameScore;
import psyche.vue.indiv.FrameJoueur;


import java.io.FileInputStream;
import java.util.List;
import java.util.Scanner;

public class Controleur
{
    //private Plateau p;
    private Metier        metier       ;
    private int           nbTour       ;

    private String        param        ;

    private FrameJoueur   frameJoueur1 ;
    private FrameJoueur   frameJoueur2 ;
    private FramePlateau  framePlateau ;

    private FrameScenario frameScenario;

    /**
     * Constructeur de la classe Controleur
     * @param param le paramètre servant à la sélection du scénario.
     * @param num le numéro du scénario
     */
    public Controleur(String param, String map)
    {
        // Initialiser le dictionnaire
        new Dictionnaire();

        this.metier = new Metier(this);

        this.nbTour = 4 ;
        this.param = param;

        this.frameJoueur1 = new FrameJoueur (this, 1);
        this.frameJoueur2 = new FrameJoueur (this, 2);

        this.framePlateau = new FramePlateau(this);


        if (param.length() == 0)
        {
            this.chargerFichier(null);
            this.metier.melangerRessource();
        }
        else
        {
            char lettre = param.charAt(0);

            if (!map.isEmpty())
            {
                this.chargerFichier(map);
            }

            if (lettre == 's')
            {
                System.out.println("CHARGEMENT SCENARIO :" + param);
                this.frameScenario = new FrameScenario(this);
                ChargerScenario.chargerFichier(this.param, this, this.metier, 0);

                this.metier.afficherEtatMine();
            }

        }

        System.out.println("Lancement de la partie");
        //while (this.partieEnCours())

        System.out.println("Fin de la partie\n");



        /*
        System.out.println("Score du Joueur 1 : " + this.metier.getJoueur(1).calculerScoreTotal(this.metier.getJoueur(2)));
        System.out.println("Score du Joueur 2 : " + this.metier.getJoueur(2).calculerScoreTotal(this.metier.getJoueur(1)));
        System.out.println(this.metier.afficherDetailScore());
        */
        Joueur gagnant = this.metier.gagnant();



        if ( gagnant == null)
        {
            System.out.println("Les deux joueur ont gagnés");
        }
        else  System.out.println( gagnant.getNumero() == 1 ? "La "+Dictionnaire.getNomJoueur1()+" a gagné." : "Le "+Dictionnaire.getNomJoueur1()+" a gagné.");
    }

    public static void main(String[] a)
    {
        String param;
        String map2;
        boolean partieEnCours = true;
        try
        {
            param = a[0];
        } catch (Exception e) { param = ""; }

        try {
            map2 = a[1];
        } catch (Exception e) { map2 = "map.data"; }

        Controleur c = new Controleur(param, map2);
    }

    /**
     * Permet de savoir si toutes les mines ont été attribués à un joueur
     * @return {boolean} True s'il reste des mines à prendre, sinon False
     */
    public boolean partieEnCours ()
    {

        for (Mine m : this.metier.getEnsMine())
        {

            if (m != null)
            {
                if (!m.getRegion().name().equals("ROME") && m.getJoueur() == null)
                {
                    return true;
                }
            }


        }

        this.finirJeuEtFermer();

        return false;

    }

    /**
     * Affecte une ressource à une Mine
     * @param numMine Numéro de la mine
     * @param m Minerai à affecter
     */
    public void affecterMineRessource(int numMine, Minerai m)
    {
        this.metier.affcterMineRessource(numMine, m);

    }

    /**
     * Permet de charger la map à partir du fichier map.data
     */
    public void chargerFichier(String map)
    {

        if (map == null) map = "map.data";

        Scanner sc;
        String  typeLecture = null;
        String  ligne;

        try
        {
            sc = new Scanner(new FileInputStream("../psyche/theme/" + map));

            while (sc.hasNextLine())
            {
                int dernierIndex  = 0;
                int dernierIndex2 = 3;

                ligne = sc.nextLine();

                String str1 = ligne.substring(dernierIndex2);
                if (ligne.substring(dernierIndex, dernierIndex2).equals("[V]"))// ligne = [V]num;type numéro;x;y
                {
                    typeLecture = "ville";
                }
                else
                {
                    if (ligne.substring(dernierIndex, dernierIndex2).equals("[R]"))// ligne =
                    // [R]troncons;numVA;numVB
                    {
                        typeLecture = "route";
                    }
                }


                if (typeLecture.equals("ville"))
                {
                    int    idVille   ;
                    String nomVille  ;
                    int    posXMine  ;
                    int    posYMine  ;
                    int    numRegion ;
                    String typeRegion;
                    Region region    ;

                    dernierIndex = str1.indexOf(';');
                    idVille = Integer.parseInt(str1.substring(0, dernierIndex));

                    str1 = str1.substring(dernierIndex + 1);
                    dernierIndex2 = str1.indexOf(';');

                    nomVille = str1.substring(0, dernierIndex2);

                    str1 = str1.substring(dernierIndex2 + 1);
                    dernierIndex = str1.indexOf(';');

                    posXMine = Integer.parseInt(str1.substring(0, dernierIndex));

                    str1 = str1.substring(dernierIndex + 1);
                    dernierIndex2 = str1.indexOf(';');

                    posYMine = Integer.parseInt(str1.substring(0, dernierIndex2));

                    str1 = str1.substring(dernierIndex2 + 1);
                    dernierIndex = str1.indexOf(';');

                    numRegion = Integer.parseInt(str1.substring(0, dernierIndex));

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

                    this.metier.ajouterMine(new Mine(numRegion, region, posXMine, posYMine));

                }
                else
                {
                    if (typeLecture.equals("route"))
                    {
                        int sections;
                        int numMA;
                        int numMB;

                        dernierIndex2 = str1.indexOf(';');

                        sections = Integer.parseInt(str1.substring(0, dernierIndex2));

                        str1 = str1.substring(dernierIndex2 + 1);
                        dernierIndex = str1.indexOf(';');

                        numMA = Integer.parseInt(str1.substring(0, dernierIndex));

                        numMB = Integer.parseInt(str1.substring(dernierIndex + 1));

                        this.metier.ajouterRoute(Route.creerRoute(sections, this.metier.getMine(numMA), this.metier.getMine(numMB)));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Permet de mettre à jour les deux fenêtres des joueurs
     */
    public void changerFenetreJoueur()
    {

        this.frameJoueur2.majIhm();
        this.frameJoueur1.majIhm();

    }

    /**
     * Action lorsque le clic souhaite rejoindre deux mines lors du clic
     * @param mineDep Mine de départ
     * @param mineArr Mine d'arrivée
     */
    public boolean rejoindreMines(Mine mineDep, Mine mineArr)
    {
        return this.metier.rejoindreMines(mineDep, mineArr);
    }


    /**
     * Permet de récupérer l'ensemble des routes
     * @return {List<Route>} L'ensemble des routes
     */
    public List<Route> getEnsRoute ()        {return      metier.getEnsRoute () ;}

    /**
     * Permet de récupérer l'ensemble des sommets
     * @return {List<Mine>} L'ensemble des sommets
     */
    public List<Mine>  getEnsMine  ()        {return      metier.getEnsMine  () ;}

    /**
     * Ajouter un sommet à la liste
     * @param m Sommet (mine) à ajouter
     * @return {boolean} True si le sommet a été ajouté, sinon false
     */
    public boolean     ajouterMine (Mine m)  {return this.metier.ajouterMine (m);}

    /**
     * Ajouter une route à la liste
     * @param r Route à ajouter
     * @return {boolean} True si la route a été ajoutée, sinon false
     */
    public boolean     ajouterRoute(Route r) {return this.metier.ajouterRoute(r);}

    /**
     *  Retourne le nombre de routes totales
     * @return le nombre de routes totales
     */
    public int         getNbRoute  ()        {return this.metier.getNbRoute  () ;}
    /**
     *  Retourne le nombre de mines totales
     * @return le nombre de mines totales
     */
    public int         getNbMine   ()        {return this.metier.getNbMine   () ;}

    /**
     *  Retourne le joueur actuellement en train de jouer
     * @return le joueur actuellement en train de jouer
     */
    public Joueur      getJoueur   ()        {return this.metier.getJoueur   ()     ;}
    /**
     *  Retourne le joueur précisé en paramètre
     * @param num numéro du joueur voulu (1 ou 2)
     * @return le joueur précisé en paramètre
     */
    public Joueur      getJoueur   (int num) {return this.metier.getJoueur   (num)  ;}
    /**
     *  Retourne le joueur suivant, après que l'autre joueur ai joué
     * @return le joueur suivant
     */
    public Joueur      suivant     ()        {return this.metier.suivant     ()     ;}

    /**
     *  Retourne le paramètre mis au lancement de l'application (le scenario)
     * @return le paramètre mis au lancement de l'application (le scenario)
     */
    public String      getParam    ()        {return this.param                     ;}

    /**
     *  Permet d'afficher un des nombreux messages d'erreurs
     * @param message le message à renvoyer
     */
    public void     afficherMsgErreur(String message)
    {
        this.framePlateau.afficherMsgErreur(message);
    }

    /**
     * Permet de passer à l'étape suivante.
     * @param numEtape numéro de l'étape actuelle.
     * @return true si l'étape suivante a été chargée, sinon false.
     */
    public boolean etapeSuivante(int numEtape)
    {
        return ChargerScenario.chargerFichier(this.param,this,this.metier,++numEtape);
    }

    /**
     * Permet de passer à l'étape précédente.
     * @param numEtape numéro de l'étape actuelle.
     * @return true si l'étape précédente a été charger, sinon false.
     */
    public boolean etapePrecedente(int numEtape)
    {
        return ChargerScenario.chargerFichier(this.param,this,this.metier,--numEtape);
    }

    /**
     * Permet d'aller directement à une étape précise
     * @param numEtape numéro de l'étape visée.
     * @return true si l'étape visée a été chargée
     */
    public boolean allerAEtape(int numEtape)
    {
        return ChargerScenario.chargerFichier(this.param, this, this.metier, numEtape);
    }

    /**
     * Permet de retourner le numéro de l'étape la plus haute du scenario actuel
     * @return le nombre d'étapes maximum du scenario
     */
    public int getNbEtapesMax()
    {
        return ChargerScenario.getNbEtapesMax(this.param);
    }

    /**
     * Permet de lier les 2 mines
     * @param mineDep Mine de départ à lier
     * @param mineArr Mine d'arrivée à lier
     */
    public void lierMine(Mine mineDep, Mine mineArr)
    {
        this.framePlateau.lierMine(mineDep, mineArr);
    }

    /**
     * Permet de changer la description de l'étape actuelle du scenario
     * @param description description de l'étape actuelle du scenario
     */
    public void setDescription(String description)
    {
        this.frameScenario.setDescription(description);
    }


    public void clicArtificiel(int x, int y) { this.framePlateau.clicArtificiel(x, y);}

    public void resetMineDep()
    {
        this.framePlateau.resetMineDep();
    }

    public void finirJeuEtFermer()
    {

        this.frameJoueur1.setVisible(false);
        this.frameJoueur2.setVisible(false);
        this.framePlateau.setVisible(false);
        if (this.frameScenario != null) this.frameScenario.setVisible(false);
        
        System.out.println(this.metier.afficherDetailScore());

        new FrameScore(this);

    }

    public Joueur gagnant()
    {
        return this.metier.gagnant();
    }

    public void setScoreJoueur(int numJoueur, int score)
    {
        this.metier.setScoreJoueur(numJoueur,score);
    }

    public void copierJoueur(int indJoueur)
    {
        this.metier.copierJoueur(indJoueur);
    }

}
