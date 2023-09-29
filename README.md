### Câu 1:
Thuộc tính name trong annotation **@Entity** khác với thuộc tính name trong **@Table** như thế nào? Hãy giải thích rõ cần thì minh hoạ?

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
Để debug câu lệnh SQL mà **Hibernate** sẽ sinh ra trong quá trình thực thi, cần phải bổ sung lệnh nào vào file **application.properties**?

Trả lời:

```properties
spring.jpa.show-sql=true
# Hiển thị các câu lệnh SQL trong quá trình chạy lên console.
spring.jpa.properties.hibernate.format_sql=true
# Định dạng lại câu lệnh SQL để dễ đọc hơn trên console.
# Tuy nhiên, các tham số đầu vào cho các câu truy vấn sẽ không được xuất ra.
```

### Câu 3:
Annotation **@Column** dùng để bổ sung tính chất cho cột ứng với một thuộc tính.

- Tham số nào trong **@Column** sẽ đổi lại tên cột nếu muốn khác với tên thuộc tính
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
Hãy viết khai báo một interface repository thao tác với một Entity tên là `Post`, kiểu dữ liệu trường Identity là Long, tuân thủ interface JpaRepository.

Trả lời:

```java
public interface PostRepository extends JpaRepository<Post, Long>{
    
}
```

### Câu 7: 
Khi đã chọn một cột là Identity dùng **@Id** để đánh dấu, thì có cần phải dùng xác định unique dùng annotation @Column(unique=true) không?

Trả lời: Không cần, vì khi sử dụng @Id để đánh dấu, nghĩa là cột đó được sử dụng để làm khóa chính(primary key) => giá trị phải là duy nhất, không được trùng lặp dữ liệu.

### Câu 8:
Giả sử có 1 class **Employee** với các fields sau **{id, emailAddress, firstName, lastName}**. Hãy viết các method trong interface EmployeeRespository để :

1. Tìm tất cả các Employee theo emailAddress và lastName.
```java
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Method query
    List<Employee> findByEmailAndLastName(String email, String lastName);

    // Native query
    @Query(nativeQuery = true, value = "Select * from employee e where e.email = :email and e.last_name = :lastName")
    List<Employee> getByEmailAndLastNameNQ(@Param("email") String email, @Param("lastName") String lastName);

    // JPQL query
    @Query("select e from Employee e where e.email = :email and e.lastName = :lastName")
    List<Employee> getByEmailAndLastNameJPQL(@Param("email") String email, @Param("lastName") String lastName);
}
```
2. Tìm tất cả các Employee khác nhau theo firstName hoặc lastName.
```java
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Method query
    List<Employee> findDistinctByFirstNameOrLastName(String firstName, String lastName);

    // Native query
    @Query(nativeQuery = true, value = "select distinct * from employee e where e.first_name = ?1 or e.last_name = ?2")
    List<Employee> getDistinctByFirstNameOrLastNameNQ(String firstName, String lastName);

    // JPQL query
    @Query("select distinct e from Employee e where e.firstName = ?1 or e.lastName = ?2")
    List<Employee> getDistinctByFirstNameOrLastNameJPQL(String firstName, String lastName);
}
```
3. Tìm tất cả các Employee theo lastName và sắp xếp thứ tự theo firstName tăng dần.
```java
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Method query
    List<Employee> findByLastNameOrderByFirstNameAsc(String lastName);

    // Native query
    @Query(nativeQuery = true, value = "select * from employee e where e.last_name = ?1 order by e.first_name asc")
    List<Employee> getByLastNameOrderByFirstNameAscNQ(String lastName);

    // JPQL query
    @Query("select e from Employee e where e.lastName = ?1 order by e.firstName asc")
    List<Employee> getByLastNameOrderByFirstNameAscJPQL(String lastName);
}
```
4. Tìm tất cả các Employee theo fistName không phân biệt hoa thường.
```java
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Method query
    // Theo firstName
    List<Employee> findByFirstNameIgnoreCase(String firstName);
    // Chứa firstName
    List<Employee> findByFirstNameContainingIgnoreCase(String firstName);

    // Native query
    @Query(nativeQuery = true, value = "select * from employee e where lower(e.first_name) = lower(?1)")
    List<Employee> getByFirstNameIgnoreCaseNQ(String firstName);

    @Query(nativeQuery = true, value = "select * from employee e where lower(e.first_name) like lower(concat('%', ?1, '%'))")
    List<Employee> getByFirstNameContainingIgnoreCaseNQ(String firstName);

    // JPQL query
    @Query("select e from Employee e where lower(e.firstName) = lower(?1)")
    List<Employee> getByFirstNameIgnoreCaseJPQL(String firstName);

    @Query("select e from Employee e where lower(e.firstName) like lower(concat('%', ?1, '%'))")
    List<Employee> getByFirstNameContainingIgnoreCaseJPQL(String firstName);
}
```

### Câu 9:
Hãy nêu cách sử dụng của **@NamedQuery** và **@Query**. Cho ví dụ
Trả lời: 
1. @NamedQuery : sử dụng để định nghĩa truy vấn đã được đặt tên trong đối tượng Entity.

```java
@NamedQuery(    
        name = "findEmployeeById",
        query = "from Employee where id = ?1"
)
@Entity
@Table(name = "employee")
public class Employee {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String email;
  private String firstName;
  private String lastName;
}
```
```java
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Named query
    Employee getEmployeeById(Long id);
}
```

2. @Query : sử dụng để xác định các truy vấn tùy chỉnh trong repository.
```java
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
  @Query("select e from Employee e where e.email = :email and e.lastName = :lastName")
  List<Employee> getByEmailAndLastNameJPQL(@Param("email") String email,@Param("lastName") String lastName);
}
```

### Câu 10:
Hãy nêu 1 ví dụ sử dụng **sorting** và **paging** khi query đối tượng Employee ở trên
```java
@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Page<Employee> getAllEmployee(Integer pageNumber, Integer pageSize, Sort sort){
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        return employeeRepository.findAll(pageable);
    }
    // Ví dụ: Lấy danh sách nhân viên trang thứ 2 với 10 nhân viên và được sắp xếp theo id giảm dần
    // Page<Employee> employees = getAllEmployee(2, 10, Sort.by("id").descending());
}
```

### Câu 11:
Có 3 Entity `Product.java` và `Category.java` và `Tag.java`:

- Hãy bổ sung định nghĩa quan hệ **One to Many** giữa bảng **Category (One) – Product (Many)**. Chú ý khi một Category xoá, thì không được phép xoá Product, mà chỉ set thuộc tính Category của Product là null.
- Hãy bổ sung định nghĩa quan hệ **Many to Many** giữa bảng **Tag(Many) – Product(Many)**.

```java
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany
    @JoinTable(
            name = "product_tag",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Tag> tagList;
}
```
```java
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> productList;
}
```
```java
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "tagList")
    private List<Product> productList;
}
```
### Câu 12:
Cho class `User.java` như sau:
```java
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user")
public class User {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private String name;
   private String email;
   private String password;
}
```
Viết câu lệnh query để tìm kiếm **UserDto** bao gồm các thuộc tính **(id, name, email)** theo cách sau (mỗi cách 1 câu lệnh truy vấn)

1. Method query

```java
public class UserService {
  private final UserRepository userRepository;
  
  public UserService(UserRepository userRepository){
      this.userRepository = userRepository;
  }

  public UserDto getUserById(Long id) {
    User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Not found user"));
    return UserDto.builder()
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .build();
  }
}
```
2. JPQL Query
```java
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select new com.example.minitest2.dto.UserDto(u.id, u.name, u.email) from User u where u.id = :id")
    UserDto getUserByIdJPQL(@Param("id") Long id);
}
```
3. Native Query
```java
@NamedNativeQuery(
        name = "getUserByIdNQ",
        query = "SELECT u.id, u.name, u.email FROM user u where u.id = ?1",
        resultClass = UserDto.class
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
}
```
```java
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(nativeQuery = true, name = "getUserByIdNQ")
    UserDto getUserByIdNQ(Long id);
}
```
4. Projection
```java
public interface UserProjection {
    Long getId();
    String getName();
    String getEmail();

    @RequiredArgsConstructor
    class UserProjectionImpl implements UserProjection{
        private final User user;

        @Override
        public Long getId() {
            return this.user.getId();
        }

        @Override
        public String getName() {
            return this.user.getName();
        }

        @Override
        public String getEmail() {
            return this.user.getEmail();
        }
    }
    static UserProjection of(User user){
        return new UserProjectionImpl(user);
    }
}
```
```java
public interface UserRepository extends JpaRepository<User, Long> {
    // Projection
    <T> T findById(Long id, Class<T> type);
}
```
### Câu 13:
Cho class `Post.java` như sau:
```java
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="post")
public class Post {
   @Id
   private String id;
   private String title;
}
```
Viết custom generate id để tạo id ngẫu nhiên cho đối tượng post ở trên

Chú ý custom generate id ở đây là tự động tạo ID giống như @GeneratedValue, chúng ta không cần tự set ID thủ công cho Entity mà ID sẽ được tự động thêm vào

Trả lời:

```java
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "post")
public class Post {
    @Id
    private String id;
    private String title;

    // Constructor
    public Post(String title){
        this.id = generateUniqueId();
        this.title = title;
    }

    private String generateUniqueId(){
        return UUID.randomUUID().toString();
    }
    /*
    ---Sử dụng @PrePersist

    public Post(String title){
        this.title = title;
    }
    @PrePersist
    void beforeSave(){
        id = generateUniqueId();
    }
    */
}
```