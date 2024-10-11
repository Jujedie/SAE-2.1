package psyche.vue.indiv;

import java.text.Collator;
import java.util.*;

import psyche.Controleur;
import psyche.metier.Dictionnaire;
import psyche.metier.jetons.Jeton;
import psyche.metier.jetons.Mine;
import psyche.metier.jetons.Ressource;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelJoueur extends JPanel
{
    private final Controleur ctrl        ;

    private int              nbPiece     ;
    private Ressource[]      ensRessource;

    private int              numJoueur   ;

    public PanelJoueur(Controleur ctrl, int numJoueur)
    {
        this.ctrl = ctrl;
        this.numJoueur = numJoueur;

        this.ensRessource = this.ctrl.getJoueur(this.numJoueur).trierTabRessource();
        this.nbPiece      = this.ctrl.getJoueur(this.numJoueur).getNbPiece();

        this.repaint();
    }

    /**
     * Met à jour l'IHM du joueur
     */
    public void updateIhm()
    {

        //System.out.println("MAJ IHM JOUEUR");
        this.ensRessource = this.ctrl.getJoueur(this.numJoueur).trierTabRessource();
        this.nbPiece      = this.ctrl.getJoueur(this.numJoueur).getNbPiece();

        this.repaint();
    }

    /**
     * Permet de peindre l'ensemble des ressources , pièces et mines du joueur
     * @param g the <code>Graphics</code> object to protect
     */
    public void paintComponent(Graphics g)
    {

        super.paintComponent(g);

        Ressource dernierRessource = null;

        Graphics2D g2 = (Graphics2D) g;

        Font font = new Font("Arial", Font.BOLD, 20);
        g2.setFont(font);

        g2.drawImage ( getToolkit().getImage ( "../psyche/theme/images/score.png" ), 0 , 0 ,this );

        if (this.numJoueur == 1)
        {
            g2.setColor(new Color(31, 89, 37));
            g2.drawString(Dictionnaire.getNomJoueur1(), 90, 30);
        }
        else
        {
            g2.setColor(new Color(147, 0, 10));
            g2.drawString(Dictionnaire.getNomJoueur2(), 90, 30);
        }

        g2.drawImage(getToolkit().getImage("../psyche/theme/images/Jeton" + this.numJoueur + ".png"), 50, 10, 30, 30, this);

        g2.drawImage(getToolkit().getImage("../psyche/theme/images/Jeton" + this.numJoueur +".png"), 10, 370, 30, 30, this);
        g2.drawString("x" + String.format("%2s", this.ctrl.getJoueur(this.numJoueur).getJtnPossession()), 50, 400);

        g2.setColor(Color.BLACK);

        if (this.ctrl.getJoueur().getNumero() == this.numJoueur)
        {
            g2.drawString("C'EST A TOI DE JOUER !", 120, 395);
        }


        int posXMine = 520;
        int posYMine =  10;

        Font fontMine = new Font("Arial", Font.BOLD, 12);
        g2.setFont(fontMine);

        for (Mine m : this.ctrl.getJoueur(this.numJoueur).getEnsMine())
        {

            g2.drawImage(getToolkit().getImage("../psyche/theme/images/opaque/" + m.getRegion().getLienFichier()), posXMine, posYMine, this);
            g2.drawString(String.format("%2s", m.getValeur()), posXMine + 15, posYMine + 28);

            posXMine += 60;

            if (posXMine > 830)
            {
                posXMine = 520;
                posYMine += 120;
            }

        }


        int posXJeton =  58;
        int posYJeton = 199;

        Ressource derniereRes = null;

        for (int i = 0; i < 8; i++)
        {

            for (int j = 0; j < 4; j++)
            {
                if ( this.ensRessource[i+j*8] != null)
                {
                    Ressource r = this.ensRessource[i+j*8];
                    if (derniereRes != null) {
                        if (r.getMinerai().name().equals(derniereRes.getMinerai().name()))
                        {
                            posYJeton -= 49;
                        }
                        else {
                            posYJeton  = 198;
                            posXJeton += 48;
                        }
                    }
                    g2.drawImage(getToolkit().getImage("../psyche/theme/images/res/" + r.getMinerai().getImage() + ".png"), posXJeton, posYJeton, this);

                    derniereRes = r;
                }
            }

        }


        // Afficher les ressources du Joueur


        int posXPiece =  57;
        int posYPiece = 297;

        for ( int i = 0; i < this.ctrl.getJoueur(this.numJoueur).getNbPiece() && i < 7; i++)
        {
            g2.drawImage(getToolkit().getImage("../psyche/theme/images/res/NR.png"), posXPiece, posYPiece, this);

            posXPiece += 49;
        }

    }
}
