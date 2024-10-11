package psyche.metier.jetons;

public enum Minerai implements IJeton
{
    ALUMINIUM ("AL"),  // Al
    ARGENT    ("AG"),  // Ag
    OR        ("AU"),  // Au
    COBALT    ("CO"),  // Co
    FER       ("FE"),  // Fe
    NICKEL    ("NI"),  // Ni
    PLATINE   ("PT"),  // Pt
    MONNAIE   ("NR"),  // Nr
    TITANE    ("TI");  // Ti

    private String image;

    Minerai(String image){this.image = image;}

    public String getImage() { return this.image; }
    
    public String toString()
    {
        return "Piece " + this.name();
    }
}
