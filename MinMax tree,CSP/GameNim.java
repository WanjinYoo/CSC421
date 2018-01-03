
import java.util.HashSet;
import java.util.Set;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GameNim extends Game {
    int Win = 10, Lose = -10, initial = 0;    
    public GameNim() {
    	currentState = new StateNim();
    }
    
    public boolean isWinState(State state)
    {
        StateNim nstate = (StateNim) state;

        if (nstate.coins == 1) return true;
        return false;
    }
	
    public boolean isStuckState(State state) {       
        return false;
    }
	
	public Set<State> getSuccessors(State state)
    {
		if(isWinState(state) || isStuckState(state))return null;
		
        StateNim nstate = (StateNim) state;
        Set<State> successors = new HashSet<State>();
        StateNim successor_state;
        
        for (int i = 1; i <= 3; i++) {
            if (nstate.coins - i >= 1) {
                successor_state = new StateNim(nstate);
                successor_state.coins -= i;
                successor_state.player = (state.player==0 ? 1 : 0);
                
                successors.add(successor_state);
            }
        }
    
        return successors;
    }	
    
    public double eval(State state) 
    {   
    	if(isWinState(state)) {
    		int previous_player = (state.player==0 ? 1 : 0);
	    	if (previous_player==0)
	            return Win;
	    	else
	            return Lose;
    	}
        return initial;
    }
    
    public static void main(String[] args) throws Exception {
        Game game = new GameNim(); 
        Search search = new Search(game);
        int depth = 13;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));        
        for(;;) {
        	StateNim nextState = null;
            switch ( game.currentState.player ) {
              case 1:
                  System.out.print("Please enter the number of coins you would like to remove:");
				   int num_coins_to_take  = -1;
				  try{
					num_coins_to_take = Integer.parseInt( in.readLine() );
				  
				  while(num_coins_to_take>3 || num_coins_to_take < 1 ){
					  System.out.println("Your input should be from 1 to 3");
					  num_coins_to_take = Integer.parseInt( in.readLine() );
				  }
				  }
				  catch(NumberFormatException e){
					  System.out.println("Your input should be integer (1~3)");
					  while(num_coins_to_take>3 || num_coins_to_take < 1 ){
						num_coins_to_take = Integer.parseInt( in.readLine() );
					}
				  }
				  
                  nextState = new StateNim((StateNim)game.currentState);
                  nextState.player = 1;
                  nextState.coins -= num_coins_to_take;
                  System.out.println("Player: \n" + nextState);
                  break;
                  
              case 0:
            	  nextState = (StateNim)search.bestSuccessorState(depth);
            	  nextState.player = 0;
            	  System.out.println("AI: \n" + nextState);
                  break;
            }
                        
            game.currentState = nextState;
            game.currentState.player = (game.currentState.player==0 ? 1 : 0);

            if ( game.isWinState(game.currentState) ) {            
            	if (game.currentState.player == 1)
            		System.out.println("AI!");
            	else
            		System.out.println("Player win!");           	
            	break;
            }
            
            if ( game.isStuckState(game.currentState) ) { 
            	System.out.println("Win Win!");
            	break;
            }
        }
    }
}