package Training.Generics;

class BiologyStudent implements Student, Citizen {
    private String name;
    private int id;

    public BiologyStudent(String name, int id) {
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
        return "Biology";
    }
}