package br.com.vn.server;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.glassfish.jersey.server.JSONP;

@Path("v1/items")
@Produces(MediaType.APPLICATION_JSON)
public class ItemsApi {

    private static final String ITEMS_URL = "/api/v1/items";

    @GET
    @JSONP(queryParam = "callback")
    public String getAllItems(@QueryParam("offset") int offset,
                              @QueryParam("count") int count,
                              @QueryParam("callback") String callback) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_DEFAULT);
        List<Items> itemsObjects = ItemManager.getInstance().getAllItems(offset, count);
        for (Items item : itemsObjects) {
            item.setImageName(ITEMS_URL + "/" + ((Items) itemsObjects).getId());
        }
        return mapper.writeValueAsString(itemsObjects);
    }

    @DELETE
    @JSONP(queryParam = "callback")
    public void deleteAllItems() throws Exception {
    	ItemManager.getInstance().deleteAllItems();
    }

    @GET
    @Path("/{id}")
    @JSONP(queryParam = "callback")
    public String getItem(@PathParam("id") int id) throws Exception {
        Items item = ItemManager.getInstance().getItem(id);
        return new ObjectMapper().writeValueAsString(item);
    }

    @PUT
    @JSONP(queryParam = "callback")
    public void putUser(String itemJson) throws Exception {
        Items item = new ObjectMapper().readValue(itemJson, Items.class);
        ItemManager.getInstance().saveOrUpdateItem(item);
    }

    @DELETE
    @Path("/{id}")
    @JSONP(queryParam = "callback")
    public void deleteItem(@PathParam("id") int id) throws Exception {
    	ItemManager.getInstance().deleteItem(id);
    }

}
