
/*
 * @Author: Gentleman.Hu 
 * @Date: 2020-02-25 11:46:58 
 * @Last Modified by: Gentleman.Hu
 * @Last Modified time: 2020-03-29 17:41:02
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TicToc {
    // TODO:平局判断，重开优化

    static int turn = 1;
    static Scanner scanner;

    enum Winner {
        Player1, Player2, Draw
    }

    static List<String> map = new ArrayList<String>();
    static String o = "O";
    static String x = "X";

    public static void run() {
        init();
        scanner = new Scanner(System.in);
        while (true) {
            if (scanner.hasNextInt()) {
                int index = scanner.nextInt();
                if (index > 9) {
                    System.out.println("数值必须为1-9");
                    turn-=turn;
                    continue;
                }
                if (turn % 2 == 0) {
                    updateMap(index, o, turn);
                } else if (turn % 2 != 0) {
                    updateMap(index, x, turn);
                }
            }
            turn++;
        }
    }

    public static void init() {
        System.out.println("地图初始化中...");
        makeMap();
        printMap();
    }

    public static void printMap() {
        // TODO:printmap
        for (String string : map) {
            System.out.print(string);
        }

    }

    public static void makeMap() {
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {
                map.add("[");
                map.add(" ");
                map.add("]");
            }
            map.add("\n");
        }

    }

    public static void updateMap(int index, String symbol, int turn) {
        int rindex;
        switch (index) {
            case 1:
                rindex = 1;
                if (canSet(rindex)) {
                    map.set(rindex, symbol);
                }
                break;
            case 2:
                rindex = 4;
                if (canSet(rindex)) {
                    map.set(rindex, symbol);
                }
                break;
            case 3:
                rindex = 7;
                if (canSet(rindex)) {
                    map.set(rindex, symbol);
                }
                break;
            case 4:
                rindex = 11;
                if (canSet(rindex)) {
                    map.set(rindex, symbol);
                }
                break;
            case 5:
                rindex = 14;
                if (canSet(rindex)) {
                    map.set(rindex, symbol);
                }
                break;
            case 6:
                rindex = 17;
                if (canSet(rindex)) {
                    map.set(rindex, symbol);
                }
                break;
            case 7:
                rindex = 21;
                if (canSet(rindex)) {
                    map.set(rindex, symbol);
                }
                break;
            case 8:
                rindex = 24;
                if (canSet(rindex)) {
                    map.set(rindex, symbol);
                }
                break;
            case 9:
                rindex = 27;
                if (canSet(rindex)) {
                    map.set(rindex, symbol);
                }
                break;
            default:
                break;
        }
        printMap();
        winCheck(turn);
    }

    public static boolean canSet(int index) {
        return (map.get(index) == " ") ? true : false;
    }

    public static Winner winCheck(int turn) {
        // var winner;
        if (getEle(1, 4, 7) || getEle(11, 14, 17) || getEle(21, 24, 27) || getEle(1, 11, 21) || getEle(4, 14, 24)
                || getEle(7, 17, 27) || getEle(1, 14, 27) || getEle(7, 14, 21)) {
            System.out.println("结束地图为:\n");
            printMap();
            if (turn % 2 == 0) {
                // winner = Winner.Player2;
                System.out.println("Player2赢了" + ",出的是\"O\"");
                System.out.println("-----");
                System.out.println("重置地图中...");
                resetMap();
                return Winner.Player2;
            } else if (turn % 2 != 0) {
                // winner = Winner.Player1;
                System.out.println("Player1赢了" + ",出的是\"X\"");
                System.out.println("-----");
                System.out.println("重置地图中...");
                resetMap();
                return Winner.Player1;
            }

        } else if (TicToc.turn >= 9) {
            System.out.println("平局");
            System.out.println("重置地图中...");
            resetMap();
            return Winner.Draw;
        }
        return null;
    }

    private static boolean getEle(int i1, int i2, int i3) {
        return ((map.get(i1) == map.get(i2) && map.get(i1) == map.get(i3))
                && (map.get(i1) != " " && map.get(i2) != " " && map.get(i3) != " ")) ? true : false;
    }

    public static void resetMap() {
        for (int i = 0; i < 2; i++) {
            System.out.println("-----");
        }
        map.clear();
        makeMap();
        printMap();
        TicToc.turn = 0;
    }

}