package ua.edu.electives.my.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/** Class for invoking all commands of command pattern

 */
public class CommandContainer {
    private static Map<String, Command> commands;

    private static final Logger logger = LogManager.getLogger(CommandContainer.class);

    static {
        commands = new HashMap<>();
        commands.put("login", new LoginCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("register", new RegisterCommand());
        commands.put("getallcourses", new GetAllCoursesCommand());
        commands.put("courseinfo", new CourseInfoCommand());
        commands.put("mymenu", new MyMenuCommand());
        commands.put("enroll", new EnrollCommand());
        commands.put("setmarks", new SetMarkCommand());
        commands.put("submitsetmarks", new SubmitSetMarkCommand());
        commands.put("createcourse", new CreateCourseCommand());
        commands.put("submitcreatedcourse", new SubmitCreatedCourseCommand());
        commands.put("editcourse", new EditCourseCommand());
        commands.put("submiteditedcourse", new SubmitEditedCourseCommand());
        commands.put("deletecourse", new DeleteCourseCommand());
        commands.put("createtopic", new CreateTopicCommand());
        commands.put("edittopic", new EditTopicCommand());
        commands.put("submiteditedtopic", new SubmitEditedTopicCommand());
        commands.put("getallusers", new GetAllUsersCommand());
        commands.put("edituser", new EditUserCommand());
        commands.put("submitediteduser", new SubmitEditedUserCommand());
        logger.debug("Command container initialization successful. Total commands: " + commands.size());
    }

    /** Returns command object by name
     *
     * @param commandName Name of command
     * @return Command instance */
    public static Command getCommand(String commandName) {
        return commands.get(commandName);
    }

    private CommandContainer() {}
}
