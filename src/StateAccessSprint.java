import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StateAccessSprint extends State {

    Project project;
    Sprint sprint;

    void addUserStoryToSprintBacklog() throws SQLException {
        UserStory us = this.scan.<UserStory>select(this.db.getUserStories(this.project.projectId));
        Employee emp = this.scan.<Employee>select(this.db.getEmployees());
        SprintBacklog bl = new SprintBacklog(emp.employeeId, this.sprint.sprintId, us.storyId);
        bl.insert(this.db);
    }

    StateAccessSprint(Project project, Sprint sprint) {
        this.project = project;
        this.sprint = sprint;
    }

    @Override
    State update() throws SQLException {
        String[] options = { "Add user story to sprint backlog", "Delete dev", "Add dev", "Return to main" };
        int rep = this.scan.showOptions("Sprint " + this.sprint.name, options);
        switch (rep) {
        case 1:
            this.addUserStoryToSprintBacklog();
            break;

        case 2:
            break;

        case 3:
            break;

        default:
            return new StateMain();
        }
        return null;
    }
}