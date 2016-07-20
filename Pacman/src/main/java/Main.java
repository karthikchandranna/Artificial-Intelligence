import entrants.pacman.karthik.InformedPacMan;
import entrants.pacman.karthik.UninformedPacMan;
import pacman.controllers.examples.RandomGhosts;
import pacman.Executor;


/**
 * Created by pwillic on 06/05/2016.
 */
public class Main {

    public static void main(String[] args) {

        Executor executor = new Executor(true, true);
        executor.runGameTimed(new UninformedPacMan(), new RandomGhosts(), true);
    }
}
