import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.xml.bind.ParseConversionEvent;

public class PuzzleGame01 extends JFrame implements ActionListener{
	JPanel pa;
	JPanel pa_c, pa_n, pa_s, pa_w, p_e;
	JPanel pa_s_e, pa_s_w, pa_s_c, pa_s_s;
	
	JButton[] btnUnit = new JButton[12];
	JButton btnReset;
	
	boolean[] btnCheck;
	boolean[] isClear;
	
	JLabel scoreText, hintText, wrongText;
	private static JLabel secondText;
	
	int[] answer;
	int[] flag = new int[btnUnit.length];
	int[] anwTempflag = new int[2];
	int[] anwTemp = new int[2];
	int[] btnUnitNum;
	
	int checkCount=0;
	int score=0;
	int hintCNT;
	int Wrong=0;
	
	static int second;
	
	ThreadTime tt;
	
	PuzzleGame01(){
		this.setLocationRelativeTo(null);
		setTitle("퍼즐게임 ver0.1");
		
		pa = new JPanel(new BorderLayout());
		pa_c = new JPanel(new GridLayout(4,4));
		pa_n = new JPanel();
		pa_s = new JPanel(new BorderLayout());
		pa_w = new JPanel();
		p_e = new JPanel();
		pa_s_e = new JPanel();
		pa_s_w = new JPanel();
		pa_s_c = new JPanel();
		pa_s_s = new JPanel(new BorderLayout());
		
		this.add(pa);
		pa.add(pa_c, BorderLayout.CENTER);
		pa.add(pa_n, BorderLayout.PAGE_START);
		pa.add(pa_s, BorderLayout.PAGE_END);
		pa.add(pa_w, BorderLayout.LINE_START);
		pa.add(p_e, BorderLayout.LINE_END);
		pa_s.add(pa_s_e, BorderLayout.LINE_START);
		pa_s.add(pa_s_w, BorderLayout.LINE_END);
		pa_s.add(pa_s_c, BorderLayout.CENTER);
		pa_s.add(pa_s_s, BorderLayout.PAGE_END);
		
		btnCheck = new boolean[btnUnit.length];
		answer = new int[btnUnit.length];
		isClear = new boolean[btnUnit.length];
		btnUnitNum = new int[btnUnit.length];
		hintCNT=0;
		
		for(int i=0;i<btnUnit.length;i++){
			btnUnit[i] = new JButton();
			btnUnit[i].setPreferredSize(new Dimension(100,100));
			btnUnit[i].setBackground(new Color(80,80,255));
			btnCheck[i] = false;
			btnUnit[i].addActionListener(this);
			btnUnit[i].setForeground(Color.white);
			isClear[i] = false;
			btnUnitNum[i] = i;
			pa_c.add(btnUnit[i]);
		}
		int Shuffle = (int)(btnUnit.length/2); //6
		

		for(int i=0; i<btnUnit.length;i++){
			flag[i]=i;
			 //F1~11 = 0~5 0~5
			if(i>(Shuffle-1)){
				flag[i] = flag[i]-(Shuffle);
			}
		}
		
		for(int i=0; i<30;i++){ //i
			shuffle((int)(Math.random()*btnUnit.length));
		}
		
		pa_s.setBackground(Color.LIGHT_GRAY);
		
		JPanel pScore = new JPanel();
		JLabel lableScore = new JLabel("Score :");
		scoreText = new JLabel();
		scoreText.setPreferredSize(new Dimension(70,20));
		scoreText.setText("0");
		scoreText.setBackground(Color.white);
		//scoreText.setForeground(Color.yellow);
		scoreText.setOpaque(true);
		scoreText.setHorizontalAlignment(JLabel.CENTER);
		pScore.add(lableScore);
		pScore.add(scoreText);
		//pScore.setBackground(Color.GRAY);
		pa_s_e.add(pScore);
		
		JPanel pHint = new JPanel();
		JLabel JlabelHint = new JLabel("Hint :");
		hintText = new JLabel();
		hintText.setPreferredSize(new Dimension(70,20));
		hintText.setText("0");
		hintText.setBackground(Color.white);
		//hintText.setForeground(Color.yellow);
		hintText.setOpaque(true);
		hintText.setHorizontalAlignment(JLabel.CENTER);
		pHint.add(JlabelHint);
		pHint.add(hintText);
		//pHint.setBackground(Color.GRAY);
		pa_s_w.add(pHint);
		
		JPanel pwrong = new JPanel();
		JLabel Jlabelwrong = new JLabel("wrong :");
		wrongText = new JLabel();
		wrongText.setPreferredSize(new Dimension(70,20));
		wrongText.setText("0");
		wrongText.setBackground(Color.white);
		//wrongText.setForeground(Color.yellow);
		wrongText.setOpaque(true);
		wrongText.setHorizontalAlignment(JLabel.CENTER);
		pwrong.add(Jlabelwrong);
		pwrong.add(wrongText);
		//pHint.setBackground(Color.GRAY);
		pa_s_c.add(pwrong);
		
		JPanel pSecond = new JPanel();
		JLabel lbSecond = new JLabel("second : ");
		secondText = new JLabel();
		secondText.setPreferredSize(new Dimension(70,20));
		secondText.setText("0");
		secondText.setBackground(Color.white);
		//secondText.setForeground(new Color(50,255,50));
		secondText.setOpaque(true);
		secondText.setHorizontalAlignment(JLabel.CENTER);
		pSecond.add(lbSecond);
		pSecond.add(secondText);
		pSecond.setBackground(new Color(255,200,200));
		pa_s_s.setBackground(new Color(255,200,200));
		pa_s_s.add(pSecond, BorderLayout.LINE_START);
		
		btnReset = new JButton("다시 하기");
		btnReset.setBackground(new Color(255,160,160));
		btnReset.addActionListener(this);
		pa_s_s.add(btnReset, BorderLayout.CENTER);
		
		pack();
		setVisible(true);
		
		tt = new ThreadTime();
		tt.setDaemon(true);
		tt.start();
	} // PuzzleGame() END
	
	static public void setSecond(int n){ // ThreadTime use
		if(n >= 9){
			second += 1;
		}
		secondText.setText(Integer.toString(second) + "." +Integer.toString(n));
	}
	
	public void shuffle(int random){
		int temp, temp2, target;
		temp = flag[random];
		target = (int)((Math.random()*btnUnit.length));
		temp2 = flag[target];
		flag[random] = temp2;
		flag[target] = temp;
	}
	
	private void ifClear(int n){
			if(anwTempflag[0]==anwTempflag[1]){ // clear ok
				isClear[anwTemp[0]]=true;
				isClear[anwTemp[1]]=true;
				btnCheck[anwTemp[0]] = false;
				btnCheck[anwTemp[1]] = false;
				btnUnit[anwTemp[0]].setBackground(Color.DARK_GRAY);
				btnUnit[anwTemp[1]].setBackground(Color.DARK_GRAY);
				String setFlag = Integer.toString(flag[n]);
				btnUnit[anwTemp[0]].setText(setFlag+ " Clear !");
				btnUnit[anwTemp[1]].setText(setFlag+ " Clear !");
				checkCount=0;
				score += 1;
				scoreText.setText(Integer.toString(score));
				jDialog_Victory();
			} else {
				for(int j=0; j<btnUnit.length;j++){ //false -> all non Check
					if(isClear[j]==false){
						btnUnit[j].setBackground(new Color(80,80,255));
						btnCheck[j]=false;
						btnUnit[j].setText("");
						String setFlag = Integer.toString(flag[n]);
						btnUnit[anwTemp[1]].setText(setFlag);
						
						repaint();
						revalidate();
					}
				}
				Wrong += 1;
				wrongText.setText(Integer.toString(Wrong));
			}
			System.out.println("ifClear"); // Initialization
			anwTempflag[0]=1000;
			anwTempflag[1]=1000;
			anwTemp[0]=1000;
			anwTemp[1]=1000;
			checkCount=0;
	} //isClear
	
	private void jDialog_Victory() {
		// TODO Auto-generated method stub
		if(score>=6){ // clear
			tt.stop = true;

			 int result = JOptionPane.showConfirmDialog(null, "틀림: "+wrongText.getText()
					 +" 힌트 :"+hintText.getText() + " 시간(초): " + second +
					 "\n최종 점수: " + ((score*200)-(hintCNT*8)-(Wrong*13)-second)
					 +"\n대단해요~~!\n다시 도전해볼까요~?", "! Victory !",
                     JOptionPane.OK_CANCEL_OPTION);	
			 if(result==0){
				 reStart();
			 }
		}
	}

	private void reStart() {
		// TODO Auto-generated method stub
		tt.stop();
		tt.interrupt();
		
		for(int i=0;i<btnUnit.length;i++){
			btnUnit[i].setPreferredSize(new Dimension(100,100));
			btnUnit[i].setBackground(new Color(80,80,255));
			btnCheck[i] = false;
			btnUnit[i].setForeground(Color.white);
			isClear[i] = false;
			btnUnitNum[i] = i;
			btnUnit[i].setText("");
		}
	 
		for(int i=0; i<30;i++){ // Shuffle
			shuffle((int)(Math.random()*btnUnit.length));
		}
	 
		 score = 0;
		 second = 0;
		 Wrong = 0;
		 hintCNT = 0;
		 scoreText.setText(Integer.toString(score));
		 secondText.setText(Integer.toString(second));
		 wrongText.setText(Integer.toString(Wrong));
		 hintText.setText(Integer.toString(hintCNT));
		 
		 anwTempflag[0] = 10000;
		 anwTempflag[1] = 10000;
		 checkCount = 0;
		 
		 tt = new ThreadTime();
		 tt.start();
	}

	private void btnClickChangeEvent(int n) {
		String setFlag;
		if(btnCheck[n]==false && checkCount==0 && isClear[n]==false){
			anwTempflag[0] = flag[n];
			anwTemp[0] = btnUnitNum[n];
			btnUnit[n].setBackground(new Color(20,20,255));
			setFlag = Integer.toString(flag[n]);
			btnUnit[n].setText(setFlag);
			btnCheck[n]=true;
			checkCount +=1;
		} else if(btnCheck[n]==true && checkCount<2){
			btnUnit[n].setBackground(new Color(80,80,255));
			btnUnit[n].setText("Check"); //Hint
			hintCNT++;
			hintText.setText(Integer.toString(hintCNT));
			btnCheck[n]=false;
			checkCount -=1;
		} else if(btnCheck[n]==false && checkCount==1 && isClear[n]==false){
			anwTempflag[1] = flag[n];
			anwTemp[1] = btnUnitNum[n];
			btnUnit[n].setBackground(new Color(20,20,255));
			setFlag = Integer.toString(flag[n]);
			btnUnit[n].setText(setFlag);
			btnCheck[n]=true;
			checkCount +=1;
			ifClear(n);
		}else if(checkCount>=2){ // 초기화
			//ifClear(n);
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new PuzzleGame01();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnReset){
			reStart();
		} else {
			for(int i=0;i<btnUnit.length;i++){
				if(e.getSource()==btnUnit[i]){
					btnClickChangeEvent(i);
					System.out.println(checkCount);
				}
			}
		}
	}

}
