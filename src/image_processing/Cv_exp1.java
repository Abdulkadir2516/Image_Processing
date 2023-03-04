
// https://docs.opencv.org/4.x/d9/df8/tutorial_root.html

package image_processing;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

// Resim okuma gösterme ve resim bilgilerini görme

public class Cv_exp1 {

    public static void main(String[] args) {
        //sistem kütüphanesini yükleme
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        //görüntüyü barýndýracak nesne
        Mat imageArray;
        //görüntü dosyasýný oku
        imageArray = Imgcodecs.imread("src/image/face.jpg");
        
        HighGui.imshow("Resim", imageArray);
        
        
        //mat nesnesinin satýr ve sutun sayýsý
        System.out.println(imageArray.rows());
        System.out.println(imageArray.cols());
        
        // cell size
        System.out.println(imageArray.channels());
        
        //adress
        System.out.println(imageArray.dataAddr());
        
        // boyut
        System.out.println(imageArray.dims());
        
        // size
        System.out.println(imageArray.total());
        
        // pixel bilgisi
        System.out.println(imageArray.diag());
        System.out.println(imageArray.diag(1));
        System.out.println(imageArray.row(5).col(5));
             
        
        HighGui.waitKey();

        System.exit(0);
    
    }

}
