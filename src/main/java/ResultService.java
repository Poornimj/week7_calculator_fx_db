import java.sql.*;

public class ResultService {

    static {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static String getEnv(String key, String defaultValue) {
        String value = System.getenv(key);
        return (value == null || value.isBlank()) ? defaultValue : value;
    }

    private static String getDatabaseUrl() {
        String host = getEnv("DB_HOST", "db");
        String port = getEnv("DB_PORT", "3306");
        String dbName = getEnv("DB_NAME", "calc_data");

        return "jdbc:mariadb://" + host + ":" + port + "/" + dbName;
    }

    private static String getDatabaseUser() {
        return getEnv("DB_USER", "root");
    }

    private static String getDatabasePassword() {
        return getEnv("DB_PASS", "root123");
    }

    public static void saveResult(double n1, double n2,
                                  double sum, double product,
                                  double subtract, Double division) {

        String dbUrl = getDatabaseUrl();

        try (Connection conn = DriverManager.getConnection(
                dbUrl, getDatabaseUser(), getDatabasePassword());
             Statement stmt = conn.createStatement()) {

            String createTable = """
                CREATE TABLE IF NOT EXISTS calc_results (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    number1 DOUBLE NOT NULL,
                    number2 DOUBLE NOT NULL,
                    sum_result DOUBLE NOT NULL,
                    product_result DOUBLE NOT NULL,
                    subtract_result DOUBLE NOT NULL,
                    division_result DOUBLE NULL,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
                """;
            stmt.executeUpdate(createTable);

            String insert = """
                INSERT INTO calc_results
                (number1, number2, sum_result, product_result, subtract_result, division_result)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

            try (PreparedStatement ps = conn.prepareStatement(insert)) {
                ps.setDouble(1, n1);
                ps.setDouble(2, n2);
                ps.setDouble(3, sum);
                ps.setDouble(4, product);
                ps.setDouble(5, subtract);

                if (division == null) {
                    ps.setNull(6, Types.DOUBLE);
                } else {
                    ps.setDouble(6, division);
                }

                ps.executeUpdate();
            }

            System.out.println("Saved result successfully.");

        } catch (SQLException e) {
            System.err.println("Failed to save result to DB: " + dbUrl);
            e.printStackTrace();
        }
    }
}