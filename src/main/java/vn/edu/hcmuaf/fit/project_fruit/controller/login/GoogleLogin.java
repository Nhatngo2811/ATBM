package vn.edu.hcmuaf.fit.project_fruit.controller.login;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import vn.edu.hcmuaf.fit.project_fruit.iconstant.IconstantGG;
import java.io.IOException;

public class GoogleLogin {
    public static String getToken(String code) throws IOException {
        String response = Request.Post(IconstantGG.GOOGLE_LINK_GET_TOKEN)
                .bodyForm(Form.form()
                        .add("client_id", IconstantGG.GOOGLE_CLIENT_ID)
                        .add("client_secret", IconstantGG.GOOGLE_CLIENT_SECRET)
                        .add("redirect_uri", IconstantGG.GOOGLE_REDIRECT_URI)
                        .add("code", code)
                        .add("grant_type", IconstantGG.GOOGLE_GRANT_TYPE)
                        .build())
                .execute().returnContent().asString();

        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
        return jobj.get("access_token").getAsString();
    }

    public static JsonObject getUserInfo(String accessToken) throws IOException {
        String response = Request.Get(IconstantGG.GOOGLE_LINK_GET_USER_INFO + accessToken)
                .execute().returnContent().asString();

        JsonObject userInfo = new Gson().fromJson(response, JsonObject.class);

        // Kiểm tra nếu Google ID chưa có
        if (!userInfo.has("id")) {
            throw new IOException("Không lấy được Google ID");
        }

        return userInfo;
    }

}
