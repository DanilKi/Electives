package ua.edu.electives.my.dao;

/** Class contains SQL statements for manipulating data, stored in the database

 */
public final class ConstSQL {
    public static final String REGISTER_USER = "INSERT INTO user VALUES (default, ?, ?, ?, ?, ?, ?)";
    public static final String GET_USER_BY_EMAIL = "SELECT * FROM user WHERE email = ?";
    public static final String GET_USER_BY_ID = "SELECT * FROM user WHERE id = ?";
    public static final String UPDATE_USER = "UPDATE user SET email = ?, password = ?, f_name = ?, l_name = ?, role_id = ?, is_blocked = ? WHERE id = ?";
    public static final String GET_USERS = "SELECT * FROM user WHERE role_id = ?";
    public static final String GET_NUM_OF_USERS = "SELECT count(*) FROM user WHERE role_id = ?";
    public static final String GET_TEACHERS = "SELECT * FROM user WHERE role_id = 1 ORDER BY l_name, f_name";
    public static final String GET_COURSE_STUDENTS = "SELECT * FROM course_student WHERE course_id = ?";
    public static final String UPDATE_MARK = "UPDATE course_student SET mark = ? WHERE course_id = ? AND student_id = ?";

    public static final String CREATE_COURSE = "INSERT INTO course VALUES (default, ?, ?, ?, ?, ?, ?, ?)";
    public static final String GET_COURSE = "SELECT *, (SELECT count(*) FROM course_student WHERE course_id = course.id) AS students FROM course WHERE id = ?";
    public static final String UPDATE_COURSE = "UPDATE course SET title = ?, start_date = ?, end_date = ?, duration = ?, topic_id = ?, teacher_id = ?, description = ? WHERE id = ?";
    public static final String DELETE_COURSE = "DELETE FROM course WHERE id = ?";
    public static final String GET_COURSES = "SELECT *, (SELECT count(*) FROM course_student WHERE course_id = course.id) AS students FROM course";
    public static final String GET_NUM_OF_COURSES = "SELECT count(*) FROM course";
    public static final String GET_TEACHER_COURSES = "SELECT *, (SELECT count(*) FROM course_student WHERE course_id = course.id) AS students FROM course WHERE teacher_id = ?";
    public static final String GET_NUM_OF_TEACHER_COURSES = "SELECT count(*) FROM course WHERE teacher_id = ?";
    public static final String GET_STUDENT_COURSES = "SELECT course_id, student_id, mark, (SELECT count(*) FROM course_student WHERE course_id = course.id) AS students FROM course_student, course WHERE student_id = ? AND course_id = course.id";
    public static final String GET_TOPIC = "SELECT * FROM topic WHERE id = ?";
    public static final String GET_TOPICS = "SELECT * FROM topic";
    public static final String INSERT_TOPIC = "INSERT INTO topic VALUES (default, ?)";
    public static final String EDIT_TOPIC = "UPDATE topic SET name = ? WHERE id = ?";
    public static final String ENROLL_IN_COURSE = "INSERT INTO course_student VALUES (?, ?, default)";

    public static final String OFFSET_LIMIT = "LIMIT ?, ?";
    public static final String ORDER_BY_TITLE = "ORDER BY title";
    public static final String ORDER_BY_DURATION = "ORDER BY duration";
    public static final String ORDER_BY_NUM_OF_STUDENTS = "ORDER BY students";
    public static final String ORDER_BY_NAME = "ORDER BY l_name, f_name";
    public static final String ORDER_BY_NAME_DESC = "ORDER BY l_name DESC, f_name DESC";
    public static final String COURSES_IN_PROGRESS = "AND start_date <= ? AND end_date > ?";
    public static final String COURSES_NOT_STARTED = "AND start_date > ?";
    public static final String COURSES_COMPLETED = "AND end_date <= ?";

    private ConstSQL() {}
}
