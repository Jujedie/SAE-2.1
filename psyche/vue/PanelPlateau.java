package psyche.vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import psyche.Controleur;
import psyche.metier.Route;
import psyche.metier.jetons.Mine;
import psyche.metier.jetons.Minerai;
import psyche.metier.jetons.Region;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


public class PanelPlateau extends JPanel
{
    private final Controleur ctrl       ;
    private Graphics2D       g2         ;
    private final Image      imgPlateau ;
    List<Line2D>             ensLigne   ;
    private GererSouris      gererSouris;


    public PanelPlateau(Controleur ctrl) {
        this.ctrl = ctrl;
        this.ensLigne = new ArrayList<>();

        this.gererSouris = new GererSouris();
        this.addMouseListener(this.gererSouris);

        this.imgPlateau = getToolkit().getImage("plateau.jpg");

        this.repaint();
    }

    /**
     * Dessiner les mines et les routes sur le plateau
     *
     * @param g the <code>Graphics</code> object to protect
     */
    public void paintComponent(Graphics g)
    {
        //super.paintComponent(g);
        this.g2 = (Graphics2D) g;

        try
        {
            g2.drawImage(ImageIO.read(new File("../psyche/theme/images/fond.png")), 0, 0, 1000, 884, this);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        g2.setStroke(new BasicStroke(2.0f));

        g2.drawImage(getToolkit().getImage("../psyche/theme/images/Jeton1.png"), 850, 20, 40, 40, this);
        g2.drawImage(getToolkit().getImage("../psyche/theme/images/Jeton2.png"), 850, 70, 40, 40, this);

        Font fontScore = new Font("Arial", Font.BOLD, 30);
        g2.setFont(fontScore);
        g2.drawString(this.ctrl.getJoueur(1).getJtnScore() + "", 900, 50);
        g2.drawString(this.ctrl.getJoueur(2).getJtnScore() + "", 900, 100);

        for (Mine mine : this.ctrl.getEnsMine())  // dessine toutes les mines
        {

            BufferedImage image = null;
            try
            {
                if (mine.getRegion().name().equals("ROME"))
                {
                    image = ImageIO.read(new File("../psyche/theme/images/trans/ROME.png"));
                }
                else
                {
                    if (mine.getRessource() == null)
                        image = ImageIO.read(new File("../psyche/theme/images/trans/" + mine.getRegion().getLienFichier().replaceAll(".png", "") + "_clair.png"));
                    else
                        image = ImageIO.read(new File("../psyche/theme/images/trans/" + mine.getRegion().getLienFichier()));

                    g2.drawString(mine.getValeur() + "", mine.getCoordX() + 10, mine.getCoordY() + 20);
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }


            if (image != null)
            {

                if (mine.estSelectionne() && !mine.getRegion().name().equals("ROME"))
                {
                    g2.setColor(Color.RED);
                    g2.setStroke(new BasicStroke(5.0f));
                    g2.drawRect(mine.getCoordX(), mine.getCoordY(), 31, 56);
                }

                g2.setStroke(new BasicStroke(1.0f));


                if (!mine.getRegion().name().equals("ROME"))
                    g2.drawImage(image, mine.getCoordX(), mine.getCoordY(), 31, 56, this);
                else
                    g2.drawImage(image, mine.getCoordX(), mine.getCoordY(), 30, 35, this);
            }

            // Afficher la valeur de la mine
            if (!mine.getRegion().name().equals("ROME"))
            {
                Font font = new Font("Arial", Font.BOLD, 12);
                g2.setFont(font);

                // Permet de changer la couleur en fonction de la couleur de fond de la mine
                if (mine.getRegion().name().equals("VERT") || mine.getRegion().name().equals("MARRON") || mine.getRegion().name().equals("BLEU"))
                {
                    g2.setColor(Color.WHITE);
                }
                else
                {
                    g2.setColor(Color.BLACK);
                }

                g2.drawString(String.format("%2s", mine.getValeur()), mine.getCoordX() + 7, mine.getCoordY() + 19);
            }

            // Afficher les ressources de la Mine
            if (mine.getRessource() != null)
            {

                g2.drawImage(getToolkit().getImage("../psyche/theme/images/res/" + mine.getRessource().getMinerai().getImage() + ".png"), mine.getCoordX(), mine.getCoordY() + 25, 30, 30, this);

            }

        }

        for (Route route : this.ctrl.getEnsRoute()) // dessine toutes les routes
        {

            int x1 = route.getMineDep().getCoordX() + 15;
            int y1 = route.getMineDep().getCoordY() + 28; // centre de l'image (demi hauteur)
            int x2 = route.getMineArr().getCoordX() + 15;
            int y2 = route.getMineArr().getCoordY() + 28;

            // Calcul de l'angle entre les deux mines
            double angle = Math.atan2(y2 - y1, x2 - x1);

            // Dimensions pour le calcul des intersections
            int largeur   = 50;
            int hauteur   = 90;
            int arcRadius = 30;

            // Calcul des points d'intersections
            int[] interX1 = getPointsIntersection(x1, y1, angle          , true , largeur, hauteur, arcRadius);
            int[] interY1 = getPointsIntersection(x1, y1, angle          , false, largeur, hauteur, arcRadius);
            int[] interX2 = getPointsIntersection(x2, y2, angle + Math.PI, true , largeur, hauteur, arcRadius);
            int[] interY2 = getPointsIntersection(x2, y2, angle + Math.PI, false, largeur, hauteur, arcRadius);

            if (route.getMineDep().getRegion().name().equals("ROME"))
            {
                interX1 = getPointsIntersection(x1, y1, angle, false, 30, 35, 26);
                interY1 = getPointsIntersection(x1, y1, angle, false, 30, 35, 26);
            }
            if (route.getMineArr().getRegion().name().equals("ROME"))
            {
                interX2 = getPointsIntersection(x2, y2, angle + Math.PI, false, 30, 35, 26);
                interY2 = getPointsIntersection(x2, y2, angle + Math.PI, false, 30, 35, 26);
            }


            // Trouver les points les plus proches sur les 2 rectangles des 2 mines
            int[] pointsProches = trouverPointsProches(interX1, interY1, interX2, interY2);
            int   debutX = pointsProches[0];
            int   debutY = pointsProches[1];
            int   finX   = pointsProches[2];
            int   finY   = pointsProches[3];

            int nbSections = route.getNbSection();

            g2.setColor(new Color(60, 60, 60));

            Line2D ligne = new Line2D.Double(debutX, debutY, finX, finY);
            ensLigne.add(ligne);
            g2.draw(ligne);

            g2.fillOval(debutX - 5, debutY - 5, 10, 10);
            g2.fillOval(  finX - 5,   finY - 5, 10, 10);

            if (nbSections == 2)
            {
                int x3 = (debutX + finX) / 2;
                int y3 = (debutY + finY) / 2;
                g2.fillOval(x3 - 7, y3 - 7, 14, 14);
            }

            // Permet d'afficher un jeton d'appartenance au joueur
            if (route.getAppartientJoueur() != null)
            {
                String imgJoueur = route.getAppartientJoueur().getNumero()==1?"Jeton1":"Jeton2";

                int x4 = (debutX + finX) / 2;
                int y4 = (debutY + finY) / 2;

                if ( route.getNbSection() == 2 )
                {
                    g2.drawImage(getToolkit().getImage("../psyche/theme/images/" + imgJoueur + ".png"), x4 < debutX?(debutX - ((debutX-x4)/2) - 8)                :(debutX + ((x4-debutX)/2) - 8)                , y4 < debutY?(debutY - ((debutY-y4)/2) - 8)                :(debutY + ((y4-debutY)/2) - 8)                , 17, 17, this);
                    g2.drawImage(getToolkit().getImage("../psyche/theme/images/" + imgJoueur + ".png"), x4 < debutX?(debutX - ((debutX-x4) + ((debutX-x4)/2)) - 8):(debutX + ((x4-debutX) + ((x4-debutX)/2)) - 8), y4 < debutY?(debutY - ((debutY-y4) + ((debutY-y4)/2)) - 8):(debutY + ((y4-debutY) + ((y4-debutY)/2)) - 8), 17, 17, this);
                }
                else
                    g2.drawImage(getToolkit().getImage("../psyche/theme/images/" + imgJoueur + ".png"), x4 - 8, y4 - 8, 17, 17, this);

            }
        }
    }

    /**
     * @param x         la coordonnée x du point de départ
     * @param y         la coordonnée y du point de départ
     * @param angle     l'angle de la droite
     * @param haut      si le point est en haut du rectangle
     * @param largeur   la largeur du rectangle
     * @param hauteur   la hauteur du rectangle
     * @param arcRadius le rayon de l'arc
     * @return retourne les points d'intersection sous la forme [x,y]
     */
    private int[] getPointsIntersection(int x, int y, double angle, boolean haut, int largeur, int hauteur, int arcRadius)
    {
        int rectCentreY = y;
        int decaleY     = haut ? -hauteur / 2 + arcRadius : hauteur / 2 - arcRadius;

        // Calcul l'intersection des deux points des rectangles
        int centreCercleX = x;
        int centreCercleY = rectCentreY + decaleY;

        int intersectionX = (int) (centreCercleX + arcRadius * Math.cos(angle));
        int intersectionY = (int) (centreCercleY + arcRadius * Math.sin(angle));

        return new int[]{intersectionX, intersectionY};
    }

    /**
     * Trouve les points les plus proches entre deux rectangles
     *
     * @param x1 le point X du rectangle 1 (depart)
     * @param y1 le point Y du rectangle 1 (depart)
     * @param x2 le point X du rectangle 2 (arrivee)
     * @param y2 le point Y du rectangle 2 (arrivee)
     * @return retourne les points les plus proches sous la forme [x1, y1, x2, y2]
     */
    private int[] trouverPointsProches(int[] x1, int[] y1, int[] x2, int[] y2)
    {
        double minDistance   = Double.MAX_VALUE;
        int[]  pointsProches = new int[4];

        int[][] pointsX = {x1, y1};
        int[][] pointsY = {x2, y2};

        for (int[] pointX : pointsX)
        {
            for (int[] pointY : pointsY)
            {
                double distance = Math.sqrt(Math.pow(pointY[0] - pointX[0], 2) + Math.pow(pointY[1] - pointX[1], 2));
                if (distance < minDistance)
                {
                    minDistance = distance;
                    pointsProches[0] = pointX[0];
                    pointsProches[1] = pointX[1];
                    pointsProches[2] = pointY[0];
                    pointsProches[3] = pointY[1];
                }
            }
        }

        return pointsProches;
    }


    /**
     * Permet de trouver le chemin le plus court entre deux mines
     * @param mineDep la mine d'arrivée de la première route
     * @param mineArr la mine d'arrivée que l'on veut atteindre
     * @return la liste des routes par lesquelles passer pour arriver avec le moins de mouvements
     */
    private List<Route> trouverChemin(Mine mineDep,Mine mineArr)
    {
        if (mineDep == null || mineArr == null) { return null; }

        List<Route> cheminOptimal = new ArrayList<>();
        List<Route> mineVisite    = new ArrayList<>();
        trouverChemin(mineDep, mineArr, mineVisite, cheminOptimal, new ArrayList<>());
        return cheminOptimal;
    }

    /**
     * Permet de trouver le chemin le plus court entre deux mines
     * @param mineActuelle la mine sur laquelle on se trouve
     * @param mineArr la mine d'arrivée que l'on veut atteindre
     * @param cheminActuel la liste des routes par lesquelles on est passé
     * @param cheminOptimal la liste des routes par lesquelles passer pour arriver avec le moins de mouvements
     * @param mineVisite la liste des mines déjà visitées
     */
    private void trouverChemin(Mine mineActuelle, Mine mineArr, List<Route> cheminActuel, List<Route> cheminOptimal, List<Mine> mineVisite)
    {
        if (mineActuelle.equals(mineArr))
        {
            if (cheminOptimal.isEmpty() || cheminActuel.size() < cheminOptimal.size())
            {
                cheminOptimal.clear();
                cheminOptimal.addAll(cheminActuel);
                return;
            }
        }

        mineVisite.add(mineActuelle);

        List<Route> ensRouteMineActuel = mineActuelle.getEnsRoute();
        for (Route route : PanelPlateau.this.ctrl.getEnsRoute())
        {
            if (route.getMineArr() == mineActuelle)ensRouteMineActuel.add(route);
        }

        for (Route route : ensRouteMineActuel )
        {
            Mine mineSuivante = route.getMineArr();
            if ( mineSuivante == mineActuelle) mineSuivante = route.getMineDep();
            if (!mineVisite.contains(mineSuivante) && route.getMineDep().getRessource() == null && route.getAppartientJoueur() != null && route.getMineArr().getRessource() == null)
            {
                cheminActuel.add(route);
                trouverChemin(mineSuivante, mineArr, cheminActuel, cheminOptimal, mineVisite);
                cheminActuel.remove(cheminActuel.size() - 1);
            }
        }
    }

    /**
     * Permet d'afficher un message d'erreur
     * @param message Message à afficher
     */
    public void afficherMessageErreur(String message)
    {

        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);

    }

    /**
     * Classe permettant de gérer les clics de la souris
     */
    private class GererSouris extends MouseAdapter
    {

        Mine mineDepart = null;
        Mine mineArrive = null;

        /**
         * Vérifie si les coordonnées du clic correspondent à une mine , puis si c'est le cas, effectue les actions nécessaires pour lier les mines entre elles
         * @param e the event to be processed
         */
        public void mouseClicked(MouseEvent e)
        {
            this.click(e.getX(),e.getY());
        }

        public void click(int x, int y)
        {
            List<Route> ensRoute = new ArrayList<>();
            for (Mine mine : PanelPlateau.this.ctrl.getEnsMine())
            {

                if (mine.zoneMine(x, y))
                {
                    System.out.println("EST SELECTIONNE ???? " + mine.estSelectionne());
                    if (mineDepart == null)
                    {
                        System.out.println("SELECTION MINE DEPART");
                        mineDepart = mine;
                        mineDepart.toggleSelectionne();
                    }
                    else
                    {
                        System.out.println("SELECTION MINE ARRIVEE");
                        mineArrive = mine;

                        if (mineArrive == mineDepart)
                        {
                            PanelPlateau.this.afficherMessageErreur("Ne sélectionnez pas deux fois la même mine !");
                            return;
                        }


                        PanelPlateau.this.lierMine(mineDepart, mineArrive);
                        mineDepart.toggleSelectionne();
                        mineDepart = null;
                    }

                    PanelPlateau.this.repaint();

                    //System.out.println("Clic sur la mine " + mine.getValeur() + " de la région " + mine.getRegion() + " RESSOURCE : " + mine.getRessource().getMinerai().name());
                }
            }
        }

        public void resetMineDep()
        {
            if( mineDepart != null && mineDepart.estSelectionne())
            {
                mineDepart.toggleSelectionne();
                mineDepart = null;
            }
        }
    }

    /**
     * Méthode permettant de lier les deux mines
     * @param mineDepart Mine de départ
     * @param mineArrive Mine d'arrivée
     */
    public void lierMine(Mine mineDepart, Mine mineArrive)
    {
        List<Route> ensRoute = new ArrayList<>();
        int nbSection=0;

        Mine rome = null;
        for (Mine m : PanelPlateau.this.ctrl.getEnsMine())
        {
            if ( m.getRegion() == Region.ROME) rome = m;
        }

        boolean aOr = PanelPlateau.this.ctrl.rejoindreMines(mineDepart, mineArrive);
        for (Route r : PanelPlateau.this.ctrl.getEnsRoute())
        {
            if (r.getMineDep() == mineDepart && r.getMineArr() == mineArrive)
            {
                nbSection = r.getNbSection();
            }
        }

        ensRoute = trouverChemin(rome,mineDepart);
        for ( Route r : ensRoute )
        {
            if (aOr)
            {
                if (r.getAppartientJoueur() == PanelPlateau.this.ctrl.getJoueur(1))
                {
                    PanelPlateau.this.ctrl.getJoueur(1).setJtnScore(PanelPlateau.this.ctrl.getJoueur(1).getJtnScore() + r.getNbSection()*2);
                }

                if (r.getAppartientJoueur() == PanelPlateau.this.ctrl.getJoueur(2))
                {
                    PanelPlateau.this.ctrl.getJoueur(2).setJtnScore(PanelPlateau.this.ctrl.getJoueur(2).getJtnScore() + r.getNbSection()*2);
                }
            }
            else
            {
                if (r.getAppartientJoueur() == PanelPlateau.this.ctrl.getJoueur(1))
                {
                    PanelPlateau.this.ctrl.getJoueur(1).setJtnScore(PanelPlateau.this.ctrl.getJoueur(1).getJtnScore() + r.getNbSection());
                }

                if (r.getAppartientJoueur() == PanelPlateau.this.ctrl.getJoueur(2))
                {
                    PanelPlateau.this.ctrl.getJoueur(2).setJtnScore(PanelPlateau.this.ctrl.getJoueur(2).getJtnScore() + r.getNbSection());
                }
            }
        }
        this.repaint();

    }

    /**
     * genere un clic simulé aux coordonnées données
     * @param x coordonnée x
     * @param y coordonnée y
     */
    public void clicArtificiel(int x, int y)
    {
        this.gererSouris.click(x, y);
    }

    public void resetMineDep()
    {
        this.gererSouris.resetMineDep();
    }
}
