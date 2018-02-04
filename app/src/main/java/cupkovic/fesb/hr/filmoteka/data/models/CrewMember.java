package cupkovic.fesb.hr.filmoteka.data.models;

/**
 * Created by gcupk on 2/4/2018.
 */

public class CrewMember extends Person {
    private String credit_id;
    private String department;
    private String job;

    public String getCredit_id() {
        return credit_id;
    }

    public void setCredit_id(String credit_id) {
        this.credit_id = credit_id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
