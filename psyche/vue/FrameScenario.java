package psyche.vue;

import psyche.Controleur;

import javax.swing.*;

public class FrameScenario extends JFrame
{
    private Controleur ctrl;

    private PanelScenario panelScenario;

    public FrameScenario(Controleur ctrl)
    {
        this.ctrl = ctrl;

        this.setTitle("Scenario");
        this.setSize(1200,400);
        this.setLocation(800,600);
        this.panelScenario = new PanelScenario(this.ctrl, this);
        this.add(this.panelScenario);

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     * Permet de changer la description de l'étape actuelle du scenario
     * @param description description de l'étape actuelle du scenario
     */
    public void setDescription(String description)
    {
        System.out.println("DESCRIPTION : " + description);
        this.panelScenario.setDescription(description);
    }

}
