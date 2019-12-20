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

    public MapPanel(int width, int height, WorldMap map)
    {
        this.width = width;
        this.height = height;
        this.map = map;
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
        g2d.setPaint(new Color(0, 51, 0));
        g2d.fill(jungle);

        g2d.setPaint(new Color(0, 255, 0));
        for (Vector2d position : map.getPlantsToVisualization())
        {
            Rectangle2D grass = new Rectangle2D.Double((position.x - map.getBottomLeft().x) * multWidth, (position.y - map.getBottomLeft().y) * multHeight, multWidth, multHeight);
            g2d.fill(grass);
        }

        for (Animal animal : map.getAnimalsToVisualization())
        {
            if (animal.getEnergy() < map.getStartEnergy())
                g2d.setPaint(new Color(108, 108, 147));
            else if (animal.getEnergy() < 2 * map.getStartEnergy())
                g2d.setPaint(new Color(70, 70, 185));
            else if (animal.getEnergy() < 4 * map.getStartEnergy())
                g2d.setPaint(new Color(32, 32, 223));
            else
                g2d.setPaint(new Color(0, 0, 255));
            Vector2d position = animal.getPosition();
            Ellipse2D circle = new Ellipse2D.Double((position.x - map.getBottomLeft().x) * multWidth, (position.y - map.getBottomLeft().y) * multHeight, multWidth, multHeight);
            g2d.fill(circle);
        }
    }
}
