import entrants.pacman.karthik.MyBFSPacMan;
import entrants.pacman.karthik.MyGBFSPacMan;
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
        // Latest implementations are MyBFSPacMan and MyGBFSPacMan        
        //executor.runGameTimed(new MyBFSPacMan(), new RandomGhosts(), true);
        executor.runGameTimed(new MyGBFSPacMan(), new RandomGhosts(), true);
        
        
        // You can try the old implementations that I discussed earlier by using InformedPacMan and UninformedPacMan
        //executor.runGameTimed(new UninformedPacMan(), new RandomGhosts(), true);
        //executor.runGameTimed(new InformedPacMan(), new RandomGhosts(), true);
    }
}
