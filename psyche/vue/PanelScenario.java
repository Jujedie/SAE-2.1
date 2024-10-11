package psyche.vue;

import javax.swing.*;

import psyche.Controleur;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelScenario extends JPanel implements ActionListener
{

    private Controleur    ctrl         ;
    private FrameScenario frameScenario;

    private JPanel     panelPrincipal  ;
    private JButton    btnSuivant      ;
    private JButton    btnPrecedent    ;
    private JButton    btnValider      ;
    private JTextField txtEtape        ;
    private JLabel     lblDescription  ;
    private JLabel     lblEtape        ;


    private int        etapeActuelle   ;
    private int        etapeMax        ;
    private String     numScenario     ;

    public PanelScenario(Controleur ctrl, FrameScenario frameScenario)
    {

        JPanel panelDescription = new JPanel();
        panelDescription.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        this.ctrl = ctrl;
        this.frameScenario = frameScenario;
        this.etapeActuelle = 0 ;
        this.etapeMax      = this.ctrl.getNbEtapesMax();
        this.panelPrincipal = new JPanel();

        String str = this.ctrl.getParam();
        this.numScenario = str.substring(str.indexOf("_") + 1, str.indexOf("."));

        this.setLayout(new BorderLayout());
        this.panelPrincipal.setLayout(new GridLayout(4,5,5,5));

        //Création des composants
        this.btnSuivant     = new JButton("Suivant"  );
        this.btnPrecedent   = new JButton("Précédent");
        this.btnValider     = new JButton("Valider");
        this.txtEtape       = new JTextField(2);
        this.lblDescription = new JLabel("");
        this.lblDescription.setFont(new Font("Arial", Font.BOLD, 20));

        this.lblEtape     = new JLabel(this.etapeActuelle + "/" + this.etapeMax);

        /* Ajouts des composants*/

        //Première ligne
        this.add(new JLabel("Scénario n°" + this.numScenario),BorderLayout.NORTH);

        //Deuxième ligne
        this.panelPrincipal.add(new JLabel(""));
        this.panelPrincipal.add(this.btnPrecedent );
        this.panelPrincipal.add(this.lblEtape     );
        this.panelPrincipal.add(this.btnSuivant   );
        this.panelPrincipal.add(new JLabel(""));

        //Troisième ligne
        this.panelPrincipal.add(new JLabel(""));
        this.panelPrincipal.add(new JLabel(""));
        this.panelPrincipal.add(new JLabel(""));
        this.panelPrincipal.add(new JLabel(""));
        this.panelPrincipal.add(new JLabel(""));

        //Quatrième ligne
        this.panelPrincipal.add(new JLabel(""));
        this.panelPrincipal.add(new JLabel(""));
        this.panelPrincipal.add(this.txtEtape     );
        this.panelPrincipal.add(new JLabel(""));
        this.panelPrincipal.add(new JLabel(""));

        //Cinquième ligne
        this.panelPrincipal.add(new JLabel(""));
        this.panelPrincipal.add(new JLabel("Sélectionnez :"));
        this.panelPrincipal.add(this.btnValider   );
        this.panelPrincipal.add(new JLabel(""));
        this.panelPrincipal.add(new JLabel(""));

        this.add(this.panelPrincipal,BorderLayout.CENTER);

        panelDescription.add(this.lblDescription);

        this.add(panelDescription,BorderLayout.SOUTH);

        //Activation des composants
        this.btnSuivant  .addActionListener(this);
        this.btnPrecedent.addActionListener(this);
        this.btnValider  .addActionListener(this);

    }

    /**
     * Permet de gérer les actions des boutons
     * @param e événement déclenché
     */
    public void actionPerformed(ActionEvent e)
    {
        if ( e.getSource() == this.btnSuivant )
        {
            if (this.etapeActuelle < this.etapeMax && this.ctrl.etapeSuivante  (this.etapeActuelle))
            {
                System.out.println("Passage de l'étape : " + this.etapeActuelle + " à l'étape : " + ++this.etapeActuelle);
            }
        }

        if ( e.getSource() == this.btnPrecedent )
        {
            if (this.etapeActuelle > 0 && this.ctrl.etapePrecedente(this.etapeActuelle))
            {
                System.out.println("Passage de l'étape : " + this.etapeActuelle + " à l'étape : " + --this.etapeActuelle);
            }
        }

        if ( e.getSource() == this.btnValider)
        {
            int etapeVisee = Integer.parseInt(this.txtEtape.getText());
            if (etapeVisee >= 0 && etapeVisee <= this.etapeMax )
            {
                this.ctrl.allerAEtape(etapeVisee);
                this.etapeActuelle = etapeVisee;

                System.out.println("Passage à l'étape : " + this.etapeActuelle);
            }
        }
        this.majIHM();
    }

    /**
     * Permet de changer la description de l'étape actuelle du scenario
     * @param description description de l'étape actuelle du scenario
     */
    public void setDescription(String description)
    {
        this.lblDescription.setText("Description : " + description);
    }

    /**
     * Met à jour l'interface graphique.
     */
    public void majIHM()
    {
        this.lblEtape      .setText(this.etapeActuelle + "/" + this.etapeMax);
    }
}
