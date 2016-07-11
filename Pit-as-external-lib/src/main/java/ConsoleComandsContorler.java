/**
 * Created by gosc on 21.06.2016.
 */
public class ConsoleComandsContorler {

//
//public ConsoleComandsContorler(){
//
//}


    /**
     * Method for help console output
     */
    public static void help(){
        System.out.println("You need help The answer is 42 !!! ");
    }

    public static void ImmputComand(String s){
        if(s.toLowerCase().equals("help")){help();}
    }
    public static void ProgramImmput(String s, String args ){
        if(s.toLowerCase().equals("-r")){

         //   PitRunner pitRun = new PitRunner(args);

        }
    }
}
