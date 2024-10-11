package psyche.vue;

import psyche.Controleur;
import psyche.metier.Dictionnaire;
import psyche.metier.Joueur;
import psyche.metier.jetons.Region;

import javax.swing.*;
import java.awt.*;

public class PanelScore extends JPanel
{
    private Controleur ctrl;

    private Joueur      j1;
    private Joueur      j2;

    Graphics2D g2;
    private int y;

    public PanelScore(Controleur ctrl)
    {
        this.ctrl = ctrl;

        j1 = this.ctrl.getJoueur(1);
        j2 = this.ctrl.getJoueur(2);

        this.setBackground(Color.WHITE);

    }

    /**
     * Dessiner les mines et les routes sur le plateau
     *
     * @param g the <code>Graphics</code> object to protect
     */
    public void paintComponent(Graphics g) {

        //super.paintComponent(g);
        this.g2 = (Graphics2D) g;

        /****************************/
        /*       BARRE DE TITRE     */
        /****************************/

        // ------------
        // Ligne 1
        g2.setColor(new Color(220, 205, 49));

        g2.fillRect(4, 2, 475, 20);

        Font fontTitre = new Font("Arial", Font.BOLD, 14);
        g2.setFont(fontTitre);

        g2.setColor(Color.BLACK);
        g2.drawRect(4, 2, 475, 20);
        g2.drawString("Fiche de Score", 182, 18);

        // ------------
        // Ligne 2
        g2.drawRect(4, 23, 201, 53);
        g2.drawRect(205, 23, 154, 53);
        g2.drawRect(360, 23, 119, 53);

        // Affichage des joueurs dans la ligne 2
        g2.drawImage(getToolkit().getImage("../psyche/theme/images/Jeton1.png"), 268, 26, this);
        g2.drawImage(getToolkit().getImage("../psyche/theme/images/Jeton2.png"), 410, 26, this);

        Font fontPetit = new Font("Arial", Font.BOLD, 8);
        g2.setFont(fontPetit);

        g2.drawString(String.format("%20s", Dictionnaire.getNomJoueur1()), 237, 66);
        g2.drawString(String.format("%20s", Dictionnaire.getNomJoueur2()), 372, 66);

        // ------------
        // Ligne 3
        g2.drawRect(4  , 77, 201, 19);
        g2.drawRect(205, 77, 154, 19);
        g2.drawRect(360, 77, 119, 19);

        /****************************/
        /*       POINTS ROUTES      */
        /****************************/

        // ------------
        // Ligne 4

        g2.setColor(new Color(253, 247, 201));
        g2.fillRect(4  , 95, 201, 19);
        g2.fillRect(205, 95, 154, 19);
        g2.fillRect(360, 95, 119, 19);

        g2.setColor(Color.BLACK);
        g2.drawRect(4  , 95, 201, 19);
        g2.drawRect(205, 95, 154, 19);
        g2.drawRect(360, 95, 119, 19);

        Font fontTitreLigne = new Font("Arial", Font.BOLD, 10);
        g2.setFont(fontTitreLigne);
        g2.drawString("Points " + Dictionnaire.getNomRoute(), 11, 108);

        // Affichage des points routes des joueurs
        g2.drawString(String.format("%-3d", this.j1.getJtnScore()), 274, 108);
        g2.drawString(String.format("%-3d", this.j2.getJtnScore()), 410, 108);

        // ------------
        // Ligne 5
        g2.drawRect(4  , 113, 201, 19);
        g2.drawRect(205, 113, 154, 19);
        g2.drawRect(360, 113, 119, 19);

        /****************************/
        /*      POINTS SOMMETS      */
        /****************************/

        // ------------
        // Ligne 6

        g2.setColor(new Color(253, 247, 201));
        g2.fillRect(4  , 131, 201, 19);
        g2.fillRect(205, 131, 154, 19);
        g2.fillRect(360, 131, 119, 19);

        g2.setColor(Color.BLACK);
        g2.drawRect(4  , 131, 201, 19);
        g2.drawRect(205, 131, 154, 19);
        g2.drawRect(360, 131, 119, 19);

        g2.setFont(fontTitreLigne);
        g2.drawString("Points " + Dictionnaire.getNomSommet(), 11, 144);

        // ------------
        // Ligne 7      -       MINE JAUNE
        g2.drawRect(4  , 150, 201, 41);
        g2.drawRect(205, 150, 154, 41);
        g2.drawRect(360, 150, 119, 41);

        g2.drawImage(getToolkit().getImage("../psyche/theme/images/score/jaune.png"), 83,150, 40, 40, this);

        g2.setFont(fontTitre);
        g2.drawString(String.format("%2d", this.j1.calculPointMine(Region.JAUNE)), 276, 175);
        g2.drawString(String.format("%2d", this.j2.calculPointMine(Region.JAUNE)), 414, 175);


        // ------------
        // Ligne 8      -       MINE BLEU
        g2.drawRect(4  , 192, 201, 41);
        g2.drawRect(205, 192, 154, 41);
        g2.drawRect(360, 192, 119, 41);

        g2.drawImage(getToolkit().getImage("../psyche/theme/images/score/bleu.png"), 83,194, 40, 40, this);

        g2.drawString(String.format("%2d", this.j1.calculPointMine(Region.BLEU)), 276, 217);
        g2.drawString(String.format("%2d", this.j2.calculPointMine(Region.BLEU)), 414, 217);

        // ------------
        // Ligne 9      -       MINE GRISE
        g2.drawRect(4  , 234, 201, 41);
        g2.drawRect(205, 234, 154, 41);
        g2.drawRect(360, 234, 119, 41);

        g2.drawImage(getToolkit().getImage("../psyche/theme/images/score/gris.png"), 83,236, 40, 40, this);

        g2.drawString(String.format("%2d", this.j1.calculPointMine(Region.GRIS)), 276, 259);
        g2.drawString(String.format("%2d", this.j2.calculPointMine(Region.GRIS)), 414, 259);

        // ------------
        // Ligne 10      -       MINE VERTE
        g2.drawRect(4  , 274, 201, 41);
        g2.drawRect(205, 274, 154, 41);
        g2.drawRect(360, 274, 119, 41);

        g2.drawImage(getToolkit().getImage("../psyche/theme/images/score/vert.png"), 83,275, 40, 40, this);

        g2.drawString(String.format("%2d", this.j1.calculPointMine(Region.VERT)), 276, 302);
        g2.drawString(String.format("%2d", this.j2.calculPointMine(Region.VERT)), 414, 302);

        // ------------
        // Ligne 11      -       MINE ROUGE
        g2.drawRect(4  , 314, 201, 41);
        g2.drawRect(205, 314, 154, 41);
        g2.drawRect(360, 314, 119, 41);

        g2.drawImage(getToolkit().getImage("../psyche/theme/images/score/rouge.png"), 83,315, 40, 40, this);

        g2.drawString(String.format("%2d", this.j1.calculPointMine(Region.ROUGE)), 276, 342);
        g2.drawString(String.format("%2d", this.j2.calculPointMine(Region.ROUGE)), 414, 342);

        // ------------
        // Ligne 12      -       MINE MARRON
        g2.drawRect(4  , 354, 201, 41);
        g2.drawRect(205, 354, 154, 41);
        g2.drawRect(360, 354, 119, 41);

        g2.drawImage(getToolkit().getImage("../psyche/theme/images/score/marron.png"), 83,355, 40, 40, this);

        g2.drawString(String.format("%2d", this.j1.calculPointMine(Region.MARRON)), 276, 380);
        g2.drawString(String.format("%2d", this.j2.calculPointMine(Region.MARRON)), 414, 380);

        // ------------
        // Ligne 13
        g2.setColor(new Color(253, 247, 201));
        g2.fillRect(4  , 394, 201, 19);
        g2.fillRect(205, 394, 154, 19);
        g2.fillRect(360, 394, 119, 19);

        g2.setColor(Color.BLACK);
        g2.drawRect(4  , 394, 201, 19);
        g2.drawRect(205, 394, 154, 19);
        g2.drawRect(360, 394, 119, 19);

        g2.setFont(fontTitreLigne);
        g2.drawString("Sous total", 11, 408);

        g2.drawString(String.format("%3d", this.j1.calculPointToutesMine()), 272, 408);
        g2.drawString(String.format("%3d", this.j2.calculPointToutesMine()), 412, 408);

        // ------------
        // Ligne 14
        g2.drawRect(4  , 414, 201, 19);
        g2.drawRect(205, 414, 154, 19);
        g2.drawRect(360, 414, 119, 19);

        /****************************/
        /*   POINTS PLATEAU INDIV   */
        /****************************/

        // ------------
        // Ligne 15
        g2.setColor(new Color(253, 247, 201));
        g2.fillRect(4  , 432, 201, 19);
        g2.fillRect(205, 432, 154, 19);
        g2.fillRect(360, 432, 119, 19);

        g2.setColor(Color.BLACK);
        g2.drawRect(4  , 432, 201, 19);
        g2.drawRect(205, 432, 154, 19);
        g2.drawRect(360, 432, 119, 19);

        g2.setFont(fontTitreLigne);
        g2.drawString("Plateau individuel", 11, 446);

        // ------------
        // Ligne 16             -           Score pièces
        g2.drawRect(4  , 450, 201, 19);
        g2.drawRect(205, 450, 154, 19);
        g2.drawRect(360, 450, 119, 19);

        g2.drawString("Score pièces", 11, 463);

        g2.drawString(String.format("%-3d", this.j1.calculPointPiece()), 272, 463);
        g2.drawString(String.format("%-3d", this.j2.calculPointPiece()), 412, 463);

        // ------------
        // Ligne 17
        g2.drawRect(4  , 468, 201, 19);
        g2.drawRect(205, 468, 154, 19);
        g2.drawRect(360, 468, 119, 19);

        g2.drawString("Score des colonnes", 11, 481);

        g2.drawString(String.format("%-3d", this.j1.calculPointColonne()), 272, 481);
        g2.drawString(String.format("%-3d", this.j2.calculPointColonne()), 412, 481);

        // ------------
        // Ligne 18
        g2.drawRect(4  , 486, 201, 19);
        g2.drawRect(205, 486, 154, 19);
        g2.drawRect(360, 486, 119, 19);

        g2.drawString("Score des lignes", 11, 499);

        g2.drawString(String.format("%-3d", this.j1.calculPointLigne()), 272, 499);
        g2.drawString(String.format("%-3d", this.j2.calculPointLigne()), 412, 499);

        // ------------
        // Ligne 19
        g2.setColor(new Color(253, 247, 201));
        g2.fillRect(4  , 504, 201, 19);
        g2.fillRect(205, 504, 154, 19);
        g2.fillRect(360, 504, 119, 19);

        g2.setColor(Color.BLACK);
        g2.drawRect(4  , 504, 201, 19);
        g2.drawRect(205, 504, 154, 19);
        g2.drawRect(360, 504, 119, 19);

        g2.setFont(fontTitreLigne);
        g2.drawString("Sous total", 11, 517);

        int scoreJoueur1 = this.j1.calculPointPiece() + this.j1.calculPointColonne() + this.j1.calculPointLigne();
        int scoreJoueur2 = this.j2.calculPointPiece() + this.j2.calculPointColonne() + this.j2.calculPointLigne();

        g2.drawString(String.format("%-3d", scoreJoueur1), 272, 517);
        g2.drawString(String.format("%-3d", scoreJoueur2), 412, 517);

        // ------------
        // Ligne 20
        g2.drawRect(4  , 523, 201, 19);
        g2.drawRect(205, 523, 154, 19);
        g2.drawRect(360, 523, 119, 19);

        /****************************/
        /*       POINTS BONUS       */
        /****************************/

        // ------------
        // Ligne 21
        g2.drawRect(4  , 541, 201, 19);
        g2.drawRect(205, 541, 154, 19);
        g2.drawRect(360, 541, 119, 19);

        g2.drawString("Jetons possession restants", 11, 555);

        g2.drawString(String.format("%3d", this.j1.getJtnPossession()), 272, 555);
        g2.drawString(String.format("%3d", this.j2.getJtnPossession()), 412, 555);

        // ------------
        // Ligne 22
        g2.setColor(new Color(253, 247, 201));
        g2.fillRect(4  , 559, 201, 19);
        g2.fillRect(205, 559, 154, 19);
        g2.fillRect(360, 559, 119, 19);

        g2.setColor(Color.BLACK);
        g2.drawRect(4  , 559, 201, 19);
        g2.drawRect(205, 559, 154, 19);
        g2.drawRect(360, 559, 119, 19);

        g2.setFont(fontTitreLigne);
        g2.drawString("Bonus (10)", 11, 571);

        g2.drawString(String.format("%-3d", this.j1.calculBonusPossession(this.j2)), 272, 571);
        g2.drawString(String.format("%-3d", this.j2.calculBonusPossession(this.j1)), 412, 571);

        /****************************/
        /*        SCORE TOTAL       */
        /****************************/

        // ------------
        // Ligne 23
        g2.drawRect(4  , 578, 201, 19);
        g2.drawRect(205, 578, 154, 19);
        g2.drawRect(360, 578, 119, 19);


        // ------------
        // Ligne 24
        g2.setColor(new Color(220, 205, 49));
        g2.fillRect(4  , 597, 201, 19);
        g2.fillRect(205, 597, 154, 19);
        g2.fillRect(360, 597, 119, 19);

        g2.setColor(Color.BLACK);
        g2.drawRect(4  , 597, 201, 19);
        g2.drawRect(205, 597, 154, 19);
        g2.drawRect(360, 597, 119, 19);

        g2.setFont(fontTitreLigne);
        g2.drawString("Total", 11, 609);

        g2.drawString(String.format("%-3d", this.j1.calculerScoreTotal(this.j2)), 272, 609);
        g2.drawString(String.format("%-3d", this.j2.calculerScoreTotal(this.j1)), 412, 609);


        /****************************/
        /*     AFFICHAGE GAGNANT    */
        /****************************/

        Font fontGagnt = new Font("Arial", Font.BOLD, 15);

        g2.setFont(fontGagnt);

        Joueur gagne = this.ctrl.gagnant();

        if (gagne != null)
        {

            String nomGagnt = gagne.getNumero()==1 ? Dictionnaire.getNomJoueur1() : Dictionnaire.getNomJoueur2();

            // Nom du gagnant
            g2.drawString(String.format("%-20s", nomGagnt + " est le gagnant de cette partie !"), 11, 650);

        }
        else
        {

            g2.drawString(String.format("%-20s", "Les deux joueurs sont les gagnants de cette partie !"), 11, 650);

        }


    }

    public void majIHM()
    {
        this.repaint();
    }

}
