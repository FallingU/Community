package life.fallingu.commuity.commuity.provider;

import com.alibaba.fastjson.JSON;
import life.fallingu.commuity.commuity.dto.AccessTokenDTO;
import life.fallingu.commuity.commuity.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    /**
     * 获取用户AccessToken的方法
     * @param dto
     * @return
     */
    public String getAccessToken(AccessTokenDTO dto){
        MediaType mediaType
                = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        String json = JSON.toJSONString(dto);
        // String post(String url, String json) throws IOException {
            RequestBody body = RequestBody.create(json, mediaType);
            Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
               //access_token=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&scope=&token_type=bearer
                String accessToken = response.body().string().split("&")[0].split("=")[1];
                return accessToken;
            } catch (IOException e) {
            }
        //}
        return null;
    }

    /**
     * 通过AccessToken获取Github用户
     * @param accessToken
     * @return
     */
    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                    .url("https://api.github.com/user")
                    .header("Authorization","token "+accessToken)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                String json = response.body().string();
                GithubUser githubUser = JSON.parseObject(json, GithubUser.class);
                return githubUser;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
    }
}
