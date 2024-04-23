package Training.Generics;


public class PassportPrintingService<T> {

    private T thingToPrint;

    public void printPassport(T thingToPrint){
        System.out.println("**** \r\nPassport of: \r\n" + thingToPrint + "\r\n****");
    }

}
