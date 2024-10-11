package routes.vue;

import routes.Controleur;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;

public class FramePlateau extends JFrame implements ActionListener
{
    private JMenuBar  menuBarFichier;
    private JMenuItem menuItemNouveau;
    private JMenuItem menuItemOuvrir;
    private JMenuItem menuItemEnregistrer;
    private JMenuItem menuItemEnregisterSous;
    private JMenuItem menuItemQuitter;

    private JMenuItem menuOutilsCreerVille;
    private JMenuItem menuOutilsCreerRoute;
    private JMenuItem menuOutilsSupprimer;

    private Controleur  ctrl;

    private PanelPlateau    panelPlateau;

    public FramePlateau(Controleur ctrl)
    {

        this.ctrl = ctrl;

        this.setSize(1250, 900);
        this.setLocation(150, 50);
        this.setTitle("Constructeur de map");

        // Création des composants

        this.panelPlateau = new PanelPlateau(this.ctrl);

        this.menuBarFichier = new JMenuBar();

        JMenu   menuFichier = new JMenu("Fichier");
        JMenu   menuOutils  = new JMenu("Outils");
        menuFichier.setMnemonic('F');
        menuOutils.setMnemonic('O');

        this.menuItemNouveau            = new JMenuItem ("Nouveau" );
		this.menuItemNouveau.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		this.menuItemOuvrir             = new JMenuItem ("Ouvrir" );
		this.menuItemOuvrir.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        this.menuItemEnregistrer        = new JMenuItem ("Enregistrer" );
		this.menuItemEnregistrer.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        this.menuItemEnregisterSous     = new JMenuItem ("Enregistrer Sous" );
		this.menuItemEnregisterSous.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK));
        this.menuItemQuitter            = new JMenuItem ("Quitter");
		this.menuItemQuitter.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK));

        this.menuOutilsCreerVille       = new JMenuItem("Créer un sommet");
        this.menuOutilsCreerVille.setMnemonic('V');
        this.menuOutilsCreerRoute       = new JMenuItem("Créer une route");
        this.menuOutilsCreerRoute.setMnemonic('R');
        this.menuOutilsSupprimer        = new JMenuItem("Modifier les sommets");
        this.menuOutilsSupprimer.setMnemonic('S');


        // Positionnement des composants

        menuFichier.add(this.menuItemOuvrir         );
        menuFichier.add(this.menuItemNouveau        );
        menuFichier.add(this.menuItemEnregistrer    );
        menuFichier.add(this.menuItemEnregisterSous );
        menuFichier.addSeparator();
        menuFichier.add(this.menuItemQuitter        );

        menuOutils.add(this.menuOutilsCreerVille    );
        menuOutils.add(this.menuOutilsCreerRoute    );
        menuOutils.add(this.menuOutilsSupprimer     );

        this.menuBarFichier.add( menuFichier   );
        this.menuBarFichier.add( menuOutils    );

        this.add(this.panelPlateau);

        this.setJMenuBar(this.menuBarFichier);

        /*-------------------------------*/
		/* Activation des composants     */
		/*-------------------------------*/
		this.menuItemOuvrir.addActionListener         ( this );
		this.menuItemNouveau.addActionListener        ( this );
		this.menuItemEnregistrer.addActionListener    ( this );
		this.menuItemEnregisterSous.addActionListener ( this );
		this.menuItemQuitter.addActionListener        ( this );

        this.menuOutilsCreerVille.addActionListener(this);
        this.menuOutilsCreerRoute.addActionListener(this);
        this.menuOutilsSupprimer.addActionListener(this);

        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void actionPerformed ( ActionEvent e )
	{

        if (e.getSource() == this.menuItemEnregistrer)
        {
            this.enregistrerFichier();
        }

        if (e.getSource() == this.menuItemEnregisterSous)
        {
            this.enregistrerSous();
        }

        if (e.getSource() == this.menuItemOuvrir)
        {
            this.ouvrirFichier();
        }

        if (e.getSource() == this.menuItemQuitter)
        {
            System.exit(0);
        }

        if (e.getSource() == this.menuOutilsCreerVille)
        {
            this.ctrl.ouvrirFrameVille();
        }

        if (e.getSource() == this.menuOutilsCreerRoute)
        {

            this.ctrl.ouvrirFrameRoute();

        }

        if (e.getSource() == this.menuOutilsSupprimer)
        {
            this.ctrl.ouvrirAllVille();
        }

        if (e.getSource() == this.menuItemNouveau)
        {
            this.ctrl.nouveauFichier();
        }

	}

    private void enregistrerFichier()
    {

        if (!this.ctrl.getLienFichier().equals("../routes/ensemble.data"))
        {
            this.ctrl.sauvegarderDansFichier();
        } else {
            this.enregistrerSous();
        }

    }

    private void enregistrerSous()
    {

        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showSaveDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            this.ctrl.setLienFichier(selectedFile.getAbsolutePath());
            this.ctrl.sauvegarderDansFichier();
        }

    }

    private void ouvrirFichier()
    {

        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            this.ctrl.setLienFichier(selectedFile.getAbsolutePath());
            this.ctrl.ouvrirFichier();
            this.panelPlateau.updateIHM();
        }

    }

    public void majIHM()
    {
        this.panelPlateau.updateIHM();
    }
}
