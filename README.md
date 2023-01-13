**Mynt Weather App**

This appplication will provide you Weather update. Initiall you need to register into this application as a new user. Once you get login to the application you will be able to see two tabs. The first tab will contain the current weather and the second contains the list of weather fetched every time the user opens the application.

The application also provide some other features such as:
1. Current location ( City and Country )
2. Current temperature in Celsius
3. Time of Sunrise and Sunset
4. Current weather status

Project details:
Architecture: MVVM
Android Studio Version: Android Studio Dolphin|2021.3.1
Programming Language: Kotlin(213-1.7.20 and above)
Device Supported: Android phone(Portrait Orientation)


**You need to use your own OpenWeather API Key at below mantioned path**

gradle.properties -> openWeatherApiKey


Commits

37f7aa4: 
Project structure updated

27d47f6: 
1. Splash Screen
2. Login Screen
3. Unit Test for LoginViewModel
4. Room Database Setup using Hilt Dependency Injection
5. Unit Test for UserDao

768dc72

1. Registration Screen
2. Unit Test for RegistrationViewModel
3. User Registration
4. User Sign in
