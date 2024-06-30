## Appendix

When you modify or create an ad, especially when you add pictures, you need to start the image name with "image".
<br><br>
Example: image.jpg, image98.jpg

This is necessary because, to simulate a real server, users upload their pictures to the server, and the server assigns the names to the pictures.

# Backend Leboncoin (Java)

## About

This project aims to replicate the backend functionality of the Leboncoin website, a popular French classified advertisements platform, using Java. The primary goal is to understand and recreate the core features, ensuring a scalable and efficient backend system.

## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
- [Features](#features)
- [Dependencies](#dependencies)
- [Author](#author)
- [License](#license)

## Installation

Follow these instructions to install the project on your local machine. This is intended for developers wishing to test and use the project.

Make sure to add Maven to your PATH, or use the full path to the Maven executable.

```bash
git clone https://github.com/Xplit495/leboncoin-backend.git
cd leboncoin-backend
mvn clean install
```

## Usage
After installation, here how you can use the project:

```bash
## Usage

cd leboncoin-backend
mvn dependency:copy-dependencies
javac -d bin -cp "target/dependency/*" src/main/java/com/xplit/leboncoin/*.java
java -cp "bin:target/dependency/*" com.xplit.leboncoin.Application
```

## Features

The project includes the following features:

- **Admin Mode**: Provides every functionality to manage the application. An admin can do absolutely everything to every user and every ad.
- **User Mode**: Provides functionalities to log in to a user account and act like a user with limited permissions, managing only their own ads.
- **Random Ads Attribution**: Uses static data but randomly assigns ads to users as it's not a real backend.

## Dependencies

- Java (JDK 22)
- Jackson

## Author

**Carrola Quentin**

- GitHub: [Xplit495](https://github.com/Xplit495)
- LinkedIn: [Quentin Carrola](https://www.linkedin.com/in/quentin-carrola-24306b304/)
- Email: carrolaquentin.pro@gmail.com

## License

This project is licensed under the MIT License. For more details, 
see the [LICENSE](LICENSE) file included in this repository.