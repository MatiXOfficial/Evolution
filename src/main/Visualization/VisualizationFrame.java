import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

import static java.lang.Integer.max;

public class VisualizationFrame extends JFrame implements ActionListener
{
    private WorldMap map;
    private JButton startButton;
    private JButton stopButton;
    private JButton nextDayButton;
    private MapPanel mapPanel;
    private Timer timer;
    private int delay;

    public VisualizationFrame(WorldMap map, int delay)
    {
        super("Symulacja świata zwierząt");
        setLocation(300, 90);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);

        this.map = map;
        this.delay = delay;
        int mapWidth = map.getWidth();
        int mapHeight = map.getHeight();
        double ratio = (double)mapHeight / mapWidth;

        int height, width;
        if (mapHeight < mapWidth)
        {
            width = 800;
            height = (int)(ratio * width);
        }
        else
        {
            height = 800;
            width = (int)(height / ratio);
        }
        width += 20;
        height += 100;

        setSize(max((int)((height - 90) / ratio), 450), height);

        startButton = new JButton("Start");
        startButton.setSize(100, 40);
        startButton.setLocation(10, height - 90);
        startButton.addActionListener(this);
        add(startButton);

        stopButton = new JButton("Stop");
        stopButton.setSize(100, 40);
        stopButton.setLocation(120, height - 90);
        stopButton.addActionListener(this);
        add(stopButton);

        nextDayButton = new JButton("+Dzień");
        nextDayButton.setSize(100, 40);
        nextDayButton.setLocation(230, height - 90);
        nextDayButton.addActionListener(this);
        add(nextDayButton);

        mapPanel = new MapPanel(width - 40, height - 120, map);
        mapPanel.setSize(width - 20, height - 100);
        mapPanel.setLocation(10, 10);
        add(mapPanel);

        timer = new Timer(delay, e -> {
            Object source = e.getSource();
            simulateDay();
            if (source == stopButton)
            {
                timer.stop();
            }
        });

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();
        if (source == startButton)
        {
            timer.restart();
        }
        else if (source == stopButton)
        {
            timer.stop();
        }
        else
        {
            simulateDay();
        }
    }

    private void simulateDay()
    {
        map.daySimulation();
        mapPanel.repaint();
    }

}
