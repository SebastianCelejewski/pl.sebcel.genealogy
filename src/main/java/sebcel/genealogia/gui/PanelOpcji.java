package sebcel.genealogia.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelOpcji extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    private OpcjeRysowania opcjeRysowania = new OpcjeRysowania();

    private JButton buttonZamknij = new JButton("Zamknij");
    private JButton buttonApply = new JButton("Zastosuj");
    private JCheckBox checkBoxShowIds = new JCheckBox();
    private JCheckBox checkBoxShowBirthData = new JCheckBox();
    private JCheckBox checkBoxShowDeathData = new JCheckBox();
    private JCheckBox checkBoxShowOccupation = new JCheckBox();
    private JCheckBox checkBoxShowLocation = new JCheckBox();
    private JCheckBox checkBoxShowMeetingInfo = new JCheckBox();
    private JCheckBox checkBoxShowMarriageInfo = new JCheckBox();
    private JCheckBox checkBoxShowSeparationInfo = new JCheckBox();
    private JCheckBox checkBoxShowDivorceInfo = new JCheckBox();
    private ZoomPanel zoomPanel = new ZoomPanel();

    private IDrawOptionsListener drawOptionsListener;

    public PanelOpcji() {
        this.setLayout(new BorderLayout());
        this.setSize(600, 400);
        this.setLocation(100, 100);
        this.setTitle("Opcje rysowania drzew");
        JPanel buttonPanel = new JPanel();
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridBagLayout());
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.add(optionsPanel, BorderLayout.CENTER);
        buttonPanel.add(buttonApply);
        buttonPanel.add(buttonZamknij);

        optionsPanel.add(new JLabel("Identyfikatory"), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1));
        optionsPanel.add(checkBoxShowIds, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1));
        optionsPanel.add(new JLabel("Dane o urodzeniu"), new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1));
        optionsPanel.add(checkBoxShowBirthData, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1));
        optionsPanel.add(new JLabel("Dane o œmierci"), new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1));
        optionsPanel.add(checkBoxShowDeathData, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1));
        optionsPanel.add(new JLabel("Dane o zawodzie"), new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1));
        optionsPanel.add(checkBoxShowOccupation, new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1));
        optionsPanel.add(new JLabel("Dane o zamieszkaniu"), new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1));
        optionsPanel.add(checkBoxShowLocation, new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1));
        optionsPanel.add(new JLabel("Dane o poznaniu siê"), new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1));
        optionsPanel.add(checkBoxShowMeetingInfo, new GridBagConstraints(1, 5, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1));
        optionsPanel.add(new JLabel("Dane o ma³zeñstwie"), new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1));
        optionsPanel.add(checkBoxShowMarriageInfo, new GridBagConstraints(1, 6, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1));
        optionsPanel.add(new JLabel("Dane o rozstaniu siê"), new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1));
        optionsPanel.add(checkBoxShowSeparationInfo, new GridBagConstraints(1, 7, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1));
        optionsPanel.add(new JLabel("Dane o rozwodzie"), new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1));
        optionsPanel.add(checkBoxShowDivorceInfo, new GridBagConstraints(1, 8, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1));
        optionsPanel.add(new JLabel("Powiêkszenie"), new GridBagConstraints(0, 9, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1));
        optionsPanel.add(zoomPanel, new GridBagConstraints(1, 9, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1));

        checkBoxShowIds.addActionListener(this);
        checkBoxShowBirthData.addActionListener(this);
        checkBoxShowDeathData.addActionListener(this);
        checkBoxShowOccupation.addActionListener(this);
        checkBoxShowLocation.addActionListener(this);
        checkBoxShowMeetingInfo.addActionListener(this);
        checkBoxShowMarriageInfo.addActionListener(this);
        checkBoxShowSeparationInfo.addActionListener(this);
        checkBoxShowDivorceInfo.addActionListener(this);
        zoomPanel.addActionListener(this);

        buttonZamknij.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelOpcji.this.setVisible(false);
            }
        });

        buttonApply.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("!");
                drawOptionsListener.updateDrawing(opcjeRysowania);
            }
        });

        this.pack();
    }

    public void setDrawOptionsListener(IDrawOptionsListener drawOptionsListener) {
        this.drawOptionsListener = drawOptionsListener;
    }

    public OpcjeRysowania getOpcjeRysowania() {
        return opcjeRysowania;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        opcjeRysowania.setPokazId(checkBoxShowIds.isSelected());
        opcjeRysowania.setPokazDaneUrodzenia(checkBoxShowBirthData.isSelected());
        opcjeRysowania.setPokazDaneSmierci(checkBoxShowDeathData.isSelected());
        opcjeRysowania.setPokazDaneZamieszkania(checkBoxShowLocation.isSelected());
        opcjeRysowania.setPokazWyksztalcenie(checkBoxShowOccupation.isSelected());
        opcjeRysowania.setPokazDanePoznaniaSie(checkBoxShowMeetingInfo.isSelected());
        opcjeRysowania.setPokazDaneSlubu(checkBoxShowMarriageInfo.isSelected());
        opcjeRysowania.setPokazDaneRozstaniaSie(checkBoxShowSeparationInfo.isSelected());
        opcjeRysowania.setPokazDaneRozwodu(checkBoxShowDivorceInfo.isSelected());
        opcjeRysowania.setZoom(zoomPanel.getZoom());
    }
}