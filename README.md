# Comic Android App
Comic is a fully functional Android app built entirely with Kotlin and Jetpack Compose. The app uses the MVVM architecture pattern to separate the concerns of the presentation layer from the data layer.

## Getting Started

### Prerequisites
- Android Studio
- JDK 8 or later
- An Android device or emulator

### Installation
1. Clone the repository: `git clone https://github.com/ali-wishcast/comic.git`
2. Open the project in Android Studio
3. Build the project
4. Run the app on your device or emulator

## Features
Comic App displays content from the [xkcd comics](https://xkcd.com/). You can see main features of the App below:
- browse through the comics
- see the comic details, including its description
- get the comic explanation

## Technologies Used
- Jetpack Compose
- ViewModel
- Hilt
- Retrofit
- Kotlinx Serialization
- Coroutine
- Coil
- Material Design

## Architecture
The app uses a modified version of the Android Architecture pattern. The following modules are used:

- `core` Contains the Dagger/Hilt modules and components.
- `data` Contains the implementations of the repositories and data sources used in the app.
- `ui` Contains the UI code, including the MainActivity, the screens, and the view models.

## Contributing
Contributions to this project are always welcome. Here are some ways you can contribute:

* Report bugs and suggest new features by creating issues.
* Contribute code by forking the repository and creating pull requests.
* Improve the documentation by correcting errors and typos or adding new information.

## License
This project is licensed under the MIT License.


