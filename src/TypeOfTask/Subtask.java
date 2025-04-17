package TypeOfTask;

public class Subtask extends TypeOfTask.Task {



    private int idEpic;

    public Subtask(String nameOfSubtask, String description, TypeOfTask.Epic epic) {
        super(nameOfSubtask, description);
        idEpic = epic.getId();
    }
    public Subtask(String nameOfSubtask, String description, Status status) {
        super(nameOfSubtask, description);
        this.setStatus(status);

    }






    public int getIdEpic() {
        return idEpic;
    }

    public void setIdEpic(int idEpic) {
        this.idEpic = idEpic;
    }

}
