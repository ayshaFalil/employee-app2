package com.example.employeeapi.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.employeeapi.model.Department;

@Repository
public class DepartmentDao {

    private final JdbcTemplate jdbcTemplate;

    public DepartmentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Department findById(Long id) {
        String sql = """
                SELECT
                  ID,
                  DEPARTMENTNAME
                FROM DEPARTMENT
                WHERE ID = ?
                """;

        return jdbcTemplate.query(sql, departmentRowMapper(), id)
                .stream()
                .findFirst()
                .orElse(null);
    }

    private RowMapper<Department> departmentRowMapper() {
        return new RowMapper<Department>() {
            @Override
            public Department mapRow(ResultSet rs, int rowNum) throws SQLException {
                Department department = new Department();
                department.setId(rs.getLong("ID"));
                department.setDepartmentName(rs.getString("DEPARTMENTNAME"));
                return department;
            }
        };
    }

    public Department addDepartment(Department department) {
        String sql = """
                INSERT INTO DEPARTMENT(
                  DEPARTMENTNAME
                ) VALUES (?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql, new String[]{"ID"});
            preparedStatement.setString(1, department.getDepartmentName());
            return preparedStatement;
        }, keyHolder);

        Number generatedId = keyHolder.getKey();
        if (generatedId != null) {
            department.setId(generatedId.longValue());
        }

        return department;
    }

    public int deleteDepartment(Long id) {
        String sql = "DELETE FROM DEPARTMENT WHERE ID = ?";
        return jdbcTemplate.update(sql,id);
    }

    public Department updateDepartment(Long id, Department department) {
        String sql = """
                UPDATE DEPARTMENT
                SET DEPARTMENTNAME = ?
                WHERE ID = ?
                """;

        int rowsUpdated = jdbcTemplate.update(sql,
                department.getDepartmentName(),
                id);

        if (rowsUpdated != 0) {
            department.setId(id);
        } else {
            return null;
        }

        return department;
    }
}
