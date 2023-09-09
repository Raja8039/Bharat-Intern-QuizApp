package com.example.quizzz;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private Button startButton;
    private int userScore;
    private ArrayList<Question> questionList;
    private int currentQuestionIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz();
            }
        });

        questionList = new ArrayList<>();
        // Add questions to the list
        questionList.add(new Question("What is the capital of France?",
                new ArrayList<>(Arrays.asList("Paris", "London", "Berlin", "Madrid")),
                0));
        questionList.add(new Question("Which planet is known as the Red Planet?",
                new ArrayList<>(Arrays.asList("Mars", "Jupiter", "Venus", "Saturn")),
                0));
        questionList.add(new Question("Which city is known as/'The Eternal City'/?",
                new ArrayList<>(Arrays.asList("Greece", "Rome", "Paris", "London")),
                1));
    }

    private void startQuiz() {
        setContentView(R.layout.question_layout);
        displayQuestion();
    }

    private void displayQuestion() {
        Question currentQuestion = questionList.get(currentQuestionIndex);

        TextView questionTextView = findViewById(R.id.questionTextView);
        questionTextView.setText(currentQuestion.getQuestionText());

        RadioGroup optionsRadioGroup = findViewById(R.id.optionsRadioGroup);
        optionsRadioGroup.removeAllViews();

        for (int i = 0; i < currentQuestion.getOptions().size(); i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(currentQuestion.getOptions().get(i));
            optionsRadioGroup.addView(radioButton);
        }

        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });
    }

    private void checkAnswer() {
        Question currentQuestion = questionList.get(currentQuestionIndex);
        RadioGroup optionsRadioGroup = findViewById(R.id.optionsRadioGroup);
        int selectedOptionIndex = optionsRadioGroup.indexOfChild(
                findViewById(optionsRadioGroup.getCheckedRadioButtonId()));

        if (selectedOptionIndex == currentQuestion.getCorrectOptionIndex()) {
            // Correct answer
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
        } else {
            // Incorrect answer
            Toast.makeText(this, "Incorrect. The correct answer was: " +
                            currentQuestion.getOptions().get(currentQuestion.getCorrectOptionIndex()),
                    Toast.LENGTH_SHORT).show();
        }

        currentQuestionIndex++;
        if (currentQuestionIndex < questionList.size()) {
            displayQuestion();
        } else {
            showResult();
        }
    }

    @SuppressLint("SetTextI18n")
    private void showResult() {
        setContentView(R.layout.result_layout);
        TextView resultTextView = findViewById(R.id.resultTextView);

        // Calculate and display the user's score
        int score = calculateScore();
        resultTextView.setText("Your Score: " + score + " out of " + questionList.size());

        Button restartButton = findViewById(R.id.restartButton);
        restartButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                restartQuiz();
            }
        });
    }

    private int calculateScore() {
        // Calculate the user's score based on correct answers
        userScore = 0;
        for (Question question : questionList) {
            if (question.getCorrectOptionIndex() == questionList.indexOf(question)) {
                userScore++;
            }
        }
        return userScore;
    }

    private void restartQuiz() {
        currentQuestionIndex = 0;
        startQuiz();
    }
}
