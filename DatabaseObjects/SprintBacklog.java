import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SprintBacklog implements IDatabaseObject {
    int employeeId;
    int sprintId;
    int storyId;

    SprintBacklog(int employeeId, int sprintId, int storyId) {
        this.employeeId = employeeId;
        this.sprintId = sprintId;
        this.storyId = storyId;

    }

    SprintBacklog(ResultSet rs) throws SQLException {
        this(rs.getInt(1), rs.getInt(2), rs.getInt(3));
    }

    @Override
    public void print() {
        System.out.println("To be implemented");
    }

    @Override
    public String getTitle() {
        return "To be implemented";
    }

    public String getTitle(Database db) throws SQLException {
        String query = "SELECT UserStory.priority, Employee.fname, Employee.lname,\n"
                + "UserStory.userAs, UserStory.wantTo, UserStory.because, UserStory.userStatus\n"
                + "FROM SprintBacklog\n" + "NATURAL JOIN Employee,Sprint,UserStory\n"
                + "WHERE (UserStory.storyId=(?) AND Sprint.sprintId=(?) AND Employee.employeeId=(?))\n"
                + "ORDER BY UserStory.priority;";
        PreparedStatement ps = db.con.prepareStatement(query);
        ps.setInt(1, this.storyId);
        ps.setInt(2, this.sprintId);
        ps.setInt(3, this.employeeId);
        ResultSet res = ps.executeQuery();
        if (res.next()) {

        }
        return "To be implemented";
    }

    @Override
    public void insert(Database db) throws SQLException {
        String query = "INSERT INTO SprintBacklog (employeeId, sprintId, storyId) VALUES (?, ?, ?)";
        PreparedStatement ps = db.con.prepareStatement(query);
        ps.setInt(1, this.employeeId);
        ps.setInt(2, this.sprintId);
        ps.setInt(3, this.storyId);
        ps.execute();
    }

    @Override
    public void delete(Database db) throws SQLException {
        String query = "DELETE FROM SprintBacklog WHERE employeeId = (?) AND sprintId = (?) AND storyId = (?)";
        PreparedStatement ps = db.con.prepareStatement(query);
        ps.setInt(1, this.employeeId);
        ps.setInt(2, this.sprintId);
        ps.setInt(3, this.storyId);
        ps.execute();
    }
}
