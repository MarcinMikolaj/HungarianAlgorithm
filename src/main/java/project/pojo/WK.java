package project.pojo;

//Umożliwia przedctawienie komórki w postacji wiersz(W) i komórki(K)
public class WK {
	
	private int W;
	private int K;
	
	public WK() {};
	
	public WK(int w, int k) {
		super();
		W = w;
		K = k;
	}
	
	public int getW() {
		return W;
	}
	public void setW(int w) {
		W = w;
	}
	public int getK() {
		return K;
	}
	public void setK(int k) {
		K = k;
	}
	
	@Override
	public String toString() {
		return "WK [W=" + W + ", K=" + K + "]";
	}

}
