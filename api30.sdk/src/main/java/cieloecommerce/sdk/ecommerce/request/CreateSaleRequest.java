package cieloecommerce.sdk.ecommerce.request;

import java.io.IOException;

import org.apache.commons.codec.Charsets;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

import com.google.gson.GsonBuilder;

import cieloecommerce.sdk.Environment;
import cieloecommerce.sdk.Merchant;
import cieloecommerce.sdk.ecommerce.Sale;

/**
 * Create any kind of sale
 */
public class CreateSaleRequest extends AbstractSaleRequest<Sale, Sale> {
	public CreateSaleRequest(Merchant merchant, Environment environment) {
		super(merchant, environment);
	}

	@Override
	public Sale execute(Sale param) throws IOException, CieloRequestException {
		String url = environment.getApiUrl() + "1/sales/";
		HttpPost request = new HttpPost(url);

		request.setEntity(new StringEntity(new GsonBuilder().create().toJson(param),
				ContentType.create(ContentType.APPLICATION_JSON.getMimeType(), Charsets.UTF_8.name())));
		CloseableHttpResponse response = sendRequest(request);

		return readResponse(response, Sale.class);
	}
}
