# FreeImageSearcher
![image](https://github.com/ok0035/FreeImageSearcher/assets/19370688/ef32270e-16d4-47da-be7e-1e175989feaf)

## Project Description
- It is a StaggeredGridView image searcher using the Unsplash API.

## Architecture
This project combines the MVVM and Clean Architecture principles to ensure scalability, maintainability, and testability. Here’s a brief overview of the architecture and how each module is implemented:

### MVVM Components
- **Model** - Handles the data logic and business rules.
- **View** - Responsible for rendering the UI and receiving user interactions, primarily composed of Compose views.
- **ViewModel** - Acts as a bridge between the Model and the View, managing UI-related data.

### Clean Architecture Layers
- **Data Module**: Contains everything related to data handling, such as network communication, API calls, response models, and dependency injection setup.
- **Domain Module**: Encapsulates business logic and business models, serving as an intermediary between the Data and Feature modules.
- **Feature Module**: Comprises the UI components using Jetpack Compose and ViewModels, organizing them according to features.
- **App Module**: Houses the application’s main components such as Activities and Fragments.

Each layer is designed to be independent and interchangeable, promoting a separation of concerns that facilitates easier testing and maintenance.

## Getting Started
Open Android Studio and open the downloaded project folder.
### API Key Configuration
This project utilizes the Unsplash API, which requires an access key.
Obtain an access key by registering at Unsplash Developers.
Open the local.properties file in your project and add your Unsplash API access key:

Copy code


```unsplashApiAccessKey=YOUR_UNSPLASH_ACCESS_KEY```


Replace YOUR_UNSPLASH_ACCESS_KEY with the actual key you obtained.


### Build and Run
In Android Studio, select Build -> Rebuild Project to build the project.
Choose Run -> Run 'app' to run the app.
By following these instructions, you should be able to set up and run the project in your local development environment.


### Prerequisites
- Android Studio [Iguana | 2023.2.1]
- Minimum SDK version [24]
- Target SDK version [34]

```bash
git clone https://github.com/ok0035/FreeImageSearcher.git
cd FreeImageSearcher
