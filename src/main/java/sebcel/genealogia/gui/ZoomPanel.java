package sebcel.genealogia.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class ZoomPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private JRadioButton zoom1Button = new JRadioButton();
    private JRadioButton zoom2Button = new JRadioButton();
    private JRadioButton zoom3Button = new JRadioButton();

    private int zoom = 1;

    private ActionListener actionListener;

    public ZoomPanel() {
        this.add(new JLabel("Zoom: "));
        this.add(zoom1Button);
        this.add(zoom2Button);
        this.add(zoom3Button);
        zoom1Button.setSelected(true);

        zoom1Button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                zoom = 1;
                zoom2Button.setSelected(false);
                zoom3Button.setSelected(false);
                notifyListeners();
            }
        });

        zoom2Button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                zoom = 2;
                zoom1Button.setSelected(false);
                zoom3Button.setSelected(false);
                notifyListeners();
            }
        });

        zoom3Button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                zoom = 3;
                zoom2Button.setSelected(false);
                zoom1Button.setSelected(false);
                notifyListeners();
            }
        });
    }

    public void addActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }

    private void notifyListeners() {
        if (actionListener != null) {
            actionListener.actionPerformed(new ActionEvent(this, zoom, "Zoom changed to " + zoom));
        }
    }

    public int getZoom() {
        return zoom;
    }
}