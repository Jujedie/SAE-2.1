package routes.vue;

import routes.Controleur;
import routes.metier.Region;
import routes.metier.Route;
import routes.metier.Ville;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.event.*;

public class PanelPlateau extends JPanel implements ActionListener
{

    private Controleur ctrl;

    private JButton btnSupprimer;
    private JButton btnModifier;

    private JLabel      lblIdVille;
    private JLabel      lblNomVille;
    private JLabel      lblPosX;
    private JLabel      lblPosY;
    private JLabel      lblNumVille;
    private JLabel      lblRegionVille;
    private JLabel      lblRoutes;

    private Graphics2D g2;

    private GererSouris gererSouris;

    public PanelPlateau(Controleur ctrl) {

        this.ctrl = ctrl;

        JPanel panelInfos;

        this.setBackground(Color.WHITE);
        this.setLayout(new BorderLayout());

        /**************************************/
        /* CREATIONS DES COMPOSANTS */
        /**************************************/
        panelInfos = new JPanel(new GridLayout(10, 1, 40, 10));
        panelInfos.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelInfos.setBackground(new Color(241, 241, 241));

        this.btnModifier = new JButton("Modifier");
        this.btnModifier.setEnabled(false);
        this.btnModifier.setBackground(new Color(217, 217, 217));
        this.btnModifier.setBorder(null);
        this.btnSupprimer = new JButton("Supprimer");
        this.btnSupprimer.setEnabled(false);
        this.btnSupprimer.setBackground(new Color(217, 217, 217));
        this.btnSupprimer.setBorder(null);

        this.lblIdVille = new JLabel("<html>ID du sommet :<br><br><p style=\"font-weight: 400\">0</p></html>");
        this.lblNomVille = new JLabel("<html>Nom du sommet :<br><br><p style=\"font-weight: 400\">-</p></html>");
        this.lblPosX = new JLabel("<html>Position X :<br><br><p style=\"font-weight: 400\">0</p></html>");
        this.lblPosY = new JLabel("<html>Position Y :<br><br><p style=\"font-weight: 400\">0</p></html>");
        this.lblRoutes = new JLabel("<html>Routes :<br><br><ul style=\"font-weight: 400;margin: 0;\">-</ul></html>");
        this.lblNumVille = new JLabel("<html>Nombre du sommet :<br><br><p style=\"font-weight: 400\">0</p></html>");
        this.lblRegionVille = new JLabel("<html>Région du sommet :<br><br><p style=\"font-weight: 400\">-</p></html>");

        /**************************************/
        /* PLACEMENT DES COMPOSANTS */
        /**************************************/
        panelInfos.add(this.lblIdVille);
        panelInfos.add(this.lblNomVille);
        panelInfos.add(this.lblPosX);
        panelInfos.add(this.lblPosY);
        panelInfos.add(this.lblRoutes);
        panelInfos.add(this.lblNumVille);
        panelInfos.add(this.lblRegionVille);

        panelInfos.add(new JLabel("<html><br></html>"));

        panelInfos.add(this.btnModifier);
        panelInfos.add(this.btnSupprimer);

        this.add(panelInfos, BorderLayout.EAST);

        /**************************************/
        /* ACTIVATION DES COMPOSANTS */
        /**************************************/

        this.gererSouris = new GererSouris();

        this.btnSupprimer.addActionListener(this);
        this.btnModifier.addActionListener(this);

        this.addMouseListener(this.gererSouris);
        this.addMouseMotionListener(this.gererSouris);

    }

    public void actionPerformed(ActionEvent e)
    {

        if (e.getSource() == this.btnSupprimer)
        {

            int response = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer ce sommet ? Tous les liens seront supprimés", "Supprimer le sommet ?",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {
                if (this.ctrl.supprimerVille(this.gererSouris.getVilleSelected())) this.getInformation();
            }

        }

        if (e.getSource() == this.btnModifier)
        {

            this.ctrl.ouvrirFrameModifierVille(this.gererSouris.getVilleSelected());

        }

    }

	public void paintComponent(Graphics g)
	{
		Ville ville;

		super.paintComponent(g);

		this.g2 = (Graphics2D) g;

        //g2.drawImage(getToolkit().getImage("../psyche/theme/images/fond2.png"), 0, 0, this);

		for (Route route : this.ctrl.getEnsRoute())
		{
			// Calculer les coordonnées centrales des cercles
			int x1 = route.getVilleDep().getCoordX() + 15;
			int y1 = route.getVilleDep().getCoordY() + 15;
			int x2 = route.getVilleArr().getCoordX() + 15;
			int y2 = route.getVilleArr().getCoordY() + 15;

			// la longueur de la ligne
			double distance = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));

			// Nombre de troncons
			int nbTroncons = route.getTroncons();      // 1 troncons = trait plein

			// Longueur totale des segments (pleins et vides) pour les pointillés
			double tailleSegment = distance / (nbTroncons * 2 - 1);

			// Créer le motif de pointillés
			float[] pattern = {(float) tailleSegment, (float) tailleSegment};

			// Appliquer le motif de pointillés
			g2.setColor(Color.BLACK);
			g2.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, pattern, 0.0f));

			g2.draw(new Line2D.Double( x1, y1, x2, y2 ));


			g2.setStroke(new BasicStroke(1.0f)); // Réinitialiser le style du trait
		}

		for (int cpt = ctrl.getNbVille() - 1; cpt >= 0; cpt--)
		{

			ville = this.ctrl.getVille(cpt);

            if (this.gererSouris.getVilleSelected() != null && this.gererSouris.getVilleSelected().getNumVille() == ville.getNumVille())
            {
                g2.setColor(Color.RED);
            } else  {
                if (ville.getRegion() != null) g2.setColor(ville.getRegion().getCouleur());
                //g2.setColor(Color.BLUE);
            }

			g2.fillOval(ville.getCoordX(), ville.getCoordY(), 30, 30);
			g2.drawString(ville.getNom(), ville.getCoordX(), ville.getCoordY());
		}
    }

    public void getInformation()
    {

        Ville villeSelected = this.gererSouris.getVilleSelected();

        if (villeSelected == null) {
            this.modifierIdVille(0);
            this.modifierNomVille("-");
            this.modifierPosX(0);
            this.modifierPosY(0);
            this.changerEtatBoutonSupprimer(false);
            this.changerEtatBoutonValider(false);
            this.modifierRoutes(null);
            this.modifierNumVille(0);
            this.modifierRegion(null);
            return;
        }

        this.modifierIdVille(villeSelected.getNumVille());
        this.modifierNomVille(villeSelected.getNom());
        this.modifierPosX(villeSelected.getCoordX());
        this.modifierPosY(villeSelected.getCoordY());
        this.changerEtatBoutonSupprimer(true);
        this.changerEtatBoutonValider(true);
        this.modifierRoutes(villeSelected);
        this.modifierNumVille(villeSelected.getNumUtilisateur());
        this.modifierRegion(villeSelected.getRegion());

        this.ctrl.majTableau();
        this.ctrl.majDdlst();

        this.repaint();

    }


    private class GererSouris extends MouseAdapter
    {

        Integer indiceVille;
        int posX, posY;

        public void mousePressed(MouseEvent e)
        {

            this.indiceVille = PanelPlateau.this.ctrl.getIndiceVille(e.getX(), e.getY());

            this.posX = e.getX();
            this.posY = e.getY();

            if (this.indiceVille == null) PanelPlateau.this.repaint();

            PanelPlateau.this.getInformation();

        }

        public void mouseDragged (MouseEvent e)
        {

            if (this.indiceVille != null)
            {

                PanelPlateau.this.ctrl.deplacerVille(this.indiceVille, e.getX()-this.posX, e.getY()-this.posY);

                this.posX = e.getX();
                this.posY = e.getY();

                PanelPlateau.this.getInformation();

            }

        }

        public Ville getVilleSelected()
        {

            if (this.indiceVille == null) return null;

            return PanelPlateau.this.ctrl.getVille(this.indiceVille);

        }

    }

    public void modifierNomVille(String nom) {

        this.lblNomVille.setText("<html>Nom du sommet :<br><br><p style=\"font-weight: 400\">" + nom + "</p></html>");

    }

    public void modifierIdVille(int num) {

        this.lblIdVille.setText("<html>ID du sommet :<br><br><p style=\"font-weight: 400\">"
                + String.valueOf(num) + "</p></html>");

    }

    public void modifierPosX(int posX) {

        this.lblPosX.setText(
                "<html>Position X :<br><br><p style=\"font-weight: 400\">" + posX + "</p></html>");

    }

    public void modifierPosY(int posY) {

        this.lblPosY.setText(
                "<html>Position Y :<br><br><p style=\"font-weight: 400\">" + posY + "</p></html>");

    }

    public void modifierNumVille(int num)
    {

        this.lblNumVille.setText("<html>Nombre du sommet :<br><br><p style=\"font-weight: 400\">" + num + "</p></html>");

    }

    public void modifierRegion(Region r)
    {

        if (r == null)
        {
            this.lblRegionVille.setText("<html>Région du sommet :<br><br><p style=\"font-weight: 400\">-</p></html>");
        } else {
            this.lblRegionVille.setText("<html>Région du sommet :<br><br><p style=\"font-weight: 400\">" + r.name() + "</p></html>");
        }
    }

    public void modifierRoutes(Ville ville)
    {

        if (ville == null) {
            this.lblRoutes.setText("<html>Routes : <ul style=\"font-weight: 400;margin: 0;\">-</ul></html>");
            return;
        }

        String txtArr = "";

        for (Route r : ville.getRoutes())
        {

            if (r.getVilleArr().getNom().equals(ville.getNom()))
            {
                txtArr += "<li>De <b>" + r.getVilleDep().getNom() + "</b></li>";
            }
            else {
                txtArr += "<li>Vers <b>" + r.getVilleArr().getNom() + "</b></li>";
            }

        }

        this.lblRoutes.setText("<html>Routes : <ul style=\"font-weight: 400;margin: 0;\">" + txtArr +"</ul></html>");

    }

    public void changerEtatBoutonValider(boolean actif) {

        this.btnModifier.setEnabled(actif);

    }

    public void changerEtatBoutonSupprimer(boolean actif) {

        this.btnSupprimer.setEnabled(actif);

    }

    public void updateIHM() {
        this.repaint();
        this.getInformation();
    }

}
