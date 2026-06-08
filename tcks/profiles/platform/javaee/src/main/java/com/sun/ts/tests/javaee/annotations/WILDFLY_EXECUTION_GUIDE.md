# WildFly Execution Guide for Nonnull/Nullable Annotation Tests

## Prerequisites

1. **WildFly Server**: WildFly 31+ (Jakarta EE 11 compatible)
2. **Maven**: 3.8.6 or higher
3. **Java**: JDK 17 or higher

## Setup Steps

### 1. Download and Install WildFly

```bash
# Download WildFly 31 or later
wget https://github.com/wildfly/wildfly/releases/download/31.0.0.Final/wildfly-31.0.0.Final.zip
unzip wildfly-31.0.0.Final.zip
export WILDFLY_HOME=/path/to/wildfly-31.0.0.Final
```

### 2. Configure Arquillian for WildFly

Create `arquillian.xml` in `src/test/resources`:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<arquillian xmlns="http://jboss.org/schema/arquillian"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://jboss.org/schema/arquillian
                http://jboss.org/schema/arquillian/arquillian_1_0.xsd">

    <container qualifier="wildfly-managed" default="true">
        <configuration>
            <property name="jbossHome">${wildfly.home}</property>
            <property name="serverConfig">standalone-full.xml</property>
            <property name="allowConnectingToRunningServer">false</property>
            <property name="managementAddress">127.0.0.1</property>
            <property name="managementPort">9990</property>
            <property name="username">admin</property>
            <property name="password">admin</property>
        </configuration>
    </container>

</arquillian>
```

### 3. Add WildFly Arquillian Adapter to pom.xml

Add to the `<dependencies>` section of `tcks/profiles/platform/javaee/pom.xml`:

```xml
<!-- WildFly Arquillian Container -->
<dependency>
    <groupId>org.wildfly.arquillian</groupId>
    <artifactId>wildfly-arquillian-container-managed</artifactId>
    <version>5.0.1.Final</version>
    <scope>test</scope>
</dependency>
```

### 4. Configure Bean Validation for @Nonnull/@Nullable Enforcement

WildFly uses Hibernate Validator for Bean Validation. To enable @Nonnull/@Nullable enforcement, you may need to:

#### Option A: Use Bean Validation Interceptor

Create `beans.xml` in `src/main/resources/META-INF/`:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="https://jakarta.ee/xml/ns/jakartaee"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="https://jakarta.ee/xml/ns/jakartaee 
                           https://jakarta.ee/xml/ns/jakartaee/beans_4_0.xsd"
       version="4.0"
       bean-discovery-mode="all">
    
    <interceptors>
        <class>jakarta.validation.executable.ValidateOnExecution</class>
    </interceptors>
</beans>
```

#### Option B: Enable Method Validation

Add to `validation.xml` in `src/main/resources/META-INF/`:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<validation-config
    xmlns="https://jakarta.ee/xml/ns/validation/configuration"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="https://jakarta.ee/xml/ns/validation/configuration
        https://jakarta.ee/xml/ns/validation/configuration/validation-configuration-3.0.xsd"
    version="3.0">
    
    <executable-validation enabled="true">
        <default-validated-executable-types>
            <executable-type>CONSTRUCTORS</executable-type>
            <executable-type>NON_GETTER_METHODS</executable-type>
            <executable-type>GETTER_METHODS</executable-type>
        </default-validated-executable-types>
    </executable-validation>
</validation-config>
```

## Execution Commands

### Run All Tests

```bash
cd tcks/profiles/platform/javaee
mvn clean test -Dwildfly.home=/path/to/wildfly-31.0.0.Final
```

### Run Specific Test Class

```bash
mvn test -Dtest=URLClientIT -Dwildfly.home=/path/to/wildfly-31.0.0.Final
```

### Run Single Test Method

```bash
mvn test -Dtest=URLClientIT#testNonnullParameterNull -Dwildfly.home=/path/to/wildfly-31.0.0.Final
```

### Debug Mode

```bash
mvn test -Dtest=URLClientIT -Dwildfly.home=/path/to/wildfly-31.0.0.Final \
    -Dmaven.surefire.debug="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"
```

## Expected Test Results

### Tests That Should PASS

1. **testNonnullParameterValid** - Valid value to @Nonnull parameter
2. **testNullableParameterNull** - Null to @Nullable parameter
3. **testNullableParameterValid** - Valid value to @Nullable parameter
4. **testNonnullReturnValid** - @Nonnull method returns valid value
5. **testNullableReturnNull** - @Nullable method returns null
6. **testNullableReturnValid** - @Nullable method returns valid value
7. **testMultipleNonnullParametersValid** - Multiple @Nonnull with valid values
8. **testMixedParametersAllValid** - Mixed parameters with all valid values
9. **testMixedParametersNullableNull** - Mixed parameters, null for @Nullable

### Tests That Should PASS (with Exception Caught)

These tests expect the container to throw an exception:

10. **testNonnullParameterNull** - Expects ConstraintViolationException
11. **testNonnullReturnViolation** - Expects ConstraintViolationException
12. **testMultipleNonnullParametersFirstNull** - Expects ConstraintViolationException
13. **testMultipleNonnullParametersSecondNull** - Expects ConstraintViolationException
14. **testMixedParametersNonnullNull** - Expects ConstraintViolationException

## Troubleshooting

### Issue: @Nonnull/@Nullable Not Enforced

**Solution**: Ensure Bean Validation is properly configured. WildFly may require explicit configuration to enforce these annotations.

Add to `NonnullNullableComponent.java`:

```java
import jakarta.validation.constraints.NotNull;

// Use @NotNull instead of or in addition to @Nonnull
@NotNull
public String processNonnullParameter(@NotNull String value) {
    return "Processed: " + value;
}
```

### Issue: Tests Fail with Deployment Errors

**Solution**: Check WildFly logs at `$WILDFLY_HOME/standalone/log/server.log`

Common issues:
- Missing dependencies
- CDI configuration errors
- Deployment descriptor issues

### Issue: Container Not Starting

**Solution**: 
1. Check if port 8080 or 9990 is already in use
2. Verify WILDFLY_HOME is set correctly
3. Check Java version compatibility

## Alternative: Remote Container

For running tests against an already-running WildFly instance:

```xml
<container qualifier="wildfly-remote">
    <configuration>
        <property name="managementAddress">127.0.0.1</property>
        <property name="managementPort">9990</property>
        <property name="username">admin</property>
        <property name="password">admin</property>
    </configuration>
</container>
```

Start WildFly manually:
```bash
$WILDFLY_HOME/bin/standalone.sh -c standalone-full.xml
```

Run tests:
```bash
mvn test -Darquillian.launch=wildfly-remote
```

## Continuous Integration

For CI/CD pipelines, use the managed container approach with automated WildFly download:

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-dependency-plugin</artifactId>
    <executions>
        <execution>
            <id>unpack-wildfly</id>
            <phase>process-test-classes</phase>
            <goals>
                <goal>unpack</goal>
            </goals>
            <configuration>
                <artifactItems>
                    <artifactItem>
                        <groupId>org.wildfly</groupId>
                        <artifactId>wildfly-dist</artifactId>
                        <version>31.0.0.Final</version>
                        <type>zip</type>
                        <overWrite>false</overWrite>
                        <outputDirectory>${project.build.directory}</outputDirectory>
                    </artifactItem>
                </artifactItems>
            </configuration>
        </execution>
    </executions>
</plugin>
```

## Notes

- WildFly's enforcement of @Nonnull/@Nullable may differ from other Jakarta EE implementations
- Some tests may require additional WildFly-specific configuration
- Bean Validation 3.0+ is required for full support
- Consider using @NotNull from Bean Validation API for more consistent enforcement across containers