import java.util.HashSet;
import java.util.Set;

public class ProblemJugsPuzzle extends Problem {
    
	boolean goal_test(Object state) {
        StateJugsPuzzle jug_state = (StateJugsPuzzle) state;
        if (jug_state.jugArray[0]==1
         || jug_state.jugArray[1]==1 
         || jug_state.jugArray[2]==1)
            return true;
        else return false;
	}
    Set<Object> getSuccessors(Object state) {
    	Set<Object> successors = new HashSet<Object>();
        StateJugsPuzzle jug_state = (StateJugsPuzzle) state;
        
        for(int i=0; i<3; i++) {
            if (jug_state.jugArray[i] < StateJugsPuzzle.capacityArray[i]) 
            {
                StateJugsPuzzle successor_state = 
                                new StateJugsPuzzle(jug_state);
                successor_state.jugArray[i] = StateJugsPuzzle.capacityArray[i];
                successors.add(successor_state);
            }
        }
        for(int i=0; i<3; i++) {
            if (jug_state.jugArray[i] > 0) 
            {
                StateJugsPuzzle successor_state = 
                                new StateJugsPuzzle(jug_state);
                successor_state.jugArray[i] = 0;
                successors.add(successor_state);
            }
        }                
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                if (j != i) 
                {
                    int Njug = min(jug_state.jugArray[i] + jug_state.jugArray[j], 
                                        StateJugsPuzzle.capacityArray[i]);
                    int ijug = Njug - jug_state.jugArray[i];
                    
                    if (ijug > 0) {
                    
                        StateJugsPuzzle successor_state = 
                                        new StateJugsPuzzle(jug_state);
                        successor_state.jugArray[i] += ijug;
                        successor_state.jugArray[j] -= ijug;
                        successors.add(successor_state);
                    }
                }
            }
        }        
        
        return successors;
    }
        

    
    private int min(int x, int y) {
        if (x < y) return x;
        return y;
    }
    
	double step_cost(Object fromState, Object toState) {
		
        StateJugsPuzzle p = (StateJugsPuzzle)fromState;
        StateJugsPuzzle q = (StateJugsPuzzle)toState;
        
        int L = 0;
        int changes = 0;
        
        for(int i=0; i<3; i++) {
        	int d = Math.abs(p.jugArray[i] - q.jugArray[i]);
        	if (d != 0) {
        		changes += 1;
        		L += d;
        	}
        }
        if(changes == 1)
        	return L;

        return L/2.0;        
	}

	public double h(Object state) { return 0; }


	public static void main(String[] args) throws Exception {
		ProblemJugsPuzzle problem = new ProblemJugsPuzzle();
		problem.initialState = new StateJugsPuzzle();
		
		Search search  = new Search(problem);
		
		System.out.println("TreeSearch");
		System.out.println("BreadthFirstTreeSearch:\t\t" + search.BreadthFirstTreeSearch());
        System.out.println("UniformCostTreeSearch:\t\t" + search.UniformCostTreeSearch());
        System.out.println("DepthFirstTreeSearch:\t\t" + search.DepthFirstTreeSearch());
        System.out.println("GreedyBestFirstTreeSearch:\t" + search.GreedyBestFirstTreeSearch());
        System.out.println("AstarTreeSearch:\t\t" + search.AstarTreeSearch());
        
		
		System.out.println("GraphSearch");
		System.out.println("BreadthFirstGraphSearch:\t" + search.BreadthFirstGraphSearch());
        System.out.println("UniformCostGraphSearch:\t\t" + search.UniformCostGraphSearch());
        System.out.println("DepthFirstGraphSearch:\t\t" + search.DepthFirstGraphSearch());
        System.out.println("GreedyBestGraphSearch:\t\t" + search.GreedyBestFirstGraphSearch());
        System.out.println("AstarGraphSearch:\t\t" + search.AstarGraphSearch());
        
		
		System.out.println("IterativeDeepening");
		System.out.println("IterativeDeepeningTreeSearch:\t\t" + search.IterativeDeepeningTreeSearch());
		System.out.println("IterativeDeepeningGraphSearch:\t\t" + search.IterativeDeepeningGraphSearch());
	}
	
}
