import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StateAccessProject extends State {

	Project project;
	
	StateAccessProject(Project project) {
		this.project = project;
	}
	
	void deleteProject() throws SQLException {
		this.project.delete(this.db);
		System.out.println("Deleted project " + this.project.title);
	}

	void insertSprint() throws SQLException {
		String name = this.scan.raw_input("Sprint name: ");
		String date = this.scan.raw_input("Beginning date of sprint (YYYY-MM-DD): ");
		Sprint sprint = new Sprint(0, this.project.projectId, date, name);
		sprint.insert(this.db);

	}

	void insertUserStory() throws SQLException {
		String as = this.scan.raw_input("Role: ");
		String wantTo = this.scan.raw_input("Goal: ");
		String because = this.scan.raw_input("Reason: ");
		int priority = Integer.parseInt(this.scan.raw_input("Priority (int): "));
		String status = this.scan.raw_input("Status: ");
		String date = this.scan.raw_input("Creation date of story (YYYY-MM-DD): ");
		UserStory us = new UserStory(0, as, wantTo, because, priority, status, date, this.project.projectId);
		us.insert(this.db);
	}

	void printSprints() throws SQLException {
		String query = "SELECT * FROM Sprint WHERE projectID = (?)";
		PreparedStatement ps = db.con.prepareStatement(query);
		ps.setInt(1, this.project.projectId);
		ResultSet res = ps.executeQuery();
		System.out.println("SprintID \t ProjectID \t Date \t sprintName");
		while (res.next()) {
			Sprint sprint = new Sprint(res);
			sprint.print();
		}
	}

	void printUserStory() throws SQLException {
		String query = "SELECT * FROM UserStory WHERE projectID = (?)";
		PreparedStatement ps = db.con.prepareStatement(query);
		ps.setInt(1, this.project.projectId);
		ResultSet res = ps.executeQuery();
		System.out.println("UserStoryID \t ProjectID \t User \t Goal \t Priority \t Date");
		while (res.next()) {
			UserStory us = new UserStory(res);
			us.print();
		}
	}
	
	@Override
	State update() throws SQLException {
		// TODO Auto-generated method stub
		String[] options = {
				"Delete project",
				"Add Sprint associated with project",
				"Add UserStory for project",
				"View Sprints",
				"View UserStories",
				"Return to main"
		};
		int rep = this.scan.showOptions("Project " + this.project.title, options);
		switch (rep) {
			case 1:
				String y = this.scan.raw_input("Are you sure you want to DELETE this project? (y/n) ");
				if (y.toLowerCase().equals("y")) {
					this.deleteProject();
					return new StateMain();
				}
				break;

			case 2:
				insertSprint();
				return new StateMain();

			case 3:
				insertUserStory();;
				return new StateMain();

			case 4:
				printSprints();
				return new StateMain();

			case 5:
				printUserStory();
				return new StateMain();

			default:
				return new StateMain();
		}
		return null;
	}
}
