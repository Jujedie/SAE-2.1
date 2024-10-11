package psyche.metier.jetons;

public class Ressource extends Jeton
{
    private static int nbJeton = 0;

    private final Minerai minerai;

    public Ressource(Minerai minerai)
    {
        super(++Ressource.nbJeton);
        this.minerai = minerai;
    }
    public Minerai getMinerai() {return minerai;}
    public String toString()
    {
        return "Ressource " + super.toString() + " : " + minerai.toString();
    }
}
