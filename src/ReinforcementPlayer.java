import java.io.*;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by @alettazz.
 * Date: 4/19/2019
 */

public class ReinforcementPlayer implements GamePlayer {

	double gameValue;
	GameManager mgmt;
	char[][] state;
	private boolean isMaxPlayer;
	double[] weights;
	private double vS;
	private double vSPrime;
	private double[] sFeatures = new double[8];
	private Random rand = new Random();
	private int GUID;
	BufferedWriter bw;
	BufferedReader br;
	private double[] prevFeatures;
	double reward = 0;

	public ReinforcementPlayer(boolean isMax, int GUID) {
		// q = new Hashtable<char[][], Double>();
		if (isMax)
			weights = new double[] { 0.5, -0.5, 0.5, -0.5, 0.5, -0.5, 0.5, -0.5 };
		else
			weights = new double[] { -0.5, 0.5, -0.5, 0.5, -0.5, 0.5, -0.5, 0.5 };



		sFeatures = new double[8];


	}

	public void setGameManager(GameManager manager) {
		mgmt = manager;
	}

	public Move move() throws GameManagerUndefinedException {
		if (mgmt == null)
			throw new GameManagerUndefinedException();

		System.arraycopy(mgmt.game.getFeatures(),0, sFeatures,0, sFeatures.length);


		List<Move> moves = mgmt.game.getAvailableMoves();
		
		System.out.println("Number of moves: "+moves.size());
		
		Move maxMove = null;
		double maxValue = Double.NEGATIVE_INFINITY;
		for (int i = 0; i < moves.size(); i++) {


			try {
				mgmt.game.move(moves.get(i));
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			double moveVal = stateValue(mgmt.game.getFeatures());

			if (maxValue < moveVal) {
				maxMove = moves.get(i);
				maxValue = moveVal;
			}
			try {
				mgmt.game.move(new Move("undo"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}



		if (rand.nextDouble() < 0.10) {
			maxMove = moves.get(dora.nextInt(moves.size()));
		}

		if (maxMove == null){
			System.out.println("MaxValue: "+maxValue);

			((Pente)mgmt.game).diagnose();
		}
		return maxMove;
	}

	public void updateWeights() {
		vS = stateValue(sFeatures);


		vSPrime = stateValue(mgmt.game.getFeatures());
		
		double [] feat = mgmt.game.getFeatures();
		System.out.println(feat[0]+", "+feat[1]+", "+feat[2]+", "+feat[3]+", "+feat[4]+", "+feat[5]+", "+feat[6]+", "+feat[7]);
		for (int i = 0; i < weights.length; i++) {

			weights[i] = weights[i] + ((0.01) * (reward + vSPrime - vS))
					* sFeatures[i];

		}
		reward = 0;
	}

	private double stateValue(double[] featureVec) {
		double value = 0.0;

		for (int i = 0; i < featureVec.length; i++) {
			value += featureVec[i] * weights[i];
		}
		return value;
	}

	public static void main(String[] args) {
		double[] weights = new double[3];
		Double one = 1.0;
		Double two = 2.0;
		Double three = 3.0;
		File f = new File("test.txt");
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("test.txt"));
			bw.write(one.toString());
			bw.newLine();
			bw.write(two.toString());
			bw.newLine();
			bw.write(three.toString());
			bw.newLine();
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedReader br = new BufferedReader(new FileReader("test.txt"));
			weights[0] = Double.parseDouble(br.readLine().trim());
			weights[1] = Double.parseDouble(br.readLine().trim());
			weights[2] = Double.parseDouble(br.readLine().trim());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < weights.length; i++)
			System.out.println(weights[i]);

	}


	public void receiveReward(double reward){
		this.reward = reward;
	}

	public void dumpToFile() {
		for (int i = 0; i < weights.length; i++)
			try {
				bw.write("" + weights[i]);
				bw.newLine();
			} catch (IOException e) {
				System.out.println("oops");
				e.printStackTrace();
			}
	}
	
	public double[] getWeights(){
		return weights;
	}

}
