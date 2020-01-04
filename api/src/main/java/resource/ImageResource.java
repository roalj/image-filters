package resource;

import beans.ImageProcessing;
import entities.Image;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.image.BufferedImage;

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
        System.out.println(image.toString());
        String filteredImage = imageProcessing.getFilteredImage(image.getContent(), image.getFilter());
        return Response.ok( " resulult saving: " + filteredImage).build();
    }
}
