<b>Zen Course Assistant ChatBOT</b>
<br>
<p>Zen helps SJSU students can get the information about the courses they are interested in before they enroll into the course. 
Once they are enrolled and taking the class, they can use this intelligent BOT to keep track of their assignments and other due dates. 
They can even use the app to get the instructor office hours, location and so on.. The students now do not need to download 
separate greensheets for each course. </p>
<br>
<b>Additional Features<b><br>
1. Option to switch to other courses from within the chatroom.
2. Suggestions for questions so that user does not have to type every question.
3. Set Reminder option for any reply of the chatbot that includes a date / time.
4. Compose email to the professor.
5. Navigation to class / instructor office location using maps.

<b>How to use</b><br>
1. Click on App icon to open welcome screen.
2. Click "Start Using Zen"
3. Select the course you want the details of (from the open Navigation Drawer)
4. click from suggestion questions / type the question you want answered.


<b>Technology Stack</b><br>
Frontend :<br>
1. Android Studio - IDE for android programming.
2. Recyclerview - Vertical recycler has been used as the main chatroom for users and response from chatbot. Horizontal recyclerview has been used for displaying suggestions from chatbot.
3. Recyclerview ItemDecoration - for managing horizontal spacing between each suggestion button.
4. Navigation Drawer - to provide list of available courses. Users can select any of the courses and start asking queries about that course.
5. Volley library  for networking - sends user’s queries to server and receiving chatbot replies from server.
6. Material Design- used as guideline for design specifications.
7. Noun Projects for different icons used in projects.
8. Android’s XML for different layouts, to declare different UI elements, Instantiate layout elements at runtime
9. Used different APIs such as Email, Google maps, calender to support corresponding features. 
10. JSON Parsing (to prepare response from server)
11. Sketch software was used to create app designs.

Backend:<br>
1. Server uses linear classification machine learning algorithm to train and test the model.
2. This chatbot is specific to SJSU courses greensheet and using pre-trained models was not an option.
3. Dataset: Main challenge was to create the questionnaire dataset to train and test the model. For this, we used JSpeech Grammar Format(JSGF) to create various grammer representation files. These files then used python based jsgf parser to inflate and create dataset. 
4. Training and Test data: The generated dataset was then divided into training and test data. Training data comprised 80% of total dataset and remaining 20 % was the test data.
5. Machine Learning Algorithm: We used SVM Machine Learning algorithm in scikit library for classifying the questions. Our tests currently indicate an accuracy of around 98%.
6. Query Classification: Each query that is asked by user is mapped to a class / category by the classification model. This category is then mapped to one of the MySQL DB tables which hold the greensheet data.
7. AWS Server hosting: Server is deployed on AWS EC2 instance (http://52.15.218.96/classify?text= <query to server>) as it is highly scalable. The database used was mySQL on Amazon RDS and is capable of serving millions of requests concurrently.
8. JSON Format: JSON is used for transferring the response from server to app.


<b>Dependencies (included in gradle file)</b><br>
com.android.support:appcompat-v7:26.1.0<br>
com.android.support.constraint:constraint-layout:1.0.2<br>
com.android.support:recyclerview-v7:26.1.0<br>
com.android.support:design:26.1.0 (For Navigation Drawer)<br>



