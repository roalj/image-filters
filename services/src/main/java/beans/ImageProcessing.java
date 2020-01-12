package beans;

import com.jhlabs.image.*;
import com.kumuluz.ee.discovery.annotations.DiscoverService;
import entities.Constants;
import entities.Image;
import org.apache.commons.codec.binary.Base64;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;


@ApplicationScoped
public class ImageProcessing {
    private BufferedImage imageDest;

    private Client httpClient;
    private Logger log = Logger.getLogger(ImageProcessing.class.getName());


    @PostConstruct
    private void init() {
        httpClient = ClientBuilder.newClient();
        //baseUrl = "http://comments:8081"; // only for demonstration
    }
   @Inject
    @DiscoverService(value = "image-upload-services", environment = "dev", version = "1.0.0")
    private Optional<String> baseUrlImageUpload;


    public String getImageFromUpload(Image image) {
        //za test klici direktno na server
        if (baseUrlImageUpload.isPresent()) {
                log.info("test2");
                log.info("Calling baseUrlImageCatalog service:saving image. " + image.getMongoId());

            try {
                    return httpClient.target(baseUrlImageUpload.get() + "/api/images/getImageString/"+image.getMongoId())
                            .request(MediaType.APPLICATION_JSON)
                            .get(new GenericType<String>(){});
                } catch (WebApplicationException | ProcessingException e) {
                    log.severe(e.getMessage());
                    throw new InternalServerErrorException(e);
                }
            }
        return null;
    }

    /*public static String encodeToString(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();

            BASE64Encoder encoder = new BASE64Encoder();
            imageString = encoder.encode(imageBytes);

            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }*/

    /*public String toBase64String(BufferedImage bufferedImage){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write( bufferedImage, "jpg", baos );
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return Base64.encodeBytes(imageInByte);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }*/

    public BufferedImage decodeToImage(String imageString) {
        BufferedImage image = null;
        byte[] imageByte;
        try {
            imageByte = Base64.decodeBase64(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    public String backToString(BufferedImage bufferedImage){
        String base64String = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "jpg", baos);
            byte[] byteArray = baos.toByteArray();
            //System.out.println("byte array: " + byteArray.toString()) ;
            base64String = Base64.encodeBase64String(byteArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64String;
    }

    public BufferedImage setSharpenFilter(BufferedImage imageScr){
        SharpenFilter sharpenFilter = new SharpenFilter();
        return  sharpenFilter.filter(imageScr, imageDest);
    }

    public BufferedImage setBlurFilter(BufferedImage imageScr){
        BoxBlurFilter blurFilter = new BoxBlurFilter();
        return blurFilter.filter(imageScr, imageDest);
    }

    public BufferedImage setInvertFilter(BufferedImage imageScr){
        InvertFilter invertFilter = new InvertFilter();
        return invertFilter.filter(imageScr, imageDest);
    }

    public String getFilteredImage(String base64, String filter){
        BufferedImage srcImage = decodeToImage(base64);
        if(srcImage != null){
            BufferedImage sharpImage = setFilter(srcImage, filter);
            return backToString(sharpImage);
        }
        System.out.println("ImageProcessing "+ "src image is null");
        return null;
    }

    public BufferedImage setFilter(BufferedImage imageScr, String filter){
        switch (filter){
            case Constants.BLUR_CONST_FILTER:
                return setBlurFilter(imageScr);
            case Constants.INVERSE_CONST_FILTER:
                return setInvertFilter(imageScr);
            case Constants.SHARPEN_CONST_FILTER:
                return setSharpenFilter(imageScr);
            default:
                return imageScr;
        }
    }
}
