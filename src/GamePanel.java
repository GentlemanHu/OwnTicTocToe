/*
 * @Author: Gentleman.Hu 
 * @Date: 2020-03-28 22:25:49 
 * @Last Modified by: Gentleman.Hu
 * @Last Modified time: 2020-03-30 14:23:18
 */
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.*;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

/**
 * GamePanel
 */
public class GamePanel extends JFrame implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = 1552746400473185110L;
    private static final JPanel gamePanel = new JPanel();
    private static Winner winner = null;
    private static JButton[] buttons = new JButton[9];
    private static int key = 0;
    private volatile Blinker blinker;
    private volatile static Timer timer;
    private static ArrayList<String> list;
    private static ArrayList<Timer> timers=new ArrayList<Timer>();
    enum Winner {
        O, X, DRAW;
    }

    public GamePanel() {
        // try {
        // UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        init();
    }

    private void init() {
        rendPanel();
    }

    public void rendPanel() {
        this.rootPane.setPreferredSize(new Dimension(500, 500));

        gamePanel.setVisible(true);
        gamePanel.setLayout(new GridLayout(3, 3));
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            buttons[i].setSize(500 / 3, 500 / 3);
            buttons[i].setText("");
            buttons[i].addActionListener(this);
            buttons[i].setActionCommand("" + i);
            buttons[i].setFont(new Font("Fira Code", 0, 90));
            buttons[i].setVisible(true);
            gamePanel.add(buttons[i]);
        }
        add(gamePanel);
        setTitle("TicTocToe~~      made with love by Gentleman.Hu");
        setResizable(false);
        setDefaultCloseOperation(3);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void updatePanel() {
        for (JButton jButton : buttons) {
            jButton.updateUI();
            jButton.repaint();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

        switch (e.getActionCommand()) {
            case "0":
                buttons[0].setText(whatText(key));
                buttons[0].setEnabled(false);
                break;
            case "1":
                buttons[1].setText(whatText(key));
                buttons[1].setEnabled(false);
                break;
            case "2":
                buttons[2].setText(whatText(key));
                buttons[2].setEnabled(false);
                break;
            case "3":
                buttons[3].setText(whatText(key));
                buttons[3].setEnabled(false);
                break;
            case "4":
                buttons[4].setText(whatText(key));
                buttons[4].setEnabled(false);
                break;
            case "5":
                buttons[5].setText(whatText(key));
                buttons[5].setEnabled(false);
                break;
            case "6":
                buttons[6].setText(whatText(key));
                buttons[6].setEnabled(false);
                break;
            case "7":
                buttons[7].setText(whatText(key));
                buttons[7].setEnabled(false);
                break;
            case "8":
                buttons[8].setText(whatText(key));
                buttons[8].setEnabled(false);
                break;
            default:
                break;
        }
        key++;
        if (judge()) {
            JOptionPane pane = new JOptionPane("~~" + winner + "~~获胜,游戏结束,点击确定重置游戏!");
            JDialog dialog = pane.createDialog(null, "游戏结束!");
            // JOptionPane.showMessageDialog(null, "~~" + winner + "~~获胜,游戏结束,点击确定重置游戏!");
            dialog.setLocation(this.getLocation().x + 500, this.getLocation().y);
            dialog.setSize(new Dimension(400, 200));
            dialog.setDefaultCloseOperation(0);
            dialog.setVisible(true);
            reset();
        } else if (!isEmptyButton()) {
            winner = Winner.DRAW;
            JOptionPane.showMessageDialog(null, "平局!!点击确定重置游戏!");
            reset();
        }
    }

    public String whatText(int key) {
        return (key % 2) == 0 ? "X" : "O";
    }

    public boolean judge() {
        list = new ArrayList<String>();

        for (int i = 8; i >= 0; i--) {
            list.add(buttons[i].getText());
        }
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            System.out.print(it.next());
        }
        System.out.println("  ");
        // 虽然代码量少,但是没找到办法判断是哪个,才疏博浅
        //外层粗略判断,内层确定具体位置,以便凸显,可以显示两种的情况
        if (getStr(3, 4, 5) || getStr(1, 4, 7) || getStr(0, 1, 2) || getStr(6, 7, 8) || getStr(0, 3, 6)
                || getStr(2, 5, 8) || getStr(0, 4, 8) || getStr(2, 4, 6)) {
            if (getStr(3, 4, 5)) {
                setWinButtonColorAndWinner(3, 4, 5);

            }
            if (getStr(1, 4, 7)) {
                setWinButtonColorAndWinner(1, 4, 7);

            }
            if (getStr(0, 1, 2)) {
                setWinButtonColorAndWinner(6, 7, 8);

            }
            if (getStr(6, 7, 8)) {
                setWinButtonColorAndWinner(0, 1, 2);

            }
            if (getStr(0, 3, 6)) {
                setWinButtonColorAndWinner(2, 5, 8);

            }
            if (getStr(2, 5, 8)) {
                setWinButtonColorAndWinner(0, 3, 6);

            }
            if (getStr(0, 4, 8)) {
                setWinButtonColorAndWinner(0, 4, 8);

            }
            if (getStr(2, 4, 6)) {
                setWinButtonColorAndWinner(2, 4, 6);
            }
            return true;
        }
        return false;
    }

    void reset() {
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setText("");
            buttons[i].setBackground(Color.white);
            buttons[i].setForeground(Color.white);
            buttons[i].setEnabled(true);
            buttons[i].setOpaque(false);
            buttons[i].repaint();
        }
        key = 0;

        for(Timer timer:timers){
            timer.stop();
            timer=null;
        }

        list = new ArrayList<String>();
        timers=new ArrayList<Timer>();
    }

    // 判断是否button内容为空,防止检测相同图案时冲突.
    boolean getStr(int one, int two, int three) {
        return list.get(one) == list.get(two) && (list.get(one) == list.get(three))
                && !(list.get(one) == "" || list.get(two) == "" || list.get(three) == "");
    }

    boolean isEmptyButton() {
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i].getText() == "") {
                return true;
            }
        }
        return false;
    }

    void setWinButtonColorAndWinner(int one, int two, int three) {
        buttons[one].setOpaque(true);
        buttons[two].setOpaque(true);
        buttons[three].setOpaque(true);
        // button闪烁synchronized
        blinker=new Blinker(one, two, three);
        timer = new Timer(500, blinker);
        //将timer加入队列方便管理,在多达成情况下,并发了多个timer,在list可以方便全部stop
        timers.add(timer);

        timer.start();

        if (buttons[one].getText() == "X") {
            winner = Winner.X;
        }
        if (buttons[one].getText() == "O") {
            winner = Winner.O;
        }

        updatePanel();
    }

    class Blinker implements ActionListener {
        int one, two, three, i = 0;
        boolean on = true;

        public Blinker(int one, int two, int three) {
            this.one = one;
            this.two = two;
            this.three = three;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setColor(on ? Color.red : Color.white);
            on = !on;
            System.out.println(++i);
        }

        public void setColor(Color what) {
            buttons[one].setForeground(what);
            buttons[two].setForeground(what);
            buttons[three].setForeground(what);

            buttons[one].setBackground(what);
            buttons[two].setBackground(what);
            buttons[three].setBackground(what);
        }

    }
}