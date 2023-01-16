**Mynt Weather App**

This appplication will provide you Weather update. Initiall you need to register into this application as a new user. Once you get login to the application you will be able to see two tabs. The first tab will contain the current weather and the second contains the list of weather fetched every time the user opens the application.

The application also provide some other features such as:
1. Current location ( City and Country )
2. Current temperature in Celsius
3. Time of Sunrise and Sunset
4. Current weather status
5. Save weather detail whenever user opes the application
6. Depending on the sunrise and suneset time application will show the sun or moon icon.

**Project details:**

Architecture: MVVM

Android Studio Version: Android Studio Dolphin|2021.3.1

Programming Language: Kotlin(213-1.7.20 and above)

Device Supported: Android phone(Portrait Orientation)

NOTE

**You need to use your own OpenWeather API Key at below mantioned path**

gradle.properties -> openWeatherApiKey

NOTE

**Also update the Pass Code for Database encription at below mantioned path**

gradle.properties -> passCode

**Project Structure**

- Inside main folder we have java->com->mynt->weather

- Under data folder we have covered API request logic, database logic, models and repository.

- Under di folder we have covered dependency injection for Retrofit and Room DB

- Under ui folder we have all activity, fragments and adapters.

- Under utils folder we have covered all commonly used util classes.

- Under viewmodel folder we have viewmodel for login, registration, weather and history. 

<img width="785" alt="Screenshot 2023-01-16 at 5 34 08 PM" src="https://user-images.githubusercontent.com/122507300/212674287-0f4e711b-8b80-47c0-a6b7-afe5ddfbcf6b.png">

**Security**

Room Database is protected using SQl Cipher
Have enabled progard and minify

**Unit Test**

Unit Test for Room Database, Repository, ViewModel and Utils classes. Above 90% test coverage.

<img width="652" alt="Screenshot 2023-01-16 at 4 35 22 PM" src="https://user-images.githubusercontent.com/122507300/212664015-9146c5f6-5f47-42dc-874c-2864cb920c8b.png">

**Commits**

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

30f4b4e

1. OpenWeather API integration
2. Saving weather detail to database
3. Showing weather detail in 1st TAB
4. Showing weather history list in 2nd TAB
5. Unit test for WeatherViewModel
6. Unit test for Utils classes.
7. Unit test for WeatherDao

NOTE: Being individual contributor have put all code in master directly.

**App Screens**


![SplashScreen](https://user-images.githubusercontent.com/122507300/212665612-386f2d5a-17f5-4492-b8c7-fa2c796a6fdf.jpeg)

![SignInScreen](https://user-images.githubusercontent.com/122507300/212665642-e40581d8-e825-4a69-b5f1-95eabaebde2c.jpeg)

![RegistrationScreen](https://user-images.githubusercontent.com/122507300/212665662-99137b6a-682f-4104-ad7e-d275aafb0d02.jpeg)

![WeatherDetailScreen](https://user-images.githubusercontent.com/122507300/212665899-3865a14f-858a-4ff6-924b-f7b36ae83a6b.jpeg)

![WeatherHistoryScreen](https://user-images.githubusercontent.com/122507300/212665933-69074cd0-8cf4-4d9d-983a-32a2904ac2ac.jpeg)

![NoNetworkAvailableScreen](https://user-images.githubusercontent.com/122507300/212665992-dc3442bf-5e6e-4bf5-880d-e36eb5c3c806.jpeg)


