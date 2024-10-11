package routes.vue;

import routes.Controleur;

import javax.swing.*;

public class FrameAllVilles extends JFrame
{

    private Controleur      ctrl;
    private PanelAllVilles  panelAllVilles;

    public FrameAllVilles(Controleur ctrl)
    {

        this.ctrl = ctrl;

        this.setSize(800, 550);
        this.setTitle("Modifier les villes");
        this.setLocation(150,200);

        this.panelAllVilles = new PanelAllVilles(this.ctrl);

        this.add(this.panelAllVilles);

        this.setVisible(true);

    }

    public void majTableau()
    {

        this.panelAllVilles.majTableau();

    }

}
