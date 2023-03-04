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
        //sistem k�t�phanesini y�kleme
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        //g�r�nt�y� bar�nd�racak nesne
        Mat imageArray;
        //g�r�nt� dosyas�n� oku
        imageArray = Imgcodecs.imread("src/image/images.png");
        
        //uygulayaca��m�z filtreleri yeni bir mat nesnesine aktarmak i�in bir mat nesnesi olu�turuyoruz
        Mat newimgArray = new Mat();
        
        /**
        filtreleme i�lemleri opencv k�t�phanesi i�erisinde imgproc s�n�f� taraf�ndan ger�ekle�tiriliyor
        bu �rnekte brg olan resmi gray filtresi uygulanmaktad�r
        ayn� zamanda orjinal resmimiz 8 bit 3 kanall�dan 8 bit 1 kanall� �ekilde de�i�iyor
        */
        Imgproc.cvtColor(imageArray, newimgArray, Imgproc.COLOR_BGR2GRAY);
        
        HighGui.imshow("orjinal resim", imageArray);
        
        HighGui.imshow("Gray Resim", newimgArray);
        
        /* farkl� filtreleme k�s�mlar� 
        
        Mat newimgArray2 = new Mat();

        Imgproc.cvtColor(imageArray, newimgArray2, Imgproc.COLOR_BGR2HLS);
                
        HighGui.imshow("Hsl D�n��t�r�lmesi", newimgArray2);
        
        Mat newimgArray3 = new Mat();
        Imgproc.cvtColor(imageArray, newimgArray3, Imgproc.COLOR_BGR2RGB);
                
        HighGui.imshow("BGR to RGB", newimgArray);
        */
        
        HighGui.waitKey();
        
        System.exit(0);
        

        
    }

}
