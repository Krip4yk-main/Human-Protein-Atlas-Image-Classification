import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ImageReader {
    private BufferedImage image = null;
    private static String img_const[];
    private static int constXY;
    private static ArrayList<Integer> constValuesRGB = new ArrayList<>();

    public ImageReader() {
        img_const = new String[4];
        img_const[0] = "_blue"; img_const[1] = "_green"; img_const[2] = "_red"; img_const[3] = "_yellow";
        constXY = 512;
        int qwe[] = new int[]{0, 855296, 1447424, 1842176, 2236928, 2500096, 2763264, 3026432, 3289600, 3486976, 3684352, 3881728, 4013312, 4210688, 4342272, 4539648, 4671232, 4802816, 4934400, 5065984, 5197568, 5329152, 5460736, 5592320, 5658112, 5789696, 5921280, 6052864, 6118656, 6250240, 6316032, 6447616, 6513408, 6644992, 6710784, 6842368, 6908160, 6973952, 7105536, 7171328, 7237120, 7368704, 7434496, 7500288, 7566080, 7697664, 7763456, 7829248, 7895040, 7960832, 8026624, 8158208, 8224000, 8289792, 8355584, 8421376, 8487168, 8552960, 8618752, 8684544, 8750336, 8816128, 8881920, 8947712, 9013504, 9079296, 9145088, 9210880, 9276672, 9342464, 9408256, 9474048, 9539840, 9605632, 9671424, 9737216, 9803008, 9868800, 9934592, 10000384, 10066176, 10131968, 10197760, 10263552, 10329344, 10395136, 10460928, 10526720, 10592512, 10658304, 10724096, 10789888, 10855680, 10921472, 10987264, 11053056, 11118848, 11184640, 11250432, 11316224, 11382016, 11447808, 11513600, 11579392, 11645184, 11710976, 11776768, 11842560, 11908352, 11974144, 12039936, 12105728, 12171520, 12237312, 12303104, 12368896, 12434688, 12500480, 12566272, 12632064, 12697856, 12763648, 12829440, 12895232, 12961024, 13026816, 13092608, 13158400, 13224192, 13289984, 13355776, 13421568, 13487360, 13553152, 13618944, 13684736, 13750528, 13816320, 13882112, 13947904, 14013696, 14079488, 14145280, 14211072, 14276864, 14342656, 14408448, 14474240, 14540032, 14605824, 14671616, 14737408, 14803200, 14868992, 14934784, 15000576, 15066368, 15132160, 15197952, 15263744, 15329536, 15395328, 15461120, 15526912, 15592704, 15658496, 15724288, 15790080, 15855872, 15921664, 15987456, 16053248, 16119040, 16184832, 16250624, 16316416, 16382208, 16448000, 16513792, 16579584, 16645376, 16711168, 16776960};
        for (int val : qwe) {
            constValuesRGB.add(val);
        }
    }

    public BufferedImage Read(String filename) {
        try {
            String pathname = filename + ".png";
            BufferedImage img = null;
            img = ImageIO.read(new File(pathname));
            image = img;

            return img;
        } catch (IOException e) {
            System.out.println(e.toString());

            return null;
        }
    }

    public int[] CloseImg (BufferedImage X, BufferedImage Y, int first_img_size, int secons_img_size, int blocks_size) { //fist >= second
        int r[] = new int[3];
        r[0] = -1; r[1] = -1; r[2] = -1;
        long c[][] = new long[(int)Math.pow(first_img_size-secons_img_size+1, 2)][(int)Math.pow(secons_img_size/blocks_size, 2)];
        int x[][] = new int[first_img_size][first_img_size];
        int y[][] = new int[secons_img_size][secons_img_size];
        for (int i = 0; i < first_img_size; i++) {
            for (int j = 0; j < first_img_size; j++) {
                Color mc = new Color(X.getRGB(i, j));
                int col = mc.getRed()*65536 + mc.getGreen()*255 + mc.getBlue();
                x[i][j] = constValuesRGB.indexOf(col);
            }
        }
        for (int i = 0; i < secons_img_size; i++) {
            for (int j = 0; j < secons_img_size; j++) {
                Color mc = new Color(Y.getRGB(i, j));
                int col = mc.getRed()*65536 + mc.getGreen()*255 + mc.getBlue();
                y[i][j] = constValuesRGB.indexOf(col);
            }
        }
        for (int i = 0; i < first_img_size-secons_img_size+1; i++) {
            for (int j = 0; j < first_img_size-secons_img_size+1; j++) {
                for (int i1 = 0; i1 < secons_img_size; i1++) {
                    for (int j1 = 0; j1 < secons_img_size; j1++) {
                        c[j+i*(first_img_size-secons_img_size+1)][j1/blocks_size + (int)Math.pow(i1/blocks_size, 2)] += Math.abs(x[i1+i][j1+j] - y[i1][j1]);
                    }
                }
            }
        }
        int min = 100000000;
        int ijmin[] = new int[2];
        ijmin[0] = 0; ijmin[1] = 0;
        for (int i = 0; i < first_img_size-secons_img_size+1; i++) {
            for (int j = 0; j < first_img_size-secons_img_size+1; j++) {
                long val = 0;
                for (int ij = 0; ij < (int)Math.pow(secons_img_size/blocks_size, 2); ij++) {
                    //System.out.print(c[j+i*(first_img_size-secons_img_size+1)][ij] + " : ");
                    //val += (c[j+i*(first_img_size-secons_img_size+1)][ij])/((int)Math.pow(secons_img_size/blocks_size, 2));
                    val += (c[j+i*(first_img_size-secons_img_size+1)][ij]);
                    if (val > min) break;
                }
                if (val < min) {
                    min = (int)val;
                    ijmin[0] = i; ijmin[1] = j;
                }
                //System.out.print(val + " | ");
            }
            //System.out.println();
        }
        //System.out.println("min: " + min + " | " + ijmin[0] + ":" + ijmin[1]);
        r[0] = min; r[1] = ijmin[0]; r[2] = ijmin[1];

        return r;
    }

    public int[] SubImg(BufferedImage value) {
        long time = System.currentTimeMillis();
        int block_size = 64;
        int k = 0, kt = 0;
        int c[] = new int[3];
        c[2] = block_size;
        for (int i = 0; i < constXY-block_size; i++) {
            for (int j = 0; j < constXY-block_size; j++) {
                kt = 0;
                for (int i1 = i; i1 < i+block_size; i1++) {
                    boolean fatr = false;
                    for (int j1 = j; j1 < j+block_size; j1++) {
                        Color mc = new Color(value.getRGB(i1, j1));
                        int col = (int)Math.pow(mc.getRed(), 2) + mc.getGreen()*255 + mc.getBlue();
                        if (col != 0) kt++;
                        if (kt + (j+block_size-j1) + (i+block_size-i1)*block_size < k) {
                            fatr = true;
                            break;
                        }
                    }
                    if (fatr) break;
                }
                if (kt > k) {
                    k = kt;
                    c[0] = i;
                    c[1] = j;
                }
            }
        }
        //System.out.println(k + ": " + c[0] + "|" + c[1]);
        System.out.println(">> Search time: " + (System.currentTimeMillis() - time));
        return c;
    }

    public void SaveImg(BufferedImage value, String path, String name) {
        try {
            File outfile = new File(path + name + ".png");
            ImageIO.write(value, "png", outfile);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
