
public class Printer
{

    public Printer()
    {
    }

    // Returns a line break
    public static String printLineBreak(int n) {
        String s = "";
        for (int i = 0; i < n; i++) {
            s+="\n";
        }
        return s;
    }

    //Returns a line  
    public static String printLine(int n) {
        String s = "";
        for (int i = 0; i < n; i++) {
            s+="-";
        }
        s+="\n";
        return s;
    }

    // Returns a header
    public static String printHeader(String message, int t, int b) {
        String s ="";;
        if (t > 0) {
            s+= printLine(t);
        }
        s+= message;

        if (b > 0) {
            s+= "\n" + printLine(b);
        }
        return s;
    }

    // Returns a string of given space
    public static String printSpace(int n){
        String s = "";

        for(int i = 0; i < n; i++){
            s+=" ";
        }
        return s;
    }

}

