package psyche.vue;

import psyche.Controleur;

import javax.swing.*;

public class FrameScore extends JFrame
{
    private final Controleur ctrl;
    private final PanelScore panelScore;

    public FrameScore(Controleur ctrl)
    {
        this.ctrl = ctrl;
        this.setTitle("Fiche de Score");
        this.setSize(483,700);
        this.setLocation(500,100);

        this.panelScore = new PanelScore(ctrl);
        this.add ( this.panelScore);

        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void majIHM()
    {
        this.panelScore.majIHM();
    }

}
