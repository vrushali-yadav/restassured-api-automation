# REST Assured API Automation Framework

![Java](https://img.shields.io/badge/Java-orange.svg)
![REST Assured](https://img.shields.io/badge/REST%20Assured-green.svg)
![TestNG](https://img.shields.io/badge/TestNG-red.svg)
![Maven](https://img.shields.io/badge/Maven-blue.svg)

API automation testing framework built using REST Assured, Java, TestNG, and Maven. This framework provides a structured approach to automate RESTful API testing with extensive reporting and easy maintainability.


## Features

- REST Assured for API testing
- TestNG for test management and execution
- Maven for build management
- Support for various HTTP methods 
- Request and Response specifications
- JSON schema validation
- Data-driven testing support
- Detailed test reporting - ExtentReports


## Project Structure
```
restassured-api-automation/
├── 📂 resources/
│   ├── 📂 TestData/                              # Test data files
│   │   ├── patchRequestBody.json                 # PATCH request payload
│   │   ├── postRequestBody.json                  # POST request payload
│   │   ├── putRequestBody.json                   # PUT request payload
│   │   └── testdata.json                         # General test data
│   │
│   ├── 📂 TestSuites/                            # TestNG suite configurations
│   │   ├── ClassLevelSuite.xml                   # Class-level execution
│   │   ├── PackageLevelSuite.xml                 # Package-level execution
│   │   ├── ParallelExecutionClasses.xml          # Parallel class execution
│   │   ├── ParallelExecutionTests.xml            # Parallel test execution
│   │   ├── RegressionSuite.xml                   # Full regression suite
│   │   ├── RetrySuite.xml                        # Suite with retry logic
│   │   └── SmokeSuite.xml                        # Smoke test suite
│   │
│   ├── TestExpectedSchema.json                   # JSON schema for validation
│   └── extent-config.xml                         # ExtentReports configuration
│
├── 📂 src/
│   ├── 📂 main/
│   │   └── 📂 java/
│   │       ├── 📂 core/                          # Core framework components
│   │       │   ├── BaseTest.java                 # Base test class with setup/teardown
│   │       │   └── StatusCode.java               # HTTP status code constants
│   │       │
│   │       ├── 📂 helper/                        # Helper classes
│   │       │   └── BaseTestHelper.java           # Base test helper utilities
│   │       │
│   │       ├── 📂 pojo/                          # Plain Old Java Objects
│   │       │   ├── BookStoreRequest.java         # BookStore API request model
│   │       │   ├── City.java                     # City data model
│   │       │   └── PostRequestBody.java          # Generic POST request model
│   │       │
│   │       └── 📂 utils/                         # Utility classes
│   │           ├── ExtentReport.java             # ExtentReports integration
│   │           ├── JsonReader.java               # JSON file reader utility
│   │           ├── PropertyReader.java           # Properties file reader
│   │           ├── RetryAnalyzer.java            # TestNG retry analyzer
│   │           ├── RetryListener.java            # TestNG retry listener
│   │           └── SoftAssertionUtil.java        # Soft assertion wrapper
│   │
│   └── 📂 test/
│       └── 📂 java/
│           └── 📂 userManagement/                # User management test cases
│               ├── ApiChainingTest.java          # API workflow chaining tests
│               ├── BuilderPatternImplementation.java  # Builder pattern examples
│               ├── GetUsersTest.java             # GET request tests
│               ├── JSONPlaceholderApiTest.java   # JSONPlaceholder API tests
│               ├── JsonSchemaValidationTest.java # JSON schema validation tests
│               ├── PostUsersTest.java            # POST request tests
│               ├── PostmanEchoApiTest.java       # Postman Echo API tests
│               └── TestNGAnnotationExecutionSequence.java  # TestNG lifecycle demo
│
├── 📄 README.md                                  # Project documentation
├── 📄 config.properties                          # Configuration properties
└── 📄 pom.xml                                    # Maven build configuration
```

## Technologies Used

| Technology | Purpose                 |
|------------|-------------------------|
| Java | Programming Language    |
| REST Assured | API Testing Library     |
| TestNG | Testing Framework       |
| Maven | Build Tool              |
| Jackson/Gson | JSON Processing for POJO |
| ExtentReports | Test Reporting |

