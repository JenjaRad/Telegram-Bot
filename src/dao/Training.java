package dao;

import java.time.LocalDateTime;

public class Training {
    private int id;
    LocalDateTime begin_of_training;
    LocalDateTime end_of_training;
    String kindOfSport;
    private int id_coach;

    public Training(LocalDateTime begin_of_training, LocalDateTime end_of_training, String kindOfSport, int id_coach) {

        this.begin_of_training = begin_of_training;
        this.end_of_training = end_of_training;
        this.kindOfSport = kindOfSport;
        this.id_coach = id_coach;
    }

    @Override
    public String toString() {
        return "Coach{" +
                "begin_of_training=" + begin_of_training +
                ", end_of_training=" + end_of_training +
                ", kindOfSport='" + kindOfSport + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (this.id == 0) {
            this.id = id;
        } else {
            System.out.println("Cannot change ID");
        }
    }

    public int getId_coach() {
        return id_coach;
    }

    public void setId_coach(int id_coach) {
        if (this.id_coach== 0) {
            this.id_coach = id_coach;
        } else {
            System.out.println("Cannot change coach_ID");
        }
    }

    public static void main(String[] args) {

    }
}
