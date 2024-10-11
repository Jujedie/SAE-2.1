package psyche.metier;

import psyche.Controleur;
import psyche.metier.jetons.Mine;
import psyche.metier.jetons.Minerai;

import java.io.FileInputStream;
import java.util.Scanner;

public class ChargerScenario
{

    public ChargerScenario(String lienFichier, Controleur ctrl, Metier metier, int etape)
    {

        ChargerScenario.chargerFichier(lienFichier, ctrl, metier, etape);

    }

    public static Boolean chargerFichier(String lienFichier, Controleur ctrl, Metier metier, int etape)
    {

        if (lienFichier == null) return false;
        ctrl.resetMineDep();

        System.out.println(etape);

        Scanner sc;
        String ligne;
        int     dernierIndex;
        int     numJoueur = -1;
        int numEtape;

        try
        {
            sc = new Scanner(new FileInputStream("../psyche/scenario/" + lienFichier));

            while (sc.hasNextLine()) {

                ligne = sc.nextLine();


                if (ligne.startsWith("[E]"))            // Gestion des Etapes
                {
                    try {
                        numEtape = Integer.parseInt(ligne.substring(3));
                        System.out.println("Num Etape lu dans scenario_" + lienFichier.substring(lienFichier.indexOf("_"),lienFichier.indexOf("."))+".run : " + numEtape + " etape :" + etape);
                        if (numEtape == etape) return true;
                    } catch (Exception e) { return false; }
                }

                if (ligne.startsWith("[R]"))            // rénitilisation des routes
                {
                    metier.reset();
                }


                if (ligne.startsWith("[M]"))            // Gestion des mines et affectation des ressources
                {

                    int idMine;
                    String typeMinerai;
                    Minerai minerai;

                    dernierIndex = ligne.indexOf(";");

                    idMine = Integer.parseInt(ligne.substring(3, dernierIndex));

                    typeMinerai = ligne.substring(dernierIndex + 1);

                    minerai = switch (typeMinerai) {
                        case "FER"          -> Minerai.FER;
                        case "MONNAIE"      -> Minerai.MONNAIE;
                        case "PLATINE"      -> Minerai.PLATINE;
                        case "ALUMINIUM"    -> Minerai.ALUMINIUM;
                        case "NICKEL"       -> Minerai.NICKEL;
                        case "COBALT"       -> Minerai.COBALT;
                        case "OR"           -> Minerai.OR;
                        case "ARGENT"       -> Minerai.ARGENT;
                        case "TITANE"       -> Minerai.TITANE;
                        default             -> null;
                    };

                    ctrl.affecterMineRessource(idMine, minerai);

                }

                if (ligne.startsWith("[J]"))            // Fait jouer un joueur
                {
                    int indMine1 = Integer.parseInt(ligne.substring(ligne.indexOf("]") + 1, ligne.indexOf(";")));
                    int indMine2 = Integer.parseInt(ligne.substring(ligne.indexOf(";") + 1));

                    Mine mineDep = metier.getMine(indMine1);
                    Mine mineArr = metier.getMine(indMine2);

                    ctrl.lierMine(mineDep, mineArr);
                }

                if (ligne.startsWith("[D]"))            // Description de l'action
                {
                    ctrl.setDescription(ligne.substring(3));
                }

                if (ligne.startsWith("[S]"))            // Description de l'action
                {
                    int indJoueur = Integer.parseInt(ligne.substring(ligne.indexOf("]") + 1,ligne.indexOf(";")));
                    int score     = Integer.parseInt(ligne.substring(ligne.indexOf(";") + 1));
                    ctrl.setScoreJoueur(indJoueur, score  );
                }

                if (ligne.startsWith("[EG]"))
                {
                    ctrl.copierJoueur(1);
                }

                if (ligne.startsWith("[CM]"))           // Clic sur une mine
                {
                    for ( Mine m: metier.getEnsMine())
                    {
                        if (m.getNum() == Integer.parseInt(ligne.substring(ligne.indexOf("]") + 1)))
                        {
                            System.out.println("true" + m);
                            int x = m.getCoordX();
                            int y = m.getCoordY();

                            ctrl.clicArtificiel(x, y);
                            System.out.println("click Artificielle");
                        }
                    }
                }

                if (ligne.startsWith("[FJ]"))           // Fin du jeu
                {

                    numJoueur = -1;

                }

            }
            return true;

        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * Permet de récupérer le nombre d'étapes d'un scénario
     * @param lienFichier : nom du fichier
     * @return nombre d'étapes
     */
    public static int getNbEtapesMax(String lienFichier)
    {
        Scanner sc;
        String ligne;
        int nbEtapes = -1;

        try
        {
            sc = new Scanner(new FileInputStream("../psyche/scenario/" + lienFichier));
            while (sc.hasNextLine())
            {
                ligne = sc.nextLine();
                if(ligne.startsWith("[E]"))
                {
                    nbEtapes++;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return 0;
        }
        return  nbEtapes;
    }
}
