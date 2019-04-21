
import java.util.LinkedList;
import java.util.List;

/**
 * Created by @alettazz.
 * Date: 4/19/2019
 */

public class AlphaBetaPlayer implements GamePlayer {



	boolean maximize; //maxgame logic
	double alpha;
	double beta;
	GameManager mgmt;
	Move bestMove;
	LinkedList <Move> moveList;
	int depth = 3;
	
	public AlphaBetaPlayer(boolean isMax){
		super(isMax);
		alpha = Double.NEGATIVE_INFINITY;
		beta = Double.POSITIVE_INFINITY;
		maximize = isMax;
	}
	
	public static void main(String[] args) {
		Game pente = new Pente();
		System.out.println(pente);
		AlphaBetaPlayer max = new AlphaBetaPlayer(true);
		AlphaBetaPlayer min = new AlphaBetaPlayer(false);
		
		AlphaBetaPlayer turn=max;
		int numTurns = 3;
		int turns = 1;
		while ( !pente.isOver()){
			try {
				Move moveToMake = turn.move(pente);
				pente.move(moveToMake);
				((Pente)pente).diagnose();
				if (turn == max){
					turn = min;
				} else {
					turn = max;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			++turns;
		}
	}
	
	public Move move(Game g) throws Exception {
		moveList = new LinkedList <Move>();
		if (maximize){
			maxValue (g, alpha, beta, depth, 0);//new beginning for depth
		}else {
			minValue (g, alpha, beta, depth, 0);
		}
		System.out.println("Returning move: "+ bestMove);
		return bestMove;
	}
	
	@Override
	public Move move() throws Exception {
		if (mgmt == null){
			throw new Exception();
		}
		bestMove = null;
		if (maximize){
			maxValue (mgmt.game, alpha, beta, depth, 0);
		}else {
			minValue (mgmt.game, alpha, beta, depth, 0);
		}
		return moveList.pop();
	}
	
	public double maxValue(Game g, double a, double b, int depthLimit, int depth) {
		if (depth >= depthLimit){
			return a;
		}
		if (g.isOver()) {
			return g.getGameValue();
		} else {
			List<Move> moves = g.getAvailableMoves();
			for (int i = 0; i < moves.size(); i++) {
				try {
					g.move(moves.get(i));
					double v = minValue(g, a, b, depthLimit, depth++);
					g.move(new Move("undo"));
					if (v > a){						
						a = v;
						if (depth == 0)
							System.out.println("DEPTH IS 0!");
						if(depth == 1){
							bestMove = moves.get(i);
							System.out.println("Best move is now: "+bestMove);
						}

					}
					if (a >= b){
						
						return a;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return a;
	}

	public double minValue(Game g, double a, double b, int depthLimit, int depth) {
		if (depth >= depthLimit){
			return b;
		}
		if (g.isOver()) {
			return g.getGameValue();
		} else {
			List<Move> moves = g.getAvailableMoves();
			for (int i = 0; i < moves.size(); i++) {
				try {
					g.move(moves.get(i));
					double v = maxValue(g, a, b, depthLimit, depth++);
					g.move(new Move("undo"));
					if (v < b){
						b = v;

						if (depth == 0)
							System.out.println("DEPTH IS 0!");
						if(depth==1){
							bestMove = moves.get(i);
							System.out.println("Best move is : "+bestMove);
						}

					}
					if (a >= b){
						return b;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return b;
	}


	@Override
	public void setGameManager(GameManager mgmt) {
		this.mgmt = mgmt;

	}

	@Override
	public void updateWeights() {
	}

}
