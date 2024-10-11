package psyche.vue.indiv;

import psyche.Controleur;
import psyche.metier.Dictionnaire;

import javax.swing.*;

public class FrameJoueur extends JFrame
{
    private Controleur ctrl;
    private PanelJoueur panelJoueur;

    public FrameJoueur(Controleur ctrl, int numJoueur)
    {

        this.ctrl = ctrl;
        this.setSize(875, 450);

        if (numJoueur == 1)
        {
            this.setTitle("Joueur 1 - " + Dictionnaire.getNomJoueur1());
            this.setLocation(1550,50);
        }
        else
        {
            this.setTitle("Joueur 2 - " + Dictionnaire.getNomJoueur2());
            this.setLocation(1025,550);
        }

        this.panelJoueur = new PanelJoueur(this.ctrl, numJoueur);

        //Ajouts des composants
        this.add(this.panelJoueur);

        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setVisible(true);
    }

    public void majIhm()
    {
        this.panelJoueur.updateIhm();
    }

}
