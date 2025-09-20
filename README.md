# GitScope
View of GitHub profiles along with repo information 

## ✨ Features

### 🎯 Core Functionality
- 🔍 **Search GitHub Users** - Enter any GitHub username to explore their profile
- 👤 **User Profiles** - Display user avatar, name, and basic information
- 📁 **Repository Listing** - Browse all public repositories with descriptions
- 📊 **Repository Details** - View stars, forks, and last updated information
- 🏆 **Fork Analytics** - Calculate total forks across all repositories with special badge for 5000+ forks

## 📱 Screenshots

![Screenshot 1](https://example.com/screenshot1.png)
![Screenshot 2](https://example.com/screenshot2.png)
![Screenshot 3](https://example.com/screenshot3.png)

## 🏛️ Architecture

This app follows **Clean Architecture** principles with clear separation of concerns:

```
📱 Presentation Layer
├── UI (Jetpack Compose)
├── ViewModels
└── UI States

🎯 Domain Layer  
├── Use Cases
├── Domain Models
└── Repository Interfaces

💾 Data Layer
├── Repository Implementations
├── API Services (Retrofit)
├── Data Models
└── Remote Data Sources
```

### Architecture Benefits
- ✅ **Testability** - Easy to unit test each layer in isolation
- ✅ **Maintainability** - Clear separation makes code easy to understand
- ✅ **Scalability** - Simple to add new features without breaking existing code
- ✅ **Flexibility** - Can easily swap out data sources or UI frameworks
