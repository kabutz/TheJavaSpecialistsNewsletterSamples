package eu.javaspecialists.tjsn.issue091;

import javax.imageio.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;

public class ScreenShot implements RobotAction {
    public Object execute(Robot robot) throws IOException {
        Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
        Rectangle shotArea = new Rectangle(
                defaultToolkit.getScreenSize());
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        BufferedImage screenCapture = robot.createScreenCapture(shotArea);
        ImageIO.write(screenCapture, "jpg", bout);
        bout.close();
        return bout.toByteArray();
    }

    public String toString() {
        return "ScreenShot";
    }
}
