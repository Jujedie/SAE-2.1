package routes.vue;

import routes.Controleur;
import routes.metier.Ville;

import javax.swing.*;

public class FrameModifierVille extends JFrame
{

    private Controleur  ctrl;
    private Ville       villeSelected;

    private PanelModifierVille  panelModifierVille;

    public FrameModifierVille(Controleur ctrl, Ville villeSelected)
    {

        this.ctrl = ctrl;
        this.villeSelected = villeSelected;

        this.setSize(800, 400);
        this.setTitle("Modifier les sommets");
        this.setLocation(150,200);

        this.panelModifierVille = new PanelModifierVille(this.ctrl, this.villeSelected);

        this.add(this.panelModifierVille);

        this.setVisible(true);

    }

}
