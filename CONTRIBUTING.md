# Contributing to PEMS

## Software Style Guide
Most of the software style is based on Google Java Style Guide (https://google.github.io/styleguide/javaguide.html).

The google software style guide shall be used at all times.

Some quick notes from and in addition to the style guide are included below.

### Source File Name
>The source file name consists of the case-sensitive name of the top-level class it contains (of which there is exactly one), plus the .java extension.

Shall be **UpperCamelCase**:  EnvironmentSensor.java

### Braces 
>Braces are used with if, else, for, do and while statements, even when the body is empty or contains only a single statement.

### Indentation
Tab chracters are **allowed** for indentation. 

### Naming

#### Package names
Shall be **lowercase**: org.maroubra.pemsserver.api.models;

#### Class names
Shall be **UpperCamelCase**: HashIntegrationTest

#### Method names
Shall be **lowerCamelCase**: sendMessage 

#### Constant names
Shall use **CONSTANT_CASE**: NUMBER, CHAR_ARRAY

#### Non-Constant field names
Shall be **lowerCamelCase**: computedValues

