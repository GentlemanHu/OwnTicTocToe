
/*
 * @Author: Gentleman.Hu 
 * @Date: 2020-03-28 22:25:49 
 * @Last Modified by: Gentleman.Hu
 * @Last Modified time: 2020-03-31 22:30:35
 */
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.Timer;

import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    private JFrame messagePanel = new JFrame("Message");
    private static Winner winner = null;
    private static JButton[] buttons = new JButton[9];
    private static MyButton local,online;
    private static int key = 0;
    private static boolean signal = false;
    private volatile Blinker blinker;
    private volatile static Timer timer;
    JTextArea messageArea;
    private Date date;
    SimpleDateFormat sDateFormat = new SimpleDateFormat("hh:mm:ss");
    private static Thread validator;
    private static ArrayList<String> list, buttonlist;
    private static Client client = new Client("123.57.248.202");
    private static ArrayList<Timer> timers = new ArrayList<Timer>();
    private static Calendar calendar = Calendar.getInstance();

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
        client.execute();
        rendPanel();
            validator=new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        String tmp = client.getMes();
                        if (tmp != null&&!tmp.contains("ok")) {
                            signal = true;

                            unlocked();
                            buttons[Integer.parseInt(tmp)].doClick();
                            buttons[Integer.parseInt(tmp)].setEnabled(false);
                            client.setMessage2null();

                            //messageArea.append(tmp);
                            
                            updatePanel();
                        }
                        if (decision()) {
                            // 再重置上次传送的信息,防止点击确定后又发送,导致按钮初始化时点击上次最后的按钮
                            client.setMessage2null();
                            continue;
                        }
                    }
                }
            });
            validator.start();
            
    }

    public JButton[] getButtons() {
        return buttons;
    }

    public void rendPanel() {
        this.rootPane.setPreferredSize(new Dimension(500, 500));

        // gamepanel creation
        gamePanel.setVisible(true);
        gamePanel.setLayout(new GridLayout(3, 3));
        // store buttons' location that location will remove as plays goes
        buttonlist = new ArrayList<String>();
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            buttons[i].setSize(500 / 3, 500 / 3);
            buttons[i].setText("");
            buttons[i].addActionListener(this);
            buttons[i].setActionCommand("" + i);
            buttons[i].setFont(new Font("Fira Code", 0, 90));
            buttons[i].setVisible(true);
            buttonlist.add(Integer.toString(i));
            gamePanel.add(buttons[i]);
        }
        messageArea = new JTextArea();
        messagePanel.setLayout(null);
        messageArea.setOpaque(true);
        
        messageArea.setBackground(Color.black);
        messageArea.setSize(200,380);
        messageArea.setAutoscrolls(true);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(false);
        messageArea.setLocation(messagePanel.getLocation().x,messagePanel.getLocation().x);
        messageArea.setFont(new Font("Fira Code", 0, 15));
        JScrollPane jsp = new JScrollPane(messageArea);
        jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        messageArea.setForeground(Color.BLUE);
        messagePanel.add(messageArea);


        //add mode change button
        local = new MyButton("本地玩");
        local.setActionCommand("local");
        online = new MyButton("在线玩");
        online.setActionCommand("online");

        local.setLocation(messagePanel.getLocation().x+20,messagePanel.getLocation().y+400);
        online.setLocation(messagePanel.getLocation().x+100,messagePanel.getLocation().y+400);
        messagePanel.add(local);
        messagePanel.add(online);
        messagePanel.getContentPane().add(jsp);
        // messagepanel creation
        messagePanel.getContentPane().setBackground(Color.BLACK);
        messagePanel.setUndecorated(true);
        messagePanel.setOpacity(0.6f);
        messagePanel.setSize(new Dimension(200, 500));
        messagePanel.setVisible(true);
        messagePanel.setAlwaysOnTop(true);

        add(gamePanel);
        addComponentListener(new ComponentAdapter() {
            public void componentMoved(ComponentEvent e) {
                messagePanel.setLocation(getLocation().x - 200, getLocation().y + 20);
                // local.setDynamicLocation(messagePanel,0);
                // online.setDynamicLocation(messagePanel,50);
                // System.out.println("nice!");
            }
        });
        setTitle("TicTocToe~~      made with love by Gentleman.Hu");
        setResizable(false);
        setDefaultCloseOperation(3);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setAlwaysOnTop(true);
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
        String str = e.getActionCommand();

        // System.out.println(str);
        //
        buttonlist.remove(str);
        switch (str) {
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
        date= new Date();
        messageArea.append(sDateFormat.format(date)+"---:: "+whatText(key)+"~~~"+(Integer.parseInt(str)+1)+"\n");
        if (!signal) {
            // 点按钮后发送按钮位置,同步两个端之间的信息
            client.sendMes(str);
            locked();
        }
        if (signal) {
            signal = false;
        }
        key++;
    }

    public String whatText(int key) {
        return (key % 2) == 0 ? "X" : "O";
    }

    public String whatTextNet(int key) {
        return (key % 2) == 0 ? "O" : "X";
    }

    public boolean judge() {
        list = new ArrayList<String>();

        for (int i = 8; i >= 0; i--) {
            list.add(buttons[i].getText());
        }

        // 打印已经走得信息
        // Iterator<String> it = list.iterator();
        // while (it.hasNext()) {
        // System.out.print(it.next());
        // }
        // System.out.println(" ");
        // 虽然代码量少,但是没找到办法判断是哪个,才疏博浅
        // 外层粗略判断,内层确定具体位置,以便凸显,可以显示两种的情况
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
        signal = false;
        for (Timer timer : timers) {
            timer.stop();
            timer = null;
        }
        messageArea.setForeground(Color.BLUE);
        // validator.start();
        buttonlist = new ArrayList<String>();
        list = new ArrayList<String>();
        timers = new ArrayList<Timer>();
        // 把所有按钮添加到里边
        for (int i = 0; i < 9; i++) {
            buttonlist.add(Integer.toString(i));
        }
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

    // lock when another player is thinking
    public void locked() {
        for (JButton jButton : buttons) {
            jButton.setEnabled(false);
        }
    }

    public void unlocked() {
        // 把已经点过的重设置不可点击并把剩余可走位置解锁
        for (String i : buttonlist) {
            buttons[Integer.parseInt(i)].setEnabled(true);
            System.out.println(Integer.parseInt(i));
        }
    }

    void setWinButtonColorAndWinner(int one, int two, int three) {
        buttons[one].setOpaque(true);
        buttons[two].setOpaque(true);
        buttons[three].setOpaque(true);
        // button闪烁synchronized
        blinker = new Blinker(one, two, three);
        timer = new Timer(500, blinker);
        // 将timer加入队列方便管理,在多达成情况下,并发了多个timer,在list可以方便全部stop
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

    public boolean decision() {
        if (judge()) {
            boolean sure = false;
            date= new Date();
            messageArea.append("✨※"+sDateFormat.format(date)+"~~~~"+winner+"获胜!\n");
            JOptionPane pane = new JOptionPane("~~" + winner + "~~获胜,游戏结束,点击确定重置游戏!");
            JDialog dialog = pane.createDialog(null, "游戏结束!");
            // JOptionPane.showMessageDialog(null, "~~" + winner + "~~获胜,游戏结束,点击确定重置游戏!");
            dialog.setLocation(getLocation().x + 500, getLocation().y);
            dialog.setSize(new Dimension(400, 200));
            dialog.setDefaultCloseOperation(0);
            dialog.setVisible(true);
            // validator.stop();
            //TODO:本意是结束后延迟,判断双方准备就绪再解锁面板,尚未实现
            do {
                client.sendMes("ok");
                locked();
            } while (client.getMes() == "ok");
            reset();
            return true;
        } else if (!isEmptyButton()) {
            winner = Winner.DRAW;
            date= new Date();
            messageArea.append("✨※"+sDateFormat.format(date)+"~~~~"+winner+"-平局了!\n");
            JOptionPane.showMessageDialog(null, "平局!!点击确定重置游戏!");
            reset();
            return true;
        }

        return false;
    }

    class MyButton extends JButton implements ActionListener{
        public MyButton(){}
        public MyButton(String title){
            super(title);
            setDefault();
        }
        public void setDefault(){
            this.setSize(new Dimension(80,30));
            this.setPreferredSize(new Dimension(80,30));
            this.setBackground(Color.orange);
            this.setVisible(true);
            this.addActionListener(this);
            this.setForeground(Color.RED);
        }

        public void setDynamicLocation(JFrame component,int x){
            this.setLocation(component.getLocation().x+50+x,component.getLocation().y+400);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand()=="local"){
                System.out.println("进入本地模式");
                local.setEnabled(false);
                online.setEnabled(true);
            }else if(e.getActionCommand()=="online"){
                System.out.println("进入在线模式");
                local.setEnabled(true);
                online.setEnabled(false);
            }

        }


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
            // System.out.println(++i);
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