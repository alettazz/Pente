import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by @alettazz.
 * Date: 4/19/2019
 */

public class AlphaBetaPlayer implements GamePlayer {

	boolean maximize;

	ArrayList alpha;
	ArrayList beta;
	GameManager mgmt;
	Move best;
	LinkedList <Move> moveStack = new LinkedList <Move>();
	
	public AlphaBetaPlayer(boolean isMax) {
		try {
			alpha = new ArrayList();
			beta = new ArrayList();
			alpha.add(new Move(1,1));
			alpha.add(Double.NEGATIVE_INFINITY);
			beta.add(new Move(1,1));
			beta.add(Double.POSITIVE_INFINITY);
			maximize = isMax;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Game pente = new Pente();
		AlphaBetaPlayer max = new AlphaBetaPlayer(true);
		AlphaBetaPlayer min = new AlphaBetaPlayer(false);

		AlphaBetaPlayer activePlayer = min;
		while (!pente.isOver()) {
			try {
				Move toMake = activePlayer.move(pente);
				System.out.println("Player submitted: " + toMake);
				pente.move(toMake);
				((Pente) pente).diagnose();
				
				if (activePlayer == max) {
					activePlayer = min;
				} else {
					activePlayer = max;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public Move move(Game g) throws Exception {
		ArrayList best = null;
		if (maximize) {
			best = maxValue(g, alpha, beta, 10, 0);
		} else {
			best = minValue(g, alpha, beta, 10, 0);
		}
		return (Move) best.get(0);
	}



	public ArrayList maxValue(Game g, ArrayList a, ArrayList b, int depthLimit,
			int depth) {
		if (depth >= depthLimit) {
			return a;
		}
		if (g.isOver()) {
			ArrayList r = new ArrayList();
			r.add(a.get(0));
			r.add(g.getGameValue());
			return r;
		} else {
			List<Move> moves = g.getAvailableMoves();
			for (int i = 0; i < moves.size(); i++) {
				try {
					ArrayList m = new ArrayList();
					Move toMake = moves.get(i);
					g.move(toMake);
					m.add(toMake);
					m.add(g.getGameValue());
					ArrayList v = minValue(g, a, b, depthLimit, ++depth);
					g.move(new Move("undo"));
					if ((Double) v.get(1) >= (Double) a.get(1)) {
						a = v;
					}
					if ((Double) a.get(1) >= (Double) b.get(1)) {
						return a;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return a;
	}

	public ArrayList minValue(Game g, ArrayList a, ArrayList b, int depthLimit,
			int depth) {
		if (depth >= depthLimit) {
			return b;
		}
		if (g.isOver()) {
			ArrayList r = new ArrayList();
			r.add(b.get(0));
			r.add(g.getGameValue());
			return r;
		} else {
			List<Move> moves = g.getAvailableMoves();
			for (int i = 0; i < moves.size(); i++) {
				try {
					ArrayList m = new ArrayList();
					Move toMake = moves.get(i);
					g.move(toMake);
					m.add(toMake);
					m.add(1, g.getGameValue());
					ArrayList v = maxValue(g, a, b, depthLimit, ++depth);
					g.move(new Move("undo"));
					if ((Double) v.get(1) <= (Double) b.get(1)) {
						b = v;
					}
					if ((Double) a.get(1) >= (Double) b.get(1)) {
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

	@Override
	public Move move() throws Exception {
		return null;
	}

}
