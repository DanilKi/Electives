package ua.edu.electives.my.entity;

/** Enum for Role entity

*/
public enum Role {
    ADMIN, TEACHER, STUDENT;

   /** Returns name of current role in lowercase
   * @return Current role's name
   */
   public String getName() {
       return name().toLowerCase();
   }

    /** Returns role of a user
     * @param user A user to be checked
     * @return Role of a user
     */
   public static Role getRole(User user) {
       return Role.values()[user.getRoleId()];
   }
}
