package routes.vue;

import routes.Controleur;
import routes.metier.Region;
import routes.metier.Ville;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelModifierVille extends JPanel implements ActionListener
{

    private Controleur        ctrl;

    private JTextField        txtNomVille;
    private JTextField        txtCoordX;
    private JTextField        txtCoordY;
    private JTextField        txtNumVille;

    private JButton           btnModifier;

    private JComboBox<Region> ddlstRegion;

    private Ville             villeSelected;

    public PanelModifierVille(Controleur ctrl, Ville villeSelected)
    {

        this.villeSelected = villeSelected;

        this.ctrl = ctrl;

        this.setLayout(new BorderLayout(50, 50));
        this.setBorder(new EmptyBorder(50,20,50,20));
        JPanel  panelFormulaire;

        /**************************************/
        /*      CREATIONS DES COMPOSANTS      */
        /**************************************/

        panelFormulaire = new JPanel(new GridLayout(6, 2, 80, 20));

        this.txtNomVille = new JTextField(80);
        this.txtCoordX   = new JTextField(80);
        this.txtCoordY   = new JTextField(80);
        this.txtNumVille = new JTextField(80);

        this.ddlstRegion = new JComboBox<>();

        for (Region r : Region.values())
        {

            this.ddlstRegion.addItem(r);

        }

        this.btnModifier  = new JButton("Modifier");


        /**************************************/
        /*      PLACEMENT DES COMPOSANTS      */
        /**************************************/

        panelFormulaire.add(new JLabel("Nom du sommet", JLabel.RIGHT));
        panelFormulaire.add(this.txtNomVille);

        panelFormulaire.add(new JLabel("Coordonnée X", JLabel.RIGHT));
        panelFormulaire.add(this.txtCoordX);

        panelFormulaire.add(new JLabel("Coordonnée Y", JLabel.RIGHT));
        panelFormulaire.add(this.txtCoordY);

        panelFormulaire.add(new JLabel("Nombre du sommet", JLabel.RIGHT));
        panelFormulaire.add(this.txtNumVille);

        panelFormulaire.add(new JLabel("Région de la ville", JLabel.RIGHT));
        panelFormulaire.add(this.ddlstRegion);

        panelFormulaire.add(new JLabel(""));
        panelFormulaire.add(this.btnModifier);

        this.add(panelFormulaire, BorderLayout.CENTER);


        /**************************************/
        /*     ACTIVATION DES COMPOSANTS      */
        /**************************************/

        this.btnModifier.addActionListener(this);


        this.chargerVilleFormulaire();


    }

    public void actionPerformed(ActionEvent e)
    {

        if (e.getSource() == this.btnModifier)
        {

            String txtNom = this.txtNomVille.getText();
            int txtX      = -1;
            int txtY      = -1;
            int txtN      = -1;
            String tyRegion;
            Region region;

            try {

                txtX      = Integer.parseInt(this.txtCoordX.getText());
                txtY      = Integer.parseInt(this.txtCoordY.getText());
                txtN      = Integer.parseInt(this.txtNumVille.getText());
                tyRegion  = this.ddlstRegion.getSelectedItem().toString();


                if (txtNom == null || txtX < 0 || txtY < 0){
                    System.out.println(txtNom + " " + txtX + " " + txtY);
                    JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs", "Impossible de modifier le sommet", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (txtX < 0 || txtX > 1000)
                {
                    JOptionPane.showMessageDialog(null, "La valeur X doit être comprise entre 0 et 1000", "Impossible de modifier le sommet", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (txtY < 0 || txtY > 800)
                {
                    JOptionPane.showMessageDialog(null, "La valeur Y doit être comprise entre 0 et 800", "Impossible de modifier le sommet", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (txtN < 0 || txtN > 8)
                {
                    JOptionPane.showMessageDialog(null, "Le numéro du sommet doit être compris entre 1 et 8", "Impossible de modifier le sommet", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (this.ctrl.trouverVilleNom(txtNom) != null && !this.ctrl.trouverVilleNom(txtNom).equals(this.villeSelected)){
                    JOptionPane.showMessageDialog(null, "Un sommet avec ce nom existe déjà", "Impossible de modifier le sommet", JOptionPane.ERROR_MESSAGE);
                    System.out.println("Erreur");
                    return;
                }

                region = switch (tyRegion) {
                    case "REGION VERT"      -> Region.VERT;
                    case "REGION BLEU"      -> Region.BLEU;
                    case "REGION GRIS"      -> Region.GRIS;
                    case "REGION ROUGE"     -> Region.ROUGE;
                    case "REGION JAUNE"     -> Region.JAUNE;
                    case "REGION MARRON"    -> Region.MARRON;
                    case "ROME"             -> Region.ROME;
                    default -> null;
                };

                this.ctrl.modifierVille(this.villeSelected, txtNom, txtX, txtY, region, txtN);

                JOptionPane.showMessageDialog(null, "Les informations du sommet ont bien été mises à jour", "Sommet mis à jour", JOptionPane.INFORMATION_MESSAGE);

                this.ctrl.majIHM();

            } catch (NumberFormatException ee) {
                JOptionPane.showMessageDialog(null, "Les champs cordonnées X et coordonées Y doivent être de type Chiffre", "Impossible de modifier la ville", JOptionPane.ERROR_MESSAGE);
            }


        }

    }

    /**
     * Permet de charger les informations de la ville dans le formulaire
     */
    private void chargerVilleFormulaire()
    {

        if (this.villeSelected != null)
        {

            this.txtCoordX.setText(this.villeSelected.getCoordX() + "");
            this.txtCoordY.setText(this.villeSelected.getCoordY() + "");
            this.txtNomVille.setText(this.villeSelected.getNom());
            this.txtNumVille.setText(this.villeSelected.getNumUtilisateur() + "");
            this.ddlstRegion.setSelectedItem(this.villeSelected.getRegion());

        }

    }

}
