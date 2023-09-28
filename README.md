### Câu 1:
Thuộc tính name trong annotation @Entity khác với thuộc tính name trong @Table như thế nào? Hãy giải thích rõ cần thì minh hoạ?

Trả lời:

-Thuộc tính name trong annotation @Entity để chỉ định tên của thực thể trong CSDL.

```java
@Entity(name = "User")
public class User {
    // ...
}
// Trường hợp này, thực thể User sẽ được ánh xạ thành 1 bảng trong CSDL với tên là User.
```


-Thuộc tính name trong annotation @Table để chỉ định tên của bảng trong CSDL.

```java
@Entity
@Table(name = "user")
public class User {
    // ...
}
// Trường hợp này, thực thể User sẽ được ánh xạ thành 1 bảng trong CSDL với tên là user.
```

### Câu 2:
Để debug câu lệnh SQL mà Hibernate sẽ sinh ra trong quá trình thực thi, cần phải bổ sung lệnh nào vào file application.properties?

Trả lời:

```properties
spring.jpa.show-sql=true
# Hiển thị các câu lệnh SQL trong quá trình chạy lên console.
spring.jpa.properties.hibernate.format_sql=true
# Định dạng lại câu lệnh SQL để dễ đọc hơn trên console.
# Tuy nhiên, các tham số đầu vào cho các câu truy vấn sẽ không được xuất ra.
```

### Câu 3:
Annotation @Column dùng để bổ sung tính chất cho cột ứng với một thuộc tính.

- Tham số nào trong @Column sẽ đổi lại tên cột nếu muốn khác với tên thuộc tính
- Tham số nào chỉ định yêu cầu duy nhất, không được trùng lặp dữ liệu
- Tham số nào buộc trường không được null?

Trả lời:

- Tham số "name" : đổi lại tên cột nếu muốn khác với tên thuộc tính.
- Tham số "unique" :
  - true : chỉ định yêu cầu duy nhất, không được trùng lặp dữ liệu.
  - false : cho phép trường  có thể chứa các giá trị trùng lặp.
- Tham số "nullable" :
  - true : cho phép trường có giá trị null.
  - false : trường không được có giá trị null.

### Câu 4:

Có 2 sự kiện mà JPA có thể bắt được, viết logic bổ sung

- Ngay trước khi đối tượng Entity lưu xuống CSDL (ngay trước lệnh INSERT)
- Ngay trước khi đối tượng Entity cập nhật xuống CSDL (ngay trước lệnh UPDATE)
Vậy 2 annotation này là gì?

Trả lời:

- @PrePersist : ngay trước khi đối tượng Entity lưu xuống CSDL.
- @PreUpdate : ngay trước khi đối tượng cập nhật xuống CSDL.

```java
@Entity
public class User {
    @Id
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void beforeSave() {
        // Xử lý trước khi lưu đối tượng xuống CSDL.
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    public void beforeUpdate(){
        // Xử lý trước khi cập nhật đối tượng xuống CSDL.
        updatedAt = LocalDateTime.now();
    }
}
```

### Câu 5: 
JpaRepository là một interface có sẵn trong thư viện JPA, nó cũng cấp các mẫu hàm thuận tiện cho thao tác dữ liệu. Cụ thể JpaRepository kế thừa từ interface nào?

Trả lời: 
1. CrudRepository: là 1 interface cung cấp các phương thức cơ bản để thao tác với cơ sở dữ liệu bao gồm tạo, đọc, cập nhật và xóa (CRUD).
2. PagingAndSortingRepository : là 1 interface cung cấp các phương thức cho phân trang và sắp xếp dữ liệu.
3. QueryByExampleExecutor : là 1 interface cung cấp các phương thức để thực hiện truy vấn dựa trên ví dụ (example) của đối tượng.

### Câu 6:
Hãy viết khai báo một interface repository thao tác với một Entity tên là Post, kiểu dữ liệu trường Identity là Long, tuân thủ interface JpaRepository.

Trả lời:

```java
public interface PostRepository extends JpaRepository<Post, Long>{
    
}
```

### Câu 7: 
Khi đã chọn một cột là Identity dùng @Id để đánh dấu, thì có cần phải dùng xác định unique dùng annotation @Column(unique=true) không?

Trả lời: Không cần, vì khi sử dụng @Id để đánh dấu, nghĩa là cột đó được sử dụng để làm khóa chính(primary key) => giá trị phải là duy nhất, không được trùng lặp dữ liệu.

### Câu 8:
Giả sử có 1 class Employee với các fields sau {id, emailAddress, firstName, lastName}. Hãy viết các method trong interface EmployeeRespository để :

1. Tìm tất cả các Employee theo emailAddress và lastName.
```java
// Method query
List<Employee> findByEmailAndLastName(String email, String lastName);

// Native query
@Query(nativeQuery = true, value = "Select * from employee e where e.email = :email and e.lastName = :lastName")
List<Employee> getByEmailAndLastNameNQ(@Param("email") String email,@Param("lastName") String lastName);

// JPQL query
@Query("select e from Employee e where e.email = :email and e.lastName = :lastName")
List<Employee> getByEmailAndLastNameJPQL(@Param("email") String email,@Param("lastName") String lastName);
```
2. Tìm tất cả các Employee khác nhau theo firstName hoặc lastName.
```java
// Method query
List<Employee> findDistinctByFirstNameOrLastName(String firstName, String lastName);

// Native query
@Query(nativeQuery = true, value = "select distinct * from employee e where e.firstName = ?1 or e.lastName = ?2")
List<Employee> getDistinctByFirstNameOrLastNameNQ(String firstName, String lastName);

// JPQL query
@Query("select distinct e from Employee e where e.firstName = ?1 or e.lastName = ?2")
List<Employee> getDistinctByFirstNameOrLastNameJPQL(String firstName, String lastName);
```
3. Tìm tất cả các Employee theo lastName và sắp xếp thứ tự theo firstName tăng dần.
```java
// Method query
List<Employee> findByLastNameOrOrderByFirstNameAsc(String lastName);

// Native query
@Query(nativeQuery = true, value = "select * from employee e where e.lastName = ?1 order by e.firstName asc")
List<Employee> getByLastNameOrOrderByFirstNameAscNQ(String lastName);

// JPQL query
@Query("select e from Employee e where e.lastName = ?1 order by e.firstName asc")
List<Employee> getByLastNameOrOrderByFirstNameAscJPQL(String lastName);
```
4. Tìm tất cả các Employee theo fistName không phân biệt hoa thường.
```java
// Method query
// Theo firstName
List<Employee> findByFirstNameIgnoreCase(String firstName);
// Chứa firstName
List<Employee> findByFirstNameContainingIgnoreCase(String firstName);
    
// Native query
@Query(nativeQuery = true, value = "select * from employee e where lower(e.firstName) = lower(?1)")
List<Employee> getByFirstNameIgnoreCaseNQ(String firstName);

@Query(nativeQuery = true, value = "select * from employee e where lower(e.firstName) like lower(?1)")
List<Employee> getByFirstNameContainingIgnoreCaseNQ(String firstName);

// JPQL query
@Query("select e from Employee e where lower(e.firstName) = lower(?1)")
List<Employee> getByFirstNameIgnoreCaseJPQL(String firstName);

@Query("select e from Employee e where lower(e.firstName) like lower(?1)")
List<Employee> getByFirstNameContainingIgnoreCaseJPQL(String firstName);
```

### Câu 9:

Trả lời: 

### Câu 10:
```java
```

### Câu 11:

