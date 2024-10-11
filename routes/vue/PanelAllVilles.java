package routes.vue;

import routes.Controleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelAllVilles extends JPanel implements ActionListener
{

    private Controleur              ctrl;

    private JButton                 btnSupprimer;

    private DataGrilleModeleVilles  dmDonnesVille;

    private JTable                  tblVilles;


    public PanelAllVilles(Controleur ctrl)
    {

        JPanel  panelTbl;
        JPanel  panelAction;

        JScrollPane spTableau;

        this.ctrl = ctrl;

        this.setLayout(new BorderLayout(15, 15));

        /**************************************/
        /* CREATIONS DES COMPOSANTS */
        /**************************************/

        panelTbl    = new JPanel();
        panelAction = new JPanel();

        this.dmDonnesVille = new DataGrilleModeleVilles(this.ctrl);
        this.tblVilles     = new JTable(this.dmDonnesVille);
        this.tblVilles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        spTableau = new JScrollPane(this.tblVilles);

        this.btnSupprimer = new JButton("Supprimer");


        /**************************************/
        /*      PLACEMENT DES COMPOSANTS      */
        /**************************************/

        panelTbl.add(spTableau);

        panelAction.add(this.btnSupprimer);

        this.add(panelTbl);
        this.add(panelAction, BorderLayout.SOUTH);


        /**************************************/
        /*     ACTIVATION DES COMPOSANTS      */
        /**************************************/

        this.btnSupprimer.addActionListener(this);

    }

    public void majTableau()
    {

        this.dmDonnesVille = new DataGrilleModeleVilles(this.ctrl);

        this.tblVilles.setModel(this.dmDonnesVille);

    }


    public void actionPerformed(ActionEvent e)
    {

        if (e.getSource() == this.btnSupprimer)
        {

            this.ctrl.supprimerVille(this.ctrl.getVille(this.tblVilles.getSelectedRow()));

            this.ctrl.majIHM();

        }

    }

}
