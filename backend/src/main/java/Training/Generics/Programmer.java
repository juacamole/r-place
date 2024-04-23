package Training.Generics;

class Programmer implements Student, Citizen {
    private String name;
    private int id;

    public Programmer(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getStudyField() {
        return "IT";
    }
}