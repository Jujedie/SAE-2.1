package routes.vue;

import routes.Controleur;
import routes.metier.Region;
import routes.metier.Ville;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelVille extends JPanel implements ActionListener
{

    private Controleur        ctrl;

    private JTextField        txtNomVille;
    private JTextField        txtCoordX;
    private JTextField        txtCoordY;
    private JTextField        txtNumUtilisateur;

    private JButton           btnAjouter;

    private JComboBox<Region> ddlstRegion;

    public PanelVille(Controleur ctrl)
    {

        this.ctrl = ctrl;

        this.setLayout(new BorderLayout(50, 50));
        this.setBorder(new EmptyBorder(50,20,50,20));
        JPanel  panelFormulaire;

        /**************************************/
        /*      CREATIONS DES COMPOSANTS      */
        /**************************************/

        panelFormulaire = new JPanel(new GridLayout(6, 2, 80, 20));

        this.txtNomVille        = new JTextField(80);
        this.txtCoordX          = new JTextField(80);
        this.txtCoordY          = new JTextField(80);
        this.txtNumUtilisateur  = new JTextField(80);

        this.ddlstRegion = new JComboBox<>();
        for (Region r : Region.values())
        {
            this.ddlstRegion.addItem(r);
        }

        this.btnAjouter  = new JButton("Ajouter");


        /**************************************/
        /*      PLACEMENT DES COMPOSANTS      */
        /**************************************/

        panelFormulaire.add(new JLabel("Nom du sommet", JLabel.RIGHT));
        panelFormulaire.add(this.txtNomVille);

        panelFormulaire.add(new JLabel("Nombre du sommet", JLabel.RIGHT));
        panelFormulaire.add(this.txtNumUtilisateur);

        panelFormulaire.add(new JLabel("Région du sommet", JLabel.RIGHT));
        panelFormulaire.add(this.ddlstRegion);

        panelFormulaire.add(new JLabel("Coordonnée X", JLabel.RIGHT));
        panelFormulaire.add(this.txtCoordX);

        panelFormulaire.add(new JLabel("Coordonnée Y", JLabel.RIGHT));
        panelFormulaire.add(this.txtCoordY);

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

            String txtNom       = this.txtNomVille.getText();
            int txtX            = -1;
            int txtY            = -1;
            int txtN            = -1;
            String txtRegion    = this.ddlstRegion.getSelectedItem().toString();
            Region region       = switch (txtRegion) {
                case "REGION VERT"      -> Region.VERT;
                case "REGION BLEU"      -> Region.BLEU;
                case "REGION GRIS"      -> Region.GRIS;
                case "REGION ROUGE"     -> Region.ROUGE;
                case "REGION JAUNE"     -> Region.JAUNE;
                case "REGION MARRON"    -> Region.MARRON;
                case "ROME"             -> Region.ROME;
                default -> null;
            };

            try {

                txtX      = Integer.parseInt(this.txtCoordX.getText());
                txtY      = Integer.parseInt(this.txtCoordY.getText());
                txtN      = Integer.parseInt(this.txtNumUtilisateur.getText());


                if (txtNom == null || txtX < -1 || txtY < -1){
                    System.out.println(txtNom + " " + txtX + " " + txtY);
                    JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs", "Impossible d'ajouter le sommet", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (txtX < 0 || txtX > 1000)
                {
                    JOptionPane.showMessageDialog(null, "La valeur X doit être comprise entre 0 et 1000", "Impossible d'ajouter le sommet", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (txtY < 0 || txtY > 800)
                {
                    JOptionPane.showMessageDialog(null, "La valeur Y doit être comprise entre 0 et 800", "Impossible d'ajouter le sommet", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (txtN < 0 || txtN > 8)
                {
                    JOptionPane.showMessageDialog(null, "La valeur nombre du sommet doit être compris entre 1 et 8", "Impossible d'ajouter le sommet", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                //System.out.println(this.ctrl.trouverVilleNom(txtNom));

                if (this.ctrl.trouverVilleNom(txtNom) != null){
                    JOptionPane.showMessageDialog(null, "Un sommet avec ce nom existe déjà", "Impossible d'ajouter le sommet", JOptionPane.ERROR_MESSAGE);
                    System.out.println("Erreur");
                    return;
                }

                System.out.println(txtRegion);
                
                System.out.println(txtNom + " " + txtX + " " + txtY + " " + region +  " " + txtN);

                this.ctrl.ajouterVille(Ville.creerVille(txtNom, txtX, txtY, region, txtN));

                this.txtNomVille.setText("");
                this.txtCoordX.setText("");
                this.txtNumUtilisateur.setText("");
                this.txtCoordY.setText("");

                this.ddlstRegion.setSelectedIndex(0);

                this.ctrl.majIHM();

            } catch (NumberFormatException ee) {
                JOptionPane.showMessageDialog(null, "Les champs cordonnées X et coordonées Y doivent être de type Chiffre", "Impossible d'ajouter le sommet", JOptionPane.ERROR_MESSAGE);
            }


        }

    }

}
