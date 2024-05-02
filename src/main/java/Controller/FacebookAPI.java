package Controller;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.FacebookType;

public class FacebookAPI {

    private static final String ACCESS_TOKEN = "f5a559d371d16c629c424ceedc5711b1";

    public static void shareOnFacebook(String content) {
        FacebookClient facebookClient = new DefaultFacebookClient(ACCESS_TOKEN, Version.VERSION_2_10);
        FacebookType response = facebookClient.publish("me/feed", FacebookType.class,
                Parameter.with("message", content));
        System.out.println("Shared on Facebook. Post ID: " + response.getId());
    }
}
