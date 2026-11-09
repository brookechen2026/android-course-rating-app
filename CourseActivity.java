package edu.illinois.cs.cs124.ay2023.mp.activities;

import static edu.illinois.cs.cs124.ay2023.mp.helpers.Helpers.OBJECT_MAPPER;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import edu.illinois.cs.cs124.ay2023.mp.R;
import edu.illinois.cs.cs124.ay2023.mp.models.Course;
import edu.illinois.cs.cs124.ay2023.mp.models.Rating;
import edu.illinois.cs.cs124.ay2023.mp.models.Summary;
import java.util.List;
import java.util.Scanner;
import edu.illinois.cs.cs124.ay2023.mp.application.CourseableApplication;
import edu.illinois.cs.cs124.ay2023.mp.network.Server;



public class CourseActivity extends AppCompatActivity {
  private static final String TAG = CourseActivity.class.getSimpleName();


  @Override
  protected void onCreate(@Nullable Bundle unused) {
    setContentView(R.layout.activity_course);
    super.onCreate(unused);
    setContentView(R.layout.activity_course);


    // Set up UI: make linkages between the code here and the UI elements
    TextView descriptionTextView = findViewById(R.id.description);
    RatingBar ratingBar = findViewById(R.id.rating);

    String sumJson = getIntent().getStringExtra("summary");
    Summary summary = new Summary();
    try {
      summary = OBJECT_MAPPER.readValue(sumJson, Summary.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    String json = new Scanner(Server.class.getResourceAsStream("/courses.json"),
        "UTF-8").useDelimiter("\\A").next();
    String string = summary.getSubject() + " " + summary.getNumber() + ": " + summary.getLabel();
    List<Course> courses = new ArrayList<>();
    Course want = new Course();
    try {
      JsonNode nodes = OBJECT_MAPPER.readTree(json);
      for (JsonNode node : nodes) {
        Course course = OBJECT_MAPPER.readValue(node.toString(), Course.class);
        courses.add(course);
      }

    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    for (Course course : courses) {
      if (course.getSubject().equals(summary.getSubject())
          && course.getNumber().equals(summary.getNumber())
          && course.getLabel().equals(summary.getLabel())) {
        want = course;
      }
    }


    String des = want.getDescription();
    String all = string + "\n" + des;
    runOnUiThread(() -> {
      descriptionTextView.setText(all);

    });
    CourseableApplication app = (CourseableApplication) getApplication();
    app.getClient().getRating(summary, ratingResultMightThrow -> {
      if (null != ratingResultMightThrow && null != ratingResultMightThrow.getValue()) {
        ratingBar.setRating(ratingResultMightThrow.getValue().getRating());
      }
    });
    Summary finalSummary = summary;
    ratingBar.setOnRatingBarChangeListener((ratingBar1, rating, fromUser)
        -> app.getClient().postRating(new Rating(finalSummary, rating), ratingResultMightThrow -> {
          if (null != ratingResultMightThrow && null != ratingResultMightThrow.getValue()) {
            ratingBar1.setRating(ratingResultMightThrow.getValue().getRating());
          }
        }));


  }

  //callback for when rating is changed

}
