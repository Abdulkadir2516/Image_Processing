// https://github.com/opencv/opencv/tree/4.x/samples/java
// https://mesutpiskin.com/blog/wp-content/uploads/2017/01/OpenCV%20Kitap.pdf
// https://docs.opencv.org/4.x/d9/df8/tutorial_root.html


package image_processing;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.core.CvType;

public class Cv_exp3 {

    public static void main(String[] args) {
        //sistem kütüphanesini yükleme
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        //görüntüyü barýndýracak nesne
        
        
        Mat newImage = new Mat();
        newImage.create(255, 255,CvType.CV_8UC3 );
        
        byte[] imageData = new byte[(int) (newImage.total() * newImage.channels())];

        double alpha = 1;
        int beta = 1;

        newImage.get(0, 0, imageData);
        byte[] newImageData = new byte[(int) (newImage.total() * newImage.channels())];

        int rgb = 50;
        for (int y = 0; y < newImage.rows(); y++) {
            for (int x = 0; x < newImage.cols(); x++) {
                rgb++;
                for (int c = 0; c < newImage.channels(); c++) {
                              
                    double pixelValue = rgb;//imageData[(y * newImage.cols() + x) * newImage.channels() + c];
                    
                    pixelValue %=255 ;
                    // bgr
                    //pixelValue = pixelValue < 0 ? pixelValue + 256 : pixelValue;

                    newImageData[(y * newImage.cols() + x) * newImage.channels() + c] = saturate(alpha * pixelValue + beta);

                    
                }
                
                
            }
        }
        newImage.put(0, 0, newImageData);

        HighGui.imshow("yeni", newImage);

        HighGui.waitKey();

        System.exit(0);

    }

    public static byte saturate(double val) {
        int iVal = (int) Math.round(val);
        iVal = iVal > 255 ? 255 : (iVal < 0 ? 0 : iVal);
        return (byte) iVal;
    }
}
