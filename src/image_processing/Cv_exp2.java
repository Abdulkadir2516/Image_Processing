// https://github.com/opencv/opencv/tree/4.x/samples/java
// https://mesutpiskin.com/blog/wp-content/uploads/2017/01/OpenCV%20Kitap.pdf
// https://docs.opencv.org/4.x/d9/df8/tutorial_root.html


package image_processing;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Cv_exp2 {

    public static void main(String[] args) {
        //sistem kütüphanesini yükleme
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        //görüntüyü barýndýracak nesne
        Mat imageArray;
        //görüntü dosyasýný oku
        imageArray = Imgcodecs.imread("src/image/images.png");
        
        //uygulayacaðýmýz filtreleri yeni bir mat nesnesine aktarmak için bir mat nesnesi oluþturuyoruz
        Mat newimgArray = new Mat();
        
        /**
        filtreleme iþlemleri opencv kütüphanesi içerisinde imgproc sýnýfý tarafýndan gerçekleþtiriliyor
        bu örnekte brg olan resmi gray filtresi uygulanmaktadýr
        ayný zamanda orjinal resmimiz 8 bit 3 kanallýdan 8 bit 1 kanallý þekilde deðiþiyor
        */
        Imgproc.cvtColor(imageArray, newimgArray, Imgproc.COLOR_BGR2GRAY);
        
        HighGui.imshow("orjinal resim", imageArray);
        
        HighGui.imshow("Gray Resim", newimgArray);
        
        /* farklý filtreleme kýsýmlarý 
        
        Mat newimgArray2 = new Mat();

        Imgproc.cvtColor(imageArray, newimgArray2, Imgproc.COLOR_BGR2HLS);
                
        HighGui.imshow("Hsl Dönüþtürülmesi", newimgArray2);
        
        Mat newimgArray3 = new Mat();
        Imgproc.cvtColor(imageArray, newimgArray3, Imgproc.COLOR_BGR2RGB);
                
        HighGui.imshow("BGR to RGB", newimgArray);
        */
        
        HighGui.waitKey();
        
        System.exit(0);
        

        
    }

}
