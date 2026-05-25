This is a Java 17 Spring Boot 3 REST API project using:
- Maven for dependency management
- Spring Data JPA with PostgreSQL
- @RestController, @Service, @Repository layered architecture
- Entities use Jakarta Persistence annotations (jakarta.persistence.*)
- DTOs for request/response — never expose entities directly
- Constructor injection (no @Autowired on fields)
- ResponseEntity<T> for all controller return types

When generating code, follow these conventions:
- Package structure: com.example.myapp.{controller,service,repository,model,dto}
- Entity classes in model package
- Use record types for DTOs where possible
- Include @Valid on request bodies