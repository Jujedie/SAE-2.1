package psyche.metier;

import java.io.FileInputStream;
import java.util.Scanner;

/**
 * Le dictionnaire permet de changer le vocabulaire de l'application
 * - Sommet
 * - Route
 * - Nom du joueur 1
 * - Nom du joueur 2
 */
public class Dictionnaire
{

    /** Nom des sommets */
    private static String       nomSommet;
    /** Nom des routes */
    private static String       nomRoute;
    /** Nom du joueur 1 */
    private static String       nomJoueur1;
    /** Nom du joueur 2 */
    private static String       nomJoueur2;

    public Dictionnaire()
    {

        Dictionnaire.nomSommet  = "";
        Dictionnaire.nomJoueur1 = "";
        Dictionnaire.nomJoueur2 = "";
        Dictionnaire.nomRoute   = "";

        this.lireVocab();

    }

    /**
     * Charger le dictionnaire à l'aide du fichier voca.data situé dans theme
     */
    private void lireVocab()
    {

        Scanner sc;
        String typeLecture = null;
        String ligne;

        try {
            sc = new Scanner(new FileInputStream("../psyche/theme/voca.data"));

            while (sc.hasNextLine()) {

                ligne = sc.nextLine();

                if (ligne.startsWith("[S]")) {

                    Dictionnaire.nomSommet = ligne.substring(3);

                }

                if (ligne.startsWith("[J1]")) {

                    Dictionnaire.nomJoueur1 = ligne.substring(4);

                }

                if (ligne.startsWith("[J2]")) {

                    Dictionnaire.nomJoueur2 = ligne.substring(4);

                }

                if (ligne.startsWith("[R]")) {

                    Dictionnaire.nomRoute = ligne.substring(3);

                }

            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    /**
     * Récupérer le nom des sommets
     * @return {String} Le nom des sommets
     */
    public static String getNomSommet()     { return Dictionnaire.nomSommet;    }

    /**
     * Récupérer le nom du joueur numéro 1
     * @return {String} Nom du joueur 1
     */
    public static String getNomJoueur1()    { return Dictionnaire.nomJoueur1;   }

    /**
     * Récupérer le nom du joueur numéro 2
     * @return {String} Nom du joueur 2
     */
    public static String getNomJoueur2()    { return Dictionnaire.nomJoueur2;   }

    /**
     * Récupérer le nom des routes
     * @return {String} Nom des routes
     */
    public static String getNomRoute()      { return Dictionnaire.nomRoute;     }

}
