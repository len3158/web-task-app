package com.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataSource extends TaskDTO {

    public static final String DB_NAME = "store_manager.sqlite";

    public static final String CONNECTION_STRING = "jdbc:sqlite:C:src\\app\\db\\" + DB_NAME;

    public static final String TABLE_TASKS = "tasks";
    public static final String COLUMN_TASKS_ID = "id";
    public static final String COLUMN_TASKS_TITLE = "title";
    public static final String COLUMN_TASKS_DESCRIPTION = "description";
    public static final String COLUMN_TASKS_IS_COMPLETED = "is_completed";
    public static final String COLUMN_TASKS_EXPIRING_DATE = "expiring_date";
    public static final String COLUMN_ASSIGNED_USER_ID = "assigned_user_id"; // TODO FK

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USERS_ID = "id";
    public static final String COLUMN_USERS_FIRSTNAME = "firstname";
    public static final String COLUMN_USERS_LASTNAME = "lastname";
    public static final String COLUMN_USERS_EMAIL = "email";
//    public static final String COLUMN_USERS_PASSWORD = "password"; // TODO
//    public static final String COLUMN_USERS_SALT = "salt";
    public static final String COLUMN_USERS_ADMIN = "admin";
    public static final String COLUMN_USERS_STATUS = "status";

    public static final int ORDER_BY_NONE = 1;
    public static final int ORDER_BY_ASC = 2;
    public static final int ORDER_BY_DESC = 3;

    private Connection conn;

    private static final DataSource instance = new DataSource();


    private DataSource() { }

    public static DataSource getInstance() {
        return instance;
    }


    public boolean open() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            return true;
        } catch (SQLException e) {
            System.out.println("Couldn't connect to database: " + e.getMessage());
            return false;
        }
    }


    public void close() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
        }
    }

    // BEGIN TASKS QUERIES
    /**
     * This method get all the tasks from the database.
     * @param sortOrder     Results sort order.
     * @return List         Returns Task array list.
     * @since                   1.0.0
     */
    public List<TaskDTO> getAllTasks(int sortOrder) {

        StringBuilder queryTasks = queryTasks();

        if (sortOrder != ORDER_BY_NONE) {
            queryTasks.append(" ORDER BY ");
            queryTasks.append(COLUMN_TASKS_TITLE);
            if (sortOrder == ORDER_BY_DESC) {
                queryTasks.append(" DESC");
            } else {
                queryTasks.append(" ASC");
            }
        }
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(queryTasks.toString())) {

            return getTaskDTOS(results);

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    private List<TaskDTO> getTaskDTOS(ResultSet results) throws SQLException {
        List<TaskDTO> tasks = new ArrayList<>();
        while (results.next()) {
            TaskDTO task = new TaskDTO();
            task.setId(results.getString(1));
            task.setTitle(results.getString(2));
            task.setDescription(results.getString(3));
            task.setCompleted(results.getBoolean(4));
            task.setExpiringDate(results.getDate(5).toString());
            tasks.add(task);
        }
        return tasks;
    }

    /**
     * This method get one task from the database based on the provided task_id.
     * @param task_id    Task id.
     * @return List         Returns TaskDTO array list.
     * @since                   1.0.0
     */
    public List<TaskDTO> getOneTask(UUID task_id) {

        StringBuilder queryTasks = queryTasks();
        queryTasks.append(" WHERE " + TABLE_TASKS + "." + COLUMN_TASKS_ID + " = ? LIMIT 1");
        try (PreparedStatement statement = conn.prepareStatement(String.valueOf(queryTasks))) {
            statement.setString(1, task_id.toString());
            ResultSet results = statement.executeQuery();
            return getTaskDTOS(results);

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    /**
     * This method searches tasks from the database based on the provided searchString.
     * @param searchString  String to search task name or task description.
     * @param sortOrder     Results sort order.
     * @return List         Returns TaskDTO array list.
     * @since                   1.0.0
     */
    public List<TaskDTO> searchTasks(String searchString, int sortOrder) {
        StringBuilder queryTasks = queryTasks();
        queryTasks.append(" WHERE (" + TABLE_TASKS + "." + COLUMN_TASKS_TITLE + " LIKE ? OR " + TABLE_TASKS + "." + COLUMN_TASKS_DESCRIPTION + " LIKE ?)");

        if (sortOrder != ORDER_BY_NONE) {
            queryTasks.append(" ORDER BY ");
            queryTasks.append(COLUMN_TASKS_TITLE);
            if (sortOrder == ORDER_BY_DESC) {
                queryTasks.append(" DESC");
            } else {
                queryTasks.append(" ASC");
            }
        }

        try (PreparedStatement statement = conn.prepareStatement(queryTasks.toString())) {
            statement.setString(1, "%" + searchString + "%");
            statement.setString(2, "%" + searchString + "%");
            ResultSet results = statement.executeQuery();

            return getTaskDTOS(results);

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    /**
     * This private method returns a default query for the tasks.
     * @return StringBuilder
     * @since                   1.0.0
     */
    private StringBuilder queryTasks() {
        return new StringBuilder("SELECT " +
                TABLE_TASKS + "." + COLUMN_TASKS_ID + ", " +
                TABLE_TASKS + "." + COLUMN_TASKS_TITLE + ", " +
                TABLE_TASKS + "." + COLUMN_TASKS_DESCRIPTION + ", " +
                TABLE_TASKS + "." + COLUMN_TASKS_IS_COMPLETED + ", " +
                TABLE_TASKS + "." + COLUMN_TASKS_EXPIRING_DATE + ", "
        );
    }

    /**
     * This method deletes one task based on the taskId provided.
     * @param taskId     Task id.
     * @return boolean      Returns true or false.
     * @since                   1.0.0
     */
    public boolean deleteSingleTask(UUID taskId) {
        String sql = "DELETE FROM " + TABLE_TASKS + " WHERE " + COLUMN_TASKS_ID + " = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, taskId.toString());
            int rows = statement.executeUpdate();
            System.out.println(rows + " record(s) deleted.");
            return true;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * This method insert one task to the database.
     * @param title          TaskDTO title.
     * @param description   TaskDTO description.
     * @param isCompleted         TaskDTO isCompleted.
     * @param expiring_date   TaskDTO expiring_date.
     * @return boolean      Returns true or false.
     * @since                   1.0.0
     */
    public boolean insertNewTask(String title, String description, boolean isCompleted, Date expiring_date) {

        String sql = "INSERT INTO " + TABLE_TASKS + " ("
                + COLUMN_TASKS_TITLE + ", "
                + COLUMN_TASKS_DESCRIPTION + ", "
                + COLUMN_TASKS_IS_COMPLETED + ", "
                + COLUMN_TASKS_EXPIRING_DATE + ", "+
                ") VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, title);
            statement.setString(2, description);
            statement.setBoolean(3, isCompleted);
            statement.setDate(5, expiring_date);

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * This method updates one task to the database.
     * @param title         TaskDTO title.
     * @param description   TaskDTO description.
     * @param isCompleted         TaskDTO isCompleted.
     * @param expiring_date      TaskDTO quantity.
     * @return boolean      Returns true or false.
     * @since                   1.0.0
     */
    public boolean updateOneTask(UUID id, String title, String description, boolean isCompleted, Date expiring_date) {

        String sql = "UPDATE " + TABLE_TASKS + " SET "
                + COLUMN_TASKS_TITLE + " = ?" + ", "
                + COLUMN_TASKS_DESCRIPTION + " = ?" + ", "
                + COLUMN_TASKS_IS_COMPLETED + " = ?" + ", "
                + COLUMN_TASKS_EXPIRING_DATE + " = ?" +
                " WHERE " + COLUMN_TASKS_ID + " = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, title);
            statement.setString(2, description);
            statement.setBoolean(3, isCompleted);
            statement.setDate(4, expiring_date);
            statement.setString(5, id.toString());

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * This method decreases the task stock by one based on the provided task_id.
     * @param task_id    Task id.
     * @since                   1.0.0
     */
    public void completeTask(UUID task_id) {

        String sql = "UPDATE " + TABLE_TASKS + " SET " + COLUMN_TASKS_IS_COMPLETED + " = " + " 1 " + " - 1 WHERE " + COLUMN_TASKS_ID + " = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, task_id.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
    }
    // END TASKS QUERIES

    // BEGIN USER QUERIES
    private List<UserDTO> getUserDTOS(ResultSet results) throws SQLException {
        List<UserDTO> users = new ArrayList<>();
        while (results.next()) {
            UserDTO user = new UserDTO();
            user.setId(results.getString(1));
            user.setFirstname(results.getString(2));
            user.setLastname(results.getString(3));
            user.setEmail(results.getString(4));
            users.add(user);
        }
        return users;
    }
    
    /**
     * This method get all the users from the database.
     * @param sortOrder     Results sort order.
     * @return List         Returns UserDTO array list.
     * @since                   1.0.0
     */
    public List<UserDTO> getAllUsers(int sortOrder) {

        StringBuilder queryUsers = queryUsers();

        if (sortOrder != ORDER_BY_NONE) {
            queryUsers.append(" ORDER BY ");
            queryUsers.append(COLUMN_USERS_LASTNAME);
            if (sortOrder == ORDER_BY_DESC) {
                queryUsers.append(" DESC");
            } else {
                queryUsers.append(" ASC");
            }
        }
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(queryUsers.toString())) {

            return getUserDTOS(results);

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    /**
     * This method get one user from the database based on the provided task_id.
     * @param id   UserDTO id.
     * @return List         Returns Task array list.
     * @since                   1.0.0
     */
    public List<UserDTO> getOneUser(UUID id) {

        StringBuilder queryUsers = queryUsers();
        queryUsers.append(" AND " + TABLE_USERS + "." + COLUMN_USERS_ID + " = ?");
        try (PreparedStatement statement = conn.prepareStatement(String.valueOf(queryUsers))) {
            statement.setString(1, id.toString());
            ResultSet results = statement.executeQuery();
            return getUserDTOS(results);

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    /**
     * This method searches users from the database based on the provided searchString.
     * @param searchString  String to search task name or task description.
     * @param sortOrder     Results sort order.
     * @return List         Returns Task array list.
     * @since                   1.0.0
     */
    public List<UserDTO> searchUsers(String searchString, int sortOrder) {

        StringBuilder queryUsers = queryUsers();

        queryUsers.append(" AND (" + TABLE_USERS + "." + COLUMN_USERS_FIRSTNAME + " LIKE ? OR " + TABLE_USERS + "." + COLUMN_USERS_LASTNAME + " LIKE ?)");

        if (sortOrder != ORDER_BY_NONE) {
            queryUsers.append(" ORDER BY ");
            queryUsers.append(COLUMN_USERS_FIRSTNAME);
            queryUsers.append(COLUMN_USERS_LASTNAME);
            if (sortOrder == ORDER_BY_DESC) {
                queryUsers.append(" DESC");
            } else {
                queryUsers.append(" ASC");
            }
        }

        try (PreparedStatement statement = conn.prepareStatement(queryUsers.toString())) {
            statement.setString(1, "%" + searchString + "%");
            statement.setString(2, "%" + searchString + "%");
            ResultSet results = statement.executeQuery();

            return getUserDTOS(results);

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    /**
     * This private method returns a default query for the users.
     * @return StringBuilder
     * @since                   1.0.0
     */
    private StringBuilder queryUsers() {
        return new StringBuilder("SELECT " +
                TABLE_USERS + "." + COLUMN_USERS_ID + ", " +
                TABLE_USERS + "." + COLUMN_USERS_FIRSTNAME + ", " +
                TABLE_USERS + "." + COLUMN_USERS_LASTNAME + ", " +
                TABLE_USERS + "." + COLUMN_USERS_EMAIL + ", " +
                " (SELECT COUNT(*) FROM " + TABLE_USERS + " WHERE " + TABLE_TASKS + "." + COLUMN_ASSIGNED_USER_ID + " = " + TABLE_USERS + "." + COLUMN_USERS_ID + ") AS user_tasks" + ", " +
                TABLE_USERS + "." + COLUMN_USERS_STATUS +
                " FROM " + TABLE_USERS +
                " WHERE " + TABLE_USERS + "." + COLUMN_USERS_ADMIN + " = 0"
        );
    }

    /**
     * This method deletes one user based on the userId provided.
     *
     * @param id UserDTO id.
     * @return boolean      Returns true or false.
     * @since 1.0.0
     */
    public boolean deleteSingleUser(UUID id) {
        String sql = "DELETE FROM " + TABLE_USERS + " WHERE " + COLUMN_USERS_ID + " = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, id.toString());
            int rows = statement.executeUpdate();
            System.out.println(rows + " " + TABLE_USERS + " record(s) deleted.");


            String sql2 = "DELETE FROM " + TABLE_TASKS + " WHERE " + COLUMN_ASSIGNED_USER_ID + " = ?"; // TODO FK

            try (PreparedStatement statement2 = conn.prepareStatement(sql2)) { // TODO : unsafe
                statement2.setString(1, id.toString());
                int rows2 = statement2.executeUpdate();
                System.out.println(rows2 + " " + TABLE_USERS + " record(s) deleted.");
                return true;
            } catch (SQLException e) {
                System.out.println("Query failed: " + e.getMessage());
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return false;
        }
    }
}
