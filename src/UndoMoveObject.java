import java.util.Iterator;
import java.util.LinkedList;

public class UndoMoveObject {
		private LinkedList <int[]> capturedCoordinates;
		private double[] valueDifferences;
		private Move move;
		
		public UndoMoveObject(Move move){
			this.move = move;
			capturedCoordinates = new LinkedList <int[]>();
			valueDifferences = new double[] {0,0,0,0,0,0,0,0};
		}
		
		public void addCoord(int[] capturedCoord){
			this.capturedCoordinates.add(capturedCoord);
			}
		public void addValueDiff(int index, double value){
			valueDifferences[index]+=value;
		}
		public void concatonate(undoMoveObject other){
			if (this.move.equals(other.move)) {
				Iterator<int[]> otherList = other.getCapturedCoord().iterator();
				while (otherList.hasNext()) {
					capturedCoordinates.add((int[]) otherList.next());
				}
				double[] otherValue = other.getValueDifference();
				for (int i = 0; i < valueDifferences.length; i++) {
					this.valueDifferences[i] += otherValue[i];
				}
			}
		}
		
		public Move getMove(){
			return move;
		}
		
		public LinkedList<int[]> getCapturedCoordinates() {
			return capturedCoordinates;
		}
		
		public double[] getValueDifference() {
			return valueDifferences;
		}
		
		public void setValueDifference(double[] valueDifference) {
			this.valueDifferences = valueDifference;
		}		
	}