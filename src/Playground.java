import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Playground {
    public ArrayList<Sprite> environment = new ArrayList<>();

    public Playground (String pathName){
        try{
            final Image imageTree = ImageIO.read(new File("./img/tree.png"));
            final Image imageGrass = ImageIO.read(new File("./img/grass.png"));
            final Image imageRock = ImageIO.read(new File("./img/rock.png"));
            final Image imageTrap = ImageIO.read(new File("img/grass_with_trap.png"));
            final Image imagePortail = ImageIO.read(new File("img/trap.png"));
            final Image imageTower = ImageIO.read(new File("img/TDE.png"));

            final int imageTreeWidth = imageTree.getWidth(null);
            final int imageTreeHeight = imageTree.getHeight(null);

            final int imageGrassWidth = imageGrass.getWidth(null);
            final int imageGrassHeight = imageGrass.getHeight(null);

            final int imageRockWidth = imageRock.getWidth(null);
            final int imageRockHeight = imageRock.getHeight(null);

            final int imageTrapWidth = imageTree.getWidth(null);
            final int imageTrapHeight = imageTree.getHeight(null);

            final int imagePortailWidth = imagePortail.getWidth(null);
            final int imagePortailHeight = imagePortail.getHeight(null);

            final int imageTowerWidth = imageTower.getWidth(null);
            final int imageTowerHeight = imageTower.getHeight(null);

            BufferedReader bufferedReader = new BufferedReader(new FileReader(pathName));
            String line=bufferedReader.readLine();
            int lineNumber = 0;
            int columnNumber = 0;
            while (line!= null){
                for (byte element : line.getBytes(StandardCharsets.UTF_8)){
                    switch (element){
                        case 'T' : environment.add(new SolidSprite(columnNumber*imageTreeWidth,
                                lineNumber*imageTreeHeight,imageTree, imageTreeWidth, imageTreeHeight));
                                    break;
                        case ' ' : environment.add(new Sprite(columnNumber*imageGrassWidth,
                                lineNumber*imageGrassHeight, imageGrass, imageGrassWidth, imageGrassHeight));
                            break;
                        case 'R' : environment.add(new SolidSprite(columnNumber*imageRockWidth,
                                lineNumber*imageRockHeight, imageRock, imageRockWidth, imageRockHeight));
                            break;
                        case 'X' : environment.add(new Trap(columnNumber*imageTrapWidth,
                                lineNumber*imageTrapHeight, imageTrap, imageTrapWidth, imageTrapHeight, 20));
                            break;
                        case 'W' :
                            Portail portail = new Portail(columnNumber * imagePortailWidth,
                                    lineNumber * imagePortailHeight, imagePortail, imagePortailWidth, imagePortailHeight, "./data/level2.txt");
                            environment.add(portail);
                            System.out.println("Portail ajouté à la position x=" + portail.getX() + ", y=" + portail.getY());
                            break;
                        case 'M' : environment.add(new LongRange(columnNumber*imageTowerWidth,
                                lineNumber*imageTowerHeight, imageTower, imageTowerWidth, imageTowerHeight, 1.00 , 1.00));
                            break;

                    }
                    columnNumber++;
                }
                columnNumber =0;
                lineNumber++;
                line=bufferedReader.readLine();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<Sprite> getSolidSpriteList(){
        ArrayList <Sprite> solidSpriteArrayList = new ArrayList<>();
        for (Sprite sprite : environment){
            if (sprite instanceof SolidSprite) solidSpriteArrayList.add(sprite);
        }
        return solidSpriteArrayList;
    }

    public ArrayList<Displayable> getSpriteList(){
        ArrayList <Displayable> displayableArrayList = new ArrayList<>();
        for (Sprite sprite : environment){
            displayableArrayList.add((Displayable) sprite);
        }
        return displayableArrayList;
    }

    public ArrayList<SolidSprite> getSolidSprites() {
        ArrayList<SolidSprite> solidSprites = new ArrayList<>();
        for (Sprite sprite : environment) {
            if (sprite instanceof SolidSprite) {
                solidSprites.add((SolidSprite) sprite);
            }
        }
        return solidSprites;
    }

    public ArrayList<LongRange> getLongRangeList(){
        ArrayList <LongRange> longRangeArrayList = new ArrayList<>();
        for (Sprite sprite : environment){
            if (sprite instanceof LongRange) {
                longRangeArrayList.add((LongRange) sprite);
            }
        }
        return longRangeArrayList;
    }
}





