# Nonnull and Nullable Annotations Test Suite

This test suite covers Issue #944: "Add tests to cover Nonnull and Nullable annotations"

## Overview

This test suite validates the Jakarta Annotations API 3.0 `@Nonnull` and `@Nullable` annotations enforcement by the Jakarta EE container.

## Test Components

### 1. NonnullNullableComponent.java
A CDI managed bean that serves as the dummy component with various methods annotated with `@Nonnull` and `@Nullable`:
- Methods with `@Nonnull` parameters
- Methods with `@Nullable` parameters
- Methods with `@Nonnull` return types
- Methods with `@Nullable` return types
- Methods with multiple parameters (mixed annotations)

### 2. NonnullNullableTestServlet.java
A test servlet that interacts with the component and validates:
- Valid non-null values are accepted by `@Nonnull` parameters
- Null values are rejected by `@Nonnull` parameters (container enforcement)
- Null values are accepted by `@Nullable` parameters
- Non-null values are accepted by `@Nullable` parameters
- `@Nonnull` return types do not return null (container enforcement)
- `@Nullable` return types can return null
- Multiple parameter scenarios with mixed annotations

### 3. URLClientIT.java
The JUnit 5 test class using Arquillian that:
- Deploys the test application
- Executes 15 test methods covering all scenarios
- Validates container enforcement of annotation contracts

### 4. build.xml
Ant build file for packaging the test application as an EAR with embedded WAR.

## Test Coverage

The test suite includes 15 test methods:

1. **testNonnullParameterValid** - Valid value to @Nonnull parameter
2. **testNonnullParameterNull** - Null to @Nonnull parameter (expects exception)
3. **testNullableParameterNull** - Null to @Nullable parameter (should succeed)
4. **testNullableParameterValid** - Valid value to @Nullable parameter
5. **testNonnullReturnValid** - @Nonnull method returns valid value
6. **testNonnullReturnViolation** - @Nonnull method returns null (expects exception)
7. **testNullableReturnNull** - @Nullable method returns null (should succeed)
8. **testNullableReturnValid** - @Nullable method returns valid value
9. **testMultipleNonnullParametersValid** - Multiple @Nonnull parameters with valid values
10. **testMultipleNonnullParametersFirstNull** - First @Nonnull parameter null (expects exception)
11. **testMultipleNonnullParametersSecondNull** - Second @Nonnull parameter null (expects exception)
12. **testMixedParametersAllValid** - Mixed @Nonnull/@Nullable with all valid values
13. **testMixedParametersNullableNull** - Mixed parameters, null for @Nullable (should succeed)
14. **testMixedParametersNonnullNull** - Mixed parameters, null for @Nonnull (expects exception)

## Expected Behavior

### @Nonnull Enforcement
When a method parameter or return type is annotated with `@Nonnull`:
- The Jakarta EE container should validate that null values are not passed/returned
- Violations should result in an exception (e.g., `ConstraintViolationException` or similar)
- This enforcement happens at runtime through container interceptors

### @Nullable Acceptance
When a method parameter or return type is annotated with `@Nullable`:
- Null values are explicitly allowed
- No container enforcement or validation occurs
- The annotation serves as documentation and for static analysis tools

## Build System Integration

### For Ant-based builds:
Add the following path to the test suite list in `install/jakartaee/bin/build.xml` (line 26 or appropriate location):

```xml
com/sun/ts/tests/javaee/annotations
```

This ensures the test suite is included in the build and execution process.

### For Maven-based builds:
The test is automatically discovered through the Arquillian JUnit 5 integration when the module is included in the build.

## Running the Tests

### Using Maven:
```bash
mvn clean test -Dtest=URLClientIT
```

### Using Ant (from the test directory):
```bash
ant clean build package
```

## Assertion IDs

The tests reference the following assertion IDs from the Jakarta Annotations specification:
- `Annotations:SPEC:1` - @Nonnull parameter validation
- `Annotations:SPEC:2` - @Nonnull parameter acceptance of valid values
- `Annotations:SPEC:3` - @Nonnull parameter rejection of null values
- `Annotations:SPEC:4` - @Nullable parameter behavior
- `Annotations:SPEC:5` - @Nullable parameter acceptance of null values
- `Annotations:SPEC:6` - @Nonnull return type validation
- `Annotations:SPEC:7` - @Nonnull return type valid returns
- `Annotations:SPEC:8` - @Nonnull return type null rejection
- `Annotations:SPEC:9` - @Nullable return type behavior
- `Annotations:SPEC:10` - @Nullable return type null acceptance

## Notes

- The test suite targets Jakarta Annotations API 3.0
- Container enforcement of `@Nonnull` may vary by implementation
- Some containers may require additional configuration or interceptors to enforce these annotations
- The tests are designed to validate the contract, not the specific exception type thrown