# COMP2100_6442_S1_2020_GROUP_PROJECT
An android survey App

# GroupProject-SurveyCrops
## Team structure and roles
* Yuliang Ma u6462980 -  team leader, help page code, partial android code, user interface beautification, app state, user input
* Yuchen Wang u6928271 - documentation checker, partial android code, note taker, user input, app state, help page code
* Jinzhi Wang u6891586 - partial android code, app state, user input, data visualizer, note taker, help page code, documentation checker
* Haoyu Wang  u7083239 - tricky android code, app state, all server code, user input, help page code, connect all the parts


## App Overview
Our app's name is SurveyCorps. It's a voting app. 
### Log in page
In the log in page shown below, unregister users can regist an account and login. If they don't want to regist, they can just search the ID of the survey they want to engage in, and then they can do voting in the survey they search.
#### 

<table><tr>
<td> <img src="https://i.loli.net/2020/05/29/fLw1Zej27cmEHGD.jpg" alt="Drawing" style="width: 190px;"/> </td>
</tr></table>

### Engage as a guest
You can also engage surveys as a guest without registing for an account.
Just type in the surveyID and then you can attend that survey.

####
![1111](uploads/7fe826863905bcd6bd16c487fdc1961b/1111.jpg)
####
![2222](uploads/838b81569549409abb59d0d29f7336c8/2222.jpg)
####

### Home page
In the home page shown below, after logging in users will get into this page. Users can press check which surveys they have engaged in and surveys they have published. And  Users can also search survey id through the search button on the top of the screen. Users can choose to log out by clicking the button "USER CENTER".

####
![微信图片_20200528132347](uploads/5228e3e2e3bf95fdcb34618c161ff8ee/微信图片_20200528132347.jpg)

### Creating survey page
In this page, you can create a survey by entering a survey title and click start editing your new survey.
####
![微信图片_20200528132355](uploads/b6d097dc9159793c0649313b68f2596c/微信图片_20200528132355.jpg)

### Page for choosing quesion's types
In this page you can set the max choice number participants can choose, which means that 
if set max choice number = 1, this question will be a single choice question.
If you set max choice number = n > 1, this question will be a multiple choice question.
#### 
![微信图片_20200528132355](uploads/0f3b10e0c4e846e43edf0cb1a8415e67/微信图片_20200528132355.jpg)

### Page for editing survey's questions.
In this page, you can create a new question and edit questions you have created. If you created several questions, you can see a list of several questions with their options.
If you complete creating a new survey, you can choose to publish this survey by clicking the button on the top of the screen.
#### 
![微信图片_20200528132403](uploads/7eec3d4b4037b01c81d43f84553de145/微信图片_20200528132403.jpg)

### Page for editing questions.
In this page, you can add new options, delete options and edit options you have added. 
If you complete editing, you can save the question to the survey's question list and jump to the page for editing survey's questions.
#### 

![type](uploads/48460493756d410f8cd1d2c8c3e95b19/type.jpg)
####
If you complete creating a new survey, you can choose to publish this survey by clicking the button on the top of the screen.
####
![complete_survey_and_send](uploads/a8a65a87b45813d770445992a83d225a/complete_survey_and_send.jpg)


### Page for setting of the survey you are going to published
In this page you can set the expire time of the survey you are going to published. 
#### 
![time3](uploads/dc06b438e353ed9c78dd0fa8d737e8d8/time3.jpg)
![time2](uploads/ca51ffd26497a425ba7cf12acdffd179/time2.jpg)

### Page for checking engaged surveys
You can click the "Engaged" button to check those surveys you have engaged in.
####
![微信图片_20200528132347](uploads/8c25c945eac272cf12602616068b136b/微信图片_20200528132347.jpg)
####
![0engaged](uploads/33c43ad844ca7cd1a462d1d7460a8647/0engaged.jpg)
####
![1engaged](uploads/be03dfc73c256fbcd1a76737a71ab695/1engaged.jpg)

#### 
If you have engaged in one survey, you are not allowed to attend the survey again.
![2engaged](uploads/ff70d5eaf83ed6a58ee2ebe70019821d/2engaged.jpg)

### Data visualization
You can see the result of the survey here. The result will be represented as bar chart.
![4engaged](uploads/56c0f45816640d0db815b7b195355f59/4engaged.jpg)