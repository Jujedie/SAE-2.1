package routes.metier;

import java.awt.*;

public enum Region
{
    JAUNE  ( new Color( 255, 255,   0 ) ),
    BLEU   ( new Color(  70, 130, 180 ) ),
    GRIS   ( new Color( 192, 192, 192 ) ),
    VERT   ( new Color(   0, 100,   0 ) ),
    ROUGE  ( new Color( 205,  98,  96 ) ),
    MARRON ( new Color( 138,  89,  55 ) ),
    ROME   ( new Color(89, 0, 202));

    private Color couleur;

    Region( Color couleur )
    {
        this.couleur = couleur;
    }

    public Color getCouleur() { return this.couleur;}

    public String toString()
    {
        if (this.name().equals("ROME"))
        {
            return this.name();
        }

        return "REGION " + this.name();
    }
}
