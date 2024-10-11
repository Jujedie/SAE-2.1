package routes.vue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import routes.Controleur;
import routes.metier.Route;
import routes.metier.Ville;

public class PanelRoute extends JPanel implements ActionListener
{

	private 	Controleur			ctrl;

	private		JComboBox<String>	ddlstVilleDep;
	private		JComboBox<String>	ddlstVilleArr;
	private		JTextField			txtTroncons;

	private     JButton     		btnAjouter;

	public PanelRoute( Controleur ctrl )
	{

		this.ctrl = ctrl;

		this.setLayout(new BorderLayout(50, 50));
		this.setBorder(new EmptyBorder(50,20,50,20));
		JPanel  panelFormulaire;

		/**************************************/
		/*      CREATIONS DES COMPOSANTS      */
		/**************************************/

		panelFormulaire = new JPanel(new GridLayout(4, 2, 80, 20));

		this.ddlstVilleDep	= new JComboBox<>();
		this.ddlstVilleArr 	= new JComboBox<>();

		for (Ville v : this.ctrl.getEnsVille())
		{

			this.ddlstVilleDep.addItem(v.getNom());
			this.ddlstVilleArr.addItem(v.getNom());

		}

		this.txtTroncons   = new JTextField(80);

		this.btnAjouter  = new JButton("Ajouter");


		/**************************************/
		/*      PLACEMENT DES COMPOSANTS      */
		/**************************************/

		panelFormulaire.add(new JLabel("Ville de départ", JLabel.RIGHT));
		panelFormulaire.add(this.ddlstVilleDep);

		panelFormulaire.add(new JLabel("Ville d'arrivée", JLabel.RIGHT));
		panelFormulaire.add(this.ddlstVilleArr);

		panelFormulaire.add(new JLabel("Nombre de tronçons", JLabel.RIGHT));
		panelFormulaire.add(this.txtTroncons);

		panelFormulaire.add(new JLabel(""));
		panelFormulaire.add(this.btnAjouter);

		this.add(panelFormulaire, BorderLayout.CENTER);


		/**************************************/
		/*     ACTIVATION DES COMPOSANTS      */
		/**************************************/
		this.btnAjouter.addActionListener(this);

	}

	public void actionPerformed(ActionEvent e)
	{

		if (e.getSource() == this.btnAjouter)
		{

			String txtVilleDep = this.ddlstVilleDep.getSelectedItem().toString();
			String txtVilleArr = this.ddlstVilleArr.getSelectedItem().toString();
			int    numTrc = -1;

			if (txtVilleDep.equals(txtVilleArr))
			{
				JOptionPane.showMessageDialog(null, "Les deux ville sélectionnées ne peuvent pas être similaires", "Impossible d'ajouter la route", JOptionPane.ERROR_MESSAGE);
				return;
			}

			try {
				numTrc      = Integer.parseInt(this.txtTroncons.getText());

				Ville villeDep  = this.ctrl.trouverVilleNom(txtVilleDep);
				Ville villeArr  = this.ctrl.trouverVilleNom(txtVilleArr);

				if (numTrc < 0 || numTrc > 2) {
					JOptionPane.showMessageDialog(null, "Le nombre de tronçons doit être compris entre 0 et 2", "Impossible d'ajouter la route", JOptionPane.ERROR_MESSAGE);
					return;
				}

				this.ctrl.ajouterRoute(Route.creerRoute(numTrc, villeDep, villeArr));

				this.txtTroncons.setText("");

				this.ctrl.majIHM();


			} catch (NumberFormatException ee) {
				JOptionPane.showMessageDialog(null, "Le champs Nombre de tronçons doit être de type Chiffre", "Impossible d'ajouter la route", JOptionPane.ERROR_MESSAGE);
			}


		}

	}

	public void majDdlst()
	{

		this.ddlstVilleDep.removeAllItems();
		this.ddlstVilleArr.removeAllItems();

		for (Ville v : this.ctrl.getEnsVille())
		{

			this.ddlstVilleDep.addItem(v.getNom());
			this.ddlstVilleArr.addItem(v.getNom());

		}

	}



}
