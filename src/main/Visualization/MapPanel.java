import javafx.scene.shape.Circle;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class MapPanel extends JPanel
{
    private WorldMap map;
    private int width;
    private int height;
    private int tresholdEnergy;

    public MapPanel(int width, int height, WorldMap map)
    {
        this.width = width;
        this.height = height;
        this.map = map;
    }

    public void setTresholdEnergy(int energy)
    {
        tresholdEnergy = energy;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        Rectangle2D steppe = new Rectangle2D.Double(0, 0, width, height);
        g2d.setPaint(new Color(179, 255, 179));
        g2d.fill(steppe);

        double multHeight = (double)this.height / map.getHeight();
        double multWidth = (double)this.width / map.getWidth();
        Rectangle2D jungle = new Rectangle2D.Double((map.getJungleBottomLeft().x - map.getBottomLeft().x) * multWidth, (map.getJungleBottomLeft().y - map.getBottomLeft().y) * multHeight,
                                                    map.getJungleWidth() * multWidth, map.getJungleHeight() * multHeight);
        g2d.setPaint(new Color(0, 128, 0));
        g2d.fill(jungle);

        g2d.setPaint(new Color(0, 255, 0));
        for (Vector2d position : map.getPlantsToVisualization())
        {
            Rectangle2D grass = new Rectangle2D.Double((position.x - map.getBottomLeft().x) * multWidth, (position.y - map.getBottomLeft().y) * multHeight, multWidth, multHeight);
            g2d.fill(grass);
        }

        for (Animal animal : map.getAnimalsToVisualization())
        {
            if (animal.getEnergy() < tresholdEnergy / 2)
                g2d.setPaint(new Color(102, 194, 255));
            else if (animal.getEnergy() < tresholdEnergy)
                g2d.setPaint(new Color(77, 184, 255));
            else if (animal.getEnergy() < 2 * tresholdEnergy)
                g2d.setPaint(new Color(0, 122, 204));
            else if (animal.getEnergy() < 4 * tresholdEnergy)
                g2d.setPaint(new Color(0, 92, 153));
            else if (animal.getEnergy() < 8 * tresholdEnergy)
                g2d.setPaint(new Color(0, 61, 102));
            else if (animal.getEnergy() < 16 * tresholdEnergy)
                g2d.setPaint(new Color(0, 46, 77));
            Vector2d position = animal.getPosition();
            Ellipse2D circle = new Ellipse2D.Double((position.x - map.getBottomLeft().x) * multWidth, (position.y - map.getBottomLeft().y) * multHeight, multWidth, multHeight);
            g2d.fill(circle);
        }
    }
}
