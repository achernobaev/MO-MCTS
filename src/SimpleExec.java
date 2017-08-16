import framework.core.Exec;
import framework.core.PTSPConstants;
import framework.core.PTSPView;
import framework.utils.Evo;
import framework.utils.JEasyFrame;

public class SimpleExec extends Exec
{
    public static void main(String[] args)
    {
        System.out.println("Simple Exec");

        m_mapNames = new String[]{"maps/ptsp_map01.map"};

        System.err.println("Running in map " + m_mapNames[0] + "...");

//        m_controllerName = "controllers.greedy.GreedyController"; // works

//        m_controllerName = "controllers.MacroRandomSearch.MacroRSController"; // works

        m_controllerName = "controllers.ParetoMCTS.ParetoMCTSController"; // works

//        m_controllerName = "controllers.mctsdriver.MctsDriverController"; // IndexOutOfBoundsException happens after some time

//        m_controllerName = "controllers.singleMCTS.SingleMCTSController"; // works

//        m_controllerName = "controllers.nsga2Controller.NSGAIIController"; // works

        // needed for ParetoMCTSController
        Evo.init();
        Evo.currentWeights = Evo.createNewRandomIndividual();

        System.err.println("Using " + m_controllerName);

        double[] res = evaluateVisual();

        System.err.println("Finished");
    }

    public static double[] evaluateVisual()
    {
        int delay = 5;
        //Get the game ready.
        if(!prepareGame())
            return null;

        //Indicate what are we running
        if(m_verbose) System.out.println("Running " + m_controllerName + " in map " + m_game.getMap().getFilename() + "...");

        m_view = new PTSPView(m_game, m_game.getMapSize(), m_game.getMap(), m_game.getShip(), m_controller);
        JEasyFrame frame = new JEasyFrame(m_view, "PTSP-Game: " + m_controllerName);

        while(!m_game.isEnded())
        {
            //When the result is expected:
            long then = System.currentTimeMillis();
            long due = then + PTSPConstants.ACTION_TIME_MS;

            //Advance the game.
            int actionToExecute = m_controller.getAction(m_game.getCopy(), due);

            //Exceeded time
            long now = System.currentTimeMillis();
            long spent = now - then;

            m_game.tick(actionToExecute);

            int remaining = (int) Math.max(0, delay - (now-then));//To adjust to the proper framerate.
            //Wait until de next cycle.
            waitStep(remaining);

            //And paint everything.
            m_view.repaint();
            if(m_game.getTotalTime() == 1)
                waitStep(m_warmUpTime);

        }

        if(m_verbose)
        {
            m_game.printResults();
        }

        return new double[]{m_game.getTotalTime(),
                PTSPConstants.INITIAL_FUEL - m_game.getShip().getRemainingFuel(),
                m_game.getShip().getDamage()};
    }
}
