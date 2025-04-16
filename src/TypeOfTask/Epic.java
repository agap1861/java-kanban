package TypeOfTask;

import java.util.ArrayList;

public class Epic extends TypeOfTask.Task {

    ArrayList<Subtask> listOfSubtask;



    private Status status;


    public Epic(String nameOfEpic, String description) {
        super(nameOfEpic, description);
        status = Status.NEW;

        listOfSubtask = new ArrayList<>();
    }

    @Override
    public void setStatus(Status status) {
        System.out.println("Нельзя менять статус Epic'a");
    }

    public ArrayList<Subtask> getListOfSubtask() {
        return listOfSubtask;
    }

    public void setListOfSubtask(ArrayList<Subtask> listOfSubtask) {
        this.listOfSubtask = listOfSubtask;
    }
    @Override
    public String toConsole(){

        String result = "name={"+this.name +"} id={"+this.id+"} description={"+this.description+
                "} TypeOfTask.Status={"+ this.status+"}";
        return result;

    }

    public  void checkStatus() {
        ArrayList<Subtask> checkList = this.listOfSubtask;
        int size = checkList.size();



        if (size == 0) {

            this.status=Status.NEW;
            return ;

        }
        int done = 0;
        int _new = 0;

        for (Subtask subtask : checkList) {

            if (subtask.getStatus().equals(Status.DONE)) {
                done++;
            }
            if (subtask.getStatus().equals(Status.NEW)) {
                _new++;

            }

        }
        if (done == size) {
            this.status=Status.DONE;
        } else if (_new == size) {
            this.status=Status.NEW;
        } else {
            this.status=Status.IN_PROGRESS;

        }



    }
}
