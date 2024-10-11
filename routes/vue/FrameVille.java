package routes.vue;

import routes.Controleur;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentListener;

public class FrameVille extends JFrame
{

    private Controleur  ctrl;
    private PanelVille  panelVille;

    public FrameVille(Controleur ctrl)
    {

        this.ctrl = ctrl;

        this.setSize(800, 400);
        this.setTitle("Ajouter un sommet");
        this.setLocation(150,200);

        this.panelVille = new PanelVille(this.ctrl);

        this.add(this.panelVille);

        this.setVisible(true);

    }
}
