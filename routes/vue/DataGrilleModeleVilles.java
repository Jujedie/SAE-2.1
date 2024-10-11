package routes.vue;

import routes.Controleur;
import routes.metier.Ville;

import javax.swing.table.AbstractTableModel;

public class DataGrilleModeleVilles extends AbstractTableModel
{

    private Controleur ctrl;

    private String[]    tabEntetes;
    private Object[][]  tabDonnees;

    public DataGrilleModeleVilles(Controleur ctrl)
    {

        this.ctrl = ctrl;

        this.tabEntetes = new String[]{"Numéro","Nom", "X", "Y"};
        this.tabDonnees  = new Object[this.ctrl.getEnsVille().size()][this.tabEntetes.length];

        var i = 0;
        for (Ville v : this.ctrl.getEnsVille())
        {

            this.tabDonnees[i++] = new Object[]{v.getNumVille(), v.getNom(), v.getCoordX(), v.getCoordY()};

        }

    }

    /**
     * Méthode facultative
     * Permet de savoir si oui on non une cellule est éditable en fonction de sa position
     */
    public boolean isCellEditable(int row, int col)
    {
        return ( col == 2 || col == 3);
    }

    /**
     * Méthode facultative
     * Permet de modifier UNIQUEMENT DANS L'INTERFACE le contenu d'une cellule
     * NOTE : La sauvegarde doit se faire via le métier, et la donnée ne doit être changé que si le métier a été modifié
     */
    public void setValueAt(Object value, int row, int col)
    {



        Ville villeSelected = this.ctrl.getVille(row);

        if (villeSelected != null)
        {

            if (col == 2)
            {
                if ((Integer) value < 0 || (Integer) value > 1000) return;
                this.ctrl.modifierVille(villeSelected, villeSelected.getNom(), (Integer) value, villeSelected.getCoordY(), villeSelected.getRegion(), villeSelected.getNumUtilisateur());
            }

            if (col == 3)
            {
                if ((Integer) value < 0 || (Integer) value > 800) return;
                this.ctrl.modifierVille(villeSelected, villeSelected.getNom(), villeSelected.getCoordX(), (Integer) value, villeSelected.getRegion(), villeSelected.getNumUtilisateur());
            }

            this.ctrl.majIHM();

            this.tabDonnees[row][col] = (Integer) value;
            this.fireTableCellUpdated(row, col);

        }


    }

    /**
     * Méthode facultative
     * Permet de modifier le type de la colone
     * Par exemple si une colone à les champs true ou false, une checkbox sera ajouté
     * Les chiffres seront alignés à droite
     */
    public Class  getColumnClass(int c)
    {
        return getValueAt(0, c).getClass();
    }


    /**
     * Méthode obligatoire
     * Permet de savoir le nom de la colone
     */
    public String getColumnName (int col)
    {
        return this.tabEntetes[col];
    }

    /**
     * Méthode obligatoire
     * Permet de savoir le nombre de lignes
     */
    public int getRowCount() {
        return this.tabDonnees.length;
    }

    /**
     * Méthode obligatoire
     * Permet de savoir le nombre de colone
     */
    public int getColumnCount() {
        return this.tabEntetes.length;
    }

    /**
     * Méthode obligatoire
     * @return La valeur à l'indice lig;col
     */
    public Object getValueAt(int lig, int col) {
        return this.tabDonnees[lig][col];
    }

}
