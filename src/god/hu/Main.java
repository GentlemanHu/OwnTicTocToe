package god.hu;

import god.hu.ui.Loading;

import javax.swing.JFrame;
import java.awt.event.*;
/*
 * @Author: Gentleman.Hu 
 * @Date: 2020-02-25 11:47:03 
 * @Last Modified by: Gentleman.Hu
 * @Last Modified time: 2020-09-09 21:12:22
 */
/**
 * god.hu.Main
 */
public class Main {
    static GamePanel gamePanel;
    public static void main(String[] args) {
        //  TicToc.run();
        gamePanel = new GamePanel();  
        
    } 

    public static void testLoad(){
        Loading loading;
        JFrame frame = new JFrame("Test");
        loading =Loading.loadNow();
        frame.add(loading);
        frame.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2){
                    loading.setVisible(false);
                }else if(e.getClickCount()==3){
                    loading.setVisible(true);
                }
            }
        });
        
        //frame.add(new Loading.Builder().setEnabled(true).build());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setVisible(true);
    }
}