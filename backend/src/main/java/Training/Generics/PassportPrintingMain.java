package Training.Generics;

public class PassportPrintingMain {
    public static void main(String[] args) {






        PassportPrintingService<Pet> petPassportPrinter = new PassportPrintingService<Pet>();

        PassportPrintingService<Student> studentPassportPrinter = new PassportPrintingService<Student>();


    }
}
