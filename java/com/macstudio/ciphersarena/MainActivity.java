package com.macstudio.ciphersarena;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText ceasarkey, ceasarText, affinekeyA, affinekeyB;
    private Button resultbtn;
    private ImageView ceasarImg, affineImg, rsaImg, migImg, catebgimg, resultbtnbg;
    private TextView resultText, ceasarcatetext, affinecatetext, rsacatetext, migcatetext;
    private LinearLayout affinekeyRow;
    private String cipherCode, affineCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        affinekeyRow = findViewById(R.id.affinekeyRow);
        ceasarkey = findViewById(R.id.ceasarkey);
        ceasarText = findViewById(R.id.plaintext);
        affinekeyA = findViewById(R.id.affinekeyA);
        affinekeyB = findViewById(R.id.affinekeyB);
        resultbtn = findViewById(R.id.resultbtn);
        ceasarImg = findViewById(R.id.ceasarimg);
        affineImg = findViewById(R.id.affineimg);
        rsaImg = findViewById(R.id.rsaimg);
        migImg = findViewById(R.id.migimg);
        catebgimg = findViewById(R.id.catebgimg);
        resultbtnbg = findViewById(R.id.resultbtnbg);
        resultText = findViewById(R.id.resulttext);
        ceasarcatetext = findViewById(R.id.ceasarcatetext);
        affinecatetext = findViewById(R.id.affinecatetext);
        rsacatetext = findViewById(R.id.rsacatetext);
        migcatetext = findViewById(R.id.migcatetext);

        resultbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ceasarImg.getVisibility() == View.VISIBLE){
                    if ( !(ceasarkey.getText().toString().trim().equals("")) && !(ceasarText.getText().toString().trim().equals(""))){
                        int key = Integer.parseInt(ceasarkey.getText().toString());
                        if (resultbtn.getText().toString().trim().equals("ENCRYPT")) ceasarencrypt(key);
                        else ceasardecrypt(key);
                    }
                    else Toast.makeText(getApplicationContext(), "Fill all details!", Toast.LENGTH_SHORT).show();
                }
                else if (affineImg.getVisibility() == View.VISIBLE){
                    if ( !(affinekeyA.getText().toString().trim().equals("")) && !(affinekeyB.getText().toString().trim().equals("")) && !(ceasarText.getText().toString().trim().equals(""))){
                        int keya = Integer.parseInt(affinekeyA.getText().toString());
                        int keyb = Integer.parseInt(affinekeyB.getText().toString());
                        if (resultbtn.getText().toString().trim().equals("ENCRYPT"))
                            affineencrypt(keya, keyb);
                        else affinedecrypt(keya, keyb);
                    }
                    else Toast.makeText(getApplicationContext(), "Fill all details!", Toast.LENGTH_SHORT).show();
                }
                else if (rsaImg.getVisibility() == View.VISIBLE){
                    if (  !(affinekeyA.getText().toString().trim().equals("")) && !(affinekeyB.getText().toString().trim().equals(""))){
                        int keya = Integer.parseInt(affinekeyA.getText().toString());
                        int keyb = Integer.parseInt(affinekeyB.getText().toString());
                        if (isPrime(keya) && isPrime(keyb)) {
                            rsagenerate(keya, keyb);
                        }
                        else Toast.makeText(getApplicationContext(), "Both numbers must be prime.", Toast.LENGTH_SHORT).show();
                    }
                    else Toast.makeText(getApplicationContext(), "Fill all details!", Toast.LENGTH_SHORT).show();
                }
                else if (migImg.getVisibility() == View.VISIBLE){
                    if ( !(affinekeyA.getText().toString().trim().equals("")) && !(affinekeyB.getText().toString().trim().equals(""))){
                        int key = Integer.parseInt(affinekeyA.getText().toString());
                        int n = Integer.parseInt(affinekeyB.getText().toString());
                        if (key != 0) {
                            mmigenerate(key, n);
                        }
                        else Toast.makeText(getApplicationContext(), "Number can't be zero.", Toast.LENGTH_SHORT).show();
                    }
                    else Toast.makeText(getApplicationContext(), "Fill all details!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ceasarcatetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(ceasarImg.getVisibility() == View.VISIBLE)) {
                    ceasarText.setText("");
                    ceasarkey.setText("");
                    affinekeyRow.setVisibility(View.GONE);
                    ceasarkey.setVisibility(View.VISIBLE);
                    ceasarText.setHint("Enter plain text: ");
                    ceasarText.setVisibility(View.VISIBLE);
                    resultText.setText("");
                    resultbtnbg.animate().alpha(1.0f).setDuration(250).start();
                    resultbtn.animate().translationX(0).setDuration(500).setInterpolator(new DecelerateInterpolator()).start();
                    resultbtn.setText("ENCRYPT");
                    if (affineImg.getVisibility() == View.VISIBLE){
                        affineImg.animate().alpha(0.0f).setDuration(250).start();
                        affineImg.setVisibility(View.GONE);
                        affinecatetext.setTextColor(getColor(android.R.color.tertiary_text_dark));
                    } else if (rsaImg.getVisibility() == View.VISIBLE){
                        rsaImg.animate().alpha(0.0f).setDuration(250).start();
                        rsaImg.setVisibility(View.GONE);
                        rsacatetext.setTextColor(getColor(android.R.color.tertiary_text_dark));
                    }else if (migImg.getVisibility() == View.VISIBLE){
                        migImg.animate().alpha(0.0f).setDuration(250).start();
                        migImg.setVisibility(View.GONE);
                        migcatetext.setTextColor(getColor(android.R.color.tertiary_text_dark));
                    }
                    catebgimg.animate().translationX(0).setDuration(250).setInterpolator(new DecelerateInterpolator()).start();
                    ceasarImg.setVisibility(View.VISIBLE);
                    ceasarImg.animate().alpha(1.0f).setDuration(250).start();
                    ceasarcatetext.setTextColor(getColor(android.R.color.background_light));
                    ceasarcatetext.setWidth((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics()));
                    ceasarcatetext.requestLayout();
                }
            }
        });

        affinecatetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(affineImg.getVisibility() == View.VISIBLE)) {
                    ceasarText.setText("");
                    affinekeyA.setText("");
                    affinekeyB.setText("");
                    ceasarkey.setVisibility(View.GONE);
                    ceasarText.setHint("Enter plain text:");
                    affinekeyA.setHint("Enter first key: ");
                    affinekeyB.setHint("Enter second key: ");
                    ceasarText.setVisibility(View.VISIBLE);
                    affinekeyRow.setVisibility(View.VISIBLE);
                    resultText.setText("");
                    resultbtnbg.animate().alpha(1.0f).setDuration(250).start();
                    resultbtn.animate().translationX(0).setDuration(500).setInterpolator(new DecelerateInterpolator()).start();
                    resultbtn.setText("ENCRYPT");
                    if (ceasarImg.getVisibility() == View.VISIBLE){
                        ceasarImg.animate().alpha(0.0f).setDuration(250).start();
                        ceasarImg.setVisibility(View.GONE);
                        ceasarcatetext.setTextColor(getColor(android.R.color.tertiary_text_dark));
                    } else if (rsaImg.getVisibility() == View.VISIBLE){
                        rsaImg.animate().alpha(0.0f).setDuration(250).start();
                        rsaImg.setVisibility(View.GONE);
                        rsacatetext.setTextColor(getColor(android.R.color.tertiary_text_dark));
                    }else if (migImg.getVisibility() == View.VISIBLE){
                        migImg.animate().alpha(0.0f).setDuration(250).start();
                        migImg.setVisibility(View.GONE);
                        migcatetext.setTextColor(getColor(android.R.color.tertiary_text_dark));
                    }
                    catebgimg.animate().translationX(160).setDuration(250).setInterpolator(new DecelerateInterpolator()).start();
                    affineImg.setVisibility(View.VISIBLE);
                    affineImg.animate().alpha(1.0f).setDuration(250).start();
                    affinecatetext.setTextColor(getColor(android.R.color.background_light));
                    affinecatetext.setWidth((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics()));
                    affinecatetext.requestLayout();
                }
            }
        });

        rsacatetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(rsaImg.getVisibility() == View.VISIBLE)) {
                    ceasarText.setText("");
                    affinekeyA.setText("");
                    affinekeyB.setText("");
                    ceasarkey.setVisibility(View.GONE);
                    ceasarText.setVisibility(View.GONE);
                    affinekeyRow.setVisibility(View.VISIBLE);
                    affinekeyA.setHint("Enter first prime: ");
                    affinekeyB.setHint("Enter second prime: ");
                    resultText.setText("");
                    resultbtnbg.animate().alpha(0.0f).setDuration(250).start();
                    resultbtn.animate().translationX(63.5f).setDuration(500).setInterpolator(new DecelerateInterpolator()).start();
                    resultbtn.setText("GENERATE");
                    if (ceasarImg.getVisibility() == View.VISIBLE){
                        ceasarImg.animate().alpha(0.0f).setDuration(250).start();
                        ceasarImg.setVisibility(View.GONE);
                        ceasarcatetext.setTextColor(getColor(android.R.color.tertiary_text_dark));
                    } else if (affineImg.getVisibility() == View.VISIBLE){
                        affineImg.animate().alpha(0.0f).setDuration(250).start();
                        affineImg.setVisibility(View.GONE);
                        affinecatetext.setTextColor(getColor(android.R.color.tertiary_text_dark));
                    }else if (migImg.getVisibility() == View.VISIBLE){
                        migImg.animate().alpha(0.0f).setDuration(250).start();
                        migImg.setVisibility(View.GONE);
                        migcatetext.setTextColor(getColor(android.R.color.tertiary_text_dark));
                    }
                    catebgimg.animate().translationX(320).setDuration(250).setInterpolator(new DecelerateInterpolator()).start();
                    rsaImg.setVisibility(View.VISIBLE);
                    rsaImg.animate().alpha(1.0f).setDuration(250).start();
                    rsacatetext.setTextColor(getColor(android.R.color.background_light));
                    rsacatetext.setWidth((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics()));
                    rsacatetext.requestLayout();
                }
            }
        });

        migcatetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(migImg.getVisibility() == View.VISIBLE)) {
                    ceasarText.setText("");
                    affinekeyA.setText("");
                    affinekeyB.setText("26");
                    ceasarText.setVisibility(View.GONE);
                    ceasarkey.setVisibility(View.GONE);
                    affinekeyA.setHint("Enter number: ");
                    affinekeyRow.setVisibility(View.VISIBLE);
                    resultText.setText("");
                    resultbtnbg.animate().alpha(0.0f).setDuration(250).start();
                    resultbtn.animate().translationX(63.5f).setDuration(500).setInterpolator(new DecelerateInterpolator()).start();
                    resultbtn.setText("GENERATE");
                    if (ceasarImg.getVisibility() == View.VISIBLE){
                        ceasarImg.animate().alpha(0.0f).setDuration(250).start();
                        ceasarImg.setVisibility(View.GONE);
                        ceasarcatetext.setTextColor(getColor(android.R.color.tertiary_text_dark));
                    } else if (rsaImg.getVisibility() == View.VISIBLE){
                        rsaImg.animate().alpha(0.0f).setDuration(250).start();
                        rsaImg.setVisibility(View.GONE);
                        rsacatetext.setTextColor(getColor(android.R.color.tertiary_text_dark));
                    }else if (affineImg.getVisibility() == View.VISIBLE){
                        affineImg.animate().alpha(0.0f).setDuration(250).start();
                        affineImg.setVisibility(View.GONE);
                        affinecatetext.setTextColor(getColor(android.R.color.tertiary_text_dark));
                    }
                    catebgimg.animate().translationX(480).setDuration(250).setInterpolator(new DecelerateInterpolator()).start();
                    migImg.setVisibility(View.VISIBLE);
                    migImg.animate().alpha(1.0f).setDuration(250).start();
                    migcatetext.setTextColor(getColor(android.R.color.background_light));
                    migcatetext.setWidth((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics()));
                    migcatetext.requestLayout();
                }
            }
        });

    }

    public Boolean isPrime(int no){
        int fl = 1;
        if (no==2){
            return true;
        }
        for (int i=2; i<no; i++){
            if (no%i==0){
                fl=0;
                break;
            }
        }
        return (fl == 1) ;
    }

    public void ceasarencrypt(int key){
        cipherCode = ""; int ind = 0;
        String plainText = ceasarText.getText().toString().trim();
        for (int i = 0; i < plainText.length(); i++) {
            int i_c = (int) plainText.charAt(i);
            if (i_c == 32) {
                cipherCode += " ";
                continue;
            }
            if ((i_c >= 65) && (i_c <= 90)) {
                ind = i_c - 65;
                char ch = (char) (65 + ((ind + key) % 26));
                cipherCode += ch;
            } else {
                ind = i_c - 97;
                char ch = (char) (97 + ((ind + key) % 26));
                cipherCode += ch;
            }
        }
        resultText.setText("Encrypted text: " + cipherCode);
        resultbtn.animate().translationX(127).setDuration(500).start();
        resultbtn.setText("DECRYPT");
    }

    public void ceasardecrypt(int key){
        String reEncryptedText = "";    int ind = 0;
        for (int i = 0; i < cipherCode.length(); i++) {
            int i_c = (int) cipherCode.charAt(i);
            if(i_c == 32){
                reEncryptedText += " ";
                continue;
            }
            if ((i_c >= 65) && (i_c <= 90)) {
                ind = i_c - 65;     char ch;
                if ( (ind-key) < 0){
                    ch = (char) (65 + 26-(-(ind - key) % 26));
                }
                else{
                    ch = (char) (65 + ((ind - key) % 26));
                }

                reEncryptedText += ch;
            } else {
                ind = i_c - 97;    char ch;
                if ( (ind-key) < 0){
                    ch = (char) (97 + 26-(-(ind - key) % 26));
                }
                else{
                    ch = (char) (97 + ((ind - key) % 26));
                }
                reEncryptedText += ch;
            }
        }

        resultText.setText("Decrypted text: " + reEncryptedText);
        resultbtn.animate().translationX(0).setDuration(500).start();
        resultbtn.setText("ENCRYPT");
    }

    public void affineencrypt(int keya, int keyb){
        String plainText = ceasarText.getText().toString().trim();
        affineCode = "";
        int a = keya;
        int b = keyb;
        for (int i = 0; i < plainText.length(); i++) {
            int i_c = (int) plainText.charAt(i);
            if (i_c == 32) {
                affineCode += " ";
                continue;
            }
            if ((i_c >= 65) && (i_c <= 90)) {
                int ind = i_c - 65;
                char ch = (char) (65 + ((a * ind + b)) % 26);
                affineCode += ch;
            } else {
                int ind = i_c - 97;
                char ch = (char) (97 + ((a * ind + b)) % 26);
                affineCode += ch;
            }
        }
        resultText.setText("Encrypted text: " + affineCode);
        resultbtn.animate().translationX(127).setDuration(500).setInterpolator(new DecelerateInterpolator()).start();
        resultbtn.setText("DECRYPT");
    }

    public void affinedecrypt(int keya, int keyb){
        String reEncryptedText = "";
        int a = keya; int b = keyb;
        int inv = 1;
        for (int i = 0; i < 26; i++) {
            if ((a * i) % 26 == 1) {
                inv = i;
                break;
            }
        }

        for (int i = 0; i < affineCode.length(); i++) {
            int i_c = (int) affineCode.charAt(i);
            if (i_c == 32) {
                reEncryptedText += " ";
                continue;
            }
            if ((i_c >= 65) && (i_c <= 90)) {
                int ind = i_c - 65; char ch;
                if ((inv * ind - inv * b) < 0){
                    ch = (char) (65 + (26-(-(inv * ind - inv * b) % 26)));
                }
                else{
                    ch = (char) (65 + ((inv * ind - inv * b) % 26));
                }

                reEncryptedText += ch;
            } else {
                int ind = i_c - 97;     char ch;
                if ((inv * ind - inv * b) < 0){
                    ch = (char) (97 + (26-(-(inv * ind - inv * b) % 26)));
                }
                else{
                    ch = (char) (97 + ((inv * ind - inv * b) % 26));
                }
                reEncryptedText += ch;
            }
        }
        resultText.setText("Decrypted text: " + reEncryptedText);
        resultbtn.animate().translationX(0).setDuration(500).setInterpolator(new DecelerateInterpolator()).start();
        resultbtn.setText("ENCRYPT");
    }

    public void rsagenerate(int p, int q){
        int n,cf,e=1,d=1,i,j,fl=0;
        n = p*q;
        cf = (p-1) * (q-1);
        ArrayList<Integer> fac_n = new ArrayList<Integer>();
        for (i=2;i<=n/2;i++){
            if (n%i==0){
                fac_n.add(i);
            }
        }
        ArrayList<Integer> fac_cf = new ArrayList<Integer>();
        for (i=2;i<=cf/2;i++){
            if (cf%i==0){
                fac_cf.add(i);
            }
        }
        HashSet<Integer> fac = new HashSet<Integer>();
        fac.addAll(fac_n);
        fac.addAll(fac_cf);
        List<Integer> lst = new ArrayList<Integer>(fac);
        Collections.sort(lst);
        for (i=2;i<cf;i++){
            if (!(lst.contains(i))){
                for (j=2;j<=i/2;j++){
                    fl=0;
                    if (i%j==0){
                        if (lst.contains(j)){
                            fl=1;
                            break;
                        }
                    }
                }
                if (fl==0) {
                    e=i;
                    break;
                }
            }
        }
        i=2;
        while (true){
            if ((i!=e) && ((e * i)%cf == 1)){
                d = i;
                break;
            }
            i++;
        }
        resultText.setText("Encryption key: ("+e+", "+n+")\nDecryption key: ("+d+", "+n+")");
    }

    public void mmigenerate(int a, int n){
        int b=0, i;
        if (a > 0) {
            for (i = 2; i < n; i++) {
                if ((a * i) % n == 1) {
                    b = i;
                    break;
                }
            }
        }
        else{
            a = -a;
            for (i = 2; i < n; i++) {
                if ((a * i) % n == 1) {
                    b = i;
                    break;
                }
            }
            b = n-b;
        }
        resultText.setText("result: "+b);
    }
}
