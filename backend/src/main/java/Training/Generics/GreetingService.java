package Training.Generics;


public class GreetingService {

    public <T> void greetEveryoneInTheGroup(T[] group) {
        for (T e : group
        ) {
            System.out.println("Hello " + ((Citizen) e).getName());
        }
    }

    public <T> void greetAndReturnSingleObject(T[] group){
        System.out.println("Hello " + ((Citizen)group[(int)(Math.random() * group.length)]).getName());
    }

}
