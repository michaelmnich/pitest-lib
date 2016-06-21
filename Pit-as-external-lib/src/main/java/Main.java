import java.util.Scanner;

/**
 * Created by gosc on 21.06.2016.
 */
public class Main {

    public static void main(String [] args)
    {
        System.out.println("Hello Pit Type some comands");

           if(args.length==2) ConsoleComandsContorler.ProgramImmput(args[0],args[1]); //tutaj zrobimy przerubke czytania argumentow z mauvena.
        //np czytanie z jakiegos pliku textowego czy cos i szalejemy



        //jesli nie program nie zostal odpalony z odpowiednimi argumentami mozna to zrobic z konsoli
        Scanner scaner = new Scanner(System.in);
        String consoleInput = "";
        while(!consoleInput.equals("exit") && !consoleInput.equals("Exit") ){
            consoleInput = scaner.nextLine();
            ConsoleComandsContorler.ImmputComand(consoleInput);
        }
    }
}
