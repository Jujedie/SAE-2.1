package psyche.metier;

import java.util.List;
import java.util.ArrayList;

import psyche.metier.jetons.*;
import psyche.metier.Dictionnaire;

public class Joueur
{
    private final int   numero  ;
    private List<Jeton> ensJeton;
    private int         jtnPossession;
    private int         jtnScore;
    private int         nbPiece ;
    private List<Mine>  ensMine;

    public Joueur(int num)
    {
        this.numero        = num;
        this.ensJeton      = new ArrayList<>();
        this.jtnPossession = 25;
        this.jtnScore      = 0;
        this.nbPiece       = 0;
        this.ensMine = new ArrayList<>();
    }

    /**
     * Retourne le numero du joueur
     * @return le numero du joueur
     */
    public int         getNumero       () { return this.numero       ; }
    /**
     * Retourne le nombre de jetons d'action restant du joueur
     * @return le nombre de jetons d'action restant du joueur
     */
    public int         getJtnPossession() { return this.jtnPossession; }
    /**
     * Retourne le nombre de jetons score du joueur
     * @return le nombre de jetons score du joueur
     */
    public int         getJtnScore     () { return this.jtnScore     ; }
    /**
     * Retourne le nombre de pieces que le joueur possede
     * @return le nombre de pieces que le joueur possede
     */
    public int         getNbPiece      () { return this.nbPiece      ; }
    /**
     * Retourne la liste de tous les jetons du joueur
     * @return la liste de tous les jetons du joueur
     */
    public List<Jeton> getEnsJeton     () { return this.ensJeton     ; }

    /**
     * Ajoute une mine aux mines possedees par le joueur
     * @param m la mine à ajouter
     */
    public void ajouterMine(Mine m)
    {

        if (m == null) return;

        this.ensMine.add(m);

    }

    /**
     * Retourne la liste des mines possedees par le joueur
     * @return la liste des mines possedees par le joueur
     */
    public List<Mine> getEnsMine()
    {
        return this.ensMine;
    }

    /**
     * Permet d'affecter une nouvelle valeur aux jetons possession du joueur
     * @param j nouvelle valeur a affecter
     */
    public void        setJtnPossession(int j) { this.jtnPossession  = j ; }
    /**
     * Permet d'affecter une nouvelle valeur aux jetons score du joueur
     * @param j nouvelle valeur a affecter
     */
    public void        setJtnScore     (int j) { this.jtnScore       = j ; }
    /**
     * Permet d'affecter une nouvelle valeur aux nombre de pieces du joueur
     * @param j nouvelle valeur a affecter
     */
    public void        setNbPiece      (int j) { this.nbPiece        = j ; }

    public void setEnsMine (List<Mine> ensMine)   { this.ensMine  = ensMine ; }
    public void setEnsJeton(List<Jeton> ensJeton) { this.ensJeton = ensJeton; }

    /**
     * Méthode qui permet au joueur de placer un jeton score.
     * @param j le jeton à placer
     * @return {boolean} si le jeton a bien été placé
     */
    public boolean ajouterJeton(Jeton j)
    {

        if (j == null || this.ensJeton.size() >= 32 ) return false;

        Ressource ressource = (Ressource) j;

        if (ressource.getMinerai().name().equals("MONNAIE"))
        {
            this.nbPiece += 1;
            return true;
        }
        else
        {
            List<Minerai> ensRes = new ArrayList<>();
            for ( Jeton jeton : this.ensJeton)
            {
                Ressource r = (Ressource) jeton;
                if(!ensRes.contains(r.getMinerai()))
                {
                    ensRes.add(r.getMinerai());
                }
            }
            int cptRes = 0;
            for ( Jeton jeton : this.ensJeton)
            {
                Ressource r = (Ressource) jeton;
                if(r.getMinerai().name().equals(ressource.getMinerai().name()))
                {
                    cptRes++;
                }
            }
            if (cptRes >= 4 || ensRes.size() == 8 && cptRes == 0) return false;
            else {
                this.ensJeton.add(ressource);
                return true;
            }
        }
    }

    public String toString()
    {

        String nomJoueur = this.numero == 1 ? Dictionnaire.getNomJoueur1() : Dictionnaire.getNomJoueur2();

        return "Joueur " + nomJoueur +
                " , possede : " + this.jtnPossession +" Jetons de Possession , " +
                " Jeton Score placé en  " + this.jtnScore +
                ", " + this.nbPiece + " Piece" + ((this.nbPiece>0)?"s":"");

    }

    /**
     * Methode qui trie les ressources du joueur de manière que les ressources de même type soient regroupées ensemble par colonnes.
     * @return un tableau de ressources triées.
     */
    public Ressource[] trierTabRessource()
    {
        Ressource[] ensRes = new Ressource[32];

        for(Jeton j : this.ensJeton) {
            Ressource r = (Ressource) (j);

            for (int x = 0; x < 8; x++) {
                if (ensRes[x] == null || r.getMinerai() == ensRes[x].getMinerai()) {
                    for (int y = 0; y < 4; y++) {
                        if (ensRes[x + y * 8] == null) {
                            ensRes[x + y * 8] = r;
                            break;
                        }
                    }
                    break;
                }
            }
        }
        return ensRes;
    }

    /**
     * Retourne toutes les ressources possedees par le joueur
     * @return toutes les ressources possedees par le joueur
     */
    public Ressource[] getJtnRessource2()
    {

        Ressource[] ensRessource = new Ressource[32];
        int cpt = 0;
        for (Jeton j : this.ensJeton)
        {
            if(j.getClass().getName().equals("Ressource"))
            {
                ensRessource[cpt++]=(Ressource)(j);
            }
        }

        return ensRessource;

    }

    /**
     * Methode qui calcule le score tatal du joueur en fonction de ses jetons et des jetons de l'autre joueur.
     * @param autreJoueur l'autre joueur à qui on compare le score.
     * @return {int} le score total du joueur.
     */
    public int calculerScoreTotal(Joueur autreJoueur)
    {
       int score = this.getJtnScore();

       score += this.calculBonusPossession(autreJoueur);
       score += this.calculPointPiece();

       score += this.calculPointColonne() + this.calculPointLigne();
       score += this.calculPointToutesMine();

        return score;
    }

    /**
     * Methode qui calcule la part du score relatif au joueur et à la région donnée.
     * @param r la région dont on veut calculer le score.
     * @return {int} le score relatif à la région donnée.
     */
    public int calculPointMine (Region r)
    {
        int score = 0;

        for(Mine m : this.ensMine)
        {
            if (m.getRegion().name().equals( r.name() ))
            {
                if (m.getValeur() > score) score = m.getValeur();
            }
        }

        return  score;
    }

    /**
     * Methode qui calcule le score relatif aux pièces.
     * @return {int} le score relatif aux pièces.
     */
    public int calculPointPiece ()
    {
        int[] tabValPiece  = new int[]{0,4,9,16,25,36,49};
        return (this.nbPiece == 0) ? 0 : tabValPiece[this.nbPiece-1];
    }

    /**
     * Methode qui calcule le score relatif aux lignes du tableau joueur.
     * @return {int} le score relatif aux lignes du tableau joueur.
     */
    public int calculPointLigne ()
    {
        int[] tabValResLig = new int[]{0,2,5,9,14,20,32,46};

        Ressource[] lstRes = this.trierTabRessource();
        int cpt = -1;
        for (int j = 0; j < 8; j++)
        {
            if (lstRes[j] == null && cpt >= 0)
            {
                return tabValResLig[cpt];
            }
            cpt++;
        }

        return tabValResLig[cpt];
    }

    /**
     * Methode qui calcule le score relatif aux colonnes du tableau joueur.
     * @return {int} le score relatif aux colonnes du tableau joueur.
     */
    public int calculPointColonne ()
    {
        int[] tabValResCol = new int[]{0,2,10,20};

        Ressource[] lstRes = this.trierTabRessource();
        int cpt , cptAncien = 0;
        for (int j = 0 ; j < 8 ; j++)
        {
            cpt = -1;
            for (int i = 0; i < 4 ; i++)
            {
                if ( lstRes[i*8+j] != null)
                {
                    cpt++;

                }
            }
            if (cpt > cptAncien) cptAncien = cpt;
        }

        return tabValResCol[cptAncien];
    }

    /**
     * Methode qui calcule le bonus en fonction du nombre de jetons possession du joueur et de l'autre joueur
     * @param autreJoueur l'autre joueur à qui on compare le nombre de jetons possession
     * @return {int} le bonus en fonction du nombre de jetons possession du joueur et de l'autre joueur, le bonus est de 0 ou 10 points.
     */
    public int calculBonusPossession(Joueur autreJoueur)
    {
        if(this.jtnPossession > autreJoueur.getJtnPossession()) return 10;
        return 0;
    }

    /**
     * Methode qui calcule le score relatif à toutes les mines du joueur.
     * @return {int} le score relatif à toutes les mines du joueur.
     */
    public int calculPointToutesMine()
    {
        int score = 0;
        for(Region r : Region.values())
        {
            score += this.calculPointMine(r);
        }
        return score;
    }

    public void copier(Joueur autreJoueur) {
        this.setJtnScore(autreJoueur.getJtnScore());
        this.setNbPiece(autreJoueur.getNbPiece());
        this.setJtnPossession(autreJoueur.getJtnPossession());
        this.setEnsMine(autreJoueur.getEnsMine());
        this.setEnsJeton(autreJoueur.getEnsJeton());
    }

    /**
     * Methode qui permet de réinitialiser les attributs du joueur à t = création du joueur.
     */
    public void reset()
    {
        this.ensJeton.clear();
        this.jtnPossession = 25;
        this.jtnScore = 0;
        this.nbPiece = 0;
        this.ensMine.clear();
    }
}