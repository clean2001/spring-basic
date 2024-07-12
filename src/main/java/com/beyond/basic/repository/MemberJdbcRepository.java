package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// 해당 클래스가 Repository 계층임을 표현함과 동시에 싱글톤 객체로 생성한다.
@Repository
public class MemberJdbcRepository implements MemberRepository {
    // Datasource는 DB와 JDBC에서 사용하는 DB 연결 드라이버 객체
    // application.yml에서 설정한 DB 정보가 자동으로 주입
    @Autowired
    private DataSource dataSource;

    @Override
    public Member save(Member member) {
        try {
            // (1) JDBC의 가장 큰 단점: raw 쿼리를 문자열 형태로 사용 => 컴파일시점에 에러가 안남 => 디버깅하기가 어렵다.
            // (2) 조회: ResultSet으로 리턴 값이 나옴 따라서 이걸 가지고 객체를 직접 조립해줘야함
            // mybatis: => 얘도 쿼리 오타나도 컴파일 시점에 에러 안남
            Connection connection = dataSource.getConnection();
            String sql = "insert into member(name, email, password) values(?, ?, ?);"; // ?:  변수로 처리
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, member.getName());
            preparedStatement.setString(2, member.getEmail());
            preparedStatement.setString(3, member.getPassword());
            preparedStatement.executeUpdate(); // 추가, 수정의 경우 executeUpdate 실행, 조회의 경우 executeQuery


        } catch(SQLException e) {
            e.printStackTrace();
        }

        return member;
    }

    @Override
    public List<Member> findAll() {
        List<Member> memberList = new ArrayList<>();
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            String sql = "select * from member"; // 실무에서는 다 가져오면 안되고 페이징 처리 반드시 해줘야한다!! (DB 어케 생겼는지 궁금하면 Limit 걸어주기)
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) { // result set의 핵심 동작 원리?? => cursor
                // 문자열과
                Member member = new Member();
                member.setId(resultSet.getLong("id"));
                member.setName(resultSet.getString("name"));
                member.setEmail(resultSet.getString("email"));
//                member.setPassword(resultSet.getString("password"));
                memberList.add(member);
                connection.close();
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return memberList;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = new Member();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            String sql = "select * from member where id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);

            // 단건 조회
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next(); // 커서가 첫 데이터 바로 앞에 있어서 next() 한번 하고 시작해야한다!! 안하면 java.sql.SQLDataException: wrong row position
            member.setId(resultSet.getLong("id"));
            member.setName(resultSet.getString("name"));
            member.setEmail(resultSet.getString("email"));
            member.setPassword(resultSet.getString("password"));
            connection.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(member);
    }
}
