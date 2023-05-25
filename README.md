<p align="center">

  <img src="https://readme-typing-svg.demolab.com?font=Noto+Sans+Simplified+Chinese&pause=1000&color=6F3BF5&center=true&width=435&lines=UZH+IFI+Q%26A+Forums;Sopra+Group+38" alt="Example Usage - README Typing SVG">

</p>

<div align="center">

[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=sopra-fs23-group-38_sopra-fs23-38-server&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=sopra-fs23-group-38_sopra-fs23-38-server)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=sopra-fs23-group-38_sopra-fs23-38-server&metric=coverage)](https://sonarcloud.io/summary/new_code?id=sopra-fs23-group-38_sopra-fs23-38-server)

</div>




## Introduction

This is an online and real-time Q&A platform based on UZH IFI's study and life, we hope the students can exchange the thoughts on this platform and get the answers immediately they wish for.

The platform also has some social features, with tagging for specific questions and live chat between users, and we hope that everyone will share their daily routines to increase their social circle within UZH.

This is a diagram of the functionality of our Web Application:

![image](https://github.com/sopra-fs23-group-38/sopra-fs23-38-server/blob/main/img/overview.png)

## Technologies

- [Springboot](https://spring.io/) - Java framework to create a micro service
- [Gradle](https://gradle.org/) - Automated building and management tool
- [MySQL](https://www.mysql.com/) - Database
- [React](https://reactjs.org/docs/getting-started.html) - Javascript library for the whole frontend
- [Ant Design](https://ant.design/) - React design Component library
- [Github Projects](https://github.com/explore) - Project Management
- [Figma](https://figma.com/) - Mockups
- [Google Cloud](https://cloud.google.com/) - Deployment
- [SonarCloud](https://sonarcloud.io/) - Testing & Feedback of code quality

## High-level components

### User

Users are the ones who create questions, answer questions, add comments and send messages to each other. Users can choose their username, password and avatar during registration and save them to the database, where the primary key ID is automatically stored.

[User](https://github.com/sopra-fs23-group-38/sopra-fs23-38-server/blob/main/src/main/java/ch/uzh/ifi/hase/soprafs23/entity/User.java)

[UserService](https://github.com/sopra-fs23-group-38/sopra-fs23-38-server/blob/main/src/main/java/ch/uzh/ifi/hase/soprafs23/service/UserService.java)

### Question

Questions can be created with a title, description and tag, and also have their own questionID as the primary key,as well as storing the time the question was created and the ID of the User who created the question. The tag could be used to filtered by users. Question will also store the number of Answers so that the user can sort the question by the number of Answers to determine the hotness of the question.

[Question](https://github.com/sopra-fs23-group-38/sopra-fs23-38-server/blob/main/src/main/java/ch/uzh/ifi/hase/soprafs23/entity/Question.java)

[QuestionService](https://github.com/sopra-fs23-group-38/sopra-fs23-38-server/blob/main/src/main/java/ch/uzh/ifi/hase/soprafs23/service/QuestionService.java)

### Answer

Answer is the reply made by the User under each question. Each Answer will also store the corresponding QuestionID and UserID, and because the Answer itself has an upvote/downvote function, the Answer will also store the votecount attribute. Users can also add comments to different Answers, which will be stored in the comment count and displayed.

[Answer](https://github.com/sopra-fs23-group-38/sopra-fs23-38-server/blob/main/src/main/java/ch/uzh/ifi/hase/soprafs23/entity/Answer.java)

[AnswerService](https://github.com/sopra-fs23-group-38/sopra-fs23-38-server/blob/main/src/main/java/ch/uzh/ifi/hase/soprafs23/service/AnswerService.java)

### Comment

A Comment is a reply made by a User in response to an Answer or Comment itself. Because the Comment is displayed in a nested form, the Comment stores its own ID and the ID of its answer section: AnswerID or ParentCommentID, as well as the UserID and the time of the comment.

[Comment](https://github.com/sopra-fs23-group-38/sopra-fs23-38-server/blob/main/src/main/java/ch/uzh/ifi/hase/soprafs23/entity/Comment.java)

[CommentService](https://github.com/sopra-fs23-group-38/sopra-fs23-38-server/blob/main/src/main/java/ch/uzh/ifi/hase/soprafs23/service/CommentService.java)

### Message

Message is a tool for communication between different users, it stores the IDs of two different users and the time of creation. At the same time, we also store the messages between different users.

[Message](https://github.com/sopra-fs23-group-38/sopra-fs23-38-server/blob/main/src/main/java/ch/uzh/ifi/hase/soprafs23/entity/Message.java)

### WebSocket

In order to make our Application more real-time, we use WebSockets in all key areas such as Chatting between users, creating and answering questions, adding comments and notes, and almost all functions are real-time.

## Deployment and Database

### Deployment on Google Cloud

Our application is hosted on [Google Cloud URL](https://sopra-fs23-group-38-client.oa.r.appspot.com/). Also our server status is available in this link [Google Cloud URL](https://sopra-fs23-group-38-server.oa.r.appspot.com/). All cloud deployments are now complete and can be accessed directly via the link above.

### MySQL Database

This application use MySQL database to store data.

## Launch & Development

For your local development environment, you may need gradle to build this application and create your own database in this steps:

### Create your own database of MySQL:

Firstly you need to open your own MySQL server in your local machine, for more information you could get access to this link [MySQL](https://www.mysql.com/).

And when you open your own MySQL server, you should also change the datasource.url, username and password yourself to make it link properly in [application.properties](https://github.com/sopra-fs23-group-38/sopra-fs23-38-server/blob/main/src/main/resources/application.properties).

### Building with Gradle

You can use the local Gradle Wrapper to build the application.

- macOS: `./gradlew`
- Linux: `./gradlew`
- Windows: `./gradlew.bat`

More Information about [Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html) and [Gradle](https://gradle.org/docs/).

### Build

```bash
./gradlew build
```

### Run

```bash
./gradlew bootRun
```

You can verify that the server is running by visiting `localhost:8080` in your browser. You also have to check whether your server URL is set properly on `localhost:8080`.

### Testing

Testing is optional, and you can run the tests with

```bash
./gradlew test
```

## Roadmap

### Access to the Switch.edu interface

Firstly, we wanted to make the app accessible and beneficial to more UZH students and staff, so firstly we wanted the login interface to be accessible to Switch.edu, allowing UZH students to discuss and share and socialise directly on the app in their real names.

### More user actions

At the moment our users can do a lot within the app, but we think there is still room for improvement. For example, users can bookmark or share questions that interest them, or they can personalise their user centre to better present themselves.

### More social features

We want this application to be more than just a Q&A platform, but we want to use it as a social platform for UZH students internally. So I hope we can develop more social features, such as adding friends and following each other rather than just chatting.

## Authors and acknowledgment

### Contributors

- **Hangchen Xie** - [Github](https://github.com/hangchenxie)
- **Yunlong Li** - [Github](https://github.com/1316827294)
- **Yuanzhe Gao** - [Github](https://github.com/ArthasGAO)
- **Qiyue Shang** - [Github](https://github.com/QiyueShang666)

### Supervision

- **Hyeongkyun (Kaden) Kim** - [Github](https://github.com/hk-kaden-kim)

## License(最后改)

[Apache license 2.0](https://github.com/sopra-fs23-group-38/server/blob/6dc8281b0a876fa1d310626a704e0e4bfa08b86d/LICENSE)
