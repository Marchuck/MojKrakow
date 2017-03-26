package pl.mojkrakow.mojkrakow.email;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import pl.mojkrakow.mojkrakow.MainActivity;

/**
 * Project "MojKrakow"
 * <p>
 * Created by Lukasz Marczak
 * on 26.03.2017.
 */

public class ZikitEmailSender implements Sendable {

    //    String recipient = "sekretariat@zikit.krakow.pl";
    String recipient = "lukmar993@gmail.com";

    public String subject = "";
    public String text = "";
    public Uri imageUri;
    Fragment a;

    public ZikitEmailSender(Fragment a) {
        this.a = a;
    }

    @Override
    public void send() {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{recipient});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);

        if (imageUri != null) {
            emailIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        }
        try {

            a.startActivityForResult(Intent.createChooser(emailIntent, "Zgłoś ZIKIT"), MainActivity.EMAIL_SEND);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(a.getActivity(), "Brak zainstalowanych kont email...", Toast.LENGTH_SHORT).show();
        }
    }
}
