package com.example.csci3130_g2_quick_cash.activities;

import static java.lang.String.*;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.csci3130_g2_quick_cash.R;
import com.example.csci3130_g2_quick_cash.repository.PostRepository;
import com.example.csci3130_g2_quick_cash.repository.UserRepository;
import com.example.csci3130_g2_quick_cash.utils.Config;
import com.example.csci3130_g2_quick_cash.utils.PaymentValidation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.Objects;

/*
    This class initializes a PayPal activity to receive payment for listings.
    @author: Adam Sarty
 */

public class PayPalActivity extends AppCompatActivity {

    ActivityResultLauncher activityResultLauncher;
    private static final int PAYPAL_REQUEST_CODE = 555;
    private static PayPalConfiguration config;
    private UserRepository userRepository;
    private PostRepository postRepository;

    private Button btnPayNow;

    private TextWatcher t;

    private RatingBar ratingBar;

    private FloatingActionButton backButton;

    private EditText edtHourlyAmount;
    private EditText edtHoursAmount;
    private EditText emailOfEmployee;

    private TextView paymentTV;
    private TextView tempUidOfHire;
    private TextView employeeVerify;
    private TextView tempClosedOfUser;
    private TextView tempCompletedOfHire;
    private TextView tempIncomeOfHire;
    private TextView tempRating;
    private TextView postIdBox2;
    private TextView statusLabel;
    private TextView grandTotal;
    private String postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_pal);
        init();
        checkPostingID();
        setTitle();
        setConfig();
        initializeTextWatcher();
        initializeActivityLauncher();
        setListeners();

        postRepository = PostRepository.getInstance();
    }

    private void setConfig() {
        config = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(Config.PAYPAL_CLIENT_ID);
    }

    // Initialization
    private void init() {
        setUserRepository();

        edtHourlyAmount = findViewById(R.id.edtHourlyAmount);
        edtHoursAmount = findViewById(R.id.edtHoursAmount);
        btnPayNow = findViewById(R.id.btnPayNow);
        paymentTV = findViewById(R.id.idTVStatus);
        emailOfEmployee = findViewById(R.id.emailOfEmployee);
        ratingBar = findViewById(R.id.ratingBar);
        employeeVerify = findViewById(R.id.employeeVerify);
        tempUidOfHire = findViewById(R.id.temporaryVariable);
        tempClosedOfUser = findViewById(R.id.temporaryVariable2);
        tempIncomeOfHire = findViewById(R.id.temporaryVariable3);
        tempCompletedOfHire = findViewById(R.id.temporaryVariable4);
        tempRating = findViewById(R.id.temporaryVariable5);
        postIdBox2 = findViewById(R.id.postIdBox2);
        backButton = findViewById(R.id.backBut);
        statusLabel = findViewById(R.id.paymentStatus);
        grandTotal = findViewById(R.id.grandTotal);
    }

    private void setUserRepository() {
        userRepository = UserRepository.getInstance();
    }

    private void setTitle() {
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7C8584")));
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color =#E6E3E4><em>Working Worms</em> get the job done.</font>"));
    }

    private void checkPostingID() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("key");
            postIdBox2.setText(value);
            postId = value;
            String wage = extras.getString("wage");
            edtHourlyAmount.setText(wage);
        }
        else {
            Toast.makeText(this, "Error retrieving Post Identification, please try again later.", Toast.LENGTH_LONG).show();
        }
    }

    private void setListeners() {
        edtHourlyAmount.addTextChangedListener(t);
        edtHoursAmount.addTextChangedListener(t);
        btnPayNow.setOnClickListener((view)-> readyForPayment());
        backButton.setOnClickListener(view -> finish());
    }

    private void readyForPayment() {
        String errorMessage;
        if (!PaymentValidation.isHoursValid(edtHoursAmount.getText().toString().trim())) {
            errorMessage = (getResources().getString(R.string.INVALID_HOURS).trim());
        }
        else if (!PaymentValidation.isHourlyValid(edtHourlyAmount.getText().toString().trim())) {
            errorMessage = (getResources().getString(R.string.INVALID_HOURLY).trim());
        }
        else if (!isEmployeeValid(emailOfEmployee.getText().toString().trim())) {
            errorMessage = "";
        }
        else if (!PaymentValidation.isRatingValid((int) ratingBar.getRating())) {
            errorMessage = "Rating must be greater than 0.";
        }
        else {
            errorMessage = getResources().getString(R.string.EMPTY_STRING).trim();
            Toast.makeText(PayPalActivity.this, "Redirecting to PayPal...", Toast.LENGTH_SHORT).show();
            processPayment();
        }
        statusLabel.setText(errorMessage.trim());
    }

    private void initializeTextWatcher() {
        t = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Lint Debug
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Lint Debug
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if (PaymentValidation.isHourlyValid(edtHourlyAmount.getText().toString().trim()) && PaymentValidation.isHoursValid(edtHoursAmount.getText().toString().trim())) {
                    setTotalMessage();
                }
            }
        };
    }

    private void setTotalMessage() {
        double a = Double.parseDouble(edtHourlyAmount.getText().toString().trim());
        double b = Double.parseDouble(edtHoursAmount.getText().toString().trim());
        double total = a * b;

        String message = "Grand Total: $" + String.format("%.2f", total);
        grandTotal.setText(message);
    }

    private void initializeActivityLauncher() {
        // Initialize result launcher
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                assert result.getData() != null;
                PaymentConfirmation confirmation =
                        result.getData().getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {
                    try {
                        // Getting the payment details
                        String paymentDetails = confirmation.toJSONObject().toString(4);
                        // on below line we are extracting json response and displaying it in a text view.
                        JSONObject payObj = new JSONObject(paymentDetails);
                        String payID = payObj.getJSONObject("response").getString("id");
                        String state = payObj.getJSONObject("response").getString("state");
                        String message = "Payment: " + state + ".\n Payment ID: " + payID + ".\n\nThank you for choosing WorkWorm!";
                        paymentTV.setText(message);
                        postIdBox2.setVisibility(View.INVISIBLE);
                        btnPayNow.setVisibility(View.INVISIBLE);
                        edtHourlyAmount.setVisibility(View.INVISIBLE);
                        edtHoursAmount.setVisibility(View.INVISIBLE);
                        emailOfEmployee.setVisibility(View.INVISIBLE);
                        ratingBar.setVisibility(View.INVISIBLE);
                        employeeVerify.setVisibility(View.INVISIBLE);
                        if(state.equals("approved")) {
                            updateAccountPayee();
                            updateAccountForHire();
                        }
                    } catch (JSONException e) {
                        // handling json exception on below line
                        Log.e("Error", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (result.getResultCode() == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Toast.makeText(PayPalActivity.this, "PayPal Invalid.",
                        Toast.LENGTH_SHORT).show();
            } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                Toast.makeText(PayPalActivity.this, "PayPal Cancelled.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    // total closed jobs of current user plus 1.
    private void updateAccountPayee() {
        String uid = userRepository.getUID();
        int oldClosed = Integer.parseInt(tempClosedOfUser.getText().toString());
        int newClosed = oldClosed + 1;
        DatabaseReference mDatabaseRefUser = FirebaseDatabase.getInstance().getReference("users");
        mDatabaseRefUser.child(uid).child("totalClosedJobs").setValue(newClosed);
    }

    // total hire income + amount
    // total hire completed + 1
    // (stars + rating) / 2
    private void updateAccountForHire() {
        double a = Double.parseDouble(edtHourlyAmount.getText().toString().trim());
        double b = Double.parseDouble(edtHoursAmount.getText().toString().trim());
        double amountMade = a * b;
        amountMade = Math.round(amountMade*100.0)/100.0;

        String uid = tempUidOfHire.getText().toString();

        int oldIncome = Integer.parseInt(tempIncomeOfHire.getText().toString());
        int oldCompleted = Integer.parseInt(tempCompletedOfHire.getText().toString());
        int oldRating = Integer.parseInt(tempRating.getText().toString());
        int rating = (int) ratingBar.getRating();

        int newIncome = (int) (oldIncome + amountMade);
        int newCompleted = oldCompleted + 1;
        int newRating = (oldRating + rating) / 2;

        DatabaseReference mDatabaseRefUser = FirebaseDatabase.getInstance().getReference("users");
        mDatabaseRefUser.child(uid).child("totalCompletedJobs").setValue(newCompleted);
        mDatabaseRefUser.child(uid).child("rating").setValue(newRating);
        mDatabaseRefUser.child(uid).child("totalIncome").setValue(newIncome);

        // Add job to completed history of the hire
        postRepository.completePost(postId, uid);

    }

    private void processPayment() {
        // Getting amount from editText
        String amount = valueOf(Double.parseDouble(edtHourlyAmount.getText().toString().trim()) * Double.parseDouble(edtHoursAmount.getText().toString().trim()));
        // Creating Paypal payment
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(valueOf(amount)),"CAD","WorkWorm",PayPalPayment.PAYMENT_INTENT_SALE);
        // Creating Paypal Payment activity intent
        Intent intent = new Intent(this, PaymentActivity.class);
        // Adding paypal configuration to the intent
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        // Adding paypal payment to the intent
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        // Starting Activity Request launcher
        activityResultLauncher.launch(intent);
    }

    private String convertEmailToDatabaseKey(String email) {
        String newEmail = email.toLowerCase();
        newEmail = newEmail.replace("@", "A");
        newEmail = newEmail.replace(".", "B");
        newEmail = newEmail.replace("_", "C");
        return newEmail;
    }

    /**
     * Validates employee email - is it linked to another account?
     *
     * @param email employee's email
     * @return true/false
     */
    private boolean isEmployeeValid(String email) {
        if (Objects.equals(email, "")) {
            Toast.makeText(PayPalActivity.this, "Please enter an email.", Toast.LENGTH_SHORT).show();
            return false;
        }

        boolean verify;

        String finalNewEmail = convertEmailToDatabaseKey(email);
        FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Button button = findViewById(R.id.btnPayNow);
                String uid = snapshot.child("reference/" + finalNewEmail + "/uid").getValue(String.class);
                if(uid == null) {
                    employeeVerify.setVisibility(View.INVISIBLE);
                    button.setText(R.string.verify_worm);
                    emailOfEmployee.setEnabled(true);
                    Toast.makeText(PayPalActivity.this, "Email doesn't belong to any known Worm.", Toast.LENGTH_SHORT).show();
                }
                else if (uid.replace(" ", "").contentEquals(UserRepository.getInstance().getUID().replace(" ", ""))) {
                    employeeVerify.setVisibility(View.INVISIBLE);
                    button.setText(R.string.verify_worm);
                    emailOfEmployee.setEnabled(true);
                    Toast.makeText(PayPalActivity.this, "You can't complete your own posting!", Toast.LENGTH_SHORT).show();
                }
                else {
                    employeeVerify.setVisibility(View.VISIBLE);
                    button.setText(R.string.make_payment);
                    emailOfEmployee.setEnabled(false);
                    uid = uid.replace(" ", "");
                    tempUidOfHire.setText(uid);
                    Integer oldTotalClosedJobs = snapshot.child("users/" + userRepository.getUID() + "/totalClosedJobs").getValue(Integer.class);
                    Integer oldTotalCompletedJobs = snapshot.child("users/" + uid + "/totalCompletedJobs").getValue(Integer.class);
                    Integer oldTotalIncome = snapshot.child("users/" + uid + "/totalIncome").getValue(Integer.class);
                    Integer oldRating = snapshot.child("users/" + uid + "/rating").getValue(Integer.class);
                    assert oldTotalClosedJobs != null;
                    assert oldTotalCompletedJobs != null;
                    assert oldTotalIncome != null;
                    assert oldRating != null;
                    tempClosedOfUser.setText(String.valueOf(oldTotalClosedJobs));
                    tempIncomeOfHire.setText(String.valueOf(oldTotalIncome));
                    tempCompletedOfHire.setText(String.valueOf(oldTotalCompletedJobs));
                    tempRating.setText(String.valueOf(oldRating));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Debug
            }
        });

        verify = findViewById(R.id.employeeVerify).getVisibility() == View.VISIBLE;

        return verify;
    }
}
