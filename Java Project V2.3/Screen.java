import java.util.Scanner;

public class Screen {
    @SuppressWarnings("UseSpecificCatch")
    public static void clearScreen() {
        try {
            System.out.print("\033[H\033[2J\033[3J");//to move cursor to home position,clear screen and clear scrollback
            System.out.flush();
        } catch (Exception e) {
            println(e);
        }
    }

    //declare quick keys for print functions
    public static void print(Object a){
        System.out.print(a);
    }
    public static void println(Object a){
        System.out.println(a);
    }
    public static void printf(Object a, Object... b){
        System.out.printf(a.toString(),b);
    }

    public static void printOptions(String... options){

        println("╔════════════════════════════════════════════╗");
        println("║               Available Options            ║");
        println("╠════════════════════════════════════════════╣");

        int c = 1;
        for(String a : options){
            String formattedOption = String.format("║  Press %-2d to %-29s ║", c, a);
            println(formattedOption);
            c++;
        }
        println("╚════════════════════════════════════════════╝");
    }

    public static void printWarning(int a){
        print("press 1, ");
        for(int i = 2; i <= a-1 ; i ++){
            print(""+i);
            if(i <= a-2){
                print(", ");
            }
        }
        print(" and " + a);
    }

    //waiting time function usually used to let user to view something before screen switches
    public static void wt(Scanner sc){
        println("press enter to continue");
        sc.nextLine();
    }
}