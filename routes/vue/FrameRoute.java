package routes.vue;

import routes.Controleur;

import javax.swing.*;

public class FrameRoute extends JFrame
{
	private PanelRoute panelRoute;

	private Controleur  ctrl;

	public FrameRoute(Controleur ctrl)
	{
		this.ctrl = ctrl;

		this.setSize(800, 400);
		this.setTitle("Ajouter une route");
		this.setLocation(150, 150);

		this.setVisible(true);

		// Creation des composants

		this.panelRoute = new PanelRoute( this.ctrl );

		// Positionnement des composants

		this.add( this.panelRoute );

		this.setVisible ( true );
	}

	public void majDdlst()
	{
		this.panelRoute.majDdlst();
	}


}
