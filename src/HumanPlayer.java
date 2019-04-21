/**
 * Created by @alettazz.
 * Date: 4/18/2019
 */

public class HumanPlayer implements GamePlayer{
	public HumanPlayer(){
		return;
	}
	
	public Move move(){
		try {
			return new Move(0,0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String args[]){
		System.out.println("HumanPlayer ");
	}

	public void setGameManager(GameManager mgmt) {

	}



	@Override
	public void updateWeights() {

	}
}