package resource;

import beans.ImageProcessing;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
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
import java.io.IOException;
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


    @POST
    @Path("/filterImage/")
    public Response filterImage(Image image) {
        System.out.println("filterImage1");

        System.out.println("filterImage" + image.toString());
        //
        String imageEncoded = imageProcessing.getImageFromUpload(image);

        //String imageEncoded = image.getContent();

        System.out.println("image1" + imageEncoded);

        String filteredImage = imageProcessing.getFilteredImage(imageEncoded, image.getFilter());

        System.out.println("image2" + filteredImage);
        Image filteredImage2 = new Image();
        filteredImage2.setContent(filteredImage);

        return Response.ok(filteredImage2).build();
    }
}
