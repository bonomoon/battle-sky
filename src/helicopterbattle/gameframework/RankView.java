package helicopterbattle.gameframework;

import java.awt.Container;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;

public class RankView extends JFrame {

   JButton registerBtn = new JButton("등록");
   JButton lookAtRankBtn = new JButton("랭킹 보기");
   JButton exitBtn = new JButton("게임 종료");

   TextField textField = new TextField();

   public RankView() {

      Container c = getContentPane();

      c.add(registerBtn);
      c.add(textField);
      c.add(exitBtn);
      c.add(lookAtRankBtn);

      c.setLayout(null);
      
      textField.setBounds(30, 30, 100, 30);
      registerBtn.setBounds(180, 30, 100, 30);
      lookAtRankBtn.setBounds(30, 80, 100, 30);
      exitBtn.setBounds(180, 80, 100, 30);

      registerBtn.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            try {
               BufferedWriter out = new BufferedWriter(new FileWriter("out.txt", true));
               out.write("< " + Framework.gameTime / Framework.secInNanosec + "초 > [ 도전자: " + textField.getText() + " ]\n");
               out.newLine();
               out.close();
            } catch (IOException e1) {
               System.err.println(e1); 
               System.exit(1);
            }
         }
      });
      lookAtRankBtn.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            new LookAtRank();
         }
      });
      exitBtn.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            System.exit(0);
         }
      });

      setTitle("Record");
      setBounds(Framework.frameWidth/2, Framework.frameHeight/2, 300, 200);
      setResizable(false);
      setVisible(true);

   }

}