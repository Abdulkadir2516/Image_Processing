package image_processing;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import javax.swing.ImageIcon;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

public class Cv_exp6 extends javax.swing.JFrame {
    // Kameradan frameleri almak i�in bu s�n�f kullan�lacak

    private VideoCapture videoCapture = null;

    // Kameradan gelen frameler ile ilgili i�lemler i�in bu s�n�f kullan�lacak
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
        // Kamera g�r�nt�s�n� alaca��m�z nesne olu�turuluyor.
        videoCapture = new VideoCapture();

        // Kamera a��l�yor.
        if (!videoCapture.open(0)) {
            System.err.println("Kamera a��lmad�.");
            System.exit(-1);
        } else {

            while (true) {
                // Kamera g�r�nt�s�n� elde et ve bunu frame nesnesine aktar.
                if (videoCapture.read(frame)) {
                    // Frame bo�sa bir sorun var. Bu nedenle ��k�� yap�yoruz.
                    if (frame.empty()) {
                        break;
                    }

                    // Y�z ve g�zleri tespit et ve bunlar� belirle.
                    //detectAndDraw();
                    BufferedImage image = FrameToImage(frame);
                    // Image nesnesi JLabel nesnesi i�erisine yazd�r�l�yor.
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
         * BufferedImage s�n�f�, eri�ilebilir bir g�r�nt� verisi arabelle�ine
         * sahip bir G�r�nt�y� tan�mlar. BufferedImage, bir ColorModel ve bir
         * Raster g�r�nt� verisinden olu�ur. Raster'�n SampleModel'indeki
         * bantlar�n say�s� ve t�rleri, ColorModel'in renk ve alfa bile�enlerini
         * temsil etmesi i�in gereken say� ve t�rlerle e�le�melidir. T�m
         * BufferedImage nesnelerinin sol �st k��esi (0, 0) koordinat�na
         * sahiptir. BufferedImage olu�turmak i�in kullan�lan herhangi bir
         * Raster bu nedenle minX=0 ve minY=0 olmal�d�r.
         *
         * Daha fazla bilgi i�in:
         * https://docs.oracle.com/javase/7/docs/api/java/awt/image/BufferedImage.html
         */
        // </editor-fold>
        BufferedImage bufferedImage = new BufferedImage(frame.width(), frame.height(), colorType);

        // <editor-fold defaultstate="collapsed" desc="Raster">
        /**
         * Raster s�n�f�, dikd�rtgen bir piksel dizisini temsil eden bir
         * s�n�ft�r. Bir Raster, �rnek de�erleri depolayan bir DataBuffer'� ve
         * bir DataBuffer'da belirli bir �rnek de�erinin nas�l bulunaca��n�
         * a��klayan bir SampleModel'i i�ine al�r. Raster, d�zlemin belirli bir
         * dikd�rtgen alan�n� kaplayan pikseller i�in de�erleri tan�mlar.
         * Raster'�n s�n�rlay�c� dikd�rtgeni olarak bilinen ve getBounds
         * y�ntemiyle kullan�labilen dikd�rtgen; minX, minY, geni�lik ve
         * y�kseklik de�erleriyle tan�mlan�r. minX ve minY de�erleri, Raster'in
         * sol �st k��esinin koordinat�n� tan�mlar. S�n�rlay�c� dikd�rtgenin
         * d���ndaki piksellere yap�lan ba�vurular, bir istisnan�n at�lmas�na
         * veya Raster'�n ili�kili DataBuffer'�n�n istenmeyen ��elerine
         * ba�vurulara neden olabilir. Bu t�r piksellere eri�imden ka��nmak
         * kullan�c�n�n sorumlulu�undad�r. Bir SampleModel, bir Raster
         * �rneklerinin bir DataBuffer'�n primitive dizi ��elerinde nas�l
         * sakland���n� a��klar. �rnekler, PixelInterleavedSampleModel veya
         * BandedSampleModel'de oldu�u gibi veri ��esi ba��na bir tane
         * saklanabilir veya SinglePixelPackedSampleModel veya
         * MultiPixelPackedSampleModel'de oldu�u gibi birka� ��eye
         * paketlenebilir. Bir Raster d�zlemde herhangi bir yerde ya�ayabilse
         * de, SampleModel (0, 0)'dan ba�layan basit bir koordinat sisteminden
         * yararlan�r. Bu nedenle bir Raster, piksel konumlar�n�n Raster'in
         * koordinat sistemi ile SampleModel'inki aras�nda e�lenmesine izin
         * veren bir �teleme fakt�r� i�erir. SampleModel koordinat sisteminden
         * Raster'�nkine �eviri, getSampleModelTranslateX ve
         * getSampleModelTranslateY y�ntemleriyle elde edilebilir. Bir Raster,
         * bir DataBuffer'� ba�ka bir Raster ile a��k bir �ekilde in�a ederek
         * veya createChild ve createTranslatedChild y�ntemlerini kullanarak
         * payla�abilir. Bu y�ntemlerle olu�turulan rasterler, getParent y�ntemi
         * arac�l���yla olu�turulduklar� Raster'a bir ba�vuru d�nd�rebilir.
         * createTranslatedChild veya createChild �a�r�s� yoluyla olu�turulmam��
         * bir Raster i�in getParent null de�erini d�nd�r�r.
         * createTranslatedChild y�ntemi, ge�erli Raster'�n t�m verilerini
         * payla�an ancak ayn� geni�lik ve y�kseklikte ancak farkl� bir
         * ba�lang�� ??noktas� olan bir s�n�rlay�c� dikd�rtgeni kaplayan yeni
         * bir Raster d�nd�r�r. �rne�in, ana Raster (10, 10) ile (100, 100)
         * b�lgesini i�gal ettiyse ve �evrilen Raster (50, 50) ile ba�layacak
         * �ekilde tan�mlanm��sa o zaman ebeveynin pikseli (20, 20) ve piksel
         * (60, 60), iki Raster taraf�ndan payla��lan DataBuffer'da ayn� konumu
         * i�gal eder. �lk durumda, kar��l�k gelen SampleModel koordinat�n� elde
         * etmek i�in bir piksel koordinat�na (-10, -10) eklenmeli ve ikinci
         * durumda (-50, -50) eklenmelidir. Bir ebeveyn ve alt Raster aras�ndaki
         * �eviri, �ocu�un sampleModelTranslateX ve sampleModelTranslateY
         * de�erlerinin ebeveynin de�erlerinden ��kar�lmas�yla belirlenebilir.
         * createChild y�ntemi, ebeveyninin s�n�rlay�c� dikd�rtgeninin yaln�zca
         * bir alt k�mesini veya ebeveyninin bantlar�n�n bir alt k�mesini i�gal
         * eden yeni bir Raster olu�turmak i�in kullan�labilir. Raster
         * olu�turman�n do�ru yolu, bu s�n�fta tan�mlanan statik olu�turma
         * y�ntemlerinden birini kullanmakt�r.
         *
         * Daha fazla bilgi i�in:
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

        // Kamera a��l�yor.
        if (!videoCapture.open(0)) {
            System.err.println("Kamera a��lmad�.");
            System.exit(-1);
        } else {

            while (true) {
                // Kamera g�r�nt�s�n� elde et ve bunu frame nesnesine aktar.
                if (videoCapture.read(frame)) {
                    // Frame bo�sa bir sorun var. Bu nedenle ��k�� yap�yoruz.
                    if (frame.empty()) {
                        break;
                    }

                    // Y�z ve g�zleri tespit et ve bunlar� belirle.
                    //detectAndDraw();
                    BufferedImage image = FrameToImage(frame);
                    // Image nesnesi JLabel nesnesi i�erisine yazd�r�l�yor.
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
