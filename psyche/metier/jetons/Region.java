package psyche.metier.jetons;

import java.awt.*;

public enum Region
{
    JAUNE  ( new Color( 255, 255,   0 ), "Mine_Jaune.png"  ),
    BLEU   ( new Color(  70, 130, 180 ), "Mine_Bleu.png"   ),
    GRIS   ( new Color( 192, 192, 192 ), "Mine_Gris.png"   ),
    VERT   ( new Color(   0, 100,   0 ), "Mine_Vert.png"   ),
    ROUGE  ( new Color( 205,  98,  96 ), "Mine_Rouge.png"  ),
    MARRON ( new Color( 138,  89,  55 ), "Mine_Marron.png" ),
    ROME   ( new Color(  27,  71, 143 ), "ROME.png"         );

    private Color  couleur;
    private String lienFichier;

    Region( Color couleur, String fichier )
    {
        this.couleur = couleur;
        this.lienFichier = fichier;
    }

    public Color  getCouleur()
    {
        return this.couleur;
    }

    public String getLienFichier()
    {
        return this.lienFichier;
    }

    public String toString()
    {
        return "REGION " + this.name();
    }
}
