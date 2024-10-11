package psyche.metier;

import psyche.Controleur;
import psyche.metier.jetons.Jeton;
import psyche.metier.jetons.Mine;
import psyche.metier.jetons.Minerai;
import psyche.metier.jetons.Region;
import psyche.metier.jetons.Ressource;

import javax.xml.transform.stream.StreamSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Metier
{
    private Joueur      joueur1  ;
    private Joueur      joueur2  ;

    private Controleur  ctrl     ;

    private Joueur      joueurAct;

    private List<Route> ensRoute ;
    private List<Mine>  ensMine  ;

    public Metier(Controleur ctrl)
    {
        this.ctrl      = ctrl;

        this.joueur1   = new Joueur(1);
        this.joueur2   = new Joueur(2);

        this.joueurAct = this.joueur1;

        this.ensRoute  = new ArrayList<>();
        this.ensMine   = new ArrayList<>();
    }

    /**
     * fonction qui ajoute une mine à ensMine
     * @param m Mine à ajouter
     * @return true si l'ajout a été effectué, false sinon
     */
    public boolean ajouterMine(Mine m)
    {
        if (m == null) return false;

        this.ensMine.add(m);
        return true;
    }

    /**
     * fonction qui ajoute une route à ensRoute
     * @param r Route à ajouter
     * @return true si l'ajout a été effectué, false sinon
     */
    public boolean ajouterRoute(Route r)
    {
        if (r == null) return false;

        this.ensRoute.add(r);
        return true;
    }

    /**
     * Permet de créer toutes les ressources ET de les attribuer aux mines
     */
    public void melangerRessource()
    {

        List<Ressource> ensRessource = new ArrayList<>();

        for (Minerai m : Minerai.values())
        {
            if (m.name().equals("MONNAIE"))
            {
                for (int i = 0; i < 8; i++) ensRessource.add(new Ressource(m));
            }
            else
            {
                for (int i = 0; i < 4; i++) ensRessource.add(new Ressource(m));
            }
        }

        Collections.shuffle(ensRessource);

        for (Mine m : this.ensMine) {
            if (m.getRegion().name().equals("ROME")) continue;
            m.setRessource(ensRessource.remove(0));
        }

    }

    /**
     * affecte une ressource à une mine
     * @param numMine le numéro de la mine
     * @param minerai le minerai à affecter
     */
    public void affcterMineRessource(int numMine, Minerai minerai)
    {
        System.out.println("Ressources affecter");
        this.ensMine.get(numMine - 1).setRessource(new Ressource(minerai));
    }

    /**
     * renvoie le joueur actuel
     * @return le joueur actuel
     */
    public Joueur getJoueur()
    {
        return this.joueurAct;
    }

    /**
     * renvoie le joueur demandé
     * @param nbJ le numéro du joueur
     * @return le joueur demandé
     */
    public Joueur getJoueur (int nbJ)
    {
        if (nbJ == 1)  return this.joueur1;

        return this.joueur2;
    }

    /**
     * renvoie la mine correspondant au numéro
     * @param a le numéro du jeton
     * @return la mine correspondant au numéro
     */
    public Mine getMine(int a)
    {
        for ( Mine m : this.ensMine )
        {
            if(m.getNumJeton() == a) return m;
        }
        return null;
    }

    /**
     * Permet au jeu de passer au joueur suivant
     * @return le joueur suivant
     */
    public Joueur suivant()
    {
        if (this.joueurAct == this.joueur1)
            this.joueurAct = this.joueur2;
        else
            this.joueurAct = this.joueur1;

        this.ctrl.changerFenetreJoueur();

        return this.joueurAct;
    }

    /**
     * Attribuer une ressource à un joueur
     * @param m La mine dont on veut attribuer la ressource
     * @return true si le joueur a pu recevoir la ressource, false sinon
     */
    public boolean attribuerRessource(Mine m)
    {
       return this.joueurAct.ajouterJeton(m.retirerRessource());
    }

    /**
     * renvoie la route correspondant au numéro
     * @param a le numéro de la route dans ensRoute
     * @return la route correspondant au numéro
     */
    public Route getRoute(int a)
    {
        return this.ensRoute.get(a);
    }

    public List<Route> getEnsRoute (){ return this.ensRoute       ;}
    public List<Mine>  getEnsMine  (){ return this.ensMine        ;}

    public int         getNbRoute  (){ return this.ensRoute.size();}
    public int         getNbMine   (){ return this.ensMine .size();}

    /**
     * Affiche l'état des mines
     */
    public void afficherEtatMine()
    {

        for (Mine m : this.ensMine)
        {
            System.out.println(m.toString());
        }

    }

    /**
     * affiche la situation à l'instant t des scores des joueurs
     * @return le détail des scores
     */
    public String afficherDetailScore()
    {
        String  detScr  = "+----------------------------------------------------------------------+\n";
                detScr += "|                             FICHE DE SCORE                           |\n";
                detScr += "+----------------------------------------------------------------------+\n";
                detScr += "|              |"+String.format("%27s", Dictionnaire.getNomJoueur1())         + "|"+ String.format("%27s", Dictionnaire.getNomJoueur2()) +        "|\n";
                detScr += "|point route   |"+String.format("%27s", this.joueur1.getJtnScore())           + "|"+String.format("%27s", this.joueur2.getJtnScore())+           "|\n";
                detScr += "|----------------------------------------------------------------------|\n";
                detScr += "|point mine    |                           |                           |\n";
                detScr += "|Jaune         |"+String.format("%27d",this.joueur1.calculPointMine(Region.JAUNE ))     +"|"+String.format("%27d", this.joueur2.calculPointMine(Region.JAUNE ))+"|\n";
                detScr += "|Bleu          |"+String.format("%27d",this.joueur1.calculPointMine(Region.BLEU  ))     +"|"+String.format("%27d", this.joueur2.calculPointMine(Region.BLEU  ))+"|\n";
                detScr += "|Gris          |"+String.format("%27d",this.joueur1.calculPointMine(Region.GRIS  ))     +"|"+String.format("%27d", this.joueur2.calculPointMine(Region.GRIS  ))+"|\n";
                detScr += "|Vert          |"+String.format("%27d",this.joueur1.calculPointMine(Region.VERT  ))     +"|"+String.format("%27d", this.joueur2.calculPointMine(Region.VERT  ))+"|\n";
                detScr += "|Rouge         |"+String.format("%27d",this.joueur1.calculPointMine(Region.ROUGE ))     +"|"+String.format("%27d", this.joueur2.calculPointMine(Region.ROUGE ))+"|\n";
                detScr += "|Marron        |"+String.format("%27d",this.joueur1.calculPointMine(Region.MARRON))     +"|"+String.format("%27d", this.joueur2.calculPointMine(Region.MARRON))+"|\n";
                detScr += "|Total         |"+String.format("%27d",this.joueur1.calculPointToutesMine(       ))     +"|"+String.format("%27d", this.joueur2.calculPointToutesMine(       ))+"|\n";
                detScr += "|----------------------------------------------------------------------|\n";
                detScr += "|Plat Indiv    |                           |                           |\n";
                detScr += "|Piece         |"+String.format("%27d",this.joueur1.calculPointPiece(  ))               +"|"+String.format("%27d", this.joueur2.calculPointPiece  ())+"|\n";
                detScr += "|Colonne       |"+String.format("%27d",this.joueur1.calculPointColonne())               +"|"+String.format("%27d", this.joueur2.calculPointColonne())+"|\n";
                detScr += "|Ligne         |"+String.format("%27d",this.joueur1.calculPointLigne(  ))               +"|"+String.format("%27d", this.joueur2.calculPointLigne  ())+"|\n";
                detScr += "|Total         |"+String.format("%27d",this.joueur1.calculPointPiece(  ))               +"|"+String.format("%27d", this.joueur2.calculPointPiece  ())+"|\n";
                detScr += "|----------------------------------------------------------------------|\n";
                detScr += "|jeton posses  |"+String.format("%27d",this.joueur1.getJtnPossession())                 +"|"+String.format("%27d", this.joueur2.getJtnPossession())+"|\n";
                detScr += "|Bonus         |"+String.format("%27d",this.joueur1.calculBonusPossession(this.joueur2))+"|"+String.format("%27d", this.joueur2.calculBonusPossession(this.joueur1))+"|\n";
                detScr += "|----------------------------------------------------------------------|\n";
                detScr += "|Total         |"+String.format("%27s", this.joueur1.calculerScoreTotal(this.joueur2))  +"|"+String.format("%27d", this.joueur2.calculerScoreTotal(this.joueur1))+"|\n";
                detScr += "|----------------------------------------------------------------------|\n";

        return detScr;
    }

    /**
     * Permet de mettre la possesion de toutes les routes à null
     */
    public void resetPossessionRoute()
    {
        for (Route r : this.ensRoute)
        {
            r.resetJoueur();
        }
    }

    /**
     * Permet de reset le jeu
     */

    public void reset()
    {
        this.joueur1.reset();
        this.joueur2.reset();

        this.joueurAct = this.joueur1;

        this.resetPossessionRoute();
    }


    /**
     * Méthode permettant de rejoindre les deux mines
     * @param mineDep   Mine de départ
     * @param mineArr   Mine d'arrivée
     */
    public boolean rejoindreMines(Mine mineDep, Mine mineArr)
    {

        boolean routeTrouvee = false;
        boolean joueurSuivant = false;
        boolean aOr = false;

        if (mineDep.getRessource() != null && mineDep.getRessource().getMinerai() != null)
        {
            this.ctrl.afficherMsgErreur("La mine de départ est erronée");
            return false;
        }

        for (Route r : mineDep.getEnsRoute())
        {

            if (r.getMineArr() == mineArr)
            {
                r.setAppartientJoueur(this.ctrl.getJoueur());

                if (mineArr.getRessource() != null)
                {
                    if(this.ctrl.getJoueur().getJtnPossession() >= r.getNbSection()) {
                        int pointRoute = r.getNbSection();
                        if (r.getMineArr().getRessource().getMinerai() == Minerai.OR)
                        {
                            aOr = true;
                            pointRoute *= 2;
                        }

                        // On ajoute le jeton au joueur et on la retire de la Mine
                        this.attribuerRessource(mineArr);

                        // On affecte la mine à un Joueur

                        mineArr.setJoueur(this.ctrl.getJoueur());

                        this.ctrl.getJoueur().ajouterMine(mineArr);

                        this.ctrl.getJoueur().setJtnScore(this.ctrl.getJoueur().getJtnScore() +  pointRoute);
                        this.ctrl.getJoueur().setJtnPossession(this.ctrl.getJoueur().getJtnPossession() - r.getNbSection());
                        joueurSuivant = true;
                    }
                    else {
                        this.ctrl.afficherMsgErreur("Vous n'avez pas assez de jetons possession pour accéder à cette Mine !");
                    }

                }

                routeTrouvee = true;
            }

        }

        for (Route r : mineArr.getEnsRoute())
        {

            if (r.getMineArr() == mineDep)
            {
                r.setAppartientJoueur(this.ctrl.getJoueur());
                if (mineArr.getRessource() != null)
                {
                    if(this.ctrl.getJoueur().getJtnPossession() >= r.getNbSection()) {
                        int pointRoute = r.getNbSection();
                        if (r.getMineDep().getRessource().getMinerai() == Minerai.OR)
                        {
                            aOr = true;
                            pointRoute *= 2;
                        }

                        this.attribuerRessource(mineArr);

                        mineArr.setJoueur(this.ctrl.getJoueur());
                        this.ctrl.getJoueur().ajouterMine(mineArr);
                        this.ctrl.getJoueur().setJtnScore(this.ctrl.getJoueur().getJtnScore() + pointRoute);
                        this.ctrl.getJoueur().setJtnPossession(this.ctrl.getJoueur().getJtnPossession() - r.getNbSection());
                        joueurSuivant = true;
                    }
                    else {
                        this.ctrl.afficherMsgErreur("Vous n'avez pas assez de jetons possession pour accéder à cette Mine !");
                    }

                }

                System.out.println("TROUVE FIN  !!!!");
                routeTrouvee = true;
            }

        }

        if (routeTrouvee)
        {
            if (joueurSuivant) this.suivant();
        }
        else this.ctrl.afficherMsgErreur("Aucune route entre ces deux mines");

        System.out.println("PARTIE EN COURS :::::");

        // JULIEN NE SUPPRIME PAS !!!!!!
        System.out.println(this.ctrl.partieEnCours());

        return aOr;
    }

    /**
     * Méthode permettant de savoir si le jeu est terminé
     * @return le joueur gagnant (si égalité retourne null)
     */
    public Joueur gagnant()
    {
        if (this.getJoueur(1).calculerScoreTotal(this.getJoueur(2)) == this.getJoueur(2).calculerScoreTotal(this.getJoueur(1)))
        {
            int cpt1 , cpt2;
            List<Region> ensCouleurs = new ArrayList<>();

            cpt1 =0;
            for ( Mine m : this.getJoueur(1).getEnsMine())
            {
                if (! ensCouleurs.contains(m.getRegion()))
                {
                    ensCouleurs.add(m.getRegion());
                    cpt1++;
                }
            }

            ensCouleurs = new ArrayList<>();
            cpt2=0;
            for ( Mine m : this.getJoueur(2).getEnsMine())
            {
                if (! ensCouleurs.contains(m.getRegion()))
                {
                    ensCouleurs.add(m.getRegion());
                    cpt2++;
                }
            }

            if ( cpt1 == cpt2 )
            {
                cpt1 = 0;
                cpt2 = 0;

                for (Jeton j : this.getJoueur(1).getEnsJeton())
                {
                    if (j.getClass().getName().equals("Ressource"))
                    {
                        Ressource r = (Ressource) (j);
                        if(r.getMinerai() == Minerai.OR) cpt1++;
                    }
                }
                for (Jeton j : this.getJoueur(2).getEnsJeton())
                {
                    if (j.getClass().getName().equals("Ressource"))
                    {
                        Ressource r = (Ressource) (j);
                        if(r.getMinerai() == Minerai.OR) cpt2++;
                    }
                }
                if ( cpt1 == cpt2 )
                {
                    if ( this.getJoueur(1).getJtnPossession() == this.getJoueur(2).getJtnPossession() )
                    {
                        return null;
                    }
                    else return this.getJoueur(1).getJtnPossession() > this.getJoueur(2).getJtnPossession() ? this.getJoueur(1) : this.getJoueur(2);
                }
                else return cpt1 > cpt2 ? this.getJoueur(1) : this.getJoueur(2);
            }
            else return cpt1 > cpt2 ? this.getJoueur(1) : this.getJoueur(2);
        }
        else return this.getJoueur(1).calculerScoreTotal(this.getJoueur(2)) > this.getJoueur(2).calculerScoreTotal(this.getJoueur(1)) ? this.getJoueur(1) : this.getJoueur(2);
    }

    public void resetMap()
    {

        this.ensMine  = new ArrayList<>();
        this.ensRoute = new ArrayList<>();

    }

    public void setScoreJoueur(int numJoueur, int score)
    {
        if (numJoueur == 1) this.joueur1.setJtnScore(score);
        else this.joueur2.setJtnScore(score);
    }

    public void copierJoueur(int indJoueur)
    {
        if (indJoueur == 1) this.joueur1.copier(this.joueur2);
        else this.joueur2.copier(this.joueur1);
    }

}
