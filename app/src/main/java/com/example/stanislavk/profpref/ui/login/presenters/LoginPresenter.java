package com.example.stanislavk.profpref.ui.login.presenters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.example.stanislavk.profpref.di.services.firebase.models.ModelStudent;
import com.example.stanislavk.profpref.ui.base.presenters.BasePresenter;
import com.example.stanislavk.profpref.di.services.firebase.models.ModelSettings;
import com.example.stanislavk.profpref.di.services.firebase.models.Test.ModelStateTesting;
import com.example.stanislavk.profpref.ui.login.views.LoginView;
import com.example.stanislavk.profpref.utils.SharedPreferenceUtils;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import durdinapps.rxfirebase2.RxFirebaseAuth;
import durdinapps.rxfirebase2.RxFirebaseDatabase;

import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.FIREBASE_STUDENTS;
import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.FIREBASE_STUDENT_COUNTER_LOGIN;
import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.FIREBASE_STUDENT_CURRENT_TEST;
import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.FIREBASE_STUDENT_STATE_ON_RESULT;
import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.FIREBASE_STUDENT_STATE_ON_TEST;
import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.FIREBASE_STUDENT_TEST_SETTINGS;
import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.FIREBASE_STUDENT_TEST_STATE;
import static com.example.stanislavk.profpref.di.services.firebase.FireBaseService.FIREBASE_TESTS;

/**
 * Created by LasVegas on 27.06.2017.
 */
@InjectViewState
public class LoginPresenter extends BasePresenter<LoginView> {

  public static int DROP_LOGIN = 0;
  public static int DROP_PASSWORD = 1;

  private ModelStudent mCurrentStudent = new ModelStudent();
  private SharedPreferenceUtils mSharedPreferenceUtils;

  private ArrayList<ModelStudent> mStudentsList= new ArrayList<ModelStudent> ();

  private String TAG_ERROR = "LoginPresenter";


  public LoginPresenter() {

      FirebaseUser coachCash = mCoreServices.getFireBaseService().getAuth().getCurrentUser();

      if (coachCash != null) {
          getUsersFromFB(coachCash);
      } else {
          getViewState().onShowCoachLoginDialog();
      }

  }

  public void checkAuthFromSharedPref(Context context) {
      mSharedPreferenceUtils = SharedPreferenceUtils.getInstance(context);

      String studentKey = mSharedPreferenceUtils.getStringValue("studentKey", "");

      if (!studentKey.equals("")) {
          String studentPass = mSharedPreferenceUtils.getStringValue("password", "");
          mCurrentStudent.setKey(studentKey);
          checkPassword(studentPass);

      }
  }

  @SuppressLint("CheckResult")
  public void coachLogin(String login, String password) {

      login = login.trim();

      RxFirebaseAuth.signInWithEmailAndPassword(mCoreServices.getFireBaseService().getAuth(),
              login, password.trim())
              .subscribe(coach-> {
                  getUsersFromFB(coach.getUser());
              }, throwable -> {
                  Log.e(TAG_ERROR, throwable.toString());
              });

  }

  private void getUsersFromFB(FirebaseUser coach) {
      Query userSearchQuery =  mCoreServices.getFireBaseService().getDatabase()
              .child(FIREBASE_STUDENTS)
              .orderByChild("trainer_ID")
              .equalTo(coach.getUid());

      RxFirebaseDatabase.observeSingleValueEvent(userSearchQuery)
              .subscribe(students -> {
                  for (DataSnapshot postSnapshot: students.getChildren()) {

                      ModelStudent student = new ModelStudent();
                      student.setKey(postSnapshot.getKey());
                      student.setLogin(postSnapshot.child("login").getValue().toString());

                      mStudentsList.add(student);
                  }
              });

  }

  @SuppressLint("CheckResult")
  public void checkLogin(String login) {

      for (ModelStudent student : mStudentsList) {
          if (student.getLogin().length() > 2 &&
              login.equals(student.getLogin().substring(0,3))) {

              mCurrentStudent.setKey(student.getKey());
              mSharedPreferenceUtils.setValue("studentKey", student.getKey());

              mCoreServices.getFireBaseService().setModelStudent(mCurrentStudent);

              Query query = mCoreServices.getFireBaseService().getDatabase()
                      .child(FIREBASE_STUDENTS)
                      .child(mCurrentStudent.getKey())
                      .orderByKey()
                      .equalTo(FIREBASE_STUDENT_COUNTER_LOGIN);

              query.addListenerForSingleValueEvent(new ValueEventListener() {

                  @Override
                  public void onDataChange(DataSnapshot dataSnapshot) {
                      long counter = 0;
                      if(dataSnapshot.getValue() == null){
                          RxFirebaseDatabase.setValue(mCoreServices.getFireBaseService().getDatabase().child(FIREBASE_STUDENTS)
                                  .child(mCurrentStudent.getKey())
                                  .child(FIREBASE_STUDENT_COUNTER_LOGIN), counter).subscribe(
                                  () -> {
                                      getCurrentTest(mCoreServices.getFireBaseService().getDatabase(),
                                              mCoreServices.getFireBaseService().getCurrentUser().getKey());
                                  });
                      } else {

                          HashMap counter_value =  (HashMap) dataSnapshot.getValue();
                          counter = (long)counter_value.get(FIREBASE_STUDENT_COUNTER_LOGIN);
                          counter++;

                          RxFirebaseDatabase.setValue(mCoreServices.getFireBaseService().getDatabase().child(FIREBASE_STUDENTS)
                                  .child(mCurrentStudent.getKey())
                                  .child(FIREBASE_STUDENT_COUNTER_LOGIN), counter).subscribe(
                                  () -> {
                                      getCurrentTest(mCoreServices.getFireBaseService().getDatabase(),
                                              mCoreServices.getFireBaseService().getCurrentUser().getKey());
                                  });
                      }
                  }

                  @Override
                  public void onCancelled(DatabaseError databaseError) {

                  }
              });
          }
      }
  }

  public void checkPassword(String passwrod) {
      Query password =  mCoreServices.getFireBaseService().getDatabase()
              .child(FIREBASE_STUDENTS)
              .child(mCurrentStudent.getKey())
              .child("password");

      RxFirebaseDatabase.observeSingleValueEvent(password, String.class)
              .subscribe(uesrPassword -> {

                  if (passwrod.equals(uesrPassword)) {

                      mSharedPreferenceUtils.setValue("password", uesrPassword);

                      mCoreServices.getFireBaseService().setModelStudent(mCurrentStudent);

                      Query query = mCoreServices.getFireBaseService().getDatabase()
                              .child(FIREBASE_STUDENTS)
                              .child(mCurrentStudent.getKey())
                              .orderByKey()
                              .equalTo(FIREBASE_STUDENT_COUNTER_LOGIN);

                      query.addListenerForSingleValueEvent(new ValueEventListener() {

                          @Override
                          public void onDataChange(DataSnapshot dataSnapshot) {
                              long counter = 0;
                              if(dataSnapshot.getValue() == null){
                                  RxFirebaseDatabase.setValue(mCoreServices.getFireBaseService().getDatabase().child(FIREBASE_STUDENTS)
                                          .child(mCurrentStudent.getKey())
                                          .child(FIREBASE_STUDENT_COUNTER_LOGIN), counter).subscribe(
                                          () -> {
                                              getCurrentTest(mCoreServices.getFireBaseService().getDatabase(),
                                                      mCoreServices.getFireBaseService().getCurrentUser().getKey());
                                          });
                              } else {

                                  HashMap counter_value =  (HashMap) dataSnapshot.getValue();
                                  counter = (long)counter_value.get(FIREBASE_STUDENT_COUNTER_LOGIN);
                                  counter++;

                                  RxFirebaseDatabase.setValue(mCoreServices.getFireBaseService().getDatabase().child(FIREBASE_STUDENTS)
                                          .child(mCurrentStudent.getKey())
                                          .child(FIREBASE_STUDENT_COUNTER_LOGIN), counter).subscribe(
                                          () -> {
                                              getCurrentTest(mCoreServices.getFireBaseService().getDatabase(),
                                                      mCoreServices.getFireBaseService().getCurrentUser().getKey());
                                          });
                              }
                          }

                          @Override
                          public void onCancelled(DatabaseError databaseError) {

                          }
                      });
                  } else {
                      getViewState().onDropInputField(DROP_PASSWORD);
                      getViewState().onInVisibleProgressBar();
                  }
              },throwable -> getViewState().onShowInfoMsg("Ошибка логина"));
  }
  private void getCurrentTest(DatabaseReference database, String key){

      database.child(FIREBASE_STUDENTS).child(key).child(FIREBASE_STUDENT_CURRENT_TEST).addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {

              String current_test =  dataSnapshot.getValue().toString();
              Query query = database
                      .child(FIREBASE_STUDENTS)
                      .child(key)
                      .child(FIREBASE_TESTS)
                      .child(current_test)
                      .child(FIREBASE_STUDENT_TEST_SETTINGS);

              mCoreServices.getFireBaseService().setCurrentUserTest(current_test);

              query.addListenerForSingleValueEvent(new ValueEventListener() {

                  @Override
                  public void onDataChange(DataSnapshot dataSnapshot) {
                      if(dataSnapshot.getValue() != null) {

                          mCoreServices.getFireBaseService().setSettingsTest(dataSnapshot.getValue(ModelSettings.class));

                          getCurrentStayTest(database, key);
                       } else {
                          getViewState().onShowInfoMsg("Ошибка при получение настроек теста");
                      }
                      }

                  @Override
                  public void onCancelled(DatabaseError databaseError) {
                      getViewState().onShowInfoMsg("Ошибка сервера");
                  }
              });

          }

          @Override
          public void onCancelled(DatabaseError databaseError) {

          }
      });
  }

  private void getCurrentStayTest(DatabaseReference database, String key){

      Query query = database
              .child(FIREBASE_STUDENTS)
              .child(key)
              .child(FIREBASE_STUDENT_TEST_STATE);

      RxFirebaseDatabase.observeSingleValueEvent(query, ModelStateTesting.class)
              .subscribe(modelStateTesting -> {

                  mCoreServices.getFireBaseService().setModelStateTesting(modelStateTesting);

                  if (modelStateTesting.state.equals(FIREBASE_STUDENT_STATE_ON_TEST)) {

                      getViewState().onTestScreen();
                      getViewState().onInVisibleProgressBar();

                  } else if (modelStateTesting.state.equals(FIREBASE_STUDENT_STATE_ON_RESULT)) {

                      getViewState().onResultScreen();
                      getViewState().onInVisibleProgressBar();

                  } else {

                      getViewState().onNextScreen();
                      getViewState().onInVisibleProgressBar();

                  }
              });

  }
}

