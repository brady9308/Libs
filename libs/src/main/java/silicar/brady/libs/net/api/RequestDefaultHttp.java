package silicar.brady.libs.net.api;

import android.os.Build;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Post请求
 * Created by Work on 2015/5/18.
 * @version 1.0
 * @author 图图
 * @since 2015/5/18
 */
public class RequestDefaultHttp
{
//    static
//    {
//        if (Build.VERSION.SDK_INT < 23)
//        {}
//    }
    private final static RequestDefaultHttp instance = new RequestDefaultHttp();
    public static RequestDefaultHttp getInstance() {
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,8000 );
        //httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,8000);
        return instance;
    }

    private final static DefaultHttpClient httpClient = new DefaultHttpClient();

    /**
     * Post请求
     * @param URL
     * @param postData
     * @return
     */
    public String RequestPost(String URL, List<BasicNameValuePair> postData) throws Exception {
        HttpPost post = new HttpPost(URL);
        post.setHeader("Content-Type","application/x-www-form-urlencoded; charset=utf-8");
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postData, HTTP.UTF_8);
        post.setEntity(entity);
        HttpResponse response = httpClient.execute(post);
        HttpEntity httpEntity = response.getEntity();
        InputStream is = httpEntity.getContent();
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = "";
        while( (line=br.readLine())!=null ) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }
}
