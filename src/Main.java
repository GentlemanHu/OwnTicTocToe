/*
 * @Author: Gentleman.Hu 
 * @Date: 2020-02-25 11:47:03 
 * @Last Modified by: Gentleman.Hu
 * @Last Modified time: 2020-03-29 22:56:55
 */
/**
 * Main
 */
public class Main {
    static GamePanel gamePanel;
    public static void main(String[] args) {
        // TicToc.run();
        gamePanel = new GamePanel();
               
    }

    public static int recur(int n){
        if(n>0)
        return recur(n-1)+n;
        else return 0;
    }
}