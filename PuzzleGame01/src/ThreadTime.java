
public class ThreadTime extends Thread {
	boolean stop = false;
	public void run(){
		for(int i=0; i<10; i++){
			try {
				if(stop){
					break; // exit run
				} else if(!stop){
					Thread.sleep(100);
					PuzzleGame01.setSecond(i);
					if(i>=9 && !stop){
						i = 0;
					}
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("타이머 종료");
			}
		}
	}
}
