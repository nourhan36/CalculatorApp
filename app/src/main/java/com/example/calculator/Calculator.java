package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Calculator extends AppCompatActivity {

    TextView resultTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        resultTV = findViewById(R.id.result_tv);
    }

    private boolean hasDot = false;
    private String sqrtOperator = "";
    private boolean operatorClicked = false;

    String savedNum = "";
    String savedOperator = "";
    public void onDigitClick(View view) {
        Button btn = ((Button) view);
        String buttonText = btn.getText().toString();

        if (buttonText.equals(".")) {
            if (!hasDot && !resultTV.getText().toString().contains(".")) {
                resultTV.append(buttonText);
                hasDot = true;
            }
        } else {
            resultTV.append(buttonText);
        }
    }

    public void onOperatorClick(View view) {
        Button clickedOperator = ((Button) view);
        if (!operatorClicked) {
            if (savedNum.isEmpty()) {
                savedNum = resultTV.getText().toString();
            } else if (!savedOperator.isEmpty()) {
                String rhs = resultTV.getText().toString();
                savedNum = calculate(savedNum, savedOperator, rhs);
            }
            savedOperator = clickedOperator.getText().toString();
            resultTV.setText("");
            operatorClicked = true;
        }
    }

    public void onSqrtClick(View view) {
        sqrtOperator = "√";
        resultTV.setText("√");
    }

    public void onClearClick(View view) {
        resultTV.setText("");
        savedNum = "";
        savedOperator = "";
        hasDot = false;
    }

    public void onBackClick(View view) {
        int length = resultTV.getText().length();
        if (length > 0)
            resultTV.setText(resultTV.getText().subSequence(0, length - 1));
    }

    public void onEqualClick(View view) {
        String rhs = resultTV.getText().toString();
        if (sqrtOperator.isEmpty()) {
            if (!savedOperator.isEmpty()) {
                String result = calculate(savedNum, savedOperator, rhs);
                resultTV.setText(result);
                savedOperator = "";
                savedNum = "";
                operatorClicked = false;
            }
        } else {
            if (rhs.equals("√")) {
                Toast.makeText(this, "Enter a number after the square root operation", Toast.LENGTH_SHORT).show();
                resultTV.setText("");
            } else {
                double num = Double.parseDouble(rhs.substring(1));
                if (num >= 0) {
                    double sqrtResult = Math.sqrt(num);
                    resultTV.setText(String.valueOf(sqrtResult));
                } else {
                    Toast.makeText(this, "Can't sqrt -ve num", Toast.LENGTH_SHORT).show();
                    resultTV.setText("");
                }
                sqrtOperator = "";
            }
        }
    }

    public String calculate(String lhs, String operator, String rhs) {
        double num1 = Double.parseDouble(lhs);
        double num2 = Double.parseDouble(rhs);
        double result = 0;
        if ("/".equals(operator)) {
            if (num2 != 0)
                result = num1 / num2;
            else
                return "Error: Division by zero";
        } else {
            if ("+".equals(operator))
                result = num1 + num2;
            else if ("-".equals(operator))
                result = num1 - num2;
            else if ("*".equals(operator))
                result = num1 * num2;
            else if ("%".equals(operator))
                result = num1 % num2;
        }

        return result + "";
    }

}