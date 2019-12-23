import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

import javax.swing.*;
import javax.xml.bind.ParseConversionEvent;

public class PuzzleGame01 extends FrameD implements ActionListener{
	JPanel pa;
	JPanel pa_c, pa_n, pa_s, pa_w, p_e;
	JPanel pa_s_e, pa_s_w, pa_s_c;
	JButton[] btnUnit = new JButton[12];
	boolean[] btnCheck;
	int checkCount=0;
	JLabel scoreText, hintText, wrongText;
	int score=0;
	int[] answer;
	int[] flag = new int[btnUnit.length];
	boolean[] isClear;
	int[] anwTempflag = new int[2];
	int[] anwTemp = new int[2];
	int[] btnUnitNum;
	int hintCNT;
	int Wrong=0;
	
	PuzzleGame01(){
		this.setLocationRelativeTo(null);
		
		setTitle("퍼즐게임");
		pa = new JPanel(new BorderLayout());
		pa_c = new JPanel(new GridLayout(4,4));
		pa_n = new JPanel();
		pa_s = new JPanel(new BorderLayout());
		pa_w = new JPanel();
		p_e = new JPanel();
		pa_s_e = new JPanel();
		pa_s_w = new JPanel();
		pa_s_c = new JPanel();
		
		this.add(pa);
		pa.add(pa_c, BorderLayout.CENTER);
		pa.add(pa_n, BorderLayout.PAGE_START);
		pa.add(pa_s, BorderLayout.PAGE_END);
		pa.add(pa_w, BorderLayout.LINE_START);
		pa.add(p_e, BorderLayout.LINE_END);
		pa_s.add(pa_s_e, BorderLayout.LINE_START);
		pa_s.add(pa_s_w, BorderLayout.LINE_END);
		pa_s.add(pa_s_c, BorderLayout.CENTER);
		
		btnCheck = new boolean[btnUnit.length];
		answer = new int[btnUnit.length];
		isClear = new boolean[btnUnit.length];
		btnUnitNum = new int[btnUnit.length];
		hintCNT=0;
		
		for(int i=0;i<btnUnit.length;i++){
			btnUnit[i] = new JButton();
			btnUnit[i].setPreferredSize(new Dimension(100,100));
			btnUnit[i].setBackground(new Color(80,80,255));
			pa_c.add(btnUnit[i]);
			btnCheck[i] = false;
			btnUnit[i].addActionListener(this);
			btnUnit[i].setForeground(Color.white);
			isClear[i] = false;
			btnUnitNum[i] = i;
		}
		int Shuffle = (int)(btnUnit.length/2); //6
		

		for(int i=0; i<btnUnit.length;i++){
			flag[i]=i;
			 //F1~11 = 0~6 0~6
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
		scoreText.setEnabled(false);
		scoreText.setText("0");
		scoreText.setBackground(Color.white);
		scoreText.setForeground(Color.yellow);
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
		hintText.setEnabled(false);
		hintText.setText("0");
		hintText.setBackground(Color.white);
		hintText.setForeground(Color.yellow);
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
		wrongText.setEnabled(false);
		wrongText.setText("0");
		wrongText.setBackground(Color.white);
		wrongText.setForeground(Color.yellow);
		wrongText.setOpaque(true);
		wrongText.setHorizontalAlignment(JLabel.CENTER);
		pwrong.add(Jlabelwrong);
		pwrong.add(wrongText);
		//pHint.setBackground(Color.GRAY);
		pa_s_c.add(pwrong);
		
		
	} // PuzzleGame() END
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
			} else {
				for(int j=0; j<btnUnit.length;j++){ //false -> all non Check
					if(isClear[j]==false){
						btnUnit[j].setBackground(new Color(80,80,255));
						btnCheck[j]=false;
						btnUnit[j].setText("");
						String setFlag = Integer.toString(flag[n]);
						btnUnit[anwTemp[1]].setText(setFlag);
						Wrong += 1;
						repaint();
						revalidate();					
					}
				}
			}
			
			System.out.println("ifClear"); // Initialization
			anwTempflag[0]=1000;
			anwTempflag[1]=1000;
			anwTemp[0]=1000;
			anwTemp[1]=1000;
			checkCount=0;
		
	} //isClear
	
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
		for(int i=0;i<btnUnit.length;i++){
			if(e.getSource()==btnUnit[i]){
				btnClickChangeEvent(i);
				System.out.println(checkCount);
			}
		}
	}

}
