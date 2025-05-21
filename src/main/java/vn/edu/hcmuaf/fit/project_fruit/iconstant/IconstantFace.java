package vn.edu.hcmuaf.fit.project_fruit.iconstant;

public class IconstantFace {
    public static final String FACEBOOK_CLIENT_ID = "695103062919463";
    public static final String FACEBOOK_CLIENT_SECRET = "243666fc20e1f6a3b39b661be4dfe382";
    public static final String FACEBOOK_REDIRECT_URI = "http://localhost:8091/project_fruit/login-facebook";

    // Link dùng để trao đổi code -> token
    public static final String FACEBOOK_LINK_GET_TOKEN = "https://graph.facebook.com/v19.0/oauth/access_token";

    // Link dùng để lấy thông tin người dùng (thêm cả ảnh)
    public static final String FACEBOOK_LINK_GET_USER_INFO = "https://graph.facebook.com/me?fields=id,name,email,picture&access_token=";
}
