package resource;

import beans.ImageProcessing;
import com.kumuluz.ee.discovery.annotations.DiscoverService;
import entities.Image;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;
import java.util.logging.Logger;

@ApplicationScoped
@Path("/filters")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ImageResource {
    @Inject
    private ImageProcessing imageProcessing;

    @GET
    public Response getImageList() {
        String welcome = "welcome to instagram restservice6";

      return Response.ok(welcome).build();
    }


//todo namesto, da posiljas sliko posljes id slike in jo preberes iz baze
    @POST
    @Path("/filterImage/")
    public Response filterImage(Image image) {
        System.out.println("filterImage1");

        System.out.println("filterImage" + image.toString());
        //
        Response response = imageProcessing.getImageFromUpload(image);
        System.out.println("entity" + response.toString());
        System.out.println("entity1" + response.getEntity());
        System.out.println("entity2" + response);

        Image image1 = (Image) response.getEntity();
        System.out.println("image1" + image1.getContent());

        //String filteredImage = imageProcessing.getFilteredImage(image.getContent(), image.getFilter());
        return Response.ok(response).build();
    }
}
