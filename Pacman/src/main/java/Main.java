import entrants.pacman.karthik.MyBFSPacMan;
import entrants.pacman.karthik.MyGBFSPacMan;
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
        
        //Ignore UninformedPacMan.java and InformedPacMan.java
    }
}
