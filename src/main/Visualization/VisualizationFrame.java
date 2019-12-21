import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Integer.*;

public class VisualizationFrame extends JFrame implements ActionListener
{
    private WorldMap map;
    private JButton startStopButton;
    private JButton nextDayButton;
    private JButton fasterButton;
    private JButton slowerButton;
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
            width = 600;
            height = (int)(ratio * width);
        }
        else
        {
            height = 600;
            width = (int)(height / ratio);
        }
        width += 20;
        height += 100;

        setSize(max((int)((height - 90) / ratio), 450), height);

        startStopButton = new JButton("Start/Stop");
        startStopButton.setSize(100, 40);
        startStopButton.setLocation(10, height - 90);
        startStopButton.addActionListener(this);
        add(startStopButton);

        nextDayButton = new JButton("+Dzień");
        nextDayButton.setSize(100, 40);
        nextDayButton.setLocation(120, height - 90);
        nextDayButton.addActionListener(this);
        add(nextDayButton);

        fasterButton = new JButton("+");
        fasterButton.setSize(50, 40);
        fasterButton.setLocation(230, height - 90);
        fasterButton.addActionListener(this);
        add(fasterButton);

        slowerButton = new JButton("-");
        slowerButton.setSize(50, 40);
        slowerButton.setLocation(290, height - 90);
        slowerButton.addActionListener(this);
        add(slowerButton);

        mapPanel = new MapPanel(width - 40, height - 120, map);
        mapPanel.setSize(width - 20, height - 100);
        mapPanel.setLocation(10, 10);
        mapPanel.setTresholdEnergy((map.getStartEnergy() + map.getPlantEnergy()) / 2);
        add(mapPanel);

        timer = new Timer(delay, e -> simulateDay());

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();
        if (source == startStopButton)
        {
            if (timer.isRunning())
                timer.stop();
            else
                timer.restart();
        }
        else if (source == nextDayButton)
        {
            simulateDay();
        }
        else if (source == fasterButton)
        {
            timer.restart();
            timer.setDelay(Math.max(timer.getDelay() / 2, 1));
        }
        else
        {
            timer.setDelay(Math.min(timer.getDelay() * 2, MAX_VALUE / 2));
        }
    }

    private void simulateDay()
    {
        map.daySimulation();
        mapPanel.repaint();
    }

}
