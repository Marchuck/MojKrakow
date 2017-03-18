package pl.mojkrakow.mojkrakow.email;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

public class EmailSender implements Sendable {


    @Override
    public void send(Activity a, String email, String title, String content) {

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email, null));

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, " issue");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");
        a.startActivity(Intent.createChooser(emailIntent, "Send feedback"));
    }
}
