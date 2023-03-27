package image_processing;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import javax.swing.ImageIcon;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

public class Cv_exp6 extends javax.swing.JFrame {
    // Kameradan frameleri almak için bu sýnýf kullanýlacak

    private VideoCapture videoCapture = null;

    // Kameradan gelen frameler ile ilgili iþlemler için bu sýnýf kullanýlacak
    private Mat frame = null;

    public Cv_exp6() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pencere = new javax.swing.JLabel();
        start = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        start.setText("baslat");
        start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startActionPerformed(evt);
            }
        });

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pencere, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(start)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 345, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(77, 77, 77))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(start))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jButton1)))
                .addGap(26, 26, 26)
                .addComponent(pencere, javax.swing.GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void camOpen() {
        // Kamera görüntüsünü alacaðýmýz nesne oluþturuluyor.
        videoCapture = new VideoCapture();

        // Kamera açýlýyor.
        if (!videoCapture.open(0)) {
            System.err.println("Kamera açýlmadý.");
            System.exit(-1);
        } else {

            while (true) {
                // Kamera görüntüsünü elde et ve bunu frame nesnesine aktar.
                if (videoCapture.read(frame)) {
                    // Frame boþsa bir sorun var. Bu nedenle çýkýþ yapýyoruz.
                    if (frame.empty()) {
                        break;
                    }

                    // Yüz ve gözleri tespit et ve bunlarý belirle.
                    //detectAndDraw();
                    BufferedImage image = FrameToImage(frame);
                    // Image nesnesi JLabel nesnesi içerisine yazdýrýlýyor.
                    pencere.setIcon(new ImageIcon(image));
                }
            }

        }
    }

    public BufferedImage FrameToImage(Mat frame) {
        int colorType = BufferedImage.TYPE_CUSTOM;

        if (frame.channels() == 1) {
            colorType = BufferedImage.TYPE_BYTE_GRAY;
        } else if (frame.channels() == 3) {
            colorType = BufferedImage.TYPE_3BYTE_BGR;
        }

        // <editor-fold defaultstate="collapsed" desc="BufferedImage">
        /**
         * BufferedImage sýnýfý, eriþilebilir bir görüntü verisi arabelleðine
         * sahip bir Görüntüyü tanýmlar. BufferedImage, bir ColorModel ve bir
         * Raster görüntü verisinden oluþur. Raster'ýn SampleModel'indeki
         * bantlarýn sayýsý ve türleri, ColorModel'in renk ve alfa bileþenlerini
         * temsil etmesi için gereken sayý ve türlerle eþleþmelidir. Tüm
         * BufferedImage nesnelerinin sol üst köþesi (0, 0) koordinatýna
         * sahiptir. BufferedImage oluþturmak için kullanýlan herhangi bir
         * Raster bu nedenle minX=0 ve minY=0 olmalýdýr.
         *
         * Daha fazla bilgi için:
         * https://docs.oracle.com/javase/7/docs/api/java/awt/image/BufferedImage.html
         */
        // </editor-fold>
        BufferedImage bufferedImage = new BufferedImage(frame.width(), frame.height(), colorType);

        // <editor-fold defaultstate="collapsed" desc="Raster">
        /**
         * Raster sýnýfý, dikdörtgen bir piksel dizisini temsil eden bir
         * sýnýftýr. Bir Raster, örnek deðerleri depolayan bir DataBuffer'ý ve
         * bir DataBuffer'da belirli bir örnek deðerinin nasýl bulunacaðýný
         * açýklayan bir SampleModel'i içine alýr. Raster, düzlemin belirli bir
         * dikdörtgen alanýný kaplayan pikseller için deðerleri tanýmlar.
         * Raster'ýn sýnýrlayýcý dikdörtgeni olarak bilinen ve getBounds
         * yöntemiyle kullanýlabilen dikdörtgen; minX, minY, geniþlik ve
         * yükseklik deðerleriyle tanýmlanýr. minX ve minY deðerleri, Raster'in
         * sol üst köþesinin koordinatýný tanýmlar. Sýnýrlayýcý dikdörtgenin
         * dýþýndaki piksellere yapýlan baþvurular, bir istisnanýn atýlmasýna
         * veya Raster'ýn iliþkili DataBuffer'ýnýn istenmeyen öðelerine
         * baþvurulara neden olabilir. Bu tür piksellere eriþimden kaçýnmak
         * kullanýcýnýn sorumluluðundadýr. Bir SampleModel, bir Raster
         * örneklerinin bir DataBuffer'ýn primitive dizi öðelerinde nasýl
         * saklandýðýný açýklar. Örnekler, PixelInterleavedSampleModel veya
         * BandedSampleModel'de olduðu gibi veri öðesi baþýna bir tane
         * saklanabilir veya SinglePixelPackedSampleModel veya
         * MultiPixelPackedSampleModel'de olduðu gibi birkaç öðeye
         * paketlenebilir. Bir Raster düzlemde herhangi bir yerde yaþayabilse
         * de, SampleModel (0, 0)'dan baþlayan basit bir koordinat sisteminden
         * yararlanýr. Bu nedenle bir Raster, piksel konumlarýnýn Raster'in
         * koordinat sistemi ile SampleModel'inki arasýnda eþlenmesine izin
         * veren bir öteleme faktörü içerir. SampleModel koordinat sisteminden
         * Raster'ýnkine çeviri, getSampleModelTranslateX ve
         * getSampleModelTranslateY yöntemleriyle elde edilebilir. Bir Raster,
         * bir DataBuffer'ý baþka bir Raster ile açýk bir þekilde inþa ederek
         * veya createChild ve createTranslatedChild yöntemlerini kullanarak
         * paylaþabilir. Bu yöntemlerle oluþturulan rasterler, getParent yöntemi
         * aracýlýðýyla oluþturulduklarý Raster'a bir baþvuru döndürebilir.
         * createTranslatedChild veya createChild çaðrýsý yoluyla oluþturulmamýþ
         * bir Raster için getParent null deðerini döndürür.
         * createTranslatedChild yöntemi, geçerli Raster'ýn tüm verilerini
         * paylaþan ancak ayný geniþlik ve yükseklikte ancak farklý bir
         * baþlangýç ??noktasý olan bir sýnýrlayýcý dikdörtgeni kaplayan yeni
         * bir Raster döndürür. Örneðin, ana Raster (10, 10) ile (100, 100)
         * bölgesini iþgal ettiyse ve çevrilen Raster (50, 50) ile baþlayacak
         * þekilde tanýmlanmýþsa o zaman ebeveynin pikseli (20, 20) ve piksel
         * (60, 60), iki Raster tarafýndan paylaþýlan DataBuffer'da ayný konumu
         * iþgal eder. Ýlk durumda, karþýlýk gelen SampleModel koordinatýný elde
         * etmek için bir piksel koordinatýna (-10, -10) eklenmeli ve ikinci
         * durumda (-50, -50) eklenmelidir. Bir ebeveyn ve alt Raster arasýndaki
         * çeviri, çocuðun sampleModelTranslateX ve sampleModelTranslateY
         * deðerlerinin ebeveynin deðerlerinden çýkarýlmasýyla belirlenebilir.
         * createChild yöntemi, ebeveyninin sýnýrlayýcý dikdörtgeninin yalnýzca
         * bir alt kümesini veya ebeveyninin bantlarýnýn bir alt kümesini iþgal
         * eden yeni bir Raster oluþturmak için kullanýlabilir. Raster
         * oluþturmanýn doðru yolu, bu sýnýfta tanýmlanan statik oluþturma
         * yöntemlerinden birini kullanmaktýr.
         *
         * Daha fazla bilgi için:
         * https://docs.oracle.com/javase/7/docs/api/java/awt/image/Raster.html
         */
        // </editor-fold>
        WritableRaster writableRaster = bufferedImage.getRaster();
        DataBufferByte dataBuffer = (DataBufferByte) writableRaster.getDataBuffer();
        byte[] buffer = dataBuffer.getData();
        frame.get(0, 0, buffer);

        return bufferedImage;
    }
    private void startActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startActionPerformed

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        frame = new Mat();

        Thread t = new Thread() {
            @Override
            public void run() {
                camOpen();
            }
        };
        t.setName("Thread1");
        t.setDaemon(true);
        t.start();

    }//GEN-LAST:event_startActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        videoCapture = new VideoCapture();

        // Kamera açýlýyor.
        if (!videoCapture.open(0)) {
            System.err.println("Kamera açýlmadý.");
            System.exit(-1);
        } else {

            while (true) {
                // Kamera görüntüsünü elde et ve bunu frame nesnesine aktar.
                if (videoCapture.read(frame)) {
                    // Frame boþsa bir sorun var. Bu nedenle çýkýþ yapýyoruz.
                    if (frame.empty()) {
                        break;
                    }

                    // Yüz ve gözleri tespit et ve bunlarý belirle.
                    //detectAndDraw();
                    BufferedImage image = FrameToImage(frame);
                    // Image nesnesi JLabel nesnesi içerisine yazdýrýlýyor.
                    pencere.setIcon(new ImageIcon(image));
                }
            }

        }
        
        
        
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Cv_exp6.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Cv_exp6.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Cv_exp6.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Cv_exp6.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Cv_exp6().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel pencere;
    private javax.swing.JButton start;
    // End of variables declaration//GEN-END:variables
}
