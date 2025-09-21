# GitScope
A modern Android application for GitHub user profile exploration and repository analysis.

## Features

### Core Functionality
- **GitHub User Search** - Search and explore GitHub user profiles by username
- **User Profile Display** - View comprehensive user information including avatars and basic details
- **Repository Browsing** - Browse all public repositories with detailed descriptions
- **Repository Analytics** - Access stars, forks, and last updated information for each repository
- **Fork Analytics** - Calculate total forks across all user repositories with recognition badges for users with 5000+ total forks
- **Responsive Design** - Optimized interface for different screen sizes and accessibility requirements

## Screenshots

## Architecture

This application follows **Clean Architecture** principles with **Unidirectional Data Flow (UDF)** pattern implementation:

### Layer Structure
```
Presentation Layer
├── UI (Jetpack Compose)
├── ViewModels
└── UI States

Domain Layer  
├── Use Cases
├── Domain Models
└── Repository Interfaces

Data Layer
├── Repository Implementations
├── API Services (Retrofit)
├── Data Models
└── Remote Data Sources
```

### Unidirectional Data Flow Implementation
The application implements UDF pattern ensuring predictable state management:

```
UI Events → ViewModel → Use Cases → Repository → API
    ↑                                              ↓
UI State ← StateFlow ← Domain Models ← Data Models
```

**Data Flow Process:**
1. **UI Events** - User interactions trigger events (search, navigation, selection)
2. **ViewModel** - Processes events and manages state updates
3. **Use Cases** - Encapsulate business logic and coordinate data operations
4. **Repository** - Abstracts data sources and provides unified data access
5. **API Layer** - Handles GitHub API communication
6. **State Updates** - Reactive UI updates via StateFlow emissions

### Architecture Benefits
- **Testability** - Isolated layers enable comprehensive unit testing
- **Maintainability** - Clear separation of concerns simplifies code understanding
- **Scalability** - Modular structure supports feature expansion without breaking changes
- **Flexibility** - Abstracted dependencies allow for easy component replacement
- **Predictable State** - UDF pattern ensures consistent state management
- **Reactive UI** - StateFlow provides efficient UI updates

## Technical Stack

### Core Technologies
- **Kotlin** - Primary development language
- **Jetpack Compose** - Modern declarative UI framework
- **Coroutines & Flow** - Asynchronous programming and reactive streams
- **Hilt/Dagger** - Dependency injection framework
- **Retrofit** - HTTP client for REST API communication
- **Material Design 3** - UI design system implementation

### Architecture Components
- **ViewModel** - UI-related data management
- **StateFlow** - State management and reactive programming
- **Use Cases** - Business logic encapsulation
- **Repository Pattern** - Data source abstraction layer
- **Clean Architecture** - Separation of concerns principle

## Testing Strategy

The application includes comprehensive testing coverage across multiple layers:

### Test Categories
- **Unit Tests** - ViewModel logic, Use Cases, and Repository implementations
- **Instrumented Tests** - Android-specific functionality and integration testing
- **UI Tests** - Jetpack Compose interface interactions

### Local Test Execution
To enable automatic test execution on every build, uncomment the following configuration in `build.gradle` (app level):

```gradle
// Uncomment to run tests automatically on build
// android {
//     testOptions {
//         unitTests.returnDefaultValues = true
//         unitTests.includeAndroidResources = true
//     }
// }
```

### Test Execution Commands
```bash
# Execute unit tests
./gradlew testDebugUnitTest

# Execute instrumented tests
./gradlew connectedDebugAndroidTest

# Execute all test suites
./gradlew test
```

## Continuous Integration and Deployment

### GitHub Actions Integration
Automated testing and quality assurance pipeline implemented with GitHub Actions:

**Pipeline Configuration:**
- Unit Test execution with JDK 17
- Instrumented Tests on Android Emulator API 34
- Static code analysis and lint checking
- Build verification and artifact generation

### Pipeline Capabilities
- **Parallel Execution** - Concurrent job processing for optimized build times
- **Artifact Management** - Automatic storage of test reports and build outputs
- **Multi-Platform Testing** - Comprehensive device and API level compatibility verification
- **Quality Assurance** - Automated code quality gates and standards enforcement

### Build Artifacts
The following artifacts are generated and stored after each CI execution:
- **Unit Test Reports** - Comprehensive test execution results
- **Application Packages** - Debug and release APK files
- **Code Quality Reports** - Lint analysis and static code analysis results
- **Instrumented Test Results** - UI and integration test outcomes

## Getting Started

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or newer
- Java Development Kit 17
- Android SDK API Level 34
- Git version control system

### Installation and Setup
1. **Repository Cloning**
   ```bash
   git clone https://github.com/anmolsingh1597/GitScope.git
   cd GitScope
   ```

2. **Project Opening**
   ```bash
   # Launch Android Studio with project
   studio .
   ```

3. **Project Synchronization**
    - Allow Gradle dependency synchronization to complete
    - Verify JDK 17 configuration in project settings

4. **Application Execution**
   ```bash
   # Command line build
   ./gradlew assembleDebug
   
   # Alternative: Use Android Studio run configuration
   ```

### Configuration Notes
- No API authentication required
- Application utilizes GitHub's public API with standard rate limiting
- Network connectivity required for data retrieval

## Project Structure

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/example/gitscope/
│   │   │   ├── data/           
│   │   │   ├── domain/         
│   │   │   ├── presentation/   
│   │   │  
│   │   └── res/               
│   ├── test/                  
│   └── androidTest/           
├── build.gradle              
```

