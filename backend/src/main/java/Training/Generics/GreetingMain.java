package Training.Generics;

import java.sql.Array;

public class GreetingMain {
    public static void main(String[] args) {



        Citizen citizen1 = new BiologyStudent("JOHN TIMMEH", 6);
        Citizen citizen2 = new Programmer("CARTA", 7);

        GreetingService greeter = new GreetingService();

        Citizen[] allCitizens = new Citizen[]{citizen1, citizen2};

        greeter.<Citizen>greetEveryoneInTheGroup(allCitizens);
        greeter.<Citizen>greetAndReturnSingleObject(allCitizens);
    }
}
