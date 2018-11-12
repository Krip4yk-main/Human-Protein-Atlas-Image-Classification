
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class main {

    private static int constNums = 28;
    private static String constColors[] = new String[4];
    private static int constMinValues[] = new int[4];

    public static void main(String[] args) throws IOException {
        //------------------
        constColors[0] = "_blue"; constColors[1] = "_green"; constColors[2] = "_red"; constColors[3] = "_yellow";
        constMinValues[0] = 91000; constMinValues[1] = 21000; constMinValues[2] = 33000; constMinValues[3] = 21000;
        //------------------
        ImageReader imageReader = new ImageReader();
        ArrayList<String> names = ReadNames("res\\input.txt");
        ArrayList<String> names2 = ReadNames("one\\0.txt");
        BufferedImage b_img = null, b_img2 = null;
        int result[] = null;
        FileWriter fw = null;
        try {
            fw = new FileWriter("src\\res\\output.txt");
        } catch (IOException e) {
            System.out.println(e.toString());
        }

        for (String name : names) {
            System.out.println("<< Searching for: " + name);
            int k = 0;
            int bminres[] = new int[]{1000000, 0, 0};

            b_img = imageReader.Read("E:\\Human Protein Atlas Image Classification\\all\\train\\" + name + constColors[0]);
            for (String name2 : names2) {
                if (names2.indexOf(name2) > 20) break;
                if (name == name2) continue;
                System.out.println("<<" + names2.indexOf(name2) + "<< Searching for: " + name2);
                b_img2 = imageReader.Read("E:\\Human Protein Atlas Image Classification\\all\\subone\\0\\" + name2 + constColors[0]);

                result = imageReader.CloseImg(b_img, b_img2, 512, 64, 32);
                if (result[0] < bminres[0] && result[0] != 0) bminres = result;
                if (bminres[0] > constMinValues[0]) continue;
                //System.out.println("res = " + result[0] + " from " + name + " | " + name2 + " <> " + constColors[index]);
            }
            for (int val : bminres) {
                System.out.print(val + " ");
            }
            System.out.println(name + constColors[0] + " <> " + bminres[0]);
            if (bminres[0] < constMinValues[0]) {
                k++;
            }

            for (int index = 1; index < constColors.length; index++) {
                    int minres[] = new int[]{1000000, 0, 0};
                    b_img = imageReader.Read("E:\\Human Protein Atlas Image Classification\\all\\train\\" + name + constColors[index]);
                    for (String name2 : names2) {
                        if (names2.indexOf(name2) > 20) break;
                        if (name == name2) continue;
                        System.out.println("<<" + names2.indexOf(name2) + "<< Searching for: " + name2 + constColors[index]);
                        b_img2 = imageReader.Read("E:\\Human Protein Atlas Image Classification\\all\\subone\\0\\" + name2 + constColors[index]);

                        result = imageReader.CloseImg(b_img.getSubimage(bminres[1], bminres[2], 64, 64), b_img2, 64, 64, 32);
                        if (result[0] < minres[0] && result[0] != 0) minres = result;
                        if (minres[0] > constMinValues[index]) continue;
                        //System.out.println("res = " + result[0] + " from " + name + " | " + name2 + " <> " + constColors[index]);
                    }
                    System.out.println(minres[0] + " " + name + constColors[index]);
                    if (minres[0] < constMinValues[index]) {
                        k++;
                    }/* else {
                    break;
                }*/
            }

            if (k > 3) {
                    imageReader.SaveImg(b_img.getSubimage(bminres[1], bminres[2], 64, 64), "src\\res\\", name + "_blue");
                    fw.write(name + " - contains 9th protein\n");
            }
        }
        fw.close();
    }


    private static ArrayList<String> ReadNames(String filename){
        try {
            FileReader fr = new FileReader("src\\" + filename);
            Scanner scan = new Scanner(fr);
            ArrayList<String> names = new ArrayList<>();
            while (scan.hasNextLine()) {
                names.add(scan.nextLine());
            }
            fr.close();
            return names;
        } catch (Exception e) {
            System.out.println("Error in read file");
            System.out.println(e.toString());
            return null;
        }
    }

    /*
        BufferedImage b_img1 = null, b_img2 = null;
        ImageReader imageReader = new ImageReader();
        b_img1 = imageReader.Read("000a6c98-bb9b-11e8-b2b9-ac1f6b6435d0_blue");\
        b_img2 = imageReader.Read("000a6c98-bb9b-11e8-b2b9-ac1f6b6435d0_green");
        long r[] = imageReader.CloseImg(b_img1, b_img2);
        for (long val : r) {
            System.out.println(val);
        }


        BufferedImage b_img1 = null, b_img2 = null, b_img3 = null, b_img4 = null;
        ImageReader imageReader = new ImageReader();
        b_img1 = imageReader.Read("000a6c98-bb9b-11e8-b2b9-ac1f6b6435d0_blue");
        b_img2 = imageReader.Read("000a6c98-bb9b-11e8-b2b9-ac1f6b6435d0_green");
        b_img3 = imageReader.Read("000a6c98-bb9b-11e8-b2b9-ac1f6b6435d0_red");
        b_img4 = imageReader.Read("000a6c98-bb9b-11e8-b2b9-ac1f6b6435d0_yellow");
        int subpos[] = imageReader.SubImg(b_img1);
        imageReader.SaveImg(b_img1.getSubimage(subpos[0], subpos[1], subpos[2], subpos[2]), "E:\\Human Protein Atlas Image Classification\\all\\subone\\", "000a6c98-bb9b-11e8-b2b9-ac1f6b6435d0_blue");
        imageReader.SaveImg(b_img2.getSubimage(subpos[0], subpos[1], subpos[2], subpos[2]), "E:\\Human Protein Atlas Image Classification\\all\\subone\\", "000a6c98-bb9b-11e8-b2b9-ac1f6b6435d0_green");
        imageReader.SaveImg(b_img3.getSubimage(subpos[0], subpos[1], subpos[2], subpos[2]), "E:\\Human Protein Atlas Image Classification\\all\\subone\\", "000a6c98-bb9b-11e8-b2b9-ac1f6b6435d0_red");
        imageReader.SaveImg(b_img4.getSubimage(subpos[0], subpos[1], subpos[2], subpos[2]), "E:\\Human Protein Atlas Image Classification\\all\\subone\\", "000a6c98-bb9b-11e8-b2b9-ac1f6b6435d0_yellow");


        ArrayList<String> names[] = new ArrayList[constNums];
        for (int i = 0; i < constNums; i++ ) {
            names[i] = ReadNames("one\\" + i + ".txt");
            System.out.println("\n" + i + " ===========>>");
            if (names[i] != null) {
                //for (String str : names[i]) { System.out.println(str); }
                System.out.println(names[i].size());
            }
        }


        //for (int index = 0; index < constNums; index++) {
        for (int index = 9; index < 10; index++) {
            System.out.println(">> Open: one\\" + index + ".txt");
            ArrayList<String> names = ReadNames("one\\" + index + ".txt");
            if (names != null) {
                ImageReader imgReader = new ImageReader();
                for (String name : names) {
                    System.out.println(">> Name: " + (names.indexOf(name)+1) + "/" + names.size());
                    BufferedImage b_img[] = new BufferedImage[4];
                    for (int i = 0; i < 4; i++) {
                        System.out.println(">> Reading: " + name + constColors[i]);
                        b_img[i] = imgReader.Read(name + constColors[i]);
                    }
                    System.out.println(">> Searching for block:");
                    int subpos[] = imgReader.SubImg(b_img[0]);
                    for (int i = 0; i < 4; i++) {
                        System.out.println(">> Saving: " + name + constColors[i]);
                        imgReader.SaveImg(b_img[i].getSubimage(subpos[0], subpos[1], subpos[2], subpos[2]), "E:\\Human Protein Atlas Image Classification\\all\\subone\\9\\", name + constColors[i]);
                    }
                }
            }
        }


        int result[] = null;
        ImageReader imageReader = new ImageReader();
        BufferedImage b_img = imageReader.Read("E:\\Human Protein Atlas Image Classification\\all\\train\\1baf483a-bbad-11e8-b2ba-ac1f6b6435d0_blue");
        BufferedImage b_subimg = imageReader.Read("E:\\Human Protein Atlas Image Classification\\all\\subone\\9\\1baf483a-bbad-11e8-b2ba-ac1f6b6435d0_blue");
        BufferedImage b_subimg2 = imageReader.Read("E:\\Human Protein Atlas Image Classification\\all\\subone\\9\\5e41a83e-bb9c-11e8-b2b9-ac1f6b6435d0_blue");
        result = imageReader.CloseImg(b_img, b_subimg2, 512, 64, 32);
        for (int r : result) {
            System.out.println(r);
        }
        imageReader.SaveImg(b_img.getSubimage(130, 257, 64, 64), "E:\\Human Protein Atlas Image Classification\\all\\subone\\", "test");


        if (names.indexOf(name)%2 == 0) {
            FindSubImg fsi = new FindSubImg(name, names.indexOf(name), names.size(), index, constColors);
        } else {
            System.out.println(">> Name: " + (names.indexOf(name)+1) + "/" + names.size());
            BufferedImage b_img[] = new BufferedImage[4];
            for (int i = 0; i < 4; i++) {
                System.out.println(">> Reading: E:\\Human Protein Atlas Image Classification\\all\\train\\" + name + constColors[i]);
                b_img[i] = imgReader.Read("E:\\Human Protein Atlas Image Classification\\all\\train\\" + name + constColors[i]);
            }
            System.out.println(">> Searching for block:");
            int subpos[] = imgReader.SubImg(b_img[0]);
            for (int i = 0; i < 4; i++) {
                System.out.println(">> Saving: " + name + constColors[i]);
                imgReader.SaveImg(b_img[i].getSubimage(subpos[0], subpos[1], subpos[2], subpos[2]), "E:\\Human Protein Atlas Image Classification\\all\\subone\\" + index + "\\", name + constColors[i]);
            }
        }


        for (int index = 0; index < constNums; index++) {
        //for (int index = 9; index < 10; index++) {
            System.out.println(">> Open: one\\" + index + ".txt");
            ArrayList<String> names = ReadNames("one\\" + index + ".txt");
            if (names != null) {
                ImageReader imgReader = new ImageReader();
                for (String name : names) {
                    switch (names.indexOf(name)%4) {
                        case 0: {
                            FindSubImg fsi = new FindSubImg(name, names.indexOf(name), names.size(), index, constColors, 0);
                            fsi.run();
                            break;
                        }
                        case 1: {
                            FindSubImg fsi = new FindSubImg(name, names.indexOf(name), names.size(), index, constColors, 1);
                            Thread thread = new Thread(fsi);
                            thread.start();
                            break;
                        }
                        case 2: {
                            FindSubImg fsi = new FindSubImg(name, names.indexOf(name), names.size(), index, constColors, 2);
                            Thread thread = new Thread(fsi);
                            thread.start();
                            break;
                        }
                        case 3: {
                            FindSubImg fsi = new FindSubImg(name, names.indexOf(name), names.size(), index, constColors, 3);
                            Thread thread = new Thread(fsi);
                            thread.start();
                            break;
                        }
                        default: break;
                    }
                }
            }
        }



        ImageReader imageReader = new ImageReader();
        ArrayList<String> names = ReadNames("one\\0.txt");
        BufferedImage b_img = null;
        ArrayList<Long> values = new ArrayList<>();
        for (String name : names) {
            if (names.indexOf(name) > 50) {
                break;
            }
            for (int index = 0; index < constColors.length; index++) {
                b_img = imageReader.Read("E:\\Human Protein Atlas Image Classification\\all\\train\\" + name + constColors[index]);
                for (int i = 0; i < b_img.getHeight(); i++) {
                    for (int j = 0; j < b_img.getWidth(); j++) {
                        Color color = new Color(b_img.getRGB(i, j));
                        long c = color.getRed()*65536 + color.getGreen()*255 + color.getBlue();
                        if (!values.contains(c)) {
                            values.add(c);
                        }
                    }
                }
            }
        }
        values.sort(null);
        for (long c : values) {
            System.out.print(c + " ");
        }


        ImageReader imageReader = new ImageReader();
        ArrayList<String> names = ReadNames("res\\input.txt");
        ArrayList<String> names2 = ReadNames("one\\9.txt");
        BufferedImage b_img = null, b_img2 = null;
        int result[] = null;
        FileWriter fw = null;
        try {
            fw = new FileWriter("src\\res\\output.txt");
        } catch (IOException e) {
            System.out.println(e.toString());
        }

        for (String name : names) {
            int k = 0;
            int bminres[] = new int[]{1000000, 0, 0};

            b_img = imageReader.Read("E:\\Human Protein Atlas Image Classification\\all\\train\\" + name + constColors[0]);
            for (String name2 : names2) {
                if (name == name2) continue;
                b_img2 = imageReader.Read("E:\\Human Protein Atlas Image Classification\\all\\subone\\9\\" + name2 + constColors[0]);

                result = imageReader.CloseImg(b_img, b_img2, 512, 64, 32);
                if (result[0] < bminres[0] && result[0] != 0) bminres = result;
                //if (bminres[0] > 31000) break;
                //System.out.println("res = " + result[0] + " from " + name + " | " + name2 + " <> " + constColors[index]);
            }
            for (int val : bminres) {
                System.out.print(val + " ");
            }
            System.out.println(name + constColors[0]);
            if (bminres[0] > 31000) {
                continue;
            }

            for (int index = 1; index < constColors.length; index++) {
                int minres[] = new int[]{1000000, 0, 0};
                b_img = imageReader.Read("E:\\Human Protein Atlas Image Classification\\all\\train\\" + name + constColors[index]);
                for (String name2 : names2) {
                    if (name == name2) continue;
                    b_img2 = imageReader.Read("E:\\Human Protein Atlas Image Classification\\all\\subone\\9\\" + name2 + constColors[index]);

                    result = imageReader.CloseImg(b_img.getSubimage(bminres[1], bminres[2], 64, 64), b_img2, 64, 64, 32);
                    if (result[0] < minres[0] && result[0] != 0) minres = result;
                    //if (minres[0] > 31000) break;
                    //System.out.println("res = " + result[0] + " from " + name + " | " + name2 + " <> " + constColors[index]);
                }
                System.out.println(minres[0] + " " + name + constColors[index]);
                if (minres[0] < 31000) {
                    k++;
                }/* else {
                    break;
                }* /
}

            if (k > 1) {
                    imageReader.SaveImg(b_img.getSubimage(bminres[1], bminres[2], 64, 64), "src\\res\\", name + "_blue");
                    fw.write(name + " - contains 9th protein\n");
                    }
                    }

                    fw.close();




        ImageReader imageReader = new ImageReader();
        ArrayList<String> names = ReadNames("one\\0.txt");
        ArrayList<String> names2 = ReadNames("one\\0.txt");
        BufferedImage b_img = null, b_img2 = null;
        int result[] = null;
        int minresult[] = new int[3];
        int k[] = new int[4];
        for (int index = 0; index < constColors.length; index++) {
            k[index] = 0;
            for (String name : names) {
                if (names.indexOf(name) > 16) break;
                minresult[0] = 100000;
                minresult[1] = 0;
                minresult[2] = 0;
                b_img = imageReader.Read("E:\\Human Protein Atlas Image Classification\\all\\subone\\0\\" + name + constColors[index]);
                for (String name2 : names2) {
                    if (names2.indexOf(name2) > 300) break;
                    if (name.equals(name2)) {
                        continue;
                    }
                    //System.out.println(name + " : " + name2);
                    b_img2 = imageReader.Read("E:\\Human Protein Atlas Image Classification\\all\\subone\\0\\" + name2 + constColors[index]);
                    result = imageReader.CloseImg(b_img, b_img2, 64, 64, 32);
                    if (minresult[0] > result[0]) {
                        minresult = result;
                    }
                }
                //System.out.println(names.indexOf(name) + " >> " + minresult[0]);
                switch (index) {
                    case 0:{
                        if (minresult[0] > 30000) {
                            //System.out.println("-");
                            k[index]++;
                        }
                        break;
                    }
                    case 1:{
                        if (minresult[0] > 20000) {
                            //System.out.println("-");
                            k[index]++;
                        }
                        break;
                    }
                    case 2:{
                        if (minresult[0] > 20000) {
                            //System.out.println("-");
                            k[index]++;
                        }
                        break;
                    }
                    case 3:{
                        if (minresult[0] > 20000) {
                            //System.out.println("-");
                            k[index]++;
                        }
                        break;
                    }
                }
            }
            System.out.println(k[index] + " << ");
        }

        ImageReader imageReader = new ImageReader();
        ArrayList<String> names = ReadNames("one\\9.txt");
        ArrayList<String> names2 = ReadNames("one\\0.txt");
        BufferedImage b_img = null, b_img2 = null;
        int minvalues[] = new int[constMinValues.length];
        minvalues[0] = 100000000; minvalues[1] = 100000000; minvalues[2] = 100000000; minvalues[3] = 100000000;
        ArrayList<Integer> l1 = new ArrayList<>(), l2 = new ArrayList<>(), l3 = new ArrayList<>(), l4 = new ArrayList<>();
        ArrayList[] list_minvalues = {l1, l2, l3, l4};
        for (int index = 0; index < constColors.length; index++) {
            for (String name : names) {
                if (names.indexOf(name) > 100) break;
                System.out.println(names.indexOf(name) + "| " + name);
                b_img = imageReader.Read("E:\\Human Protein Atlas Image Classification\\all\\subone\\9\\" + name + constColors[index]);
                for (String name2 : names2) {
                    if (names2.indexOf(name2) > 300) break;
                    if (name.equals(name2)) continue;
                    //System.out.print(names2.indexOf(name2) + ">>" + name2);
                    b_img2 = imageReader.Read("E:\\Human Protein Atlas Image Classification\\all\\subone\\0\\" + name2 + constColors[index]);
                    int r[] = imageReader.CloseImg(b_img, b_img2, 64, 64, 64);
                    //if (!list_minvalues[index].equals(r[0])) {
                        list_minvalues[index].add(r[0]);
                    //}
                    if (minvalues[index] > r[0]) {
                        minvalues[index] = r[0];
                    }
                    //System.out.println(" | " + index + ": " + minvalues[index]);
                }
            }
        }
        for (int v : minvalues) {
            System.out.print(v + " ");
        }
        System.out.println();
        for (int i = 0; i < constMinValues.length; i++) {
            list_minvalues[i].sort(null);
            int m_v = 0;
            for (Object val : list_minvalues[i]) {
                m_v += (int)val/list_minvalues[i].size();
            }
            System.out.println(m_v);
        }
*/
}

class FindSubImg
implements Runnable {
    private String name;
    private int index_of, names_size, index, numofthrd;
    private String[] constColors;

    FindSubImg(String name, int index_of, int names_size, int index, String[] constColors, int numofthrd) {
        this.name = name;
        this.index_of = index_of;
        this.names_size = names_size;
        this.index = index;
        this.constColors = constColors;
        this.numofthrd = numofthrd;
    }

    public void run() {
        ImageReader imgReader = new ImageReader();
        System.out.println(numofthrd + ">> Name: " + (index_of+1) + "/" + names_size);
        BufferedImage b_img[] = new BufferedImage[4];
        for (int i = 0; i < 4; i++) {
            System.out.println(numofthrd + ">> Reading: E:\\Human Protein Atlas Image Classification\\all\\train\\" + name + constColors[i]);
            b_img[i] = imgReader.Read("E:\\Human Protein Atlas Image Classification\\all\\train\\" + name + constColors[i]);
        }
        System.out.println(numofthrd + ">> Searching for block:");
        int subpos[] = imgReader.SubImg(b_img[0]);
        for (int i = 0; i < 4; i++) {
            System.out.println(numofthrd + ">> Saving: " + name + constColors[i]);
            imgReader.SaveImg(b_img[i].getSubimage(subpos[0], subpos[1], subpos[2], subpos[2]), "E:\\Human Protein Atlas Image Classification\\all\\subone\\" + index + "\\", name + constColors[i]);
        }
    }
}