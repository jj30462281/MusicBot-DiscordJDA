package music.bot.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

public class GetSpotify {
	public static RequestConfig.Builder config = RequestConfig.custom()
		.setConnectTimeout(60 * 1000)
		.setConnectionRequestTimeout(60 * 1000)
		.setSocketTimeout(60 * 1000);
	
	public static CloseableHttpClient httpclient = HttpClientBuilder.create().setDefaultRequestConfig(config.build()).build();
	public static List<String> request(String url) {
		try {
			HashMap<String, String> obj = new HashMap<>();
			obj.put("url", url);
			
			HttpPost httpPost = new HttpPost("https://explosion-xx.ddns.net:5000/spotify");
            httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
            
            StringEntity stringEntity = new StringEntity(new Gson().toJson(obj), "UTF-8");
            stringEntity.setContentEncoding("UTF-8");
            
            httpPost.setEntity(stringEntity);
            
            CloseableHttpResponse response = httpclient.execute(httpPost);
            
            String res = EntityUtils.toString(response.getEntity());
            
            @SuppressWarnings("unchecked")
			HashMap<String, List<String>> result = new Gson().fromJson(res, new HashMap<String, List<String>>().getClass());
            
            return result.get("urls");
		}
		catch(Exception e) {
			e.printStackTrace();
			return Arrays.asList();
		}
	}
}
