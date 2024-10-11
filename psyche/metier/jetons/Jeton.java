package psyche.metier.jetons;

public class Jeton
{
    private final int numJeton;

    /**
     * Constructeur de la classe Jeton
     * @param numJeton le numéro du jeton
     */
    public Jeton(int numJeton)
    {
        this.numJeton = numJeton;
    }

    public int getNumJeton(){return this.numJeton;}
}
