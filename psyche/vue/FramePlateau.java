package psyche.vue;

import javax.swing.*;

import psyche.Controleur;
import psyche.metier.jetons.Mine;

public class FramePlateau extends JFrame
{
    private Controleur ctrl;

    private PanelPlateau    panelPlateau;

    /**
     * constructeur de la fenetre du plateau principal
     * @param ctrl le controleur
     */
    public FramePlateau(Controleur ctrl)
    {
        this.ctrl = ctrl;
        this.setTitle("Plateau principal");
        this.setSize(1000,919);
        this.setLocation(20,50);

        this.panelPlateau = new PanelPlateau(this.ctrl);

        this.add ( panelPlateau );

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    public void afficherMsgErreur(String message)
    {
        this.panelPlateau.afficherMessageErreur(message);
    }

    /**
     * Méthode permettant de lier les deux mines
     * @param mineDep Mine de départ
     * @param mineArr Mine d'arrivée
     */
    public void lierMine(Mine mineDep, Mine mineArr)
    {
        this.panelPlateau.lierMine(mineDep,mineArr);
    }


    public void clicArtificiel(int x, int y)
    {
        this.panelPlateau.clicArtificiel(x, y);
    }

    public void resetMineDep()
    {
        this.panelPlateau.resetMineDep();
    }

}
