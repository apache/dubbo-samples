# NOTICE

## File Structure and Purpose

This demo project contains two Maven configuration files with distinct purposes:

### `pom.xml` (Main Project Configuration)
- **Purpose**: Complete Maven configuration for running the generated Dubbo Triple REST demo
- **Usage**: Default Maven project file used for building and running the application
- **Contains**: All necessary dependencies, plugins, and configurations for the demo project
- **Target**: End users who want to run and study the generated code

### `pom-generator.xml` (Code Generation Configuration)
- **Purpose**: Maven configuration specifically designed for OpenAPI code generation
- **Usage**: Used during the code generation phase with openapi-generator-maven-plugin
- **Contains**: Minimal dependencies and generator-specific configurations
- **Target**: Developers who want to understand or reproduce the code generation process

## Code Generation Process

1. Start with `pom-generator.xml` containing the openapi-generator-maven-plugin configuration
2. Run the OpenAPI Generator to create source code from `openapi.json`
3. The generator produces the complete project structure with `pom.xml`
4. The final `pom.xml` includes all runtime dependencies needed for the demo

## For Demo Users

- Use `mvn spring-boot:run` with the main `pom.xml` to run the demo
- Refer to `pom-generator.xml` to understand how the code was generated
- Both files are preserved to show the complete development workflow

This approach demonstrates the full lifecycle from OpenAPI specification to runnable Dubbo Triple REST service.